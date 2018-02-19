package com.home.webapp.storage;

import com.home.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> sortedList = new ArrayList<>();
        for (Map.Entry<String, Resume> entry : storage.entrySet()) {
            sortedList.add(entry.getValue());
        }
        return sortedList;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Resume getElement(Object searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void deleteElement(Object searchKey) {
        storage.remove(searchKey);
    }

    @Override
    protected void updateElement(Resume updatedResume, Object searchKey) {
        storage.put(searchKey.toString(), updatedResume);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected void saveElement(Resume resume, Object searchKey) {
        storage.put(searchKey.toString(), resume);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return storage.containsKey(searchKey);
    }
}
