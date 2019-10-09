package com.company;

import java.util.Comparator;

public class SortMoviesByDuration implements Comparator<Movie> {
    @Override
    public int compare(Movie m1, Movie m2) {
        return m1.getMovieDuration().size()-m2.getMovieDuration().size();
    }
}
