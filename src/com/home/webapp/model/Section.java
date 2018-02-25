package com.home.webapp.model;

public class Section {

    // количество колонок в секции
    private Integer columnsCount;
    // количество подсекций в секции (если есть)
    private Integer subSectionsCount;
    // резюме, к которому прикреплена секция
    private Resume resume;
    // тип секции, которому соответствует секция
    private SectionEnum sectionEnum;

    public Section(Resume resume, SectionEnum sectionEnum) {
        if (resume == null) {
            System.out.println("you must specify a valid Resume");
            return;
        }
        this.resume = resume; // связываем секцию с конкретным резюме
        this.sectionEnum = sectionEnum; // связываем секцию с конкретным типом секции
    }

    public Resume getResume() {
        return resume;
    }

    public SectionEnum getSectionEnum() {
        return sectionEnum;
    }

    public Integer getColumnsCount() {
        return columnsCount;
    }

    public void setColumnsCount(Integer columnsCount) {
        this.columnsCount = columnsCount;
    }

    public Integer getSubSectionsCount() {
        return subSectionsCount;
    }

    public void setSubSectionsCount(Integer subSectionsCount) {
        this.subSectionsCount = subSectionsCount;
    }


}
