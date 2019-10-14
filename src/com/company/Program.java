package com.company;

import java.io.File;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Program {
    private Scanner scanner = new Scanner(System.in);
    private ArrayList<Book> books;
    private ArrayList<Movie> movies;
    private boolean endProgram = false;
    private int bookChoice;
    private int movieChoice;
    private int result;
    private int choice;
    private String answer;
    private int sortingChoice;
    private boolean canYouWriteInBooks = FileUtils.checkReadOnly("availableBooks.ser");
    private boolean canYouWriteInMovies = FileUtils.checkReadOnly("availableMovies.ser");
    private boolean foundBooks = FileUtils.checkFileExist("availableBooks.ser");
    private boolean foundMovies = FileUtils.checkFileExist("availableMovies.ser");

    Program() {
        checkForSaveFile();
    }

    public void mainMenu() {
        while (!endProgram) {
            System.out.println("\tWelcome to the library!\n\n" +
                    "What would you like to browse today?\n" +
                    "1. Books.\n" +
                    "2. Movies.\n" +
                    "3. Show all books and movies.\n" +
                    "4. Exit the library\n" +
                    "----------------------------------");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Please choose a number between 1-4.\n");
            }
            switch (choice) {
                case 1:
                    bookMenu();
                    break;
                case 2:
                    movieMenu();
                    break;
                case 3:
                    showAllBooksAndMovies();
                    break;
                case 4:
                    exitLibrary();
                    break;
                default:
                    System.out.println("Choose a number between 1-4, please.");
                    break;

            }
        }
    }

    public void checkForSaveFile() {
        if (foundBooks &&
                foundMovies &&
                FileUtils.loadObjects("availableBooks.ser") != null &&
                FileUtils.loadObjects("availableMovies.ser") != null) {
            do {
                System.out.println("Would you like to see the books and movies that are currently available?");
                answer = scanner.nextLine();
                answer = answer.toLowerCase();
                switch (answer) {
                    case "yes":
                        this.books = (ArrayList<Book>) FileUtils.loadObjects("availableBooks.ser");
                        this.movies = (ArrayList<Movie>) FileUtils.loadObjects("availableMovies.ser");
                        break;
                    case "no":
                        this.books = new ArrayList<>();
                        this.movies = new ArrayList<>();
                        break;
                    default:
                        System.out.println("Answer yes or no, please.");
                        break;
                }
            }
            while (!answer.equals("yes") && !answer.equals("no"));
        } else if (foundBooks && FileUtils.loadObjects("availableBooks.ser") != null) {
            System.out.println("Would you like to see the books that are currently available?");
            do {
                try {
                    answer = scanner.nextLine();
                    answer = answer.toLowerCase();
                } catch (Exception e) {
                    System.out.println("Answer yes or no, please.");
                }
                switch (answer) {
                    case "yes":
                        this.books = (ArrayList<Book>) FileUtils.loadObjects("availableBooks.ser");
                        this.movies = new ArrayList<>();
                        break;
                    case "no":
                        this.books = new ArrayList<>();
                        this.movies = new ArrayList<>();
                        break;
                    default:
                        System.out.println("Only write yes or no.");
                        break;
                }
            } while (!answer.equals("yes") && !answer.equals("no"));
        } else if (foundMovies && FileUtils.loadObjects("availableMovies.ser") != null) {
            System.out.println("Would you like to see the movies that are currently available?");
            do {
                try {
                    answer = scanner.nextLine();
                    answer = answer.toLowerCase();
                } catch (Exception e) {
                    System.out.println("Answer yes or no, please.");
                }
                switch (answer) {
                    case "yes":
                        this.movies = (ArrayList<Movie>) FileUtils.loadObjects("availableMovies.ser");
                        this.books = new ArrayList<>();
                        break;
                    case "no":
                        this.books = new ArrayList<>();
                        this.movies = new ArrayList<>();
                        break;
                    default:
                        System.out.println("Only write yes or no.");
                        break;
                }
            }
            while (!answer.equals("yes") && !answer.equals("no"));
        } else if (!(foundBooks &&
                foundMovies &&
                FileUtils.loadObjects("availableBooks.ser") != null &&
                FileUtils.loadObjects("availableMovies.ser") != null)) {
            this.books = new ArrayList<>();
            this.movies = new ArrayList<>();
        }

    }

    public void bookMenu() {

        do {
            System.out.println("What would you like to do?\n" +
                    "1. Add book\n" +
                    "2. Show books\n" +
                    "3. Rent book\n" +
                    "4. Show average amount of pages of the books in the library\n" +
                    "5. Back to main menu\n" +
                    "----------------------------------");
            try {
                bookChoice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Please choose a number between 1-5.");
            }
            switch (bookChoice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    showBooks();
                    break;
                case 3:
                    rentBook();
                    break;
                case 4:
                    averagePages();
                    break;
                case 5:
                    backToMainMenu();
                    break;
                default:
                    System.out.println("Please choose a number between 1-5.");
                    break;
            }
        }
        while (bookChoice != 5);
    }

    public void addBook() {
        if (!canYouWriteInBooks && FileUtils.loadObjects("availableBooks.ser") != null) {
            System.out.println("The filesave for books are read only and you cant add a new book.\n");
        } else {
            boolean repeat = true;
            while (repeat) {
                System.out.println("Please write the title of the book and the amount of pages it contains in whole numbers.");
                try {
                    String title = scanner.nextLine();
                    int pages = Integer.parseInt(scanner.nextLine());
                    Book book = new Book(title, pages);
                    book.addPages(pages);
                    books.add(book);
                    book.thankYouMessage();
                    repeat = false;
                    break;
                } catch (Exception e) {
                    System.out.println(
                            "Book could not be added.\n" +
                                    "Only write numbers when asked for amount of pages.\n");

                }
            }
        }
    }

    public void showBooks() {
        if (books.isEmpty()) {
            System.out.println("No books added yet\n" +
                    "----------------------------------");
        } else {
            System.out.println("The books that are available are:");
            for (Book book : books) {
                System.out.println("Title: " + book.getTitle());
                System.out.println("Summary of the book:");
                for (Page page : book.getPages()) {
                    page.addWordsToPage();
                }
                System.out.println();
            }
        }
    }

    public void rentBook() {
        if (!canYouWriteInBooks && FileUtils.loadObjects("availableBooks.ser") != null) {
            System.out.println("The filesave for books are read only and you cant remove a book");
        } else {
            System.out.println("Enter the title of the book you would like to rent.");
            String rentBook = scanner.nextLine();
            boolean found = true;
            if (books.isEmpty()) {
                found = false;
            }
            for (int i = 0; i < books.size(); i++) {
                if (books.get(i).getTitle().equalsIgnoreCase(rentBook)) {
                    System.out.println("You rented the book and its no longer available at the library.\n" +
                            "----------------------------------");
                    for (int j = 0; j < books.size() - 1; j++) {
                        books.get(i).getPages().remove(j);
                        found = true;
                    }
                    books.remove(i);
                } else if (!(books.get(i).getTitle().equalsIgnoreCase(rentBook))) {
                    found = false;
                }
            }
            if (!found) {
                System.out.println("No books with that title is available at the library.\n" +
                        "----------------------------------");
            }
        }
    }


    public void averagePages() {
        if (books.isEmpty()) {
            System.out.println("No books added yet, average pages cannot be calculated.\n" +
                    "----------------------------------");
        } else if (books.size() < 2) {
            System.out.println("Please add at least two books in order to calculate the average.\n" +
                    "----------------------------------");

        } else {
            for (Book book : books)
                result += book.getPages().size();
            float resultFloat = (float) result;
            float finalResult = (resultFloat / books.size());
            System.out.printf("The average amount of pages per book is %f\n\n", finalResult);
        }
    }


    public void movieMenu() {
        do {

            System.out.println(
                    "What would you like to do? \n" +
                            "1. Add movie\n" +
                            "2. Show movies\n" +
                            "3. Rent movie\n" +
                            "4. Show average duration of movies in the library\n" +
                            "4. Back to main menu\n" +
                            "----------------------------------");
            try {
                movieChoice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Please choose a number between 1-4.");
            }
            switch (movieChoice) {
                case 1:
                    addMovie();
                    break;
                case 2:
                    showMovies();
                    break;
                case 3:
                    rentMovie();
                    break;
                case 4:
                    averageDuration();
                    break;
                case 5:
                    backToMainMenu();
                    break;
                default:
                    break;
            }

        } while (movieChoice != 4);
    }

    public void addMovie() {
        if (!canYouWriteInMovies && FileUtils.loadObjects("availableMovies.ser") != null) {
            System.out.println("The filesave for movies are read only and you cant add a new movie.");
        } else {
            boolean repeat = true;
            while (repeat) {
                System.out.println("Please write the title of the movie and the duration in whole minutes");
                try {
                    String movieTitle = scanner.nextLine();
                    int duration = Integer.parseInt(scanner.nextLine());
                    Movie movie = new Movie(movieTitle, duration);
                    movies.add(movie);
                    movie.addMovieDuration(duration);
                    movie.thankYouMessage();
                    repeat = false;
                    break;
                } catch (Exception e) {
                    System.out.println(
                            "Movie could not be added.\n" +
                                    "Only write numbers when asked for amount of minutes.\n");
                }
            }
        }
    }

    public void showMovies() {
        if (movies.isEmpty()) {
            System.out.println("No movies added yet\n" +
                    "----------------------------------");
        } else {
            System.out.println("The movies that are available are: \n");
            for (Movie movie : movies) {
                System.out.println(movie.getTitle());
            }
        }
    }

    public void rentMovie() {
        if (!canYouWriteInMovies && FileUtils.loadObjects("availableMovies.ser") != null) {
            System.out.println("The filesave for movies are read only and you cant rent a movie.");
        } else {
            System.out.println("Enter the title of the movie you would like to rent.");
            String rentMovie = scanner.nextLine();
            boolean found = true;
            for (int i = 0; i < movies.size(); i++) {
                if (movies.get(i).getTitle().equalsIgnoreCase(rentMovie)) {
                    found = true;
                    movies.remove(i);
                    System.out.println("You rented the movie and its no longer available at the library.\n" +
                            "----------------------------------");
                } else {
                    found = false;
                }
            }
            if (!found) {
                System.out.println("No movies with that title is available at the library.\n" +
                        "----------------------------------");
            }
        }
    }

    public void averageDuration() {
        if (movies.isEmpty()) {
            System.out.println("No movies added yet, average duration cannot be calculated.\n" +
                    "----------------------------------");
        } else if (movies.size() < 2) {
            System.out.println("Please add at least two movies in order to calculate the average duration.\n" +
                    "----------------------------------");

        } else {
            for (Movie movie : movies)
                result += movie.getMovieDuration().size();
            float resultFloat = (float) result;
            float finalResult = (resultFloat / movies.size());
            System.out.printf("The average duration per movie is %f\n\n", finalResult);
        }
    }

    public void showAllBooksAndMovies() {
        do {
            if (books.isEmpty() && movies.isEmpty()) {
                System.out.println("There are no books or movies available at the library.\n" +
                        "----------------------------------");
                break;
            } else if (!(books.isEmpty() && movies.isEmpty())) {
                System.out.println("Please choose a sorting order:\n" +
                        "1. Books and movies in alphabetical order\n" +
                        "2. Sorted by highest amount of pages and highest amount of minutes.\n" +
                        "3. Back to main menu\n" +
                        "----------------------------------");
                try {
                    sortingChoice = Integer.parseInt(scanner.nextLine());
                } catch (Exception e) {
                    System.out.println("Please choose a number between 1 - 3 please.");
                }
                switch (sortingChoice) {
                    case 1:
                        sortByTitle();
                        break;
                    case 2:
                        sortByPagesAndDuration();
                        break;
                    case 3:
                        backToMainMenu();
                        break;
                    default:
                        break;
                }
            }
        }
        while (sortingChoice != 3);
    }

    public void sortByTitle() {
        if (books.isEmpty()) {
            System.out.println("No books are available at the library.\n" +
                    "The movies available are:");
            System.out.println("Sorting by title");
            SortMoviesByTitle sortMoviesByTitle = new SortMoviesByTitle();
            Collections.sort(movies, sortMoviesByTitle);
            for (Movie movie : movies) {
                System.out.println(movie + ", duration: " + movie.getMovieDuration().size());

            }
        } else if (movies.isEmpty()) {
            System.out.println("No movies are available at the library.\n" +
                    "The books available are: ");
            SortBooksByTitle sortBooksByTitle = new SortBooksByTitle();
            Collections.sort(books, sortBooksByTitle);
            for (Book book : books) {
                System.out.print(book);
                System.out.println(", pages: " + book.getPages().size());
            }
        } else {
            System.out.println("The books available are: ");
            SortBooksByTitle sortBooksByTitle = new SortBooksByTitle();
            Collections.sort(books, sortBooksByTitle);
            for (Book book : books) {
                System.out.println(book + ", pages: " + book.getPages().size());
            }
            System.out.println("The movies available are: ");
            SortMoviesByTitle sortMoviesByTitle = new SortMoviesByTitle();
            Collections.sort(movies, sortMoviesByTitle);
            for (Movie movie : movies) {
                System.out.println(movie + ", duration: " + movie.getMovieDuration().size());
            }
        }
    }

    public void sortByPagesAndDuration() {
        if (books.isEmpty()) {
            System.out.println("No books are available at the library.\n" +
                    "The movies available are:");
            SortMoviesByDuration sortMoviesByDuration = new SortMoviesByDuration();
            Collections.sort(movies, sortMoviesByDuration);
            for (Movie movie : movies) {
                System.out.println(movie + ", duration : " + movie.getMovieDuration().size());
            }
        } else if (movies.isEmpty()) {
            System.out.println("No movies are available at the library.\n" +
                    "The books available are: ");
            System.out.println("Sorting by pages.");
            SortBooksByPages sortBooksByPages = new SortBooksByPages();
            Collections.sort(books, sortBooksByPages);
            for (Book book : books) {
                System.out.println(book + ", pages: " + book.getPages().size());
            }
        } else {
            System.out.println("The books available are: ");
            SortBooksByPages sortBooksByPages = new SortBooksByPages();
            Collections.sort(books, sortBooksByPages);
            for (Book book : books) {
                System.out.println(book + ", pages: " + book.getPages().size());

            }
            System.out.println("The movies available are: ");
            SortMoviesByDuration sortMoviesByDuration = new SortMoviesByDuration();
            Collections.sort(movies, sortMoviesByDuration);
            for (Movie movie : movies) {
                System.out.println(movie + ", duration: " + movie.getMovieDuration().size());
            }
        }
    }

    public void backToMainMenu() {
        System.out.println("Going back to main menu\n" +
                "----------------------------------");
    }

    public void exitLibrary() {
        if (books.isEmpty() && movies.isEmpty()) {
            FileUtils.deleteSaveFile("availableBooks.ser");
            FileUtils.deleteSaveFile("availableMovies.ser");
            System.out.println("We hope to see you again!");
            endProgram = true;
        } else if (!books.isEmpty() && !movies.isEmpty()) {
            do {
                System.out.println("Would you like to save the books and movies in the library for next time?");
                answer = scanner.nextLine();
                answer = answer.toLowerCase();
                switch (answer) {
                    case "yes":
                        if (!canYouWriteInMovies && !canYouWriteInBooks) {
                            System.out.println("Unable to save books and movies since the save file is read only");
                            endProgram = true;
                        } else if (canYouWriteInBooks && canYouWriteInMovies) {
                            FileUtils.saveObjects(movies, "availableMovies.ser", StandardOpenOption.CREATE);
                            FileUtils.saveObjects(books, "availableBooks.ser", StandardOpenOption.CREATE);
                            System.out.println("Books and movies are saved.\n" +
                                    "We hope to see you again!");
                            endProgram = true;
                        } else if (!canYouWriteInBooks) {
                            FileUtils.saveObjects(movies, "availableMovies.ser", StandardOpenOption.CREATE);
                            FileUtils.saveObjects(books, "availableBooks.ser", StandardOpenOption.CREATE);
                            System.out.println("Unable to save books since the savefile is read only.\n" +
                                    "Movies have been added.");
                            endProgram = true;
                        } else if (!canYouWriteInMovies) {
                            FileUtils.saveObjects(books, "availableBooks.ser", StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
                            FileUtils.saveObjects(movies, "availableMovies.ser", StandardOpenOption.CREATE);
                            System.out.println("Unable to save movies since the savefile is read only.\n" +
                                    "Books have been added.");
                            endProgram = true;
                        }
                        break;
                    case "no":
                        System.out.println("We hope to see you again!");
                        endProgram = true;
                        break;
                }
            } while (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no"));
        } else if (!movies.isEmpty()) {
            do {
                System.out.println("Would you like to save the movies in the library for next time?");
                answer = scanner.nextLine();
                answer = answer.toLowerCase();
                switch (answer) {
                    case "yes":
                        if (!canYouWriteInMovies) {
                            System.out.println("Unable to save movies since the save file is read only.");
                            FileUtils.deleteSaveFile("availableBooks.ser");
                            endProgram = true;
                        } else {
                            FileUtils.saveObjects(movies, "availableMovies.ser", StandardOpenOption.CREATE);
                            FileUtils.deleteSaveFile("availableBooks.ser");
                            System.out.println("Movies are saved.\n" +
                                    "We hope to see you again!");
                            endProgram = true;
                        }
                        break;
                    case "no":
                        System.out.println("We hope to see you again!");
                        endProgram = true;
                        break;

                }
            }
            while (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no"));
        } else if (!books.isEmpty()) {
            do {
                System.out.println("Would you like to save the books in the library for next time?");
                answer = scanner.nextLine();
                answer = answer.toLowerCase();
                switch (answer) {
                    case "yes":
                        if (!canYouWriteInBooks) {
                            System.out.println("Unable to save books since the save file is read only.");
                        } else {
                            FileUtils.saveObjects(books, "availableBooks.ser", StandardOpenOption.CREATE);
                            FileUtils.deleteSaveFile("availableMovies.ser");
                            System.out.println("Books are saved.\n" +
                                    "We hope to see you again!");
                            endProgram = true;
                        }
                        break;
                    case "no":
                        System.out.println("We hope to see you again!");
                        endProgram = true;
                        break;

                }
            } while (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no"));
        }

    }
}
