package com.home.webapp.model;

/**
 * com.urise.webapp.model.com.home.webapp.model.Resume class
 */
public class Resume {

    // Unique identifier
    private String uuid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return this.getUuid().equals(resume.getUuid()); // Таня обрати пжл внимание на это сравнение. Где-то читал, что при работе с БД возможна ситуация, когда при прямом доступе к полям возвращаются прокси-объекты из-за ленивой инициализации, а не реальные значения полей. Это правильное решение на будущее, или ненужное усложнение? Что с точки зрения производительности?
    }

    @Override
    public int hashCode() {
        String uuid = this.getUuid();
        char[] ch = uuid.toCharArray();
        int len = uuid.length();
        int hash = 0;
        for (int i = 0; i < len; i++) {
            hash = hash * 17 + ch[i];
        }
        return hash;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return uuid;
    }
}
