package com.company;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

/**
 * Seperate class for page
 */
public class Page implements Serializable {
    private int amountOfPages;
    private Random random = new Random();
    private List<String> words = FileUtils.readAllLines("text.txt");

    public Page(int amountOfPages) {
        this.amountOfPages = amountOfPages;
    }

    /**
     * Method to add random words from save file based on amount of pages (10 pages = 10 words).
     */
    public void addWordsToPage() {
        System.out.print(words.get(random.nextInt(words.size() - 1)) + " ");
    }

    @Override
    public String toString() {
        return ", pages: " + amountOfPages;
    }
}



