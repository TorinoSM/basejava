package com.home.webapp.storage;

import com.home.webapp.storage.serializer.DataStreamSerializer;

public class DataStreamPathStorageTest extends AbstractStorageTest {

    public DataStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new DataStreamSerializer()));
    }
}