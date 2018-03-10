package com.home.webapp.storage;

import com.home.webapp.exception.StorageException;
import com.home.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {

    private Path directory;

    public AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "Directory argument must not be null");
        if (!(Files.isDirectory(directory)) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException("Directory argument is not a valid directory or is not writable");
        }
    }

    protected abstract void doWrite(Resume resume, OutputStream outputStream) throws IOException;

    protected abstract Resume doRead(InputStream inputStream) throws IOException;


    @Override
    public void clear() {
        getPathsList().forEach(this::deleteElement);
    }

    @Override
    public int size() {
        return (int) getPathsList().filter(Files::isRegularFile).count(); // фильтруем список: к каждому элементу списка применяем метод isRegularFile класса Files, потом считаем количество просочившихся элементов (count возвращает long, поэтому кастим в int)
    }

    @Override
    protected List<Resume> getResumesList() {
        if (getPathsList().filter(Files::isRegularFile).count() == 0) { // если файлов нет то возвращаем пустой лист
            return Collections.emptyList();
        }
        List<Resume> resumesList = new ArrayList<>();
        getPathsList().filter(Files::isRegularFile).forEach(p -> resumesList.add(getElement(p)));
        return resumesList;
        // peek - возвращает ТОТ ЖЕ stream без изменений, просто применяет к каждому элементу функцию
        // forEach не возвращает ничего
        // map - каждый элемент отображается в другой элемент (например был стрим пасов, стал стрим резюме)
        // см. параметры-интерфейсы: что и сколько принимает, что возвращает
        // однажды использованный стрим исчезает, его нужно создавать заново, т.е. загнать его в переменную и использовать несколько раз не получится
    }

    @Override
    protected Resume getElement(Path path) {
        try {
            return doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("IO error while processing " + path, getFileName(path), e);
        }
    }

    @Override
    protected void deleteElement(Path path) {
        try {
            if (Files.isRegularFile(path)) {
                Files.delete(path);
            }
        } catch (IOException e) {
            throw new StorageException("Can not delete file " + path, getFileName(path), e);
        }
    }

    @Override
    protected void updateElement(Resume updatedResume, Path path) {
        try {
            doWrite(updatedResume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("IO error while processing " + path, updatedResume.getUuid(), e);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected void saveElement(Resume resume, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("IO error while processing " + path, resume.getUuid(), e);
        }
        updateElement(resume, path);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.isRegularFile(path);
    }

    private Stream<Path> getPathsList() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Error reading directory", "", e);
        }
    }

    private String getFileName(Path path) {
        return path.getFileName().toString();
    }
}
