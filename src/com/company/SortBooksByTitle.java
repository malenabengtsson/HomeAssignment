package com.company;

import java.util.Comparator;

/**
 * Comparator for sorting books by title
 */
public class SortBooksByTitle implements Comparator<Book> {
    @Override
    public int compare(Book b1, Book b2) {
        return b1.getTitle().compareTo(b2.getTitle());
    }
}
