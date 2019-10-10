package com.company;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Page implements Serializable {
    private int amountOfPages;
    private Random random = new Random();
    private ArrayList<String> test = new ArrayList<>();

    public Page(int amountOfPages) {
        this.amountOfPages = amountOfPages;
    }

    public int getAmountOfPages() {
        return amountOfPages;
    }

    public void addWordsToPage() {
        test.add("test");
        FileUtils.saveObjects(test, "text.txt");
        ArrayList<String> words = (ArrayList<String>) FileUtils.loadObjects("text.txt");
        if (words != null)
        {
            for (String string : words) {
                System.out.println(string);
            }
        }
        else{
            System.out.println("no");

        }
    }

    @Override
    public String toString() {
        return ", pages: " + getAmountOfPages();
    }
}



