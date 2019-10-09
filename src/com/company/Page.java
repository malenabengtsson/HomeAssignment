package com.company;

import java.io.Serializable;

public class Page implements Serializable {
    private int amountOfPages;

    public Page(int amountOfPages) {
        this.amountOfPages = amountOfPages;
    }

    public int getAmountOfPages() {
        return amountOfPages;
    }

    @Override
    public String toString() {
        return ", pages: " + getAmountOfPages();
    }
}



