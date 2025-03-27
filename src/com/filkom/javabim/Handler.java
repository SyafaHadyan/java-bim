import util.Pager;

import java.net.URI;
import java.net.URL;
import java.net.URISyntaxException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.DateTimeException;
import java.util.Scanner;
import java.util.ArrayList;

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
        if (book == null) {
            pager.info("No book found in library");
            return;
        }
        pager.info("Book count: " + book.size());
        for (int i = 0; i < book.size(); i++) {
            parseBookToPager(book.get(i));
        }
    }

    public void search() {
        //
    }

    public void update() {
        //
    }

    public void delete() {
        //
    }

    public void quit() {
        //
    }

    public void invalidInput() {
        //
    }
}
