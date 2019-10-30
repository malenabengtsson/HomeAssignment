package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * Facade, methods that has to do with save files
 */
public class FileUtils {
    /**
     * Generic method for savefile
     *
     * @param o        name of object
     * @param fileName name of savefile
     * @param option   choose option
     */
    public static void saveObjects(Object o, String fileName, StandardOpenOption... option) {
        Path path = Paths.get(fileName);
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(path, option))) {
            out.writeObject(o);
        } catch (Exception e) {
        }
    }

    /**
     * Generic method for loading object
     * @param fileName name of savefile
     * @return returns true or false if save file exist
     */
    public static Object loadObjects(String fileName) {
        Path path = Paths.get(fileName);
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(path))) {
            return in.readObject();
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * Method for saving strings to a text file
     * @param fileName name of savefile
     * @return returns true or false if save file exist
     */
    public static List<String> readAllLines(String fileName) {
        List<String> listOfStrings = null;
        try {
            listOfStrings = Files.readAllLines(Paths.get(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listOfStrings;
    }

    /**
     * Method to check if there is a save file
     * @param fileName name of save file
     * @return returns true or false if save file exist
     */
    public static boolean checkFileExist(String fileName) {
        return (Files.exists(Paths.get(fileName)));
    }

    /**
     * Method to check if a save file is read only
     * @param fileName name of savefile
     * @return returns true or false if save file is read only
     */
    public static boolean checkReadOnly(String fileName) {
        File file = new File(fileName);
        if (checkFileExist(fileName)) {
            return file.canWrite();
        }
        return true;
    }

    /**
     * Method to delete a save file if user wants to
     * @param fileName name of file
     */
    public static void deleteSaveFile(String fileName) {
        File file = new File(fileName);
        file.delete();
    }
}
