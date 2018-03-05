package com.home.webapp;

import com.home.webapp.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.home.webapp.model.SectionType.*;

public class CreateResume {

    public static void main(String[] args) {
        Resume r = new Resume("Кислин Григорий");

        r.getContacts().put(ContactType.MOBILE, "+7(921) 855-0482");

        r.getSections().put(PERSONAL, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));


        List<String> initAchiev = new ArrayList<>();
        initAchiev.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        initAchiev.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");

        r.getSections().put(ACHIEVEMENT, new ListSection(initAchiev));


        List<Organization> orgs = new ArrayList<>();
        Organization org = new Organization(
                "Java Online Projects",
                "http://javaops.ru/",
                LocalDate.of(2013, 10, 1),
                LocalDate.now(),
                "Автор проекта.",
                "Создание, организация и проведение Java онлайн проектов и стажировок.");
        orgs.add(org);

        r.getSections().put(EXPERIENCE, new OrganizationSection(orgs));


        List<Organization> schools = new ArrayList<>();
        Organization school = new Organization(
                "Java Online Projects",
                "http://javaops.ru/",
                LocalDate.of(2013, 10, 1),
                LocalDate.now(),
                "Автор проекта.",
                "Создание, организация и проведение Java онлайн проектов и стажировок.");
        schools.add(school);

        r.getSections().put(EDUCATION, new OrganizationSection(schools));
        school.addRecord(
                LocalDate.of(1987, 9, 1),
                LocalDate.of(1993, 7, 1),
                "Инженер (программист Fortran, C)"
        );

        System.out.println(r);


    }
}
