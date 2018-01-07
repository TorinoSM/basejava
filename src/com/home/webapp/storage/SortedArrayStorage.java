package com.home.webapp.storage;

import com.home.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {


    @Override
    public void update(Resume updatedResume) {
        if (updatedResume == null) {
            System.out.println("Error: Update: Cannot update with resume which is null");
            return;
        }
        int index = this.getIndex(updatedResume);
        if (index < 0) { //  uuid not found
            System.out.println("Warning: Update: Couldn't find resume with uuid = \"" + updatedResume.getUuid() + "\"");
        } else {
            storage[index] = updatedResume;
            System.out.println("Success: Update: Resume with uuid = \"" + storage[index].getUuid() + "\" is updated");
        }
    }

    @Override
    public void save(Resume r) { // массив отсортирован по возрастанию
        if (r == null) {
            System.out.println("Error: Save: Resume can't be null");
            return;
        }
        if (size >= STORAGE_LIMIT) {
            System.out.println("Error: Save: Can't save resume: reached maximum capacity of the storage (" + STORAGE_LIMIT + " records)");
            return;
        }
        int index = this.getIndex(r);
        if (index >= 0) { //  uuid found
            System.out.println("Warning: Save: Resume with uuid = \"" + r.getUuid() + "\" already exists");
        } else {  // uuid not found
            index = (-index) - 1; // calculate insertion point from the value returned
            System.arraycopy(storage, index, storage, index + 1, size - index);
            storage[index] = r;
            size++;
            System.out.println("Success: Save: Saved new resume with uuid = \"" + r.getUuid() + "\"");
        }
    }

    @Override
    public void delete(String uuid) {
        if ("".equals(uuid)) {
            System.out.println("Warning: Delete: Resume's uuid can't be empty");
            return;
        }
        int index = this.getIndex(uuid);
        if (index > -1) {
            System.arraycopy(storage, index + 1, storage, index, size - index - 1); // сдвигаем на одну позицию влево правую часть массива
            storage[size - 1] = null;
            size--;
            System.out.println("Success: Delete: Deleted resume with uuid = \"" + uuid + "\"");
        } else {
            System.out.println("Warning: Delete: Resume with uuid = \"" + uuid + "\" not found");
        }
    }

    @Override
    protected int getIndex(String uuid) {
        if ("".equals(uuid)) return -1; // uuid can not be null
        Resume key = new Resume();
        key.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, key); // if no key found, return future position of key with negative sign
    }

    @Override
    protected int getIndex(Resume resume) {
        if (resume == null) return -1; // resume can not be null
        return Arrays.binarySearch(storage, 0, size, resume);  // if no key found, return future position of key with negative sign
    }
}
