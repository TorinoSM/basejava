package com.home.webapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization {
    private final Link homepage;
    private final String description;
    private final List<Record> records = new ArrayList<>();

    public Organization(String name, String url, LocalDate startDate, LocalDate endtDate, String title, String description) {
        Objects.requireNonNull(startDate, "startDate argument must not be null");
        Objects.requireNonNull(endtDate, "endtDate argument must not be null");
        Objects.requireNonNull(title, "title argument must not be null");
        this.homepage = new Link(name, url);
        this.description = description;
        records.add(new Record(startDate, endtDate, title));
    }

    public void addRecord(LocalDate startDate, LocalDate endtDate, String title) {
        records.add(new Record(startDate, endtDate, title));
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homepage=" + homepage +
                ", description='" + description + '\'' +
                ", records=" + records +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (homepage != null ? !homepage.equals(that.homepage) : that.homepage != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        return records.equals(that.records);

    }

    @Override
    public int hashCode() {
        int result = homepage != null ? homepage.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + records.hashCode();
        return result;
    }
}
