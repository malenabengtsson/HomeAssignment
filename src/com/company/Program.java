package com.company;

import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Program {
    private Scanner scanner = new Scanner(System.in);
    private ArrayList<Book> books;
    private ArrayList<Movie> movies;
    private boolean endProgram = false;
    private int result;
    private String answer;
    private boolean canYouWriteInBooks = FileUtils.checkReadOnly("availableBooks.ser");
    private boolean canYouWriteInMovies = FileUtils.checkReadOnly("availableMovies.ser");
    private boolean foundBooks = FileUtils.checkFileExist("availableBooks.ser");
    private boolean foundMovies = FileUtils.checkFileExist("availableMovies.ser");

    public Program() {
        checkForSaveFile();
    }

    /**
     * Method to start main menu
     */
    public void mainMenu() {
        Menu.MainMenu menu;
        do {
            System.out.println("\tWelcome to the library!\n\n" +
                    "What would you like to browse today?\n");
            menu = Menu.showMenuAndGetChoice(Menu.MainMenu.values());
            switch (menu) {
                case BOOKS:
                    System.out.print("You take a right and walks towards the book aisle...\n");
                    separationLine();
                    waitingTime();
                    bookMenu();
                    break;
                case MOVIES:
                    System.out.print("You take a left and walks towards the movie aisle...\n");
                    separationLine();
                    waitingTime();
                    movieMenu();
                    break;
                case SHOW_ALL_BOOKS_AND_MOVIES:
                    System.out.print("The librarian opens up the computer to show all titles...\n");
                    separationLine();
                    waitingTime();
                    showAllBooksAndMovies();
                    break;
                case EXIT:
                    exitLibrary();
                    break;
            }
        } while (menu != Menu.MainMenu.EXIT && !endProgram);
    }

    /**
     * Method to check if theres a savefile and if user wants to load it
     */
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
                        System.out.println("Answer yes or no, please.");
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
                        System.out.println("Answer yes or no, please.");
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
        Menu.BookMenu menu;
        do {
            System.out.println("What would you like to do?\n");
            menu = Menu.showMenuAndGetChoice(Menu.BookMenu.values());
            switch (menu) {
                case ADD_BOOK:
                    addBook();
                    break;
                case SHOW_BOOKS:
                    showBooks();
                    break;
                case RENT_BOOK:
                    rentBook();
                    break;
                case SHOW_AVERAGE_AMOUNT_OF_PAGES:
                    averagePages();
                    break;
                case BACK_TO_MAIN_MENU:
                    backToMainMenu();
                    break;
            }
        }
        while (menu != Menu.BookMenu.BACK_TO_MAIN_MENU);
    }

    public void addBook() {
        Scanner scanner = new Scanner(System.in);
        if (!canYouWriteInBooks && FileUtils.loadObjects("availableBooks.ser") != null) {
            System.out.println("The filesave for books are read only and you cant add a new book.\n");
        } else {
            System.out.println("Please write the title of the book and the amount of pages it contains in whole numbers.");
            try {
                String title = scanner.nextLine();
                int pages = Integer.parseInt(scanner.nextLine());
                Book book = new Book(title, pages);
                book.addPages(pages);
                books.add(book);
                book.thankYouMessage();
                separationLine();
            } catch (Exception e) {
                System.out.println(
                        "Book could not be added.\n" +
                                "Only write numbers when asked for amount of pages.");
                separationLine();

            }
        }
    }


    public void showBooks() {
        if (books.isEmpty()) {
            System.out.println("No books added yet");
            separationLine();
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
        System.out.println();
    }

    public void rentBook() {
        if (books.isEmpty()) {
            System.out.println("Sorry, there are no books available at the library.");
            separationLine();
        } else if (!canYouWriteInBooks && FileUtils.loadObjects("availableBooks.ser") != null) {
            System.out.println("The filesave for books are read only and you cant remove a book");
        } else {
            System.out.println("The following books are available: ");
            for (Book book : books){
                System.out.println(book + ", pages: " + book.getPages().size());
            }
            System.out.println("Enter the title of the book you would like to rent.");
            String rentBook = scanner.nextLine();
            boolean found = true;
            if (books.isEmpty()) {
                found = false;
            }
            for (int i = 0; i < books.size(); i++) {
                if (books.get(i).getTitle().equalsIgnoreCase(rentBook)) {
                    System.out.println("You rented the book and its no longer available at the library.");
                    separationLine();
                    books.remove(i);
                    found = true;

                } else if (!(books.get(i).getTitle().equalsIgnoreCase(rentBook))) {
                    found = false;
                }
            }
            if (!found) {
                System.out.println("No books with that title is available at the library.");
                separationLine();
            }
        }
    }

    /**
     * Calculate average amount of pages of all books in library
     */
    public void averagePages() {
        if (books.isEmpty()) {
            System.out.println("No books added yet, average pages cannot be calculated.");
            separationLine();
        } else if (books.size() < 2) {
            System.out.println("Please add at least two books in order to calculate the average.");
            separationLine();
        } else {
            for (Book book : books)
                result += book.getPages().size();
            float resultFloat = (float) result;
            float finalResult = (resultFloat / books.size());
            System.out.printf("The average amount of pages per book is %.2f\n\n", finalResult);
            separationLine();
        }
    }

    public void movieMenu() {
        Menu.MovieMenu menu;
        do {
            System.out.println(
                    "What would you like to do?\n");
            menu = Menu.showMenuAndGetChoice(Menu.MovieMenu.values());
            switch (menu) {
                case ADD_MOVIE:
                    addMovie();
                    break;
                case SHOW_MOVIES:
                    showMovies();
                    break;
                case RENT_MOVIE:
                    rentMovie();
                    break;
                case SHOW_AVERAGE_OF_DURATION:
                    averageDuration();
                    break;
                case BACK_TO_MAIN_MENU:
                    backToMainMenu();
                    break;
            }

        } while (menu != Menu.MovieMenu.BACK_TO_MAIN_MENU & !endProgram);
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
            System.out.println("No movies added yet.");
            separationLine();
        } else {
            System.out.println("The movies that are available are: \n");
            for (Movie movie : movies) {
                System.out.println(movie.getTitle());
            }
        }
    }

    public void rentMovie() {
        if (movies.isEmpty()) {
            System.out.println("Sorry, there are no movies available at the library.");
            separationLine();
        } else if (!canYouWriteInMovies && FileUtils.loadObjects("availableMovies.ser") != null) {
            System.out.println("The filesave for movies are read only and you cant rent a movie.");
            separationLine();
        } else {
            System.out.println("The following movies are available: ");
            for (Movie movie : movies){
                System.out.println(movie + ", duration " + movie.getMovieDuration().size());
            }
            System.out.println("Enter the title of the movie you would like to rent.");
            String rentMovie = scanner.nextLine();
            boolean found = true;
            for (int i = 0; i < movies.size(); i++) {
                if (movies.get(i).getTitle().equalsIgnoreCase(rentMovie)) {
                    found = true;
                    movies.remove(i);
                    System.out.println("You rented the movie and its no longer available at the library.");
                    separationLine();
                } else {
                    found = false;
                }
            }
            if (!found) {
                System.out.println("No movies with that title is available at the library.");
                separationLine();
            }
        }
    }

    /**
     * Calculate average amount of duration of movies in library
     */
    public void averageDuration() {
        if (movies.isEmpty()) {
            System.out.println("No movies added yet, average duration cannot be calculated.");
            separationLine();
        } else if (movies.size() < 2) {
            System.out.println("Please add at least two movies in order to calculate the average duration.");
            separationLine();
        } else {
            for (Movie movie : movies)
                result += movie.getMovieDuration().size();
            float resultFloat = (float) result;
            float finalResult = (resultFloat / movies.size());
            System.out.printf("The average duration per movie is %.2f\n\n", finalResult);
        }
    }

    public void showAllBooksAndMovies() {
        if (books.isEmpty() && movies.isEmpty()) {
            System.out.println("There are no books or movies available at the library.");
            separationLine();
        } else if (!(books.isEmpty() && movies.isEmpty())) {
            Menu.SortingMenu menu;
            do {
                System.out.println("Please choose a sorting order:\n");
                menu = Menu.showMenuAndGetChoice(Menu.SortingMenu.values());
                switch (menu) {
                    case SORT_BY_TITLE:
                        sortByTitle();
                        break;
                    case SORT_BY_PAGES_AND_DURATION:
                        sortByPagesAndDuration();
                        break;
                    case BACK_TO_MAIN_MENU:
                        backToMainMenu();
                        break;
                }
            }
            while (menu != Menu.SortingMenu.BACK_TO_MAIN_MENU);
        }
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

    private void backToMainMenu() {
        System.out.println("Going back to the front desk...");
        separationLine();
        waitingTime();
        mainMenu();

    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void exitLibrary() {
        if (books.isEmpty() && movies.isEmpty()) {
            FileUtils.deleteSaveFile("availableBooks.ser");
            FileUtils.deleteSaveFile("availableMovies.ser");
            System.out.println("We hope to see you again!");

        } else if (!books.isEmpty() && !movies.isEmpty()) {
            do {
                System.out.println("Would you like to save the books and movies in the library for next time?");
                answer = scanner.nextLine();
                answer = answer.toLowerCase();
                switch (answer) {
                    case "yes":
                        if (!canYouWriteInMovies && !canYouWriteInBooks) {
                            System.out.println("Unable to save books and movies since the save file is read only");
                            break;
                        } else if (canYouWriteInBooks && canYouWriteInMovies) {
                            FileUtils.saveObjects(movies, "availableMovies.ser", StandardOpenOption.CREATE);
                            FileUtils.saveObjects(books, "availableBooks.ser", StandardOpenOption.CREATE);
                            System.out.println("Books and movies are saved.\n" +
                                    "We hope to see you again!");
                            break;
                        } else if (!canYouWriteInBooks) {
                            FileUtils.saveObjects(movies, "availableMovies.ser", StandardOpenOption.CREATE);
                            FileUtils.saveObjects(books, "availableBooks.ser", StandardOpenOption.CREATE);
                            System.out.println("Unable to save books since the savefile is read only.\n" +
                                    "Movies have been added.");
                            break;
                        } else if (!canYouWriteInMovies) {
                            FileUtils.saveObjects(books, "availableBooks.ser", StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
                            FileUtils.saveObjects(movies, "availableMovies.ser", StandardOpenOption.CREATE);
                            System.out.println("Unable to save movies since the savefile is read only.\n" +
                                    "Books have been added.");
                            break;
                        }
                        break;
                    case "no":
                        System.out.println("We hope to see you again!");
                        break;
                    default:
                        System.out.println("Answer yes or no, please.");
                        break;
                }
            } while (!answer.equals("yes") && !answer.equals("no"));
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
                            break;
                        } else {
                            FileUtils.saveObjects(movies, "availableMovies.ser", StandardOpenOption.CREATE);
                            FileUtils.deleteSaveFile("availableBooks.ser");
                            System.out.println("Movies are saved.\n" +
                                    "We hope to see you again!");
                            break;
                        }
                    case "no":
                        System.out.println("We hope to see you again!");
                        break;

                }
            }
            while (!answer.equals("yes") && !answer.equals("no"));
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
                            break;
                        }
                        break;
                    case "no":
                        if (!canYouWriteInBooks) {
                            System.out.println("Unable to delete the books in the library since the save file is read only.");
                        } else {
                            FileUtils.deleteSaveFile("availableBooks.ser");
                            System.out.println("We hope to see you again!");

                        }
                        break;
                    default:
                        System.out.println("Answer yes or no, please.");
                        break;
                }
            } while (!answer.equals("yes") && !answer.equals("no"));
        }
        waitingTime();
        endProgram = true;
    }

    private void waitingTime() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void separationLine() {
        System.out.println("--------------------------------------------------------------\n");
    }
}
