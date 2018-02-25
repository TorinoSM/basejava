package com.home;


import javafx.util.Pair;

public class plays {

    static class Up<T> {
        Up(T obj) {
            System.out.println("in constructor of Up class");
            System.out.println(obj.getClass().getCanonicalName());
        }
    }

    static class Down extends Up {
        Down() {
            super("");
            System.out.println("in constructor of Down class");
        }
    }

    static class Main {
        public static void main(String[] args) {
            new Down();
            System.out.println("-----------");
            new Up<>(Integer.valueOf(10));
            new Up<>(10);
            new Up<Number>(10);
            new Up<>("string");
            new Up<Object>("string");
            new Up<>(new Object());

            Integer i = 10;
            String s = i.toString();
            System.out.println(s);
            Pair pair;
        }
    }
}
