package com.home.webapp.storage;

import com.home.webapp.storage.serializer.ObjectStreamSerializer;

public class ObjectStreamFileStorageTest extends AbstractStorageTest {

    public ObjectStreamFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamSerializer()));
    }
}