package com.home.webapp.model;

import java.time.LocalDate;

public class Record {

    private final LocalDate startDate;
    private final LocalDate endtDate;
    private final String title;


    public Record(LocalDate startDate, LocalDate endtDate, String title) {
        this.startDate = startDate;
        this.endtDate = endtDate;
        this.title = title;
    }

    @Override
    public String toString() {
        return "Record{" +
                "startDate=" + startDate +
                ", endtDate=" + endtDate +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Record record = (Record) o;

        if (!startDate.equals(record.startDate)) return false;
        if (!endtDate.equals(record.endtDate)) return false;
        return title.equals(record.title);

    }

    @Override
    public int hashCode() {
        int result = startDate.hashCode();
        result = 31 * result + endtDate.hashCode();
        result = 31 * result + title.hashCode();
        return result;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndtDate() {
        return endtDate;
    }

    public String getTitle() {
        return title;
    }
}
