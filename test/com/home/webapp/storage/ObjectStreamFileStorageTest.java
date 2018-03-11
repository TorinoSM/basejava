package com.home.webapp.storage;

import com.home.webapp.storage.serializer.ObjectStreamSerializerStrategy;

public class ObjectStreamFileStorageTest extends AbstractStorageTest {

    public ObjectStreamFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamSerializerStrategy()));
    }
}