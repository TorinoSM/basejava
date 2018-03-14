package com.home.webapp.storage.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.home.webapp.model.Resume;
import com.home.webapp.model.Section;
import com.home.webapp.util.JsonSectionsAdapters;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonStreamSerializer implements StreamSerializerStrategy {

    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Section.class, new JsonSectionsAdapters<>())
            .create();


    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            gson.toJson(resume, writer);
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(inputStream)) {
            return gson.fromJson(reader, Resume.class);
        }
    }
}
