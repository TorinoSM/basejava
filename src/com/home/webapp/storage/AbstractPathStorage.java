package com.home.webapp.storage;

import com.home.webapp.exception.StorageException;
import com.home.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {

    private Path directory;

    public AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "Directory argument must not be null");
        if (!(Files.isDirectory(directory)) || Files.isWritable(directory)) {
            throw new IllegalArgumentException("Directory argument is not a valid directory or is not writable");
        }
    }

    protected abstract void doWrite(Resume resume, OutputStream outputStream) throws IOException;

    protected abstract Resume doRead(InputStream inputStream) throws IOException;


    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::deleteElement);
        } catch (IOException e) {
            throw new StorageException("Error listing directory", "", e);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).filter(Files::isRegularFile).count(); // фильтруем список: к каждому элементу списка применяем метод isRegularFile класса Files, потом считаем количество просочившихся элементов (count возвращает long, поэтому кастим в int)
        } catch (IOException e) {
            throw new StorageException("Error listing directory", "", e);
        }
    }

    @Override
    protected List<Resume> getResumesList() {
        List<Resume> resumesList = new ArrayList<>();

        try {
            Files.list(directory).filter(Files::isRegularFile).forEach(p -> resumesList.add(getElement(p)));
        } catch (IOException e) {
            throw new StorageException("Can not get directory content", "", e);
        }
        return resumesList;
        // peek - возвращает ТОТ ЖЕ stream без изменений, просто применяет к каждому элементу функцию
    }

    @Override
    protected Resume getElement(Path path) {
        try {
            return doRead(new BufferedInputStream(new FileInputStream(path.toFile()))); // не уверен что так правильно
        } catch (IOException e) {
            throw new StorageException("IO error while processing " + path.toString(), path.toString(), e);
        }
    }

    @Override
    protected void deleteElement(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Can not delete entity " + path.toString(), path.toString(), e);
        }
    }

    @Override
    protected void updateElement(Resume updatedResume, Path path) {
        try {
            doWrite(updatedResume, new BufferedOutputStream(new FileOutputStream(path.toFile())));
        } catch (IOException e) {
            throw new StorageException("IO error while processing " + path.toString(), path.toString(), e);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return Paths.get(uuid);
    }

    @Override
    protected void saveElement(Resume resume, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("IO error while processing " + path.toString(), path.toString(), e);
        }
        updateElement(resume, path);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }
}
