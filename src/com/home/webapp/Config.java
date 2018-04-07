package com.home.webapp;

import com.home.webapp.exception.StorageException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private static final File PROPS = new File("C:\\Users\\name2015\\basejava\\config\\resumes.properties");
    private final static Config INSTANCE = new Config();
    private String dbUrl;
    private String dbUser;
    private String dbPassword;
    private Properties properties = new Properties();
    private File storageDir;

    private Config() {
        try (InputStream fis = new FileInputStream(PROPS)) {
            properties.load(fis);
            storageDir = new File(properties.getProperty("storage.dir"));
            dbUrl = properties.getProperty("db.url");
            dbUser = properties.getProperty("db.user");
            dbPassword = properties.getProperty("db.password");
        } catch (IOException e) {
            throw new StorageException("Error handling propertie file" + PROPS.getAbsolutePath(), "", e);
        }
    }

    public static Config getInstance() {
        return INSTANCE;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public File getStorageDir() {
        return storageDir;
    }
}
