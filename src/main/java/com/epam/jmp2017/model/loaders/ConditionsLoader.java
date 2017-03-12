package com.epam.jmp2017.model.loaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.epam.jmp2017.constants.BaseConstants;
import com.epam.jmp2017.constants.Messages;
import com.epam.jmp2017.model.annotations.ConditionDisplayName;
import com.epam.jmp2017.model.conditions.Condition;


public class ConditionsLoader extends ClassLoader {
    private String path;
    private static List<Class<?>> loadedClasses = new ArrayList<>();

    //[module-3] Singleton
    private static ConditionsLoader instance = null;

    private static final Logger LOG = Logger.getLogger(ConditionsLoader.class.getName());

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
                    Path actionsPath;
                    try {
                        actionsPath = Paths.get(actionsUrl.toURI());
                        byte[] b = Files.readAllBytes(actionsPath);
                        result = defineClass(className, b, 0, b.length);
                        loadedClasses.add(result);
                    } catch (URISyntaxException e) {
                        LOG.log(Level.SEVERE, e.getMessage(), e);
                    }
                }
            }
            return result;
        } catch (FileNotFoundException e) {
            LOG.log(Level.SEVERE, "Could not find file '" + path + className.replace('.', '/') + ".class'", e);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, Messages.ERROR_READING_CLASS_FILE, e);
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

    public static void cacheConditions() {
        File dir = new File(BaseConstants.PATH_CONDITIONS + BaseConstants.PACKAGE_NAME.replace(".", "/") + "/");
        try {
            for (File file : dir.listFiles()) {
                loadCondition(BaseConstants.PACKAGE_NAME + "." + file.getName().replace(".class", ""));
            }
        } catch (NullPointerException e) {
            LOG.log(Level.SEVERE, Messages.ERROR_READING_CONDITIONS, e);
        }
    }

    public static Class<?> loadCondition(String className) {
        Class<?> result = null;
        try {
            result = ConditionsLoader.getInstance().loadClass(className);
            if (result != null &&
                  (!Condition.class.isAssignableFrom(result) || !result.isAnnotationPresent(ConditionDisplayName.class))) {
                result = null;
            }

        } catch (ClassNotFoundException e) {
            LOG.log(Level.SEVERE, "Class " + className + " not found.", e);
        }
        return result;
    }
}
