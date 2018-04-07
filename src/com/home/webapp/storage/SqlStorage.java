package com.home.webapp.storage;

import com.home.webapp.exception.ExistStorageException;
import com.home.webapp.exception.NotExistStorageException;
import com.home.webapp.exception.StorageException;
import com.home.webapp.model.*;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class SqlStorage implements Storage {

    private final String dbUrl, dbUser, dbPassword;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    @Override
    public void clear() {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM resume")) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new StorageException("Clear DB error", "", e);
        }
    }

    @Override
    public void update(Resume updatedResume) {
        if (updatedResume == null) {
            throw new IllegalArgumentException("Resume to be updated is null");
        }
        String uuid = updatedResume.getUuid();
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(*) FROM resume WHERE uuid=?")) {
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                if (Integer.valueOf(resultSet.getString(1)) == 0) {
                    throw new NotExistStorageException(uuid);
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE resume SET full_name=? WHERE uuid=?")) {
                preparedStatement.setString(1, uuid);
                preparedStatement.setString(2, updatedResume.getFullName());
                preparedStatement.execute();
            }

            // TODO: добавить обновление контактов и секций


        } catch (SQLException e) {
            throw new StorageException("Error updating resume", "", e);
        }
    }


    @Override
    public void save(Resume resume) {
        if (resume == null) {
            throw new IllegalArgumentException("Resume is null");
        }
        String uuid = resume.getUuid();
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {

            connection.setAutoCommit(false);

            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(*) FROM resume WHERE uuid=?")) {

                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                connection.commit();
                resultSet.next();
                if (Integer.valueOf(resultSet.getString(1)) > 0) {
                    throw new ExistStorageException(uuid);
                }
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {

                preparedStatement.setString(1, uuid);
                preparedStatement.setString(2, resume.getFullName());
                preparedStatement.execute();
                connection.commit();
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
                connection.commit();
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO section (resume_uuid, type, content) VALUES (?, ?, ?)")) {

                preparedStatement.setString(1, uuid);

                Map<SectionType, Section> sections = resume.getSections();
                for (Map.Entry<SectionType, Section> sectionEntry : sections.entrySet()) {
                    SectionType sectionType = sectionEntry.getKey();
                    Section section = sectionEntry.getValue();
                    if (sectionType.equals(SectionType.ACHIEVEMENT) || sectionType.equals(SectionType.QUALIFICATIONS)) {
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

                    if (sectionType.equals(SectionType.PERSONAL) || sectionType.equals(SectionType.OBJECTIVE)) {
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
                        connection.commit();

                        Integer section_id = 0;
                        try (PreparedStatement preparedStatement1 = connection.prepareStatement
                                ("SELECT id FROM section ORDER BY id DESC LIMIT 1;")) { // последняя вставленная запись
                            ResultSet resultSet = preparedStatement1.executeQuery();
                            connection.commit();
                            while (resultSet.next()) {
                                section_id = resultSet.getInt("id");
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
                                connection.commit();

                                Integer organisation_id = 0;
                                try (PreparedStatement preparedStatement2 = connection.prepareStatement
                                        ("SELECT id FROM organisation ORDER BY id DESC LIMIT 1")) {
                                    ResultSet resultSet = preparedStatement2.executeQuery();
                                    connection.commit();
                                    while (resultSet.next()) {
                                        organisation_id = resultSet.getInt("id");
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
                                    connection.commit();
                                }
                            }
                        }
                        continue;
                    }
                }
                preparedStatement.executeBatch();
                connection.commit();
            }
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new StorageException("Error saving resume", "", e);
        }
    }

    @Override
    public Resume get(String uuid) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement("" +
                     "SELECT * FROM resume AS r " +
                     "LEFT JOIN contact AS c " +
                     "ON r.uuid=c.resume_uuid " +
                     "WHERE r.uuid=?")) {
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
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
            return resume;
        } catch (SQLException e) {
            throw new StorageException("Error while getting resume from DB", "", e);
        }
    }

    @Override
    public void delete(String uuid) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM resume WHERE uuid=?")) {
            preparedStatement.setString(1, uuid);
            preparedStatement.execute();
            if (preparedStatement.getUpdateCount() == 0) {
                throw new NotExistStorageException(uuid);
            }
        } catch (SQLException e) {
            throw new StorageException("Error deleting resume", "", e);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        Set<Resume> set = new TreeSet<>();
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT uuid FROM resume")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                set.add(get(resultSet.getString("uuid").trim()));
            }
            return set.stream().collect(Collectors.toList());
        } catch (SQLException e) {
            throw new StorageException("", "", e);
        }
    }

    @Override
    public int size() {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(*) FROM resume")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String count = resultSet.getString(1);
            return Integer.valueOf(count);
        } catch (SQLException e) {
            throw new StorageException("Error while counting of DB records", "", e);
        }
    }
}

