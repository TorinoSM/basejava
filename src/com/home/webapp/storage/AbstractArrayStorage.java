package com.home.webapp.storage;

import com.home.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {

    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0; // количество непустых резюме в массиве


    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }


    public Resume get(String uuid) {
        if ("".equals(uuid)) {
            System.out.println("Error: Get: Resume's uuid can't be empty");
            return null;
        }
        int index = this.getIndex(uuid);
        if (index > -1) {
            System.out.println("Success: Get: Found resume with uuid = \"" + uuid + "\"");
            return storage[index];  // конкретное резюме найдено
        }
        System.out.println("Warning: Get: Resume with uuid = \"" + uuid + "\" did not found");
        return null;  // конкретное резюме НЕ найдено
    }

    protected abstract int getIndex(String uuid);

    protected abstract int getIndex(Resume resume);


    public int size() {
        return size;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }


}
