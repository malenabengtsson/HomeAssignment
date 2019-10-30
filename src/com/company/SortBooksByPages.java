package com.company;

import java.util.Comparator;

/**
 * Comparator for sorting books by pages
 */
public class SortBooksByPages implements Comparator<Book> {
    @Override
    public int compare(Book b1, Book b2) {
        return -(b1.getPages().size() - b2.getPages().size());
    }
}
