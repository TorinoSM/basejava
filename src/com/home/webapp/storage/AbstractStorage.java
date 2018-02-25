package com.home.webapp.storage;

import com.home.webapp.exception.ExistStorageException;
import com.home.webapp.exception.IllegalArgumentException;
import com.home.webapp.exception.NotExistStorageException;
import com.home.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<T> implements Storage {
// <T> is a mapping of searchKey Object type into concrete type in sub-classes

    private static final Logger log = Logger.getLogger(AbstractStorage.class.getName());

    public List<Resume> getAllSorted() {
        List<Resume> sortedList = getResumesArrayCopy();
        sortedList.sort(new Comparator<Resume>() {
            @Override
            public int compare(Resume o1, Resume o2) {
                int comparator = o1.getFullName().compareTo(o2.getFullName());
                if (comparator == 0)
                    return o1.getUuid().compareTo(o2.getUuid()); // если fullName одинаковые то сортируем по uuid
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

        checkForResumeIsNotNull(resume);
        String uuid = resume.getUuid();
        T searchKey = getSearchKeyIfNotExist(uuid);
        saveElement(resume, searchKey);
        log.info("Saved " + resume.toString());
    }


    public void update(Resume updatedResume) {

        checkForResumeIsNotNull(updatedResume);
        String uuid = updatedResume.getUuid();
        T searchKey = getSearchKeyIfExist(uuid);
        updateElement(updatedResume, searchKey);
        log.info("Updated " + updatedResume.toString());
    }

    private void checkForUuidIsNotEmpty(String uuid) {
        if (uuid == null || uuid.isEmpty()) {
            log.warning("Error: Method argument is not valid");
            throw new IllegalArgumentException();
        }
    }

    private void checkForResumeIsNotNull(Resume resume) {
        if (resume == null) {
            log.warning("Method argument is not valid");
            throw new IllegalArgumentException();
        }
    }

    public void delete(String uuid) {

        checkForUuidIsNotEmpty(uuid);
        T searchKey = getSearchKeyIfExist(uuid);
        deleteElement(searchKey);
        log.info("Deleted resume{uuid = " + uuid + '}');
    }


    public Resume get(String uuid) {

        checkForUuidIsNotEmpty(uuid);
        T searchKey = getSearchKeyIfExist(uuid);
        log.info("Got resume{uuid = " + uuid + '}');
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
