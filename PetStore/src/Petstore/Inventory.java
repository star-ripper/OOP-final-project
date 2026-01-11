package Petstore;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Inventory implements FileEditing {

    private List<InventoryItem> items;

    public Inventory() {
        items = new ArrayList<>();
    }

    @Override
    public void addData() {
        try (BufferedReader br = new BufferedReader(new FileReader("Inventory.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.trim().split(" {2,}");
                if (data.length < 4) continue;

                String type = data[0].trim();
                String name = data[1];
                String brand = data[2];
                int qty = Integer.parseInt(data[3]);

                InventoryItem item;

                switch (type) {
                    case "Feed:":
                        item = new Feed(name, brand, qty);
                        break;
                    case "Toy:":
                        item = new Toys(name, brand, qty);
                        break;
                    case "Necessity:":
                        item = new Necessities(name, brand, qty);
                        break;
                    default:
                        continue;
                }

                items.add(item);
            }

        } catch (IOException e) {
        }
    }

    /* ---------------- DELETE OR UPDATE DATA ---------------- */
    @Override
    public <T> void deleteFromFile(T data) {
        if (data instanceof Integer id) {
            removeItemById(id);
        } else {
            System.out.println("Unsupported data type for deletion.");
        }
        // file is already updated in removeItemById
    }

    /* ---------------- ADMIN METHODS ---------------- */
    public void addItem(InventoryItem item) {
        items.add(item);
        writeToFile();
        System.out.println("Inventory item added.");
    }

    public void removeItemById(int id) {
        InventoryItem toRemove = null;
        for (InventoryItem item : items) {
            if (item.getId() == id) {
                toRemove = item;
                break;
            }
        }
        if (toRemove != null) {
            items.remove(toRemove);
            writeToFile();
            System.out.println("Inventory item removed.");
        } else {
            System.out.println("Item not found.");
        }
    }

    /* ---------------- CUSTOMER METHOD ---------------- */
    public void buyItem(int id, int qty) {
        InventoryItem item = getItemById(id);

        if (item == null) {
            System.out.println("Item not found.");
            return;
        }
        if (qty <= 0) {
            System.out.println("Invalid quantity.");
            return;
        }
        if (qty > item.getQuantity()) {
            System.out.println("Not enough stock available.");
            return;
        }

        item.reduceQuantity(qty);
        writeToFile();
        System.out.println("Purchase successful.");
    }

    /* ---------------- HELPER METHODS ---------------- */
    private InventoryItem getItemById(int id) {
        for (InventoryItem item : items) {
            if (item.getId() == id) return item;
        }
        return null;
    }

    public void displayAll() {
        if (items.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }

        System.out.println("ID | Type | Name | Brand | Quantity");
        for (InventoryItem item : items) {
            System.out.println(
                    item.getId() + " | " +
                            item.getType() + " | " +
                            item.getName() + " | " +
                            item.getBrand() + " | " +
                            item.getQuantity()
            );
        }
    }

    /* ---------------- FILE WRITING ---------------- */
    private void writeToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Inventory.txt"))) {
            for (InventoryItem item : items) {
                bw.write(
                        item.getType() + ":    " +
                                item.getName() + "    " +
                                item.getBrand() + "    " +
                                item.getQuantity()
                );
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
