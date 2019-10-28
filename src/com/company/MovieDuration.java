package com.company;

import java.io.Serializable;

/**
 * Seperate class for movieduration
 */
public class MovieDuration implements Serializable {
    private int movieDuration;

   public MovieDuration (int movieDuration){
        this.movieDuration = movieDuration;
    }

    public int getMovieDuration() {
        return movieDuration;
    }

    @Override
    public String toString() {
        return ". Amount of minutes: " + movieDuration;
    }

}
