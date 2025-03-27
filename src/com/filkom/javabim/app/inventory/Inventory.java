package app.inventory;

import java.util.ArrayList;
import java.util.UUID;

import app.book.Book;

public class Inventory<T> {
    private ArrayList<T> itemList = new ArrayList<>();

    public void addItem(T item) {
        itemList.add(item);
    }

    public ArrayList<T> getAllItems() {
        return this.itemList;
    }

    public ArrayList<T> searchItems(String keyword) {
        return this.itemList;
    }

    public void updateItem(T item) {
        itemList.add(item);
    }

    public void removeItem(T item) {
        itemList.remove(item);
    }

}
