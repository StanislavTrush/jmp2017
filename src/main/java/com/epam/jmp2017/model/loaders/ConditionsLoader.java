package com.epam.jmp2017.model.loaders;

import com.epam.jmp2017.constants.BaseConstants;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class ConditionsLoader extends ClassLoader {
    private String path;
    private static List<Class<?>> loadedClasses = new ArrayList<>();

    //[module-3] Singleton
    private static volatile ConditionsLoader instance = null;

    public static ConditionsLoader getInstance() {
        if (instance == null) {
            synchronized (ConditionsLoader.class) {
                if (instance == null) {
                    instance = new ConditionsLoader(BaseConstants.PATH_CONDITIONS, ClassLoader.getSystemClassLoader());
                }
            }
        }
        return instance;
    }

    private ConditionsLoader() {
    }

    private ConditionsLoader(String path, ClassLoader parent) {
        super(parent);
        this.path = path;
    }

    @Override
    public Class<?> loadClass(String className) throws ClassNotFoundException {
        try {
            Class<?> result = findClass(className);
            if (result == null) {
                result = super.loadClass(className);
                if (result == null) {
                    if (!className.contains(BaseConstants.PACKAGE_NAME)) {
                        return getClass().getClassLoader().loadClass(className);
                    }
                    URL actionsUrl = new File(path + className.replace('.', '/') + ".class").toURI().toURL();
                    Path path;
                    try {
                        path = Paths.get(actionsUrl.toURI());
                        byte b[] = Files.readAllBytes(path);
                        result = defineClass(className, b, 0, b.length);
                        loadedClasses.add(result);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            }
            return result;
        } catch (FileNotFoundException ex) {
            System.out.println("Could not find file '" + path + className.replace('.', '/') + ".class'");
        } catch (IOException ex) {
            System.out.println("Error during class file reading");
        }
        return null;
    }

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        Class<?> result = findLoadedClass(className);
        for (Class<?> loadedClass : loadedClasses) {
            if (loadedClass.getName().equals(className)) {
                result = loadedClass;
                break;
            }
        }

        return result;
    }
}
