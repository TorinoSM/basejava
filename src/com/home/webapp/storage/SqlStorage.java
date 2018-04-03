package com.home.webapp.storage;

import com.home.webapp.exception.ExistStorageException;
import com.home.webapp.exception.NotExistStorageException;
import com.home.webapp.exception.StorageException;
import com.home.webapp.model.ContactType;
import com.home.webapp.model.Resume;

import java.sql.*;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
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
        } catch (SQLException e) {
            throw new StorageException("Error updating resume", "", e);
        }
    }


    @Override
    public void save(Resume r) {
        if (r == null) {
            throw new IllegalArgumentException("Resume is null");
        }
        String uuid = r.getUuid();
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
                preparedStatement.setString(2, r.getFullName());
                preparedStatement.execute();
                connection.commit();
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)")) {
                for (ContactType contactType : ContactType.values()) { // бежим по всем типам контактов (пустым и не пустым)
                    String contactValue = r.getContact(contactType);
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



            // TODO: добавить секции

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

