package com.home.webapp.storage;

import com.home.webapp.exception.ExistStorageException;
import com.home.webapp.exception.IllegalArgumentException;
import com.home.webapp.exception.NotExistStorageException;
import com.home.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<T> implements Storage {
// <T> is a mapping of searchKey Object type into concrete type in sub-classes

    public List<Resume> getAllSorted() {
        List<Resume> sortedList = getResumesArrayCopy();
        sortedList.sort(new Comparator<Resume>() {
            @Override
            public int compare(Resume o1, Resume o2) {
                int comparator = o1.getFullName().compareTo(o2.getFullName());
                if (comparator == 0) return o1.getUuid().compareTo(o2.getUuid()); // если fullName одинаковые то сортируем по uuid
                return comparator;
            }
        });
        return sortedList;
    }

    protected abstract List<Resume> getResumesArrayCopy();

    protected abstract Resume getElement(T searchKey);

    protected abstract void deleteElement(T searchKey);

    protected abstract void updateElement(Resume updatedResume, T searchKey);

    protected abstract T getSearchKey(String uuid);

    protected abstract void saveElement(Resume resume, T searchKey);

    protected abstract boolean isExist(T searchKey);

    public void save(Resume resume) {
        if (resume == null) {
            throw new IllegalArgumentException();
        }

        String uuid = resume.getUuid();
        T searchKey = getSearchKeyIfNotExist(uuid);
        System.out.println("Saved " + resume.toString());
        saveElement(resume, searchKey);
    }


    public void update(Resume updatedResume) {
        if (updatedResume == null) {
            throw new IllegalArgumentException();
        }

        String uuid = updatedResume.getUuid();
        T searchKey = getSearchKeyIfExist(uuid);
        System.out.println("Updated " + updatedResume.toString());
        updateElement(updatedResume, searchKey);
    }


    public void delete(String uuid) {
        if ("".equals(uuid)) {
            throw new IllegalArgumentException();
        }

        T searchKey = getSearchKeyIfExist(uuid);
        System.out.println("Deleted resume{uuid = " + uuid + '}');
        deleteElement(searchKey);
    }


    public Resume get(String uuid) {
        if ("".equals(uuid)) {
            throw new IllegalArgumentException();
        }

        T searchKey = getSearchKeyIfExist(uuid);
        System.out.println("Got resume{uuid = " + uuid + '}');
        return getElement(searchKey);
    }


    private T getSearchKeyIfExist(String uuid) {
        T searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private T getSearchKeyIfNotExist(String uuid) {
        T searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }
}
