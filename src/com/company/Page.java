package com.company;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

public class Page implements Serializable {
    private int amountOfPages;
    private Random random = new Random();
    private List<String> words = FileUtils.readAllLines("text.txt");

    public Page(int amountOfPages) {
        this.amountOfPages = amountOfPages;
    }

    public int getAmountOfPages() {
        return amountOfPages;
    }

    public void addWordsToPage() {
        System.out.print(words.get(random.nextInt(words.size()-1)) + " ");
    }

    @Override
    public String toString() {
        return ", pages: " + getAmountOfPages();
    }
}



