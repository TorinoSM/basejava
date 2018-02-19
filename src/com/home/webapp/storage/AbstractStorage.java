package com.home.webapp.storage;

import com.home.webapp.exception.ExistStorageException;
import com.home.webapp.exception.IllegalArgumentException;
import com.home.webapp.exception.NotExistStorageException;
import com.home.webapp.model.Resume;

import java.util.Comparator;

public abstract class AbstractStorage implements Storage {

    Comparator<Resume> comparator = new Comparator<Resume>() {
        @Override
        public int compare(Resume o1, Resume o2) {
            return o1.compareTo(o2);
        }
    };

    protected abstract Resume getElement(Object searchKey);

    protected abstract void deleteElement(Object searchKey);

    protected abstract void updateElement(Resume updatedResume, Object searchKey);

    protected abstract Object getSearchKey(String uuid);

    protected abstract void saveElement(Resume resume, Object searchKey);

    protected abstract boolean isExist(Object searchKey);

    public void save(Resume resume) {
        if (resume == null) {
            throw new IllegalArgumentException();
        }

        String uuid = resume.getUuid();
        Object searchKey = getSearchKeyIfNotExist(uuid);
        System.out.println("Success: Save: Saved new resume with uuid = \"" + uuid + "\"");
        saveElement(resume, searchKey);
    }


    public void update(Resume updatedResume) {
        if (updatedResume == null) {
            throw new IllegalArgumentException();
        }

        String uuid = updatedResume.getUuid();
        Object searchKey = getSearchKeyIfExist(uuid);
        System.out.println("Success: Update: Resume with uuid = \"" + uuid + "\" is updated");
        updateElement(updatedResume, searchKey);
    }


    public void delete(String uuid) {
        if ("".equals(uuid)) {
            throw new IllegalArgumentException();
        }

        Object searchKey = getSearchKeyIfExist(uuid);
        System.out.println("Success: Delete: Deleted resume with uuid = \"" + uuid + "\"");
        deleteElement(searchKey);
    }


    public Resume get(String uuid) {
        if ("".equals(uuid)) {
            throw new IllegalArgumentException();
        }

        Object searchKey = getSearchKeyIfExist(uuid);
        System.out.println("Success: Get: Found resume with uuid = \"" + uuid + "\"");
        return getElement(searchKey);
    }


    private Object getSearchKeyIfExist(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getSearchKeyIfNotExist(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }
}
