package com.company;

import java.util.Comparator;

/**
 * Comparator for sorting movies by duration
 */
public class SortMoviesByDuration implements Comparator<Movie> {
    @Override
    public int compare(Movie m1, Movie m2) {
        return m1.getMovieDuration().size() - m2.getMovieDuration().size();
    }
}
