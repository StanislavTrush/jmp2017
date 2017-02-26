package com.epam.jmp2017.model.loaders;

import com.epam.jmp2017.util.FileWorker;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class ConditionsLoader extends ClassLoader {
    private String path;
    private List<Class<?>> loadedClasses = new ArrayList<>();

    public ConditionsLoader(String path, ClassLoader parent) {
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
                    FileWorker fileWorker = new FileWorker();
                    if (!className.contains("com.epam.jmp2017.model.conditions.impl")) {
                        return fileWorker.getClass().getClassLoader().loadClass(className);
                    }
                    URL actionsUrl = fileWorker.getClass().getClassLoader().getResource(path + className.replace('.', '/') + ".class");
                    if (actionsUrl != null) {
                        byte b[] = fileWorker.fetchClassFromFS(actionsUrl.getFile());
                        result = defineClass(className, b, 0, b.length);
                        loadedClasses.add(result);
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
