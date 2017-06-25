package com.epam.jmp2017;

import com.epam.jmp2017.util.TrashLoader;
import javassist.CannotCompileException;
import javassist.ClassPool;

import java.util.ArrayList;
import java.util.List;

public class Runner {

    public static void main(String[] args) {
        //getOutOfMemoryErrors();
        //getStackOverflow();
        //getMetaspaceEx();

        System.out.println("Finished");
    }

    private static void getOutOfMemoryErrors() {
        for(int i=0;i<100000; i++) {
            Thread t = new Thread(new TrashLoader());
            t.start();
        }
    }

    private static void getStackOverflow() {
        List<Class> classes = new ArrayList<>();

        ClassLoader cl = new ClassLoader() {};
        for (int i=0; i<10000000; i++) {
            try {
                ClassLoader clx = new ClassLoader(cl) {};
                Class c = cl.loadClass("com.sun.media.jfxmedia.MediaPlayer");
                classes.add(cl.loadClass("com.sun.media.sound.AlawCodec"));
                cl = clx;
                System.out.println(i);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private static void getMetaspaceEx() {
        ClassPool cp = ClassPool.getDefault();
        try {
            for (int i = 0; i<10000; i++) {
                Class c = cp.makeClass("com.sun.media.jfxmedia.MediaPlayer" + i).toClass();
            }
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }
    }
}
