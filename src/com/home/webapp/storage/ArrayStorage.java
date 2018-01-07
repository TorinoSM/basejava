package com.home.webapp.storage;

import com.home.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {


    public void update(Resume updatedResume) {
        if (updatedResume == null) {
            System.out.println("Error: Update: Cannot update with resume which is null");
            return;
        }
        int index = this.getIndex(updatedResume);
        if (index > -1) {
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
        if (size >= STORAGE_LIMIT) {
            System.out.println("Error: Save: Can't save resume: reached maximum capacity of the storage (" + STORAGE_LIMIT + " records)");
            return;
        }
        int index = this.getIndex(r);
        if (index > -1) {
            System.out.println("Warning: Save: Resume with uuid = \"" + r.getUuid() + "\" already exists");
            return;
        }
        storage[size] = r;
        size++;
        System.out.println("Success: Save: Saved new resume with uuid = \"" + r.getUuid() + "\"");
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
        if (index > -1) {
            storage[index] = storage[size - 1]; // move the last element into position of the deleted one
            storage[size - 1] = null;
            size--;
            System.out.println("Success: Delete: Deleted resume with uuid = \"" + uuid + "\"");
            return;
        }
        System.out.println("Warning: Delete: Resume with uuid = \"" + uuid + "\" did not found");
    }

    protected int getIndex(String uuid) {
        if ("".equals(uuid)) return -1; // uuid can not be null
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) return i;
        }
        return -1; // uuid not found
    }

    protected int getIndex(Resume resume) {
        if (resume == null) return -1; // resume can not be null
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(resume.getUuid())) return i;
        }
        return -1; // uuid not found
    }
}
