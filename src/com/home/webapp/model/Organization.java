package com.home.webapp.model;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.home.webapp.util.DateUtil.of;

public class Organization {
    private final Link organisation;
    private List<Record> records = new ArrayList<>();

    public Organization(String name, String url, Record... records) {
        this(new Link(name, url), Arrays.asList(records));
    }

    public Organization(Link organization, List<Record> records) {
        this.organisation = organization;
        this.records = records;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "organisation=" + organisation +
                ", records=" + records +
                '}';
    }

    public Link getOrganisation() {
        return organisation;
    }

    public List<Record> getRecords() {
        return records;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization that = (Organization) o;

        if (!organisation.equals(that.organisation)) return false;
        return records.equals(that.records);

    }

    @Override
    public int hashCode() {
        int result = organisation.hashCode();
        result = 31 * result + records.hashCode();
        return result;
    }


    public static class Record {

        private final static LocalDate NOW = LocalDate.of(Year.MAX_VALUE, 1, 1);
        private final LocalDate startDate;
        private final LocalDate endtDate;
        private final String recordTitle;
        private final String description;


        public Record(LocalDate startDate, LocalDate endtDate, String recordTitle, String description) {
            Objects.requireNonNull(startDate, "startDate argument must not be null");
            Objects.requireNonNull(endtDate, "endtDate argument must not be null");
            Objects.requireNonNull(recordTitle, "recordTitle argument must not be null");
            this.startDate = startDate;
            this.endtDate = endtDate;
            this.recordTitle = recordTitle;
            this.description = description;
        }

        public Record(Integer startYear, Month startMonth, Integer endYear, Month endMonth, String recordTitle, String description) {
            this(of(startYear, startMonth), of(endYear, endMonth), recordTitle, description);
        }

        public Record(Integer startYear, Month startMonth, Integer endYear, Month endMonth, String recordTitle) {
            this(of(startYear, startMonth), of(endYear, endMonth), recordTitle, "");
        }

        public Record(Integer startYear, Month startMonth, String recordTitle, String description) {
            this(of(startYear, startMonth), NOW, recordTitle, description);
        }

        public Record(Integer startYear, Month startMonth, String recordTitle) {
            this(of(startYear, startMonth), NOW, recordTitle, "");
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Record record = (Record) o;

            if (!startDate.equals(record.startDate)) return false;
            if (!endtDate.equals(record.endtDate)) return false;
            if (!recordTitle.equals(record.recordTitle)) return false;
            return description != null ? description.equals(record.description) : record.description == null;

        }

        @Override
        public int hashCode() {
            int result = startDate.hashCode();
            result = 31 * result + endtDate.hashCode();
            result = 31 * result + recordTitle.hashCode();
            result = 31 * result + (description != null ? description.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Record{" +
                    "startDate=" + startDate +
                    ", endtDate=" + endtDate +
                    ", recordTitle='" + recordTitle + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndtDate() {
            return endtDate;
        }

        public String getRecordTitle() {
            return recordTitle;
        }

        public String getDescription() {
            return description;
        }
    }
}
