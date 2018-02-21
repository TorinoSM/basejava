package com.home.webapp.storage;

import com.home.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {

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
    protected Resume getElement(Object resumeAsSearchKey) {
        return (Resume) resumeAsSearchKey;
    }

    @Override
    protected void deleteElement(Object resumeAsSearchKey) {
        storage.remove(((Resume) resumeAsSearchKey).getUuid());
    }

    @Override
    protected void updateElement(Resume updatedResume, Object resumeAsSearchKey) {
        storage.put(updatedResume.getUuid(), updatedResume);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void saveElement(Resume resume, Object resumeAsSearchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected boolean isExist(Object resume) {
        return resume != null;
    }
}
