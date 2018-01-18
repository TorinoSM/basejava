package com.home.webapp.storage;

import com.home.webapp.exception.ExistStorageException;
import com.home.webapp.exception.IllegalArgumentException;
import com.home.webapp.exception.NotExistStorageException;
import com.home.webapp.exception.StorageException;
import com.home.webapp.model.Resume;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public abstract class AbstractArrayStorageTest {

    private Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume("uuid1"));
        storage.save(new Resume("uuid3"));
        storage.save(new Resume("uuid2"));
        storage.save(new Resume("uuid4"));
    }

    @After
    public void tearDown() throws Exception {
        storage = null;
    }

    @Test
    public void saveNotExist() throws Exception {
        storage.save(new Resume("uuid5"));

        Class classOfActualStorage = storage.getClass();
        Storage expectedStorage = (Storage) classOfActualStorage.newInstance();
        expectedStorage.save(new Resume("uuid1"));
        expectedStorage.save(new Resume("uuid3"));
        expectedStorage.save(new Resume("uuid2"));
        expectedStorage.save(new Resume("uuid4"));
        expectedStorage.save(new Resume("uuid5"));

        Assert.assertArrayEquals(expectedStorage.getAll(), storage.getAll());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(new Resume("uuid1"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveNull() throws Exception {
        storage.save(null);
    }

    @Test(expected = StorageException.class)
    public void saveStorageOverflow() throws Exception {
        for (int i = 0; i < 10000; i++) {
            storage.save(new Resume("" + i));
        }
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("test_uuid");
    }

    @Test
    public void deleteExist() throws Exception {
        storage.delete("uuid1");

        Class classOfActualStorage = storage.getClass();
        Storage expectedStorage = (Storage) classOfActualStorage.newInstance();
        expectedStorage.save(new Resume("uuid4"));
        expectedStorage.save(new Resume("uuid3"));
        expectedStorage.save(new Resume("uuid2"));

        Assert.assertArrayEquals(expectedStorage.getAll(), storage.getAll());
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertTrue(storage.size() == 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateNull() throws Exception {
        storage.update(null);
    }

    @Test
    public void updateExist() throws Exception {
        storage.update(new Resume("uuid1"));

        Class classOfActualStorage = storage.getClass();
        Storage expectedStorage = (Storage) classOfActualStorage.newInstance();
        expectedStorage.save(new Resume("uuid1"));
        expectedStorage.save(new Resume("uuid3"));
        expectedStorage.save(new Resume("uuid2"));
        expectedStorage.save(new Resume("uuid4"));

        Assert.assertArrayEquals(expectedStorage.getAll(), storage.getAll());
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(new Resume("uuid5"));
    }

    @Test
    public void getExist() throws Exception {
        Resume resume = storage.get("uuid1");
        Assert.assertTrue(new Resume("uuid1").equals(resume));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("uuid5");
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(storage.size(), 4);
    }

    @Test
    public void getAll() throws Exception {

        Class classOfActualStorage = storage.getClass();
        Storage expectedStorage = (Storage) classOfActualStorage.newInstance();
        expectedStorage.save(new Resume("uuid1"));
        expectedStorage.save(new Resume("uuid3"));
        expectedStorage.save(new Resume("uuid2"));
        expectedStorage.save(new Resume("uuid4"));

        Assert.assertArrayEquals(expectedStorage.getAll(), storage.getAll());
    }

}