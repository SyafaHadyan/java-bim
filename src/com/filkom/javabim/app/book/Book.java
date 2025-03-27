package app.book;

import java.net.URI;
import java.net.URL;
import java.net.URISyntaxException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.UUID;

public class Book {
    private UUID ID;
    private String title;
    private int edition;
    private String author;
    private URL bookCover;
    private LocalDate publishedDate;
    private int stock;

    /* Empty constructor to debug / testing purposes. */
    public Book() {
        this.ID = UUID.randomUUID();
        this.title = "Operating System Concepts";
        this.edition = 10;
        this.author = "Abraham Silberschatz";
        try {
            this.bookCover = new URI("https://codex.cs.yale.edu/avi/os-book/OS10/images/os10-cover.jpg").toURL();
        } catch (URISyntaxException | MalformedURLException e) {
            /* Nothing to handle here */
        }
        this.publishedDate = LocalDate.of(2018, 04, 15);
        this.stock = 64;
    }

    /*
     * Constructor with all attributes of the book, will generate random UUID
     * (Universally Unique Identifier) to ensure that no more than one book can have
     * the same ID.
     *
     * WIKIPEDIA:
     * the probability to find a duplicate within 103 trillion version-4 UUIDs is
     * one in a billion.
     *
     * UUID is most likely safe to be used here. Even if UUID collision happens,
     * just use hashmap to find if current ID has been used or not.
     */
    public Book(String title, int edition, String author, URL bookCover, LocalDate publishedDate, int stock) {
        this.ID = UUID.randomUUID();
        this.title = title;
        this.edition = edition;
        this.author = author;
        this.bookCover = bookCover;
        this.publishedDate = publishedDate;
        this.stock = stock;
    }

    /* Setters */
    public void setID(UUID ID) {
        this.ID = ID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setBookCover(URL bookCover) {
        this.bookCover = bookCover;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    /* Getters */
    public UUID getID() {
        return this.ID;
    }

    public String getTitle() {
        return this.title;
    }

    public int getEdition() {
        return this.edition;
    }

    public String getAuthor() {
        return this.author;
    }

    public URL getBookCover() {
        return this.bookCover;
    }

    public LocalDate getPublishedDate() {
        return this.publishedDate;
    }

    public int getStock() {
        return this.stock;
    }
}
