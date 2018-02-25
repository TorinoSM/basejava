package com.home.webapp.storage;

import com.home.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

    private final List<Resume> storage = new ArrayList<>();


    @Override
    public void clear() {
        storage.clear();
    }

    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) return i; // uuid found
        }
        return null; // uuid not found
    }

    @Override
    protected void saveElement(Resume resume, Integer index) {
        storage.add(resume);
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey != null;
    }

    @Override
    protected void updateElement(Resume updatedResume, Integer index) {
        storage.set(index, updatedResume);
    }

    @Override
    protected Resume getElement(Integer index) {
        return storage.get(index);
    }

    @Override
    protected void deleteElement(Integer index) {
        storage.remove((index).intValue());
    }

    @Override
    public List<Resume> getResumesArrayCopy() {
        return new ArrayList<>(storage);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
