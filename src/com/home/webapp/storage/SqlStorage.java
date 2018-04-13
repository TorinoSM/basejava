package com.home.webapp.storage;

import com.home.webapp.exception.ExistStorageException;
import com.home.webapp.exception.NotExistStorageException;
import com.home.webapp.exception.StorageException;
import com.home.webapp.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.home.webapp.model.SectionType.*;

public class SqlStorage implements Storage {

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path");
            e.printStackTrace();
        }
        System.out.println("PostgreSQL JDBC Driver successfully registered");
    }

    private final String dbUrl, dbUser, dbPassword;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    @Override
    public void delete(String uuid) {
        doExecute("DELETE FROM resume WHERE uuid=?", "Error deleting resume", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            preparedStatement.execute();
            if (preparedStatement.getUpdateCount() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public void clear() {
        doExecute("DELETE FROM resume", "Clear DB error", preparedStatement -> {
            preparedStatement.execute();
            return null;
        });
    }

    @Override
    public void update(Resume updatedResume) { // transactionalExecute
        if (updatedResume == null) {
            throw new IllegalArgumentException("Resume to be updated is null");
        }
        String uuid = updatedResume.getUuid();

        transactionalExecute("Error updating resume", new TransactionalSqlExecutor<Void>() {
            @Override
            public Void execute(Connection connection) throws SQLException {
                try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(*) FROM resume WHERE uuid=?")) {
                    preparedStatement.setString(1, uuid);
                    preparedStatement.execute();
                    ResultSet resultSet = preparedStatement.getResultSet();
                    resultSet.next();
                    if (resultSet.getInt(1) == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    delete(updatedResume.getUuid());
                    save(updatedResume);
                }
                return null;
            }
        });
    }


    @Override
    public void save(Resume resume) { // transactionalExecute
        if (resume == null) {
            throw new IllegalArgumentException("Resume is null");
        }
        String uuid = resume.getUuid();
        transactionalExecute("Error saving resume", new TransactionalSqlExecutor<Void>() {
            @Override
            public Void execute(Connection connection) throws SQLException {
                try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(*) FROM resume WHERE uuid=?")) {

                    preparedStatement.setString(1, uuid);
                    preparedStatement.execute();
                    ResultSet resultSet = preparedStatement.getResultSet();
                    resultSet.next();
                    if (Integer.valueOf(resultSet.getString(1)) > 0) {
                        throw new ExistStorageException(uuid);
                    }
                }

                try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {

                    preparedStatement.setString(1, uuid);
                    preparedStatement.setString(2, resume.getFullName());
                    preparedStatement.execute();
                }

                try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)")) {
                    for (ContactType contactType : ContactType.values()) { // бежим по всем типам контактов (пустым и не пустым)
                        String contactValue = resume.getContact(contactType);
                        if (contactValue != null) { // вставляем только непустые контакты
                            preparedStatement.setString(1, uuid);
                            preparedStatement.setString(2, contactType.toString());
                            preparedStatement.setString(3, contactValue);
                            preparedStatement.addBatch();
                        }
                    }
                    preparedStatement.executeBatch();
                }

                try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO section (resume_uuid, type, content) VALUES (?, ?, ?)")) {

                    preparedStatement.setString(1, uuid);

                    Map<SectionType, Section> sections = resume.getSections();
                    for (Map.Entry<SectionType, Section> sectionEntry : sections.entrySet()) {
                        SectionType sectionType = sectionEntry.getKey();
                        Section section = sectionEntry.getValue();
                        if (sectionType.equals(ACHIEVEMENT) || sectionType.equals(QUALIFICATIONS)) {
                            String content = "";
                            ListSection section0 = (ListSection) section;
                            List<String> items = section0.getItems();
                            Iterator iterator = items.iterator();
                            while (iterator.hasNext()) { // объединяем все строки в одну, разделяя их символом /n
                                content += iterator.next();
                                if (iterator.hasNext()) {
                                    content += "\n";
                                } else {
                                    break; // чтобы не делать лишнюю проверку в while на последней итерации
                                }
                            }
                            preparedStatement.setString(2, sectionType.toString());
                            preparedStatement.setString(3, content);
                            preparedStatement.addBatch();
                            continue;
                        }

                        if (sectionType.equals(PERSONAL) || sectionType.equals(OBJECTIVE)) {
                            TextSection section0 = (TextSection) section;
                            preparedStatement.setString(2, sectionType.toString());
                            preparedStatement.setString(3, section0.getContent());
                            preparedStatement.addBatch();
                            continue;
                        }

                        if (sectionType.equals(SectionType.EXPERIENCE) || sectionType.equals(SectionType.EDUCATION)) {
                            OrganizationSection section0 = (OrganizationSection) section;
                            preparedStatement.setString(2, sectionType.toString());
                            preparedStatement.setString(3, "");

                            preparedStatement.execute(); // выполнили здесь потому что в следующем запросе нужен будет реальный id секции

                            Integer section_id = 0;
                            try (PreparedStatement preparedStatement1 = connection.prepareStatement
                                    ("SELECT id FROM section ORDER BY id DESC LIMIT 1;")) { // последняя вставленная запись
                                preparedStatement1.execute();
                                ResultSet resultSet = preparedStatement1.getResultSet();
                                if (resultSet.next()) {
                                    do {
                                        section_id = resultSet.getInt("id");
                                    } while (resultSet.next());
                                }
                            }

                            try (PreparedStatement preparedStatement1 = connection.prepareStatement
                                    ("INSERT INTO organisation (name, url, section_id) VALUES (?,?,?)")) {

                                preparedStatement1.setInt(3, section_id);

                                List<Organization> organizations = section0.getOrganizations();
                                for (Organization organization : organizations) {
                                    Link orgLink = organization.getOrganisation();
                                    preparedStatement1.setString(1, orgLink.getName());
                                    preparedStatement1.setString(2, orgLink.getUrl());
                                    preparedStatement1.execute();

                                    Integer organisation_id = 0;
                                    try (PreparedStatement preparedStatement2 = connection.prepareStatement
                                            ("SELECT id FROM organisation ORDER BY id DESC LIMIT 1")) {
                                        preparedStatement2.execute();
                                        ResultSet resultSet = preparedStatement2.getResultSet();
                                        if (resultSet.next()) {
                                            do {
                                                organisation_id = resultSet.getInt("id");

                                            } while (resultSet.next());
                                        }
                                    }

                                    try (PreparedStatement preparedStatement2 = connection.prepareStatement
                                            ("INSERT INTO record (title, description, start_date, end_date, organisation_id) VALUES (?,?,?,?,?)")) {

                                        preparedStatement2.setInt(5, organisation_id);
                                        List<Organization.Record> records = organization.getRecords();

                                        for (Organization.Record record : records) {
                                            preparedStatement2.setString(1, record.getTitle());
                                            preparedStatement2.setString(2, record.getDescription());
                                            preparedStatement2.setObject(3, record.getStartDate());
                                            preparedStatement2.setObject(4, record.getEndtDate());
                                            preparedStatement2.addBatch();
                                        }
                                        preparedStatement2.executeBatch();
                                    }
                                }
                            }
                            continue;
                        }
                    }
                    preparedStatement.executeBatch();
                }
                return null;
            }
        });
    }


    @Override
    public Resume get(String uuid) {
        return doExecute("SELECT * FROM resume AS r " +
                "LEFT JOIN contact AS c " +
                "ON r.uuid=c.resume_uuid " +
                "WHERE r.uuid=?", "Error while getting resume from DB", (connection, preparedStatement) -> {
            preparedStatement.setString(1, uuid);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }

            Resume resume = new Resume(uuid, resultSet.getString("full_name"));

            do {
                String contactType = resultSet.getString("type");
                if (contactType == null) {
                    break; // если контактов нет, то прерываем цикл
                }
                resume.addContact(ContactType.valueOf(contactType), resultSet.getString("value"));
            } while (resultSet.next());

            try (PreparedStatement preparedStatement1 = connection.prepareStatement
                    ("SELECT * FROM section WHERE resume_uuid=?")) {

                preparedStatement1.setString(1, uuid);
                preparedStatement1.execute();
                ResultSet resultSet1 = preparedStatement1.getResultSet();

                if (resultSet1.next()) {
                    do {
                        String sectionType = resultSet1.getString("type");
                        if (sectionType == null) {
                            break; // если секций нет, то прерываем цикл
                        }

                        if (SectionType.valueOf(sectionType).equals(OBJECTIVE) ||
                                SectionType.valueOf(sectionType).equals(PERSONAL)) {

                            TextSection section = new TextSection(resultSet1.getString("content"));
                            resume.addSection(SectionType.valueOf(sectionType), section);
                            continue;
                        }

                        if (SectionType.valueOf(sectionType).equals(ACHIEVEMENT) ||
                                SectionType.valueOf(sectionType).equals(QUALIFICATIONS)) {


                            String content = resultSet1.getString("content");
                            String[] list = content.split("\n");
                            ListSection section = new ListSection(list);
                            resume.addSection(SectionType.valueOf(sectionType), section);
                            continue;
                        }

                        Integer section_id = resultSet1.getInt("id");
                        if (SectionType.valueOf(sectionType).equals(EXPERIENCE) ||
                                SectionType.valueOf(sectionType).equals(EDUCATION)) {

                            try (PreparedStatement preparedStatement2 = connection.prepareStatement
                                    ("SELECT * FROM organisation WHERE section_id=?")) {

                                preparedStatement2.setInt(1, section_id);
                                preparedStatement2.execute();
                                ResultSet resultSet2 = preparedStatement2.getResultSet(); // организации
                                List<Organization> organizations = new ArrayList<>();

                                if (resultSet2.next()) {
                                    do {
                                        Integer organisation_id = resultSet2.getInt("id");
                                        List<Organization.Record> records = new ArrayList<>();

                                        try (PreparedStatement preparedStatement3 = connection.prepareStatement
                                                ("SELECT * FROM record WHERE organisation_id=?")) {

                                            preparedStatement3.setInt(1, organisation_id);
                                            preparedStatement3.execute();
                                            ResultSet resultSet3 = preparedStatement3.getResultSet(); // список записей

                                            if (resultSet3.next()) {
                                                do {
                                                    String title = resultSet3.getString("title");
                                                    String description = resultSet3.getString("description");
                                                    LocalDate start_date = resultSet3.getObject("start_date", LocalDate.class);
                                                    LocalDate end_date = resultSet3.getObject("end_date", LocalDate.class);
                                                    Organization.Record record = new Organization.Record(start_date, end_date, title, description);
                                                    records.add(record);
                                                } while (resultSet3.next()); // records
                                            } else System.out.println("Empty ResultSet while Selecting from Records");
                                        }

                                        String name = resultSet2.getString("name");
                                        String url = resultSet2.getString("url");

                                        Organization organization = new Organization(name, url, records);
                                        organizations.add(organization);

                                    } while (resultSet2.next()); // organizations

                                    OrganizationSection section = new OrganizationSection(organizations);
                                    resume.addSection(SectionType.valueOf(sectionType), section);

                                } else System.out.println("Empty ResultSet while Selecting from Organisation");
                            }
                        }
                    } while (resultSet1.next()); // organization sections
                }
            }

            return resume;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        Set<Resume> set = new TreeSet<>();
        return doExecute("SELECT uuid FROM resume", "Error while executing getAllSorted() method", (PreparedStatement preparedStatement) -> {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                do {
                    set.add(get(resultSet.getString("uuid").trim()));
                } while (resultSet.next());
            }
            return set.stream().collect(Collectors.toList());
        });
    }

    @Override
    public int size() {
        return doExecute("SELECT count(*) FROM resume", "Error while counting of DB records", (PreparedStatement preparedStatement) -> {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            resultSet.next();
            String count = resultSet.getString(1);
            return Integer.valueOf(count);
        });
    }

    private <T> T doExecute(String query, String errorMessage, StatementSqlExecutor<T> executor) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return executor.execute(preparedStatement);
        } catch (SQLException e) {
            throw new StorageException(errorMessage, "", e);
        }
    }

    private <T> T transactionalExecute(String errorMessage, TransactionalSqlExecutor<T> executor) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            connection.setAutoCommit(false);
            T result = executor.execute(connection);
            connection.commit();
            return result;
        } catch (SQLException e) {
            throw new StorageException(errorMessage, "", e);
        }
    }

    private <T> T doExecute(String query, String errorMessage, DoubleSqlExecutor<T> executor) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            return executor.execute(connection, preparedStatement);
        } catch (SQLException e) {
            throw new StorageException(errorMessage, "", e);
        }
    }

    interface StatementSqlExecutor<T> {
        T execute(PreparedStatement preparedStatement) throws SQLException;
    }

    interface TransactionalSqlExecutor<T> {
        T execute(Connection connection) throws SQLException;
    }

    interface DoubleSqlExecutor<T> {
        T execute(Connection connection, PreparedStatement preparedStatement) throws SQLException;
    }
}

