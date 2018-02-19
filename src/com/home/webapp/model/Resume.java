package com.home.webapp.model;

import java.util.UUID;

public class Resume implements Comparable<Resume> {

    private final String uuid;
    private String fullName;

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public Resume(String uuid) {
        this(uuid, "");
    }

    public Resume() {
        this(UUID.randomUUID().toString(), "");
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return this.fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return "Resume{uuid=" + uuid + ",fullName=" + (fullName.equals("") ? "\"\"" : fullName) + "}";
    }

    @Override
    public int compareTo(Resume o) {
        return this.uuid.compareTo(o.uuid);
    }
}
