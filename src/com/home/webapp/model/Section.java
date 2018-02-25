package com.home.webapp.model;

public class Section {

    // количество колонок в секции
    private Integer columnsCount;
    // количество подсекций в секции (если есть)
    private Integer subSectionsCount;
    // резюме, к которому прикреплена секция
    private Resume resume;
    // тип секции
    private SectionType sectionType;

    public Section(Resume resume, SectionType sectionType) {
        if (resume == null) {
            System.out.println("you must specify a valid Resume");
            return;
        }
        this.resume = resume; // связываем секцию с конкретным резюме
        this.sectionType = sectionType; // связываем секцию с конкретным типом секции
    }

    public Resume getResume() {
        return resume;
    }

    public SectionType getSectionType() {
        return sectionType;
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
