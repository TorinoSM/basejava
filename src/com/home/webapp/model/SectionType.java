package com.home.webapp.model;

public enum SectionType {

    TITLE("ФИО и контакты"),
    PERSONAL("Личные качества"),
    OBJECTIVE("Цели"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
