package com.company;

import java.util.Comparator;

public class SortMoviesByTitle implements Comparator <Movie> {
    @Override
    public int compare(Movie m1, Movie m2) {
        return m1.getTitle().compareTo(m2.getTitle());
    }
}
