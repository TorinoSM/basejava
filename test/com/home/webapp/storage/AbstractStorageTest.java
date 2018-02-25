package com.home.webapp.storage;

import com.home.webapp.exception.ExistStorageException;
import com.home.webapp.exception.IllegalArgumentException;
import com.home.webapp.exception.NotExistStorageException;
import com.home.webapp.model.Resume;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;


public abstract class AbstractStorageTest {

    protected Storage storage;
    private Resume ruuid1 = new Resume("uuid1", "name1");
    private Resume ruuid2 = new Resume("uuid2", "name2");
    private Resume ruuid3 = new Resume("uuid3", "name3");
    private Resume ruuid4 = new Resume("uuid4", "name4");
    private Resume ruuid5 = new Resume("uuid5", "name5");

    AbstractStorageTest(Storage storage) {
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

        storage.save(ruuid5);
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


    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("test_name");
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
        Assert.assertEquals(ruuid2, storage.get("uuid2"));
        Assert.assertEquals(ruuid3, storage.get("uuid3"));
        Assert.assertEquals(ruuid4, storage.get("uuid4"));
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
        storage.get("uuid_not_exists");
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(storage.size(), 4);
    }

    @Test()
    public void getAllSorted() throws Exception {

        List<Resume> list = storage.getAllSorted();
        Assert.assertEquals(list, Arrays.asList(ruuid1, ruuid2, ruuid3, ruuid4));
        Assert.assertEquals(4, list.size());
    }

}