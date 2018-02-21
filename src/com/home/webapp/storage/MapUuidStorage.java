package com.home.webapp.storage;

import com.home.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage {

    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getResumesArrayCopy() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Resume getElement(Object uuid) {
        return storage.get(uuid.toString());
    }

    @Override
    protected void deleteElement(Object uuid) {
        storage.remove(uuid.toString());
    }

    @Override
    protected void updateElement(Resume updatedResume, Object uuid) {
        storage.put(uuid.toString(), updatedResume);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected void saveElement(Resume resume, Object uuid) {
        storage.put(uuid.toString(), resume);
    }

    @Override
    protected boolean isExist(Object uuid) {
        return storage.containsKey(uuid.toString());
    }
}
