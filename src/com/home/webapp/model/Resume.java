package com.home.webapp.model;

import java.util.Objects;
import java.util.UUID;

public class Resume implements Comparable<Resume> {

    private final String uuid;
    private final String fullName;

    public Resume(String uuid, String fullName) {
        this.uuid = Objects.requireNonNull(uuid, "uuid can not be null");
        this.fullName = Objects.requireNonNull(fullName, "fullName can not be null");
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!uuid.equals(resume.uuid)) return false;
        return fullName.equals(resume.fullName);

    }

    @Override
    public int hashCode() {
        int result = uuid.hashCode();
        result = 31 * result + fullName.hashCode();
        return result;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return "resume{uuid = " + uuid + ", fullName = " + (fullName.equals("") ? "\"\"" : fullName) + '}';
    }

    @Override
    public int compareTo(Resume o) {
        return this.uuid.compareTo(o.uuid);
    }
}
