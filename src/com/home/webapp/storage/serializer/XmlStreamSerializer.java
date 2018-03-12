package com.home.webapp.storage.serializer;

import com.home.webapp.exception.StorageException;
import com.home.webapp.model.Resume;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class XmlStreamSerializer implements StreamSerializerStrategy {


    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try {
            JAXBContext context = JAXBContext.newInstance(Resume.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(resume, outputStream);
        } catch (JAXBException e) {
            throw new StorageException("Error while marshalling with JAXB", resume.getUuid(), e);
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try {
            JAXBContext context = JAXBContext.newInstance(Resume.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (Resume) unmarshaller.unmarshal(inputStream);
        } catch (JAXBException e) {
            throw new StorageException("Error while unmarshalling with JAXB", "", e);
        }
    }
}
