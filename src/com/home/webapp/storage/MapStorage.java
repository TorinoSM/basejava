package com.home.webapp.storage;

import com.home.webapp.model.Resume;

public class MapStorage extends AbstractStorage{

    @Override
    public void clear() {

    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    protected Resume getElement(Object searchKey) {
        return null;
    }

    @Override
    protected void deleteElement(Object searchKey) {

    }

    @Override
    protected void updateElement(Resume updatedResume, Object searchKey) {

    }

    @Override
    protected Object getSearchKey(String uuid) {
        return null;
    }

    @Override
    protected void saveElement(Resume resume, Object searchKey) {

    }

    @Override
    protected boolean isExist(Object searchKey) {
        return false;
    }
}
