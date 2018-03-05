package com.home.webapp.storage;

import com.home.webapp.exception.StorageException;
import com.home.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {

    private File directory;

    public AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "Directory argument must not be null");
        if (!(directory.isDirectory())) {
            throw new IllegalArgumentException("Directory argument must be a valid directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException("Directory must be readable/writable");
        }
        this.directory = directory;
    }

    protected abstract void doWrite(Resume resume, File file) throws IOException;

    protected abstract Resume doRead(File file);

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        for (File file : files) {
            file.delete();
        }
    }

    @Override
    public int size() {
        File[] files = directory.listFiles();
        return files.length;
    }

    @Override
    protected List<Resume> getResumesList() {
        List resumesList = new ArrayList();
        File[] files = directory.listFiles();
        for (File file : files) {
            resumesList.add(doRead(file));
        }
        return resumesList;
    }

    @Override
    protected Resume getElement(File file) {
        return doRead(file);
    }

    @Override
    protected void deleteElement(File file) {
        file.delete();
    }

    @Override
    protected void updateElement(Resume updatedResume, File file) {
        try {
            doWrite(updatedResume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void saveElement(Resume resume, File file) {
        try {
            file.createNewFile();
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }
}
