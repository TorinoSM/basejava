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

    Resume ruuid1 = new Resume("uuid1");
    Resume ruuid2 = new Resume("uuid2");
    Resume ruuid3 = new Resume("uuid3");
    Resume ruuid4 = new Resume("uuid4");
    Resume ruuid5 = new Resume("uuid5");
    Resume ruuid6 = new Resume("uuid6");
    private Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(ruuid1);
        storage.save(ruuid3);
        storage.save(ruuid2);
        storage.save(ruuid4);
    }

    @After
    public void tearDown() throws Exception {
        storage = null;
    }

    @Test()
    public void saveNotExist() throws Exception {

        storage.save(new Resume("uuid5"));
        Assert.assertEquals(ruuid1, storage.get("uuid1"));
        Assert.assertEquals(ruuid2, storage.get("uuid2"));
        Assert.assertEquals(ruuid3, storage.get("uuid3"));
        Assert.assertEquals(ruuid4, storage.get("uuid4"));
        Assert.assertEquals(ruuid5, storage.get("uuid5"));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(ruuid1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveNull() throws Exception {
        storage.save(null);
    }

    @Test(expected = StorageException.class)
    public void saveStorageOverflow() throws Exception {
        for (int i = storage.size(); i < 10000; i++) {
            storage.save(new Resume(String.valueOf(i + 1)));
        }
        storage.save(new Resume("one_more_resume"));
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("test_uuid");
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteExist() throws Exception {
        storage.delete("uuid1");

        Assert.assertEquals(ruuid2, storage.get("uuid2"));
        Assert.assertEquals(ruuid3, storage.get("uuid3"));
        Assert.assertEquals(ruuid4, storage.get("uuid4"));

        storage.get("uuid1");
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
        storage.update(ruuid1);

        Assert.assertEquals(ruuid1, storage.get("uuid1"));
        Assert.assertEquals(ruuid2, storage.get("uuid2"));  // 98
        Assert.assertEquals(ruuid3, storage.get("uuid3"));  // 99
        Assert.assertEquals(ruuid4, storage.get("uuid4"));  //100
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(ruuid5);
    }

    @Test
    public void getExist() throws Exception {
        Resume resume = storage.get("uuid1");
        Assert.assertTrue(ruuid1.equals(resume));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("uuid10");
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(storage.size(), 4);
    }

    @Test()
    public void getAll() throws Exception {

        Assert.assertEquals(ruuid1, storage.get("uuid1"));
        Assert.assertEquals(ruuid2, storage.get("uuid2"));
        Assert.assertEquals(ruuid3, storage.get("uuid3"));
        Assert.assertEquals(ruuid4, storage.get("uuid4"));
        Assert.assertEquals(4, storage.size());
    }

}