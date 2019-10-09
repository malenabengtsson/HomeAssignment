package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileUtils {
    public static void saveObjects(Object o, String fileName, StandardOpenOption... option) {
        Path path = Paths.get(fileName);
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(path, option))) {
            out.writeObject(o);
        } catch (Exception e) {
        }
    }

    public static Object loadObjects(String fileName) {
        Path path = Paths.get(fileName);
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(path))) {
            return in.readObject();
        } catch (Exception e) {
        }
        return null;
    }

    public static boolean checkFileExist(String fileName) {
        return (Files.exists(Paths.get(fileName)));
    }
    public static boolean checkReadOnly (String fileName){
        File file = new File(fileName);
        return file.canWrite();
    }
}
