package com.home.webapp;

import com.home.webapp.exception.StorageException;
import com.home.webapp.model.Resume;
import com.home.webapp.storage.ListStorage;
import com.home.webapp.storage.Storage;

/**
 * Test for com.urise.webapp.storage.com.home.webapp.storage.ArrayStorage
 */
public class MainTestListArrayStorage {
    private static final Storage ARRAY_STORAGE = new ListStorage();

    public static void main(String[] args) throws StorageException {
        Resume r1 = new Resume("uuid4");
        Resume r2 = new Resume("uuid1");
        Resume r3 = new Resume("uuid3");
        Resume r4 = new Resume("uuid2");
//        Resume r5 = new Resume("uuid4");


        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(r4);
//        ARRAY_STORAGE.save(r5);
//        ARRAY_STORAGE.save(null);

        ARRAY_STORAGE.delete(r3.getUuid());
        ARRAY_STORAGE.update(r1);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

//        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
