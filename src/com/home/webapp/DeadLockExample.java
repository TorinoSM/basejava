package com.home.webapp;

public class DeadLockExample {

    private static final Object LOCK = new Object();
    private static boolean threadOneIsLocked = false;
    private static boolean threadTwoIsLocked = false;

    public static void main(String[] args) {
        new Thread("threadOne") {
            @Override
            public void run() {
                test1();
            }
        }.start();

        new Thread("threadTwo") {
            @Override
            public void run() {
                test2();
            }
        }.start();
    }

    private static void test1() {
        synchronized (LOCK) {
            while (!threadOneIsLocked) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                }
            }
            threadTwoIsLocked = true;
            LOCK.notifyAll();
        }
    }

    private static void test2() {
        synchronized (LOCK) {
            while (!threadTwoIsLocked) {
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                }
            }
            threadOneIsLocked = true;
            LOCK.notifyAll();
        }
    }
}