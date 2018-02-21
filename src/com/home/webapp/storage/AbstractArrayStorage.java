package com.home.webapp.storage;

import com.home.webapp.exception.StorageException;
import com.home.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected static final int STORAGE_LIMIT = 10000;
    protected int size = 0;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }


    @Override
    protected void updateElement(Resume updatedResume, Object index) {
        storage[(Integer) index] = updatedResume;
    }

    @Override
    protected void deleteElement(Object index) {
        deleteElementOfArray((Integer) index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void saveElement(Resume resume, Object index) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Error: Save: Can't save resume: reached maximum capacity of the storage (" + STORAGE_LIMIT + " records)", resume.getUuid());
        }
        insertElement(resume, (Integer) index);
        size++;
    }

    @Override
    protected Resume getElement(Object index) {
        return storage[(Integer) index];
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */

    @Override
    public List<Resume> getResumesArrayCopy() {
        return new ArrayList<>(Arrays.asList(Arrays.copyOfRange(storage, 0, size)));
    }

    @Override
    protected boolean isExist(Object index) {
        return (Integer) index >= 0;
    }

    protected abstract Integer getSearchKey(String uuid);

    protected abstract void insertElement(Resume resume, int index);

    protected abstract void deleteElementOfArray(int index);


}
