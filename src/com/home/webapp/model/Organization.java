package com.home.webapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization {
    private final Link homepage;
    private final String title;
    private final List<Record> records = new ArrayList<>();

    public Organization(String name, String url, LocalDate startDate, LocalDate endtDate, String title, String description) {
        Objects.requireNonNull(startDate, "startDate argument must not be null");
        Objects.requireNonNull(endtDate, "endtDate argument must not be null");
        Objects.requireNonNull(title, "title argument must not be null");
        this.homepage = new Link(name, url);
        this.title = title;
        records.add(new Record(startDate, endtDate, description));
    }

    public void addRecord(LocalDate startDate, LocalDate endtDate, String description) {
        records.add(new Record(startDate, endtDate, description));
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homepage=" + homepage +
                ", title='" + title + '\'' +
                ", records=" + records.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!homepage.equals(that.homepage)) return false;
        if (!title.equals(that.title)) return false;
        return records.equals(that.records);

    }

    @Override
    public int hashCode() {
        int result = homepage.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + records.hashCode();
        return result;
    }


}
