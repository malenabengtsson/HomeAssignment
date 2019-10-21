package com.company;

import java.util.ArrayList;

/**
 * Class for creating movies
 */
public class Movie extends LibraryItems  {
    private String title;
    private ArrayList<MovieDuration> movieDuration;

    Movie(String title, int duration){
        super (title);
        this.title = title;
        this.movieDuration = new ArrayList<>(duration);
    }
    public void thankYouMessage(){
        System.out.println("Thank you for visiting the movie aisle, the movie has been added.\n" +
                "----------------------------------");
    }

    public String getTitle() {
        return title;
    }
    public void addMovieDuration (int duration){
        for (int i = 0; i <duration; i++) {
            movieDuration.add(new MovieDuration(duration));
        }
    }

    public ArrayList<MovieDuration> getMovieDuration() {
        return movieDuration;
    }

    @Override
    public String toString() {
        return  "Title: " + getTitle();
    }


}
