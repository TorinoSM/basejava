package com.home.webapp.storage;

import com.home.webapp.storage.serializer.ObjectStreamSerializerStrategy;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {

    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectStreamSerializerStrategy()));
    }
}