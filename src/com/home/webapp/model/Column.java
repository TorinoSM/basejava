package com.home.webapp.model;


public class Column {

    // Номер колонки
    private Integer columnNumber;
    // Секция  с которой связана колонка
    private Section section;
    // Подсекция  с которой связана колонка
    private SubSection subSection;

    public Column(Section section) {
        if (section == null) {
            System.out.println("you must specify a valid Section");
            return;
        }
        this.section = section; // связываем колонку с конкретной секцией
        Integer columnsCount = section.getColumnsCount(); // берем количество колонок в секции
        columnNumber = columnsCount + 1; // устанавливаем номер колонки
        section.setColumnsCount(columnsCount + 1); // увеличиваем количество колонок в секции
    }

    public Column(SubSection subSection) {
        if (subSection == null) {
            System.out.println("you must specify a valid SubSection");
            return;
        }
        this.subSection = subSection; // связываем колонку с конкретной подсекцией
        Integer columnsCount = subSection.getColumnsCount(); // берем количество колонок в подсекции
        columnNumber = columnsCount + 1; // устанавливаем номер колонки
        subSection.setColumnsCount(columnsCount + 1); // увеличиваем количество колонок в подсекции
    }

}
