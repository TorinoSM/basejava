package com.home.webapp.storage;

import com.home.webapp.exception.ExistStorageException;
import com.home.webapp.exception.NotExistStorageException;
import com.home.webapp.model.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.*;


public abstract class AbstractStorageTest {

    protected Storage storage;
    private Resume ruuid1 = new Resume("uuid1", "Григорий Кислин");
    private Resume ruuid2 = new Resume("uuid2", "name2");
    private Resume ruuid3 = new Resume("uuid3", "name3");
    private Resume ruuid4 = new Resume("uuid4", "name4");
    private Resume ruuid5 = new Resume("uuid5", "name5");


    AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();

        // заполняем Контакты
        Map<ContactType, String> contacts = ruuid1.getContacts();
        contacts.put(ContactType.PHONE, "+7(921) 855-0482");
        contacts.put(ContactType.SKYPE, "grigory.kislin");
        contacts.put(ContactType.MAIL, "gkislin@yandex.ru");
        contacts.put(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        contacts.put(ContactType.GITHUB, "https://github.com/gkislin");
        contacts.put(ContactType.GITHUB, "https://stackoverflow.com/users/548473/gkislin");
        contacts.put(ContactType.HOME_PAGE, "http://gkislin.ru/");

        // перечень секций
        Map<SectionType, Section> sections = ruuid1.getSections();

        // заполняем Позицию
        sections.put(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));

        // заполняем Личные качества
        sections.put(SectionType.PERSONAL, new TextSection(" Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        // заполняем Достижения
        String[] s1 = {"С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.",
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.",
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).",
                "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа."};

        List<String> c1 = new ArrayList<>();
        Collections.addAll(c1, s1);
        sections.put(SectionType.ACHIEVEMENT, new ListSection(c1));

        // заполняем Квалификацию
        String[] s2 = {"JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,",
                "MySQL, SQLite, MS SQL, HSQLDB",
                "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,",
                "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,",
                "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).",
                "Python: Django.",
                "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js",
                "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka",
                "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.",
                "Инструменты: Maven + plugin development, Gradle, настройка Ngnix,",
                "администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.",
                "Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектирования, архитектурных шаблонов, UML, функционального программирования",
                "Родной русский, английский \"upper intermediate\""};

        List<String> c2 = new ArrayList<>();
        Collections.addAll(c2, s2);
        sections.put(SectionType.QUALIFICATIONS, new ListSection(c2));

        // заполняем Опыт работы
        Organization o1 = new Organization(
                "Java Online Projects",
                "http://javaops.ru/",
                LocalDate.of(2013, 10, 1),
                LocalDate.now(),
                "Автор проекта.",
                "Создание, организация и проведение Java онлайн проектов и стажировок.");

        Organization o2 = new Organization(
                "Wrike",
                "https://www.wrike.com/ru/",
                LocalDate.of(2014, 10, 1),
                LocalDate.of(2016, 1, 1),
                "Старший разработчик (backend)",
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");

        Organization o3 = new Organization(
                "RIT Center",
                "",
                LocalDate.of(2012, 4, 1),
                LocalDate.of(2014, 10, 1),
                "Java архитектор",
                "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python");

        Organization o4 = new Organization(
                "Luxoft (Deutsche Bank)",
                "http://www.luxoft.ru/",
                LocalDate.of(2013, 10, 1),
                LocalDate.of(2016, 1, 1),
                "Автор проекта.",
                "Создание, организация и проведение Java онлайн проектов и стажировок.");

        Organization o5 = new Organization(
                "Yota",
                "https://www.yota.ru/",
                LocalDate.of(2008, 6, 1),
                LocalDate.of(2010, 12, 1),
                "Ведущий специалист",
                "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)");

        Organization o6 = new Organization(
                "Enkata",
                "enkata.com",
                LocalDate.of(2007, 3, 1),
                LocalDate.of(2008, 6, 1),
                "Разработчик ПО",
                "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).");

        Organization o7 = new Organization(
                "Siemens AG",
                "https://www.siemens.com/ru/ru/home.html",
                LocalDate.of(2005, 1, 1),
                LocalDate.of(2007, 2, 1),
                "Разработчик ПО",
                "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).");

        Organization o8 = new Organization(
                "Alcatel",
                "http://www.alcatel.ru/",
                LocalDate.of(1997, 9, 1),
                LocalDate.of(2005, 1, 1),
                "Инженер по аппаратному и программному тестированию",
                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).");

        Organization[] o = {o1, o2, o3, o4, o5, o6, o7, o8};
        List<Organization> organizations = new ArrayList<>();
        Collections.addAll(organizations, o);
        sections.put(SectionType.EXPERIENCE, new OrganizationSection(organizations));

        // заполняем Образование
        Organization sch1 = new Organization(
                "Coursera",
                "https://www.coursera.org/learn/progfun1",
                LocalDate.of(2013, 3, 1),
                LocalDate.of(2013, 5, 1),
                "\"Functional Programming Principles in Scala\" by Martin Odersky",
                "");

        Organization sch2 = new Organization(
                "Luxoft",
                "http://www.luxoft-training.ru/kurs/obektno-orientirovannyy__analiz_is_kontseptualnoe_modelirovanie_na_uml_dlya_sistemnyh_analitikov_.html",
                LocalDate.of(2011, 3, 1),
                LocalDate.of(2011, 4, 1),
                "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"",
                "");

        Organization sch3 = new Organization(
                "Siemens AG",
                "https://www.siemens.com/ru/ru/home.html",
                LocalDate.of(2005, 4, 1),
                LocalDate.of(2005, 4, 1),
                "3 месяца обучения мобильным IN сетям (Берлин)",
                "");

        Organization sch4 = new Organization(
                "Alcatel",
                "http://www.alcatel.ru/",
                LocalDate.of(1997, 9, 1),
                LocalDate.of(1998, 3, 1),
                "6 месяцев обучения цифровым телефонным сетям (Москва)",
                "");

        Organization sch5 = new Organization(
                "Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                "http://www.ifmo.ru/ru/",
                LocalDate.of(1993, 9, 1),
                LocalDate.of(1996, 7, 1),
                "Аспирантура (программист С, С++)",
                "");
        sch5.addRecord(
                LocalDate.of(1987, 9, 1),
                LocalDate.of(1993, 7, 1),
                "Инженер (программист Fortran, C)"
        );

        Organization sch6 = new Organization(
                "Заочная физико-техническая школа при МФТИ",
                "http://www.school.mipt.ru/",
                LocalDate.of(1984, 9, 1),
                LocalDate.of(1987, 6, 1),
                "Закончил с отличием",
                "");

        Organization[] sch = {sch1, sch2, sch3, sch4, sch5, sch6};
        List<Organization> schools = new ArrayList<>();
        Collections.addAll(schools, sch);
        sections.put(SectionType.EDUCATION, new OrganizationSection(schools));

        // закончили заполнение резюме

        storage.save(ruuid1);
        storage.save(ruuid3);
        storage.save(ruuid2);
        storage.save(ruuid4);
    }

    @After
    public void tearDown() throws Exception {
        storage = null;
    }

    @Test()
    public void saveNotExist() throws Exception {

        storage.save(ruuid5);
        Assert.assertEquals(ruuid1, storage.get("uuid1"));
        Assert.assertEquals(ruuid2, storage.get("uuid2"));
        Assert.assertEquals(ruuid3, storage.get("uuid3"));
        Assert.assertEquals(ruuid4, storage.get("uuid4"));
        Assert.assertEquals(ruuid5, storage.get("uuid5"));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(ruuid1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveNull() throws Exception {
        storage.save(null);
    }


    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("test_name");
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteExist() throws Exception {
        storage.delete("uuid1");

        Assert.assertEquals(ruuid2, storage.get("uuid2"));
        Assert.assertEquals(ruuid3, storage.get("uuid3"));
        Assert.assertEquals(ruuid4, storage.get("uuid4"));

        storage.get("uuid1");
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertTrue(storage.size() == 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateNull() throws Exception {
        storage.update(null);
    }

    @Test
    public void updateExist() throws Exception {
        storage.update(ruuid1);

        Assert.assertEquals(ruuid1, storage.get("uuid1"));
        Assert.assertEquals(ruuid2, storage.get("uuid2"));
        Assert.assertEquals(ruuid3, storage.get("uuid3"));
        Assert.assertEquals(ruuid4, storage.get("uuid4"));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(ruuid5);
    }

    @Test
    public void getExist() throws Exception {
        Resume resume = storage.get("uuid1");
        Assert.assertTrue(ruuid1.equals(resume));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("uuid_not_exists");
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(storage.size(), 4);
    }

    @Test()
    public void getAllSorted() throws Exception {

        List<Resume> list = storage.getAllSorted();
        Assert.assertEquals(list, Arrays.asList(ruuid2, ruuid3, ruuid4, ruuid1));
        Assert.assertEquals(4, list.size());
    }

}