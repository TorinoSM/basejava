package com.home.webapp.storage;

import com.home.webapp.model.Resume;

public class MapStorage extends AbstractStorage{
    @Override
    protected void updateElement(Resume updatedResume, int index) {

    }

    @Override
    public void clear() {

    }

    @Override
    public void update(Resume updatedResume) {

    }

    @Override
    public void save(Resume r) {

    }

    @Override
    protected void insertElement(Resume r, int index) {

    }

    @Override
    protected void checkStorageOverflow(Resume r) {

    }

    @Override
    protected int getIndex(String uuid) {
        return 0;
    }

    @Override
    public Resume get(String uuid) {
        return null;
    }

    @Override
    protected Resume getElement(int index) {
        return null;
    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    protected void deleteElement(int index) {

    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

    @Override
    public int size() {
        return 0;
    }
}
