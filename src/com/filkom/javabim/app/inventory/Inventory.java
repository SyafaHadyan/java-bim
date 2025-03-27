package app.inventory;

import java.util.ArrayList;

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

    public int size() {
        return this.itemList.size();
    }

    public T get(int i) {
        return itemList.get(i);
    }
}
