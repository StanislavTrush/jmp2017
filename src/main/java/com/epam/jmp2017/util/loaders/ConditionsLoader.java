package com.epam.jmp2017.util.loaders;

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
import com.epam.jmp2017.model.conditions.CompositeCondition;
import org.springframework.beans.factory.annotation.Required;

public class ConditionsLoader extends ClassLoader {
    private String path;
    private List<Class<?>> loadedClasses = new ArrayList<>();

    private final Logger LOG = Logger.getLogger(ConditionsLoader.class.getName());

    public ConditionsLoader() {
        super(ClassLoader.getSystemClassLoader());
    }

    public ConditionsLoader(String path, ClassLoader parent) {
        super(parent);
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Required
    public void setPath(String path) {
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

    public void cacheConditions() {
        File dir = new File(BaseConstants.PATH_CONDITIONS + BaseConstants.PACKAGE_NAME.replace(".", "/") + "/");
        try {
            for (File file : dir.listFiles()) {
                loadCondition(BaseConstants.PACKAGE_NAME + "." + file.getName().replace(".class", ""));
            }
        } catch (NullPointerException e) {
            LOG.log(Level.SEVERE, Messages.ERROR_READING_CONDITIONS, e);
        }
    }

    public Class<?> loadCondition(String className) {
        Class<?> result = null;
        if (className == null || className.isEmpty()) {
            className = "com.epam.jmp2017.model.conditions.CompositeCondition";
        }
        try {
            result = loadClass(className);
            if (result != null &&
                    (!CompositeCondition.class.isAssignableFrom(result) || !result.isAnnotationPresent(ConditionDisplayName.class))) {
                result = null;
            }

        } catch (ClassNotFoundException e) {
            LOG.log(Level.SEVERE, "Class " + className + " not found.", e);
        }
        return result;
    }
}
