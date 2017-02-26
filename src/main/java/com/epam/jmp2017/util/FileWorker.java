package com.epam.jmp2017.util;

import java.io.*;

public class FileWorker {
    public byte[] fetchClassFromFS(String path) throws IOException {
        InputStream is = new FileInputStream(new File(path));

        long length = new File(path).length();

        if (length > Integer.MAX_VALUE) {
            System.out.println("File is too large");
        }

        byte[] bytes = new byte[(int) length];

        int offset = 0;
        int numRead;
        while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        try {
            if (offset < bytes.length) {
                throw new IOException("Could not completely read file " + path);
            }
        } finally {
            is.close();
        }

        return bytes;
    }
}
