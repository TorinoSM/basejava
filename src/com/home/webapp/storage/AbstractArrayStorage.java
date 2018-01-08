package com.home.webapp.storage;

import com.home.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {

    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0; // количество непустых резюме в массиве


    public void save(Resume r) {
        if (r == null) {
            System.out.println("Error: Save: Resume can't be null");
            return;
        }
        if (size >= STORAGE_LIMIT) {
            System.out.println("Error: Save: Can't save resume: reached maximum capacity of the storage (" + STORAGE_LIMIT + " records)");
            return;
        }
        int index = this.getIndex(r); // для всех реализаций требуется следующее поведение: index<0?элемент не найден+возвращается (-точка вставки)-1:элемент найден+возвращается индекс элемента

        if (index < 0) { //  uuid not found
            this.insertElement(r, index);
            size++;
            System.out.println("Success: Save: Saved new resume with uuid = \"" + r.getUuid() + "\"");
        } else {  //  uuid found
            System.out.println("Warning: Save: Resume with uuid = \"" + r.getUuid() + "\" already exists");
        }
    }

    public void delete(String uuid) {
        if ("".equals(uuid)) {
            System.out.println("Warning: Delete: Resume's uuid can't be empty");
            return;
        }
        if (size == 0) {
            System.out.println("Warning: Delete: Nothing to delete: storage is empty");
            return;
        }
        int index = this.getIndex(uuid);

        if (index < 0) { // uuid not found
            System.out.println("Warning: Delete: Resume with uuid = \"" + uuid + "\" not found");
        } else { // uuid found
            this.deleteElement(index);
            storage[size - 1] = null;
            size--;
            System.out.println("Success: Delete: Deleted resume with uuid = \"" + uuid + "\"");
        }
    }


    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

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

    public int size() {
        return size;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected abstract int getIndex(String uuid);

    protected abstract int getIndex(Resume resume);

    protected abstract void insertElement(Resume resume, int index);

    protected abstract void deleteElement(int index);


}
