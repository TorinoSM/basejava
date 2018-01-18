package com.home.webapp;

import com.home.webapp.model.Resume;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        Resume resume = new Resume("test_reflection");
        Class clazz = resume.getClass();
        Method method = clazz.getMethod("toString", null);
        String show_result = (String) method.invoke(resume, null);
        System.out.println(show_result);
    }
}
