package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * Facade. Methods that has to do with save files
 */
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

    public static List<String> readAllLines(String fileName) {

        List<String> listOfStrings = null;
        try {
            listOfStrings = Files.readAllLines(Paths.get(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listOfStrings;
    }
    public static boolean checkFileExist(String fileName) {
        return (Files.exists(Paths.get(fileName)));
    }

    public static boolean checkReadOnly(String fileName) {
        File file = new File(fileName);
        if (checkFileExist(fileName)) {
            return file.canWrite();
        }
        return true;
    }
    public static void deleteSaveFile (String fileName){
        File file = new File(fileName);
        file.delete();
    }
}
