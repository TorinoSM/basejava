package com.home.webapp.model;

public enum SectionEnum {
    PERSONAL("ФИО"),
    OBJECTIVE("Цели"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private String title;

    SectionEnum(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
