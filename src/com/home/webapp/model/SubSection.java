package com.home.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class SubSection {

    // мультиколонные записи
    private List<Row> records = new ArrayList<>();
    // первая строка подсекции
    private Row subSectionContent = new Row();

    // извлекаем текст первой строки подсекции
    public String getTitle() {
        return subSectionContent.getContent();
    }

    // устанавливаем текст первой строки подсекции
    public void setTitle(String content) {
        subSectionContent.setContent(content);
    }

    public List<Row> getRecords() {
        return records;
    }

    public Row addRecord(Integer columnsCount) {
        Row newRow = new Row(columnsCount);
        records.add(newRow);
        return newRow;
    }
}



