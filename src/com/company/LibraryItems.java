package com.company;

import java.io.Serializable;

public abstract class LibraryItems implements Serializable {
    private String title;

//superklass

    LibraryItems(String title) {
        this.title = title;

    }
    public abstract void thankYouMessage();
}

