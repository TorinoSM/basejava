package com.home.webapp.storage;
import com.home.webapp.model.Resume;

public interface Storage {

    void clear();

    void update(Resume updatedResume);

    void save(Resume r);

    Resume get(String uuid);

    void delete(String uuid);

    Resume[] getAll();

    int size();
}
