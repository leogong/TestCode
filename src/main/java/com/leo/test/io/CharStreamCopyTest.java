package com.leo.test.io;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CharStreamCopyTest {

    public static void main(String[] args) {
        args = new String[2];
        args[0] = "/home/leo/aa";
        args[1] = "/home/leo/bb";
        if (args.length < 2) {
            System.out.println("Usage: CharStreamCopyTest <original file> <copy>");
            System.exit(-1);
        }
       char[] c = new char[128]; int cLen = c.length;
        // Example use of InputStream methods
        try (FileReader fr = new FileReader(args[0]);
             FileWriter fw = new FileWriter(args[1])) {
            int count = 0;
            int read = 0;
            while ((read = fr.read(c)) != -1) {
                if (read < cLen) fw.write(c, 0, read);
                else fw.write(c);
                count += read;
            }
            System.out.println("Wrote: " + count + " characters.");
        } catch (FileNotFoundException f) {
            System.out.println("File not found: " + f);
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
    }
}