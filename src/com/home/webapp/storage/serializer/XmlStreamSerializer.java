package com.home.webapp.storage.serializer;

import com.home.webapp.exception.StorageException;
import com.home.webapp.model.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerializer implements StreamSerializerStrategy {

    private Marshaller marshaller;
    private Unmarshaller unmarshaller;

    {
        try {
            JAXBContext context = JAXBContext.newInstance(Resume.class, Link.class, ListSection.class, Organization.class, Organization.Record.class, OrganizationSection.class, TextSection.class);
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            unmarshaller = context.createUnmarshaller();
        } catch (JAXBException e) {
            throw new StorageException("Error while initializing JAXB context", "", e);
        }
    }

    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            marshaller.marshal(resume, writer);
        } catch (JAXBException e) {
            throw new StorageException("Error while marshalling with JAXB", resume.getUuid(), e);
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            return (Resume) unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            throw new StorageException("Error while unmarshalling with JAXB", "", e);
        }
    }
}
