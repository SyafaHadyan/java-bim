package app.library;

import java.util.ArrayList;

import app.book.Book;
import app.inventory.Inventory;

public class Library {
    Inventory<Book> inventory = new Inventory<>();

    public void addBook(Book book) {
        inventory.addItem(book);
    }

    public Inventory<Book> getAllBooks() {
        return this.inventory;
    }

    public ArrayList<Book> searchBooks(String keyword) {
        return null;
    }

    public void updateBook(Book book) {
        //
    }

    public void removeBook(Book book) {
        inventory.removeItem(book);
    }

}
