package com.home.webapp.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("Warning: Resume with uuid = \"" + uuid + "\" already exists", uuid);
    }
}
