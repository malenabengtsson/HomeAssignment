package com.company;

import java.io.Serializable;

/**
 * Superclass
 */
public abstract class LibraryItem implements Serializable {
    private String title;


    public LibraryItem(String title) {
        this.title = title;

    }
    public abstract void thankYouMessage();
}

