package com.company;

import java.util.ArrayList;

/**
 * Class to create books
 */
public class Book extends LibraryItem {
    private String title;
    private ArrayList<Page> pages;

    Book(String title, int amountOfPages) {
        super(title);
        this.title = title;
        this.pages = new ArrayList<>(amountOfPages);
    }

    /**
     * Add pages when book is created
     * @param amountOfPages
     */
    public void addPages(int amountOfPages) {
        for (int i = 0; i < amountOfPages; i++) {
            pages.add(new Page(amountOfPages));
        }
    }


    public ArrayList<Page> getPages() {
        return pages;
    }

    public String getTitle() {
        return title;
    }

    public void thankYouMessage() {
        System.out.println("Thank you for visiting the book aisle, the book has been added.");
    }
    @Override
    public String toString() {
        return "Title: " + getTitle();
    }

}






