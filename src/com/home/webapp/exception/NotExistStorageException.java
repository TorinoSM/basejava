package com.home.webapp.exception;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String uuid) {
        super("Warning: Resume with uuid = \"" + uuid + "\" not found", uuid);
    }
}
