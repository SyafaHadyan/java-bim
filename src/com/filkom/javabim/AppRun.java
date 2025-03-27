package com.filkom.javabim;

import java.util.Scanner;

import com.filkom.javabim.util.Pager;

public class AppRun {
    static final String VERSION = "v1.0.0";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Pager pager = new Pager();
        Handler handler = new Handler();

        /* Loop indefinetely until quit is selected. */
        while (true) {
            pager.header("Java Book Inventory Manager " + VERSION);
            pager.message("(0) Input Book");
            pager.message("(1) Get All Books");
            pager.message("(2) Search Book");
            pager.message("(3) Update Book");
            pager.message("(4) Delete Book");
            pager.message("(5) Quit");
            pager.spacer();

            /* Call input function from Pager class. */
            String userInputOption = pager.input();
            pager.footer();

            /* Switch user input, if invalid, call invalid input handler. */
            switch (userInputOption) {
                case "0":
                    handler.create();
                    break;
                case "1":
                    handler.get();
                    break;
                case "2":
                    handler.search();
                    break;
                case "3":
                    handler.update();
                    break;
                case "4":
                    handler.delete();
                    break;
                case "5":
                    scanner.close();
                    handler.quit();
                    System.exit(0);
                default:
                    handler.invalidInput();
                    break;
            }
        }
    }
}
