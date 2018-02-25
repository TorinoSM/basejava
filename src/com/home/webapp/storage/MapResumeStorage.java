package com.home.webapp.storage;

import com.home.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {

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
    protected Resume getElement(Resume resumeAsSearchKey) {
        return resumeAsSearchKey;
    }

    @Override
    protected void deleteElement(Resume resumeAsSearchKey) {
        storage.remove((resumeAsSearchKey).getUuid());
    }

    @Override
    protected void updateElement(Resume updatedResume, Resume resumeAsSearchKey) {
        storage.put(updatedResume.getUuid(), updatedResume);
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void saveElement(Resume resume, Resume resumeAsSearchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected boolean isExist(Resume resume) {
        return resume != null;
    }
}
