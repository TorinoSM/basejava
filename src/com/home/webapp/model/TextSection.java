package com.home.webapp.model;

import java.io.Serializable;
import java.util.Objects;

public class TextSection extends Section {
    private static final long serialVersionUID = 1L;
    private final String content;

    public TextSection(String content) {
        Objects.requireNonNull(content, "Content argument must not be null");
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return content;
    }

}
