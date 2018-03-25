package com.home.webapp.storage;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SortedArrayStorageTest.class,
        ArrayStorageTest.class,
        ListStorageTest.class,
        MapUuidStorageTest.class,
        MapResumeStorageTest.class,
        ObjectStreamFileStorageTest.class,
        ObjectStreamPathStorageTest.class,
        XmlStreamPathStorageTest.class,
        JsonStreamPathStorageTest.class,
        DataStreamPathStorageTest.class,
        SqlStorageTest.class
})

public class AllStorageTest {
}
