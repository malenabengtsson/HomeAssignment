package com.company;

import java.io.Serializable;

/**
 * Superclass
 */
public abstract class LibraryItems implements Serializable {
    private String title;


    LibraryItems(String title) {
        this.title = title;

    }
    public abstract void thankYouMessage();
}

