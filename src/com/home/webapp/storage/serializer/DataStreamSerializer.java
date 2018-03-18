package com.home.webapp.storage.serializer;

import com.home.webapp.model.*;

import java.io.*;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.home.webapp.model.SectionType.*;

public class DataStreamSerializer implements StreamSerializerStrategy {

    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {

            dataOutputStream.writeUTF(resume.getUuid());
            dataOutputStream.writeUTF(resume.getFullName());

            Map<ContactType, String> contacts = resume.getContacts();
            dataOutputStream.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> contact : contacts.entrySet()) {
                dataOutputStream.writeUTF(contact.getKey().toString());
                dataOutputStream.writeUTF(contact.getValue());
            }

            Map<SectionType, Section> sections = resume.getSections();
            dataOutputStream.writeInt(sections.size()); // кол секций
            for (Map.Entry<SectionType, Section> sectionEntry : sections.entrySet()) {
                SectionType sectionType = sectionEntry.getKey();
                dataOutputStream.writeUTF(sectionType.toString()); // тип секции
                Section section = sectionEntry.getValue();
                if (sectionType.equals(OBJECTIVE) || sectionType.equals(PERSONAL)) {
                    TextSection textSection = (TextSection) section;
                    dataOutputStream.writeUTF(textSection.getContent()); // content of TextSection
                }
                if (sectionType.equals(ACHIEVEMENT) || sectionType.equals(QUALIFICATIONS)) {
                    ListSection listSection = (ListSection) section;
                    List<String> items = listSection.getItems();
                    dataOutputStream.writeInt(items.size());
                    for (String item : items) {
                        dataOutputStream.writeUTF(item);
                    }
                }
                if (sectionType.equals(EXPERIENCE) || sectionType.equals(EDUCATION)) {
                    OrganizationSection organizationSection = (OrganizationSection) section;
                    List<Organization> organizations = organizationSection.getOrganizations();
                    dataOutputStream.writeInt(organizations.size()); // кол организаций
                    for (Organization organization : organizations) {
                        Link org = organization.getOrganisation();
                        dataOutputStream.writeUTF(org.getName());
                        dataOutputStream.writeUTF(org.getUrl());
                        List<Organization.Record> records = organization.getRecords();
                        dataOutputStream.writeInt(records.size()); // кол записей
                        for (Organization.Record record : records) {
                            dataOutputStream.writeUTF(record.getTitle());
                            dataOutputStream.writeUTF(record.getDescription());
                            dataOutputStream.writeInt(record.getStartDate().getYear());
                            dataOutputStream.writeInt(record.getStartDate().getMonthValue());
                            dataOutputStream.writeInt(record.getEndtDate().getYear());
                            dataOutputStream.writeInt(record.getEndtDate().getMonthValue());
                        }
                    }
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {

        Resume resume;

        try (DataInputStream dataInputStream = new DataInputStream(inputStream)) {

            resume = new Resume(dataInputStream.readUTF(), dataInputStream.readUTF());


            int size = dataInputStream.readInt(); // кол контактов
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dataInputStream.readUTF()), dataInputStream.readUTF());
            }


            size = dataInputStream.readInt(); // кол секций
            for (int i = 0; i < size; i++) {
                SectionType sectionType = SectionType.valueOf(dataInputStream.readUTF());
                if (sectionType.equals(OBJECTIVE) || sectionType.equals(PERSONAL)) {
                    resume.addSection(sectionType, new TextSection(dataInputStream.readUTF()));
                }
                if (sectionType.equals(ACHIEVEMENT) || sectionType.equals(QUALIFICATIONS)) {
                    size = dataInputStream.readInt(); // кол элементов в ListSection
                    List<String> items = new ArrayList<>();
                    for (int j = 0; j < size; j++) {
                        items.add(dataInputStream.readUTF());
                    }
                    resume.addSection(sectionType, new ListSection(items));
                }
                if (sectionType.equals(EXPERIENCE) || sectionType.equals(EDUCATION)) {
                    size = dataInputStream.readInt(); // кол элементов в OrganisationSection
                    List<Organization> organizations = new ArrayList<>();
                    for (int j = 0; j < size; j++) {
                        String name = dataInputStream.readUTF();
                        String url = dataInputStream.readUTF();
                        int recordsAmount = dataInputStream.readInt(); // кол записей
                        List<Organization.Record> record = new ArrayList<>();
                        for (int k = 0; k < recordsAmount; k++) {
                            String title = dataInputStream.readUTF();
                            String description = dataInputStream.readUTF();
                            int startYear = dataInputStream.readInt();
                            int startMonth = dataInputStream.readInt();
                            int endYear = dataInputStream.readInt();
                            int endMonth = dataInputStream.readInt();
                            record.add(new Organization.Record(startYear, Month.of(startMonth), endYear, Month.of(endMonth), title, description));
                        }
                        organizations.add(new Organization(new Link(name, url), record));
                    }
                    resume.addSection(sectionType, new OrganizationSection(organizations));
                }
            }
        }
        return resume;
    }
}
