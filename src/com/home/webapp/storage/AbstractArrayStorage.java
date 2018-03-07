package com.home.webapp.storage;

import com.home.webapp.exception.StorageException;
import com.home.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {

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
    protected void updateElement(Resume updatedResume, Integer index) {
        storage[index] = updatedResume;
    }

    @Override
    protected void deleteElement(Integer index) {
        deleteElementOfArray(index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void saveElement(Resume resume, Integer index) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Error: Save: Can't save resume: reached maximum capacity of the storage (" + STORAGE_LIMIT + " records)", resume.getUuid());
        }
        insertElement(resume, index);
        size++;
    }

    @Override
    protected Resume getElement(Integer index) {
        return storage[index];
    }

    @Override
    public List<Resume> getResumesList() {
        return new ArrayList<>(Arrays.asList(Arrays.copyOfRange(storage, 0, size)));
    }

    @Override
    protected boolean isExist(Integer index) {
        return index >= 0;
    }

    protected abstract Integer getSearchKey(String uuid);

    protected abstract void insertElement(Resume resume, int index);

    protected abstract void deleteElementOfArray(int index);


}
