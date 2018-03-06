package com.home.webapp;

import java.io.File;
import java.io.IOException;

public class MainFile {

    public static void main(String[] args) throws Exception {
        File rootDir = new File("./src");
        System.out.println(rootDir.getCanonicalPath());
        drill(rootDir);
    }

    static void drill(File FSEntity) throws IOException {
        if (FSEntity.isFile()) {
            System.out.println("\t" + FSEntity.getName());
        }
        if (FSEntity.isDirectory()) {
//            System.out.println(FSEntity.getName());
            File[] subEntities = FSEntity.listFiles();
            for (File subEntity : subEntities) {
                drill(subEntity);
            }
        }
    }
}
