package com.home.webapp.storage;

import com.home.webapp.exception.ExistStorageException;
import com.home.webapp.exception.IllegalArgumentException;
import com.home.webapp.exception.NotExistStorageException;
import com.home.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {


    public void save(Resume r) {

        if (r == null) {
            throw new IllegalArgumentException();
        }
        checkStorageOverflow(r);

        String uuid = r.getUuid();
        int index = getIndex(uuid);

        if (index < 0) {
            insertElement(r, index);
            System.out.println("Success: Save: Saved new resume with uuid = \"" + uuid + "\"");
        } else {
            throw new ExistStorageException(uuid);
        }
    }

    public void update(Resume updatedResume) {
        if (updatedResume == null) {
            throw new IllegalArgumentException();
        }

        String uuid = updatedResume.getUuid();
        int index = getIndex(uuid);

        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            System.out.println("Success: Update: Resume with uuid = \"" + uuid + "\" is updated");
            updateElement(updatedResume, index);
        }
    }

    public void delete(String uuid) {
        if ("".equals(uuid)) {
            throw new IllegalArgumentException();
        }
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            System.out.println("Success: Delete: Deleted resume with uuid = \"" + uuid + "\"");
            deleteElement(index);
        }
    }

    public Resume get(String uuid) {
        if ("".equals(uuid)) {
            throw new IllegalArgumentException();
        }

        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            System.out.println("Success: Get: Found resume with uuid = \"" + uuid + "\"");
            return getElement(index);
        }
    }

    protected abstract Resume getElement(int index);

    protected abstract void deleteElement(int index);

    protected abstract void updateElement(Resume updatedResume, int index);

    protected abstract void insertElement(Resume r, int index);

    protected abstract void checkStorageOverflow(Resume r);

    protected abstract int getIndex(String uuid);


}
