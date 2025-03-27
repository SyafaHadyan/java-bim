package app.inventory;

import java.util.ArrayList;

public class Inventory<T> {

    private ArrayList<T> itemList = new ArrayList<>();

    public void addItem(T item) {
        this.itemList.add(item);
    }

    public ArrayList<T> getAllItems() {
        return this.itemList;
    }

    public void updateItem(T item) {
        this.itemList.add(item);
    }

    public void removeItem(T item) {
        this.itemList.remove(item);
    }

    public int size() {
        return this.itemList.size();
    }

    public T get(int index) {
        return this.itemList.get(index);
    }
}
