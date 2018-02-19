package com.home.webapp.storage;
import com.home.webapp.model.Resume;

import java.util.List;

public interface Storage {

    void clear();

    void update(Resume updatedResume);

    void save(Resume r);

    Resume get(String uuid);

    void delete(String uuid);

    List<Resume> getAllSorted();

    int size();
}
