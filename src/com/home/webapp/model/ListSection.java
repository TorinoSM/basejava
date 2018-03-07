package com.home.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListSection extends Section {
    private final List<String> items;

    public ListSection(List<String> items) {
        Objects.requireNonNull(items, "Items argument must not be null");
        this.items = items;
    }

    public ListSection(String... items) {
        this(Arrays.asList(items));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return items.equals(that.items);

    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }

    public List<String> getItems() {
        return items;
    }

}