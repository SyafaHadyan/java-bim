package com.filkom.javabim.app.inventory;

import java.util.ArrayList;

public class Inventory<T> {
    private ArrayList<T> itemList = new ArrayList<>();

    /* Add item to list */
    public void addItem(T item) {
        this.itemList.add(item);
    }

    /* Return list (all items) */
    public ArrayList<T> getAllItems() {
        return this.itemList;
    }

    /* Update existing item */
    public void updateItem(int index, T item) {
        this.itemList.set(index, item);
    }

    /* Remove existing item */
    public void removeItem(T item) {
        this.itemList.remove(item);
    }

    /* Return size of list */
    public int size() {
        return this.itemList.size();
    }

    /* Return item from list with index */
    public T get(int index) {
        return this.itemList.get(index);
    }
}
