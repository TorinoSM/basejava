package com.home.webapp.storage;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SortedArrayStorage.class,
        ArrayStorage.class,
        ListStorage.class,
        MapUuidStorage.class,
        MapResumeStorage.class
})

public class AllStorageTest {
}
