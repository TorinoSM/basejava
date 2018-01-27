package com.home.webapp.storage;

import com.home.webapp.exception.IllegalArgumentException;
import com.home.webapp.exception.NotExistStorageException;
import com.home.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {

    private final ArrayList<Resume> storage = new ArrayList<>();


    @Override
    public void clear() {
        storage.clear();
        storage.trimToSize();
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) return i; // uuid found
        }
        return -1; // uuid not found
    }

    protected void insertElement(Resume resume, int index) { // index is only used for method's signature match
        storage.add(resume);
    }

    protected void updateElement(Resume updatedResume, int index) {
        storage.add(index, updatedResume);
    }


    @Override
    protected void checkStorageOverflow(Resume r) {
        // stub (not necessary for ListStorage)
    }

    protected Resume getElement(int index){
        return storage.get(index);
    }

    protected void deleteElement(int index) {
        storage.remove(index);
        storage.trimToSize();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[storage.size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
