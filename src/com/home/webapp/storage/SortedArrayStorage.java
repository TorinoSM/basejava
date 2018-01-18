package com.home.webapp.storage;

import com.home.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    protected void insertElement(Resume resume, int index) {
        index = (-index) - 1; // calculate insertion point from the value returned
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
    }

    protected void deleteElement(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1); // сдвигаем на одну позицию влево правую часть массива
    }

    @Override
    protected int getIndex(String uuid) {
        Resume key = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, key); // if no key found, return future position of key with negative sign
    }
}