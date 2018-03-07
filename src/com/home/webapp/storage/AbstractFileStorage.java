package com.home.webapp.storage;

import com.home.webapp.exception.StorageException;
import com.home.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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

    protected abstract Resume doRead(File file) throws IOException;

    @Override
    public void clear() {
        if (directory == null || !directory.isDirectory()) throw new StorageException("Can not clear directory", "");
        File[] files = directory.listFiles();
        if (files == null) throw new StorageException("Can not clear directory", "");
        for (File file : files) {
            deleteElement(file);
        }
    }

    @Override
    public int size() {
        if (directory == null || !directory.isDirectory()) throw new StorageException("Can not get directory size", "");
        String[] files = directory.list();
        if (files == null) throw new StorageException("Can not get directory size", "");
        return files.length;
    }

    @Override
    protected List<Resume> getResumesList() {
        if (directory == null || !directory.isDirectory()) return Collections.EMPTY_LIST;
        File[] files = directory.listFiles();
        if (files == null) throw new StorageException("Can not get directory content", "");
        List<Resume> resumesList = new ArrayList<>();
        for (File file : files) {
            if (file.isFile() && file.canRead()) {
                resumesList.add(getElement(file));
            }
        }
        return resumesList;
    }

    @Override
    protected Resume getElement(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("IO error while processing " + file.getName(), file.getName(), e);
        }
    }

    @Override
    protected void deleteElement(File file) {
        if (!file.delete()) throw new StorageException("Can not delete entity " + file.getName(), file.getName());
    }

    @Override
    protected void updateElement(Resume updatedResume, File file) {
        try {
            doWrite(updatedResume, file);
        } catch (IOException e) {
            throw new StorageException("IO error while processing " + file.getName(), file.getName(), e);
        }
    }

    @Override
    protected File getSearchKey(String uuid) {
        if (directory == null || !directory.isDirectory()) throw new StorageException("IO Error", "");
        return new File(directory, uuid);
    }

    @Override
    protected void saveElement(Resume resume, File file) {
        try {
            if (file.createNewFile()) {
                updateElement(resume, file);
            } else throw new StorageException("File " + file.getName() + " already exists", file.getName());
        } catch (IOException e) {
            throw new StorageException("IO error while processing " + file.getName(), file.getName(), e);
        }

    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }
}
