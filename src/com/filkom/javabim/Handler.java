package com.filkom.javabim;

import java.net.URI;
import java.net.URL;
import java.net.URISyntaxException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.logging.Level;

import com.filkom.javabim.util.Pager;
import com.filkom.javabim.app.book.Book;
import com.filkom.javabim.app.inventory.Inventory;
import com.filkom.javabim.app.library.Library;

public class Handler {
    Pager pager = new Pager();
    Library library = new Library();
    Logger logger = Logger.getLogger(Handler.class.getName());

    int readCount = 0;
    int writeCount = 0;

    /*
     * Used to convert number to ordinal for displaying edition, when editon is 0
     * (no input from user), edition will not be displayed.
     */
    private String convertEditionNumberToOrdinal(int edition) {
        String[] numberSuffix = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th",
                "th" };
        switch (edition % 100) {
            case 0:
                return "";
            case 11:
            case 12:
            case 13:
                return " " + edition + " th edition";
            default:
                return " " + edition + numberSuffix[edition % 10] + " edition";
        }
    }

    /*
     * Pager::info is used quite frequently (to display book info) (DRY principle).
     */
    private void parseBookToPager(Book book) {
        String edition = convertEditionNumberToOrdinal(book.getEdition());
        pager.info("ID: " + book.getID(), "Title: " + book.getTitle() + edition, "Author: " + book.getAuthor(),
                "Book Cover: " + book.getBookCover(), "Published Date: " + book.getPublishedDate(),
                "Stock: " + book.getStock());
    }

    /*
     * The same input field is used for both creating a new book and updating
     * existing book.
     */
    private Book completeMetadataInput() {
        pager.header("Enter Book Info");

        /* Initialize variables */
        String title;
        int edition = 0;
        String author;
        URL bookCover;
        LocalDate publishedDate;
        int stock;
        title = pager.customInput("Title", true);

        /*
         * Handle edition input (if empty string, will skip validation check and
         * proceed), if negative integer or string, user will be asked to re-enter
         * positive integer
         */
        while (true) {
            try {
                String temp = pager.customInput("Edition (Leave blank to skip)", true);
                if (temp.isEmpty()) {
                    break;
                }
                edition = Integer.parseUnsignedInt(temp);
                break;
            } catch (NumberFormatException e) {
                pager.message("Edition must be a positive number");
            }
        }
        author = pager.customInput("Author", true);

        /*
         * Validate url input (https://example.com), if invalid, user will be asked to
         * re-enter valid url
         */
        while (true) {
            try {
                String temp = pager.customInput("Book Cover (URL)", true);
                bookCover = new URI(temp).toURL();
                break;
            } catch (IllegalArgumentException | URISyntaxException | MalformedURLException e) {
                pager.message("Invalid url");
            }
        }

        /*
         * Handle date input using LocalDate class. Will loop back if input is invalid
         */
        while (true) {
            try {
                int year = Integer.parseUnsignedInt(pager.customInput("Published Year", true));
                int month = Integer.parseUnsignedInt(pager.customInput("Published Month", true));
                int dayOfMonth = Integer.parseUnsignedInt(pager.customInput("Published Day", true));
                publishedDate = LocalDate.of(year, month, dayOfMonth);
                break;
            } catch (NumberFormatException | DateTimeException e) {
                pager.message("Invalid date");
            }
        }

        /* Book stock must be valid positive integer (Integer::parseUnsignedInt) */
        while (true) {
            try {
                stock = Integer.parseUnsignedInt(pager.customInput("Stock", true));
                break;
            } catch (NumberFormatException e) {
                pager.message("Stock must be a positive number");
            }
        }
        pager.footer();
        return new Book(title, edition, author, bookCover, publishedDate, stock);
    }

    /*
     * Used to find specific attribute of a book, since we need ID, Title, and
     * Author, we just pass user input (as int) then use if-else to differentiate
     * the getter method from Book class.
     */
    private void customSearch(int functionIndex) {
        pager.header("Search");
        String keyword = null;
        UUID ID = null;

        /* Handle book ID input. */
        if (functionIndex == 0) {
            try {
                ID = UUID.fromString(pager.customInput("ID", true));
            } catch (IllegalArgumentException e) {
                pager.footer();
                pager.info("Invalid book ID");
                return;
            }
        } else {
            keyword = pager.customInput("Keyword", true);
        }
        pager.footer();
        boolean match = false;

        /* Get all books then match using functionIndex. */
        Inventory<Book> book = library.getAllBooks();

        /* Handle if Library::getAllBooks retuns null or empty sized list. */
        if (book == null || book.size() == 0) {
            pager.info("No book found in library");
            return;
        }

        /* Iterate through every book to find match from user input. */
        for (int i = 0; i < book.size(); i++) {
            if (functionIndex == 0 && book.get(i).getID().equals(ID)) {
                parseBookToPager(book.get(i));
                return;
            } else if (functionIndex == 1 && book.get(i).getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                parseBookToPager(book.get(i));
                match = true;
            } else if (functionIndex == 2 && book.get(i).getAuthor().toLowerCase().contains(keyword.toLowerCase())) {
                parseBookToPager(book.get(i));
                match = true;
            }
        }

        /* If no match found, display info to user. */
        if (!match) {
            if (functionIndex == 0) {
                pager.info("No book found with ID " + ID);
            } else if (functionIndex == 1) {
                pager.info("No book found with title " + keyword);
            } else if (functionIndex == 2) {
                pager.info("No book found with author " + keyword);
            }
        }
    }

    /* Create new book. */
    public void create() {
        /* Call completeMetadataInput then pass result to Library::addBook. */
        library.addBook(completeMetadataInput());
        this.writeCount++;
    }

    /* Get alll books from Inventory. */
    public void get() {
        Inventory<Book> book = library.getAllBooks();

        /* Handle if Inventory is empty. */
        if (book == null || book.size() == 0) {
            pager.info("No book found in library");
            return;
        }

        /* Display each book with Pager::info. */
        pager.info("Book count: " + book.size());
        for (int i = 0; i < book.size(); i++) {
            parseBookToPager(book.get(i));
        }
        this.readCount++;
    }

    /* Search book menu. */
    public void search() {

        /* Loop indefinetely until quit. */
        while (true) {
            pager.header("Search Book");
            pager.message("(0) Search by ID");
            pager.message("(1) Search by Title");
            pager.message("(2) Search by Author");
            pager.message("(3) Return to Main Menu");
            pager.spacer();
            try {
                int userInputOption = Integer.parseUnsignedInt(pager.input());
                if (userInputOption == 3) {
                    return;
                } else if (userInputOption > 3) {
                    /* Handle invalid input. */
                    invalidInput();
                } else {
                    /* Call search function */
                    customSearch(userInputOption);
                    this.readCount++;
                }
            } catch (NumberFormatException e) {
                /* Handle invalid integer */
                invalidInput();
            }
        }
    }

    /* Update existing book (using valid book ID) */
    public void update() {
        pager.header("Update Book");
        UUID ID = null;

        /* Handle book ID input */
        try {
            ID = UUID.fromString(pager.customInput("ID", true));
        } catch (IllegalArgumentException e) {
            pager.footer();
            pager.info("Invalid book ID");
            return;
        }

        /* Get all books then match with current entered book id */
        Inventory<Book> book = library.getAllBooks();
        pager.footer();

        /* Handle if no book in library */
        if (book == null || book.size() == 0) {
            pager.info("No book found in library");
            return;
        }

        /* If found, update book */
        for (int i = 0; i < book.size(); i++) {
            if (book.get(i).getID().equals(ID)) {
                library.updateBook(i, completeMetadataInput());
                this.writeCount++;
                return;
            }
        }

        /* If no book found with curretn id, inform user */
        pager.info("No book found with ID " + ID);
    }

    /* Delete book with book ID */
    public void delete() {
        readCount++;
        pager.header("Delete Book");
        UUID ID = null;
        try {
            ID = UUID.fromString(pager.customInput("ID", true));
        } catch (IllegalArgumentException e) {
            pager.footer();
            pager.info("Invalid book ID");
            return;
        }
        Inventory<Book> book = library.getAllBooks();

        /* Handle if no book in library. */
        if (book == null || book.size() == 0) {
            pager.info("No book found in library");
            return;
        }

        /* Iterate through each book and find with matching ID */
        for (int i = 0; i < book.size(); i++) {
            if (book.get(i).getID().equals(ID)) {
                pager.footer();
                parseBookToPager(book.get(i));
                library.removeBook(book.get(i));
                return;
            }
        }
        pager.footer();

        /* Handle if no book found with current ID */
        pager.info("No book found with ID " + ID);
    }

    /* Quit function */
    public void quit() {
        logger.log(Level.INFO, "Read count: " + this.readCount);
        logger.log(Level.INFO, "Write count: " + this.writeCount);
    }

    /* Handle if user input is invalid */
    public void invalidInput() {
        pager.info("Invalid input");
    }
}
