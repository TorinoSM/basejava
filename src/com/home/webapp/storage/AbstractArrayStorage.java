package com.home.webapp.storage;

import com.home.webapp.exception.ExistStorageException;
import com.home.webapp.exception.IllegalArgumentException;
import com.home.webapp.exception.NotExistStorageException;
import com.home.webapp.exception.StorageException;
import com.home.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {

    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0; // количество непустых резюме в массиве

    public void save(Resume r) {
        if (r == null) {
            throw new IllegalArgumentException();
        }
        String uuid = r.getUuid();
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Error: Save: Can't save resume: reached maximum capacity of the storage (" + STORAGE_LIMIT + " records)", uuid);
        }
        int index = getIndex(uuid); // index<0?элемент не найден+возвращается (-точка вставки)-1:элемент найден+возвращается индекс элемента

        if (index < 0) { //  uuid not found
            insertElement(r, index);
            size++;
            System.out.println("Success: Save: Saved new resume with uuid = \"" + uuid + "\"");
        } else {  //  uuid found
            throw new ExistStorageException(uuid);
        }
    }

    public void delete(String uuid) {
        if ("".equals(uuid)) {
            throw new IllegalArgumentException();
        }
        int index = getIndex(uuid);

        if (index < 0) { // uuid not found
            throw new NotExistStorageException(uuid);
        } else { // uuid found
            deleteElement(index);
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
            throw new IllegalArgumentException();
        }
        String uuid = updatedResume.getUuid();
        int index = getIndex(uuid);
        if (index < 0) { //  uuid not found
            throw new NotExistStorageException(uuid);
        } else {
            storage[index] = updatedResume;
            System.out.println("Success: Update: Resume with uuid = \"" + storage[index].getUuid() + "\" is updated");
        }
    }

    public Resume get(String uuid) {
        if ("".equals(uuid)) {
            throw new IllegalArgumentException();
        }
        int index = getIndex(uuid);

        if (index < 0) { // uuid not found
            throw new NotExistStorageException(uuid);
        } else { // uuid found
            System.out.println("Success: Get: Found resume with uuid = \"" + uuid + "\"");
            return storage[index];  // конкретное резюме найдено
        }
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

    protected abstract void insertElement(Resume resume, int index);

    protected abstract void deleteElement(int index);


}
