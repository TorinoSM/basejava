package com.home.webapp.model;

/**
 * com.urise.webapp.model.com.home.webapp.model.Resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private String uuid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return this.uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        String uuid = getUuid();
        char[] ch = uuid.toCharArray();
        int len = uuid.length();
        int hash = 0;
        for (int i = 0; i < len; i++) {
            hash = hash * 17 + ch[i];
        }
        return hash;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return uuid;
    }

    @Override
    public int compareTo(Resume o) {
        return this.uuid.compareTo(o.uuid);
    }
}
