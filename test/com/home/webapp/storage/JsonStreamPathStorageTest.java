package com.home.webapp.storage;

import com.home.webapp.storage.serializer.JsonStreamSerializer;

public class JsonStreamPathStorageTest extends AbstractStorageTest {

    public JsonStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new JsonStreamSerializer()));
    }
}