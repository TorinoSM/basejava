package com.home.webapp.storage;

import com.home.webapp.exception.ExistStorageException;
import com.home.webapp.exception.NotExistStorageException;
import com.home.webapp.model.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public abstract class AbstractStorageTest {

    protected static final File STORAGE_DIR = new File("C:\\Users\\name2015\\basejava\\storage");

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
        contacts.put(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473/gkislin");
        contacts.put(ContactType.HOME_PAGE, "http://gkislin.ru/");

        // перечень секций
//        Map<SectionType, Section> sections = ruuid1.getSections();

        // заполняем Позицию
        ruuid1.addSection(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));

        // заполняем Личные качества
        ruuid1.addSection(SectionType.PERSONAL, new TextSection(" Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        // заполняем Достижения

        ruuid1.addSection(SectionType.ACHIEVEMENT, new ListSection(
                "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.",
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.",
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).",
                "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа."
        ));

        // заполняем Квалификацию

        ruuid1.addSection(SectionType.QUALIFICATIONS, new ListSection(
                "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
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
                "Родной русский, английский \"upper intermediate\""
        ));

        // заполняем Опыт работы

        ruuid1.addSection(SectionType.EXPERIENCE, new OrganizationSection(
                new Organization("Java Online Projects",
                        "http://javaops.ru/",
                        new Organization.Record(2013, Month.of(10), "Автор проекта.",
                                "Создание, организация и проведение Java онлайн проектов и стажировок.")),
                new Organization("Wrike",
                        "https://www.wrike.com/ru/",
                        new Organization.Record(2014, Month.of(10), 2016, Month.of(1), "Старший разработчик (backend)",
                                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.")),
                new Organization("RIT Center",
                        "",
                        new Organization.Record(2012, Month.of(4), 2014, Month.of(10), "Java архитектор",
                                "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python")),
                new Organization("Luxoft (Deutsche Bank)",
                        "http://www.luxoft.ru/",
                        new Organization.Record(2013, Month.of(10), 2016, Month.of(1), "Автор проекта.",
                                "Создание, организация и проведение Java онлайн проектов и стажировок.")),
                new Organization("Yota",
                        "https://www.yota.ru/",
                        new Organization.Record(2008, Month.of(6), 2010, Month.of(12), "Ведущий специалист",
                                "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)")),
                new Organization("Enkata",
                        "enkata.com",
                        new Organization.Record(2007, Month.of(3), 2008, Month.of(6), "Разработчик ПО",
                                "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).")),
                new Organization("Siemens AG",
                        "https://www.siemens.com/ru/ru/home.html",
                        new Organization.Record(2005, Month.of(1), 2007, Month.of(2), "Разработчик ПО",
                                "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).")),
                new Organization("Alcatel",
                        "http://www.alcatel.ru/",
                        new Organization.Record(1997, Month.of(9), 2005, Month.of(1), "Инженер по аппаратному и программному тестированию",
                                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)."))
        ));


        // заполняем Образование

        ruuid1.addSection(SectionType.EDUCATION,
                new OrganizationSection(
                        new Organization("Coursera", "https://www.coursera.org/learn/progfun1",
                                new Organization.Record(2013, Month.of(3), 2013, Month.of(5), "\"Functional Programming Principles in Scala\" by Martin Odersky")),
                        new Organization("Luxoft", "http://www.luxoft-training.ru/kurs/obektno-orientirovannyy__analiz_is_kontseptualnoe_modelirovanie_na_uml_dlya_sistemnyh_analitikov_.html",
                                new Organization.Record(2011, Month.of(3), 2011, Month.of(4), "Курс \\\"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\\")),
                        new Organization("Siemens AG", "https://www.siemens.com/ru/ru/home.html",
                                new Organization.Record(2005, Month.of(1), 2005, Month.of(4), "3 месяца обучения мобильным IN сетям (Берлин)")),
                        new Organization("Alcatel", "http://www.alcatel.ru/",
                                new Organization.Record(1997, Month.of(9), 1998, Month.of(3), "6 месяцев обучения цифровым телефонным сетям (Москва)")),
                        new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru/ru/",
                                new Organization.Record(1993, Month.of(9), 1996, Month.of(7), "Аспирантура (программист С, С++)"),
                                new Organization.Record(1987, Month.of(9), 1993, Month.of(7), "Инженер (программист Fortran, C)")),
                        new Organization("Заочная физико-техническая школа при МФТИ", "http://www.school.mipt.ru/",
                                new Organization.Record(1984, Month.of(9), 1987, Month.of(6), "Закончил с отличием"))
                )
        );

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
        List<Resume> expectedList = new ArrayList<>(Arrays.asList(ruuid2, ruuid3, ruuid4, ruuid1));
        Assert.assertEquals(list, expectedList);
        Assert.assertEquals(4, list.size());
    }

}