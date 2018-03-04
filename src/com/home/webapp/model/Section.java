package com.home.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class Section {

    private List<Row> rows = new ArrayList<>(); // лист простых строк
    private List<SubSection> subSections = new ArrayList<>(); // лист подсекций
    // тип секции
    private SectionType sectionType;
    // печатаемый заголовок секции
    private String title;

    public Section(SectionType sectionType) {
        this.sectionType = sectionType;
    }

    public List<Row> getRows() {
        return rows;
    }

    public List<SubSection> getSubSections() {
        return subSections;
    }

    public SectionType getSectionType() {
        return sectionType;
    }

    public void setSectionType(SectionType sectionType) {
        this.sectionType = sectionType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Row addRow() {
        Row newRow = new Row();
        rows.add(newRow);
        return newRow;
    }

    public SubSection addSubSection() {
        SubSection newSubSection = new SubSection();
        subSections.add(newSubSection);
        return newSubSection;
    }
}
