package com.home.webapp.storage;

import com.home.webapp.Config;

public class SqlStorageTest extends AbstractStorageTest {

    private static Config config = Config.getInstance();
    private static String url = config.getDbUrl();
    private static String user = config.getDbUser();
    private static String password = config.getDbPassword();

    public SqlStorageTest() {
        super(new SqlStorage(url, user, password));
    }
}