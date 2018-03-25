package com.home.webapp.storage;

import com.home.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    protected void insertElement(Resume resume, int index) {
        index = (-index) - 1; // calculate insertion point from the value returned
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
    }

    protected void deleteElementOfArray(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1); // сдвигаем на одну позицию влево правую часть массива
        storage[size - 1] = null;
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume key = new Resume(uuid, "");
        return Arrays.binarySearch(storage, 0, size, key, new Comparator<Resume>() {
            @Override
            public int compare(Resume o1, Resume o2) {
                return o1.getUuid().compareTo(o2.getUuid());
            }
        }); // if no key found, return future position of key with negative sign
    }
}
