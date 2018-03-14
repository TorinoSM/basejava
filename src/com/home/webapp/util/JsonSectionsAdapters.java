package com.home.webapp.util;

import com.google.gson.*;

import java.lang.reflect.Type;

public class JsonSectionsAdapters<Section> implements JsonSerializer<Section>, JsonDeserializer<Section> {

    @Override
    public JsonElement serialize(Section src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject wrapper = new JsonObject();
        wrapper.addProperty("sectionClassType", src.getClass().getName());
        JsonElement element = context.serialize(src);
        wrapper.add("sectionData", element);
        return wrapper;
    }

    @Override
    public Section deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = (JsonObject) json;
        JsonElement sectionClassType = jsonObject.get("sectionClassType");
        JsonElement sectionData = jsonObject.get("sectionData");
        Type type;
        try {
            type = Class.forName(sectionClassType.getAsString());
        } catch (ClassNotFoundException e) {
            throw new JsonParseException("Class not found while deserializing JSON", e);
        }
        return context.deserialize(sectionData, type);
    }
}