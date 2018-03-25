package com.home.webapp.storage;

import com.home.webapp.exception.ExistStorageException;
import com.home.webapp.exception.NotExistStorageException;
import com.home.webapp.exception.StorageException;
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

            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(*) FROM resume WHERE uuid=?")) {
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                if (Integer.valueOf(resultSet.getString(1)) > 0) {
                    throw new ExistStorageException(uuid);
                }
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                preparedStatement.setString(1, uuid);
                preparedStatement.setString(2, r.getFullName());
                preparedStatement.execute();

            }

        } catch (SQLException e) {
            throw new StorageException("Error saving resume", "", e);
        }
    }

    @Override
    public Resume get(String uuid) {
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM resume r WHERE r.uuid = ?")) {
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, resultSet.getString("full_name"));
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
        int size = size();
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM resume")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String uuid = resultSet.getString("uuid");
                String fullName = resultSet.getString("full_name");
                Resume resume = new Resume(uuid.trim(), fullName);
                set.add(resume);
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

