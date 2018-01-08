package com.home.webapp;

import com.home.webapp.model.Resume;
import com.home.webapp.storage.ArrayStorage;
import com.home.webapp.storage.SortedArrayStorage;
import com.home.webapp.storage.Storage;

/**
 * Test for com.urise.webapp.storage.com.home.webapp.storage.ArrayStorage
 */
public class MainTestArrayStorage {
    private static final Storage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume();
        r1.setUuid("uuid4");
        Resume r2 = new Resume();
        r2.setUuid("uuid1");
        Resume r3 = new Resume();
        r3.setUuid("uuid3");
        Resume r4 = new Resume();
        r4.setUuid("uuid2");
        Resume r5 = new Resume();
        r5.setUuid("uuid4");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(r4);
        ARRAY_STORAGE.save(r5);

        ARRAY_STORAGE.delete(r3.getUuid());
        ARRAY_STORAGE.update(r1);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

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
