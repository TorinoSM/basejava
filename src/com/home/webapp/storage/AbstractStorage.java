package com.home.webapp.storage;

import com.home.webapp.exception.ExistStorageException;
import com.home.webapp.exception.IllegalArgumentException;
import com.home.webapp.exception.NotExistStorageException;
import com.home.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {


    public List<Resume> getAllSorted() {
        List<Resume> sortedList = getResumesArrayCopy();
        sortedList.sort(new Comparator<Resume>() {
            @Override
            public int compare(Resume o1, Resume o2) {
                int comparator = o1.getFullName().compareTo(o2.getFullName());
                if (comparator == 0) return o1.getUuid().compareTo(o2.getUuid());
                return comparator;
            }
        });
        return sortedList;
    }

    protected abstract List<Resume> getResumesArrayCopy();

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
        System.out.println("Saved " + resume.toString());
        saveElement(resume, searchKey);
    }


    public void update(Resume updatedResume) {
        if (updatedResume == null) {
            throw new IllegalArgumentException();
        }

        String uuid = updatedResume.getUuid();
        Object searchKey = getSearchKeyIfExist(uuid);
        System.out.println("Updated " + updatedResume.toString());
        updateElement(updatedResume, searchKey);
    }


    public void delete(String uuid) {
        if ("".equals(uuid)) {
            throw new IllegalArgumentException();
        }

        Object searchKey = getSearchKeyIfExist(uuid);
        System.out.println("Deleted resume{uuid = " + uuid + '}');
        deleteElement(searchKey);
    }


    public Resume get(String uuid) {
        if ("".equals(uuid)) {
            throw new IllegalArgumentException();
        }

        Object searchKey = getSearchKeyIfExist(uuid);
        System.out.println("Got resume{uuid = " + uuid + '}');
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
