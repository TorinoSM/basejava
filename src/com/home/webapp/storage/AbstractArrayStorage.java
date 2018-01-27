package com.home.webapp.storage;

import com.home.webapp.exception.IllegalArgumentException;
import com.home.webapp.exception.NotExistStorageException;
import com.home.webapp.exception.StorageException;
import com.home.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected static final int STORAGE_LIMIT = 10000;
    protected int size = 0;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];


    protected void checkStorageOverflow(Resume r) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Error: Save: Can't save resume: reached maximum capacity of the storage (" + STORAGE_LIMIT + " records)", r.getUuid());
        }
    }

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    protected void updateElement(Resume updatedResume, int index) {
        storage[index] = updatedResume;
    }

    protected Resume getElement(int index){
        return storage[index];
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected abstract int getIndex(String uuid);

    protected abstract void insertElement(Resume resume, int index);

    protected abstract void deleteElement(int index);


}
