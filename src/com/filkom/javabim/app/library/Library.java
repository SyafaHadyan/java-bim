package com.filkom.javabim.app.library;

import com.filkom.javabim.app.book.Book;
import com.filkom.javabim.app.inventory.Inventory;

public class Library {
    Inventory<Book> inventory = new Inventory<>();

    /* Add book from Handler class. */
    public void addBook(Book book) {
        inventory.addItem(book);
    }

    /* Get all books from inventory. */
    public Inventory<Book> getAllBooks() {
        return this.inventory;
    }

    /* Update existing book. */
    public void updateBook(int index, Book book) {
        inventory.updateItem(index, book);
    }

    /* Remove book */
    public void removeBook(Book book) {
        inventory.removeItem(book);
    }

}
