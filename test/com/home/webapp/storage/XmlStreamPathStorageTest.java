package com.home.webapp.storage;

import com.home.webapp.storage.serializer.XmlStreamSerializer;

public class XmlStreamPathStorageTest extends AbstractStorageTest {

    public XmlStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new XmlStreamSerializer()));
    }
}