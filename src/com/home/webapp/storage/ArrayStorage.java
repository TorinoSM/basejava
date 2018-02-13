package com.home.webapp.storage;

import com.home.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    protected void insertElement(Resume resume, int index) { // index is used only for method's signature match
        storage[size] = resume;
    }

    protected void deleteElementOfArray(int index) { // предполагаем, что null-элементов в массиве быть не может
        storage[index] = storage[size - 1]; // move the last element into position of the deleted one
        storage[size - 1] = null;
    }

    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) return i;
        }
        return -1; // uuid not found
    }

}
