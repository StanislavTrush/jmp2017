package com.epam.jmp2017.util.workers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FileWorker {
    private static Properties properties = null;

    private static void loadProperties() {
        InputStream fis;
        if (properties == null) {
            synchronized (FileWorker.class) {
                if (properties == null) {
                    try {
                        properties = new Properties();
                        fis = FileWorker.class.getClassLoader().getResourceAsStream("config/config.properties");
                        properties.load(fis);
                    } catch (IOException e) {
                        System.err.println("Can not load properties file.");
                    }
                }
            }
        }
    }

    public static String getProperty(String name) {
        if (properties == null) {
            loadProperties();
        }
        return properties.getProperty(name);
    }
}
