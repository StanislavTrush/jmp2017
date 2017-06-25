package com.epam.jmp2017.util;

import java.util.ArrayList;
import java.util.List;

public class TrashLoader implements Runnable {
    private static List<Class> classes = new ArrayList<>();

    @Override
    public void run() {
        for (int i=0; i<100000; i++) {
            try {
                ClassLoader cl = new ClassLoader() {};
                Class c = cl.loadClass("com.sun.media.jfxmedia.MediaPlayer");
                classes.add(c);
                System.out.println(i);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
