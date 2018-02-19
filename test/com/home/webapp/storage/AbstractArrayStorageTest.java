package com.home.webapp.storage;

import com.home.webapp.exception.StorageException;
import com.home.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {


    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveStorageOverflow() throws Exception {
        try {
            for (int i = storage.size(); i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume(String.valueOf(i + 1)));
            }
        } catch (Exception e) {  // любое checked исключение
            Assert.fail();
        }
        storage.save(new Resume("one_more_resume"));
    }

}
