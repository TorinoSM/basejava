package com.home.webapp.storage;

import com.home.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {

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
    protected Resume getElement(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void deleteElement(String uuid) {
        storage.remove(uuid);
    }

    @Override
    protected void updateElement(Resume updatedResume, String uuid) {
        storage.put(uuid, updatedResume);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected void saveElement(Resume resume, String uuid) {
        storage.put(uuid, resume);
    }

    @Override
    protected boolean isExist(String uuid) {
        return storage.containsKey(uuid);
    }
}
