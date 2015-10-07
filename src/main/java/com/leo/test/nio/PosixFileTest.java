/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leo.test.nio;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

public class PosixFileTest {

    public static void main(String[] args) {
        args = new String[1];
        args[0] = "aa";
        boolean unixFS = false;
        Set<String> views = FileSystems.getDefault().supportedFileAttributeViews();
        for (String s : views) {
            System.out.println(s);
            if (s.equals("posix")) {
                unixFS = true;
            }
        }
        if (!unixFS) {
            System.out.println("This filesystem does not support Posix permissions.");
            System.exit(-1);
        }
        Path p = Paths.get(args[0]);
        Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rwxr-x---");
        FileAttribute<Set<PosixFilePermission>> attrs = PosixFilePermissions.asFileAttribute(perms);
        try {
            Files.createFile(p, attrs);
        } catch (FileAlreadyExistsException f) {
            System.out.println("FileAlreadyExists" + f);
        } catch (IOException i) {
            System.out.println("IOException:" + i);
        }
        for (PosixFilePermission posixFilePermission : attrs.value()) {
            System.out.println(posixFilePermission);
        }
    }
}
