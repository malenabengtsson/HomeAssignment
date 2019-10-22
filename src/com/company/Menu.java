package com.company;

import java.util.Scanner;

/**
 * Class for all the different enum menus
 */
public class Menu {
    /**
     * Main menu
     */
    public enum MainMenu implements hasDescription {
        BOOKS("Books"),
        MOVIES("Movies"),
        SHOW_ALL_BOOKS_AND_MOVIES("Show all books and movies"),
        EXIT("Exit program");
        private String description;

        MainMenu(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }

    /**
     * Book menu
     */
    public enum BookMenu implements hasDescription {
        ADD_BOOK("Add book"),
        SHOW_BOOKS("Show books"),
        RENT_BOOK("Rent book"),
        SHOW_AVERAGE_AMOUNT_OF_PAGES("Show average amount of pages of the books in the library"),
        BACK_TO_MAIN_MENU("Back to main menu");
        private String description;

        BookMenu(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }

    /**
     * Movie menu
     */
    public enum MovieMenu implements hasDescription {
        ADD_MOVIE("Add movie"),
        SHOW_MOVIES("Show movies"),
        RENT_MOVIE("Rent movie"),
        SHOW_AVERAGE_OF_DURATION("Show average duration of movies in the library"),
        BACK_TO_MAIN_MENU("Back to main menu");
        private String description;

        MovieMenu(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }

    /**
     * Sorting menu
     */
    public enum SortingMenu implements hasDescription {
        SORT_BY_TITLE("Sort books and movies by title"),
        SORT_BY_PAGES_AND_DURATION("Sort books and movies by pages and duration"),
        BACK_TO_MAIN_MENU("Back to main menu");
        private String description;

        SortingMenu(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }
    }

    public static <T extends hasDescription> T showMenuAndGetChoice(T[] menuChoices) {
        int choice = -1;
        while (choice == -1 || choice > menuChoices.length) {
            int i = 1;
            for (T menuChoice : menuChoices) {
                System.out.println(i + ". " + menuChoice.getDescription());
                i++;
            }
            Scanner scanner = new Scanner(System.in);
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice > menuChoices.length) {
                    System.out.println("That is not an acceptable choice.\n");
                }
            } catch (Exception e) {
                System.out.println("That is not an acceptable choice.\n");
            }
        }
        return menuChoices[choice - 1];
    }
}


