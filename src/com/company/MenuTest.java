package com.company;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.*;

public class MenuTest {
    Program program = new Program();

    @Test
    public void addBook() {
        int size = program.getBooks().size();
     //   String input = "Test one\nhk";  --- this will give false since hk cant be parsed to int
        String input = "Test one\n10";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        program.addBook();
        assertEquals(size + 1, program.getBooks().size());
    }
    @Test
    public void addMovie(){
        int size = program.getMovies().size();
        String input = "Movie one\n60";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        program.addMovie();
        assertEquals(size +1, program.getMovies().size());
    }

}