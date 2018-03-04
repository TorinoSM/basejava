package com.home.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class Row {
    private List<Cell> cells = new ArrayList<>();

    public Row(Integer columnsCount) {
        if (columnsCount < 1) columnsCount = 1; // число колонок не может быть меньше одного
        for (int i = 0; i < columnsCount; i++) {
            cells.add(new Cell());
        }
    }

    // конструктор по умолчанию создает строку с одной колонкой
    public Row() {
        this(1);
    }

    public List<Cell> getCells() {
        return cells;
    }

    // метод вызванный для строки, извлекает текст из первого столбца этой строки
    public String getContent() {
        return cells.get(0).getContent();
    }

    // метод вызванный для строки, устанавливает текст в первом столбце этой строки
    public void setContent(String content) {
        cells.get(0).setContent(content);
    }

    // возвращает ссылку на ячейку (начиная с нуля)
    public Cell getCell(Integer columnNumber) {
        return cells.get(columnNumber);
    }

}
