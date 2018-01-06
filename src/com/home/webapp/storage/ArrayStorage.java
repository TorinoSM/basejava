package com.home.webapp.storage;

import com.home.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {

    // добавить конструктор

    private Resume[] storage = new Resume[10000];
    private int lenght = storage.length; // максимальный размер массива
    private int size = 0; // количество непустых резюме в массиве

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    private int getIndex(String uuid) {
        if ("".equals(uuid)) return -1; // uuid can not be null
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) return i;
        }
        return -1; // uuid not found
    }

    private int getIndex(Resume resume) {
        if (resume == null) return -1; // resume can not be null
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(resume.getUuid())) return i;
        }
        return -1; // uuid not found
    }

    public void update(Resume updatedResume) {
        if (updatedResume == null) {
            System.out.println("Error: Update: Cannot update with resume which is null");
            return;
        }
        int index = this.getIndex(updatedResume);
        if (index != -1) {
            storage[index] = updatedResume;
            System.out.println("Success: Update: Resume with uuid = \"" + storage[index].getUuid() + "\" is updated");
            return;
        }
        System.out.println("Warning: Update: Couldn't find resume with uuid = \"" + updatedResume.getUuid() + "\"");
    }

    public void save(Resume r) {
        if (r == null) {
            System.out.println("Error: Save: Resume can't be null");
            return;
        }
        if (size >= lenght) {
            System.out.println("Error: Save: Can't save resume: reached maximum capacity of the storage (" + lenght + " records)");
            return;
        }
        int index = this.getIndex(r);
        if (index != -1) {
            System.out.println("Warning: Save: Resume with uuid = \"" + r.getUuid() + "\" already exists");
            return;
        }
        storage[size] = r;
        size++;
        System.out.println("Success: Save: Saved new resume with uuid = \"" + r.getUuid() + "\"");
    }

    public Resume get(String uuid) {
        if ("".equals(uuid)) {
            System.out.println("Error: Get: Resume's uuid can't be empty");
            return null;
        }
        int index = this.getIndex(uuid);
        if (index != -1) {
            System.out.println("Success: Get: Found resume with uuid = \"" + uuid + "\"");
            return storage[index];  // конкретное резюме найдено
        }
        System.out.println("Warning: Get: Resume with uuid = \"" + uuid + "\" did not found");
        return null;  // конкретное резюме НЕ найдено
    }

    public void delete(String uuid) {   // предполагаем, что null-элементов в массиве быть не может
        if ("".equals(uuid)) {
            System.out.println("Warning: Delete: Resume's uuid can't be empty");
            return;
        }
        if (size == 0) {
            System.out.println("Warning: Delete: Nothing to delete: storage is empty");
            return;
        }
        int index = this.getIndex(uuid);
        if (index != -1) {
            storage[index] = storage[size - 1]; // move the last element into position of the deleted one
            storage[size - 1] = null;
            size--;
            System.out.println("Success: Delete: Deleted resume with uuid = \"" + uuid + "\"");
            return;
        }
        System.out.println("Warning: Delete: Resume with uuid = \"" + uuid + "\" did not found");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }
}
