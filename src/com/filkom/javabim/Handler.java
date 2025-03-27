import util.Pager;

import java.net.URI;
import java.net.URL;
import java.net.URISyntaxException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import app.book.Book;
import app.inventory.Inventory;
import app.library.Library;

public class Handler {
    Scanner scanner = new Scanner(System.in);
    Pager pager = new Pager();
    Library library = new Library();

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

    private void parseBookToPager(Book book) {
        String edition = convertEditionNumberToOrdinal(book.getEdition());
        pager.info("ID: " + book.getID(), "Title: " + book.getTitle() + edition, "Author: " + book.getAuthor(),
                "Book Cover: " + book.getBookCover(), "Published Date: " + book.getPublishedDate(),
                "Stock: " + book.getStock());
    }

    public void customSearch(int functionIndex) {
        pager.header("Search");
        String keyword = null;
        UUID ID = null;
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
        Inventory<Book> book = library.getAllBooks();
        if (book == null || book.size() == 0) {
            pager.info("No book found in library");
            return;
        }
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

    public void create() {
        pager.header("Create New Book");
        String title;
        int edition = 0;
        String author;
        URL bookCover;
        LocalDate publishedDate;
        int stock;
        title = pager.customInput("Title", true);
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
        while (true) {
            try {
                String temp = pager.customInput("Book Cover (URL)", true);
                bookCover = new URI(temp).toURL();
                break;
            } catch (IllegalArgumentException | URISyntaxException | MalformedURLException e) {
                pager.message("Invalid url");
            }
        }
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
        while (true) {
            try {
                stock = Integer.parseUnsignedInt(pager.customInput("Stock", true));
                break;
            } catch (NumberFormatException e) {
                pager.message("Stock must be a positive number");
            }
        }
        library.addBook(new Book(title, edition, author, bookCover, publishedDate, stock));
        pager.footer();
    }

    public void get() {
        Inventory<Book> book = library.getAllBooks();
        if (book == null || book.size() == 0) {
            pager.info("No book found in library");
            return;
        }
        pager.info("Book count: " + book.size());
        for (int i = 0; i < book.size(); i++) {
            parseBookToPager(book.get(i));
        }
    }

    public void search() {
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
                    invalidInput();
                } else {
                    customSearch(userInputOption);
                }
            } catch (NumberFormatException e) {
                invalidInput();
            }
        }
    }

    public void update() {
        //
    }

    public void delete() {
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
        if (book == null || book.size() == 0) {
            pager.info("No book found in library");
            return;
        }
        for (int i = 0; i < book.size(); i++) {
            if (book.get(i).getID().equals(ID)) {
                pager.footer();
                parseBookToPager(book.get(i));
                library.removeBook(book.get(i));
                return;
            }
        }
        pager.footer();
        pager.info("No book found with ID " + ID);
    }

    public void quit() {
        //
    }

    public void invalidInput() {
        pager.info("Invalid input");
    }
}
