package com.home.webapp.model;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

import static com.home.webapp.model.SectionType.*;

public class Resume implements Comparable<Resume> {

    private final String uuid;
    private final String fullName;
    private HashMap<SectionType, Section> sections = new HashMap<>();

    public Resume(String uuid, String fullName) {
        this.uuid = Objects.requireNonNull(uuid, "uuid can not be null");
        this.fullName = Objects.requireNonNull(fullName, "fullName can not be null");
        sections.put(TITLE, new Section(TITLE));
        sections.put(PERSONAL, new Section(PERSONAL));
        sections.put(OBJECTIVE, new Section(OBJECTIVE));
        sections.put(ACHIEVEMENT, new Section(ACHIEVEMENT));
        sections.put(QUALIFICATIONS, new Section(QUALIFICATIONS));
        sections.put(EXPERIENCE, new Section(EXPERIENCE));
        sections.put(EDUCATION, new Section(EDUCATION));

        sections.get(TITLE).setTitle(fullName);
        sections.get(PERSONAL).setTitle(PERSONAL.getTitle());
        sections.get(OBJECTIVE).setTitle(OBJECTIVE.getTitle());
        sections.get(ACHIEVEMENT).setTitle(ACHIEVEMENT.getTitle());
        sections.get(QUALIFICATIONS).setTitle(QUALIFICATIONS.getTitle());
        sections.get(EXPERIENCE).setTitle(EXPERIENCE.getTitle());
        sections.get(EDUCATION).setTitle(EDUCATION.getTitle());

    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public HashMap<SectionType, Section> getSections() {
        return sections;
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
