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

    public void updateItem(int index, T item) {
        this.itemList.set(index, item);
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
