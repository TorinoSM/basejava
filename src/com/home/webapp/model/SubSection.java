package com.home.webapp.model;


public class SubSection {

    // Номер подсекции
    private Integer subSectionNumber;
    // Секция  с которой связана колонка
    private Section section;
    // количество колонок в подсекции
    private Integer columnsCount;

    public SubSection(Section section) {
        if (section == null) {
            System.out.println("you must specify a valid Section");
            return;
        }
        this.section = section; // связываем подсекцию с конкретной секцией
        Integer subSectionsCount = section.getSubSectionsCount(); // берем количество подсекций в секции
        subSectionNumber = subSectionsCount + 1; // устанавливаем номер подсекции
        section.setColumnsCount(subSectionsCount + 1); // увеличиваем количество подсекций в секции
    }

    public Integer getSubSectionNumber() {
        return subSectionNumber;
    }

    public Section getSection() {
        return section;
    }

    public Integer getColumnsCount() {
        return columnsCount;
    }

    public void setColumnsCount(Integer columnsCount) {
        this.columnsCount = columnsCount;
    }


}
