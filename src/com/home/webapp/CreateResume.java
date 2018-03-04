package com.home.webapp;

import com.home.webapp.model.*;

import java.util.HashMap;

import static com.home.webapp.model.SectionType.EDUCATION;
import static com.home.webapp.model.SectionType.TITLE;

public class CreateResume {

    public static void main(String[] args) {

        Resume k = new Resume("Кислин Григорий");

        HashMap<SectionType, Section> sections = k.getSections();

        // заполняем секцию ФИО + Контакты
        Section TITLE_section = sections.get(TITLE);
        Row TITLE_row_1 = TITLE_section.addRow();
        TITLE_row_1.setContent("Тел.: +7(921) 855-0482");
        Row TITLE_row_2 = TITLE_section.addRow();
        TITLE_row_2.setContent("Skype: grigory.kislin");

        // заполняем секцию Образование
        Section EDUCATION_section = sections.get(EDUCATION);

        ////////////////////////////

        SubSection EDUCATION_subSection_1 = EDUCATION_section.addSubSection();
        EDUCATION_subSection_1.setTitle("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики");

        Row EDUCATION_row_1 = EDUCATION_subSection_1.addRecord(2);
        EDUCATION_row_1.getCell(0).setContent("09/1993 - 07/1996");
        EDUCATION_row_1.getCell(1).setContent("Аспирантура (программист С, С++)");

        Row EDUCATION_row_2 = EDUCATION_subSection_1.addRecord(2);
        EDUCATION_row_2.getCell(0).setContent("09/1987 - 07/1993");
        EDUCATION_row_2.getCell(1).setContent("Инженер (программист Fortran, C)");

        //////////////////////////////

        SubSection EDUCATION_subSection_2 = EDUCATION_section.addSubSection();
        EDUCATION_subSection_2.setTitle("Заочная физико-техническая школа при МФТИ");

        Row EDUCATION_row_2_1 = EDUCATION_subSection_2.addRecord(2);
        EDUCATION_row_2_1.getCell(0).setContent("09/1984 - 06/1987");
        EDUCATION_row_2_1.getCell(1).setContent("Закончил с отличием");

        System.out.println();

    }
}
