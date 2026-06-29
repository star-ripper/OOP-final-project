package petstore.models;

import petstore.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Inventory implements FileEditing {

    private final List<InventoryItem> items = new ArrayList<>();
    private final File file = new File("Inventory.txt");

    public Inventory() {
        if (!file.exists()) {
            try { file.createNewFile(); }
            catch (IOException e) { e.printStackTrace(); }
        }
    }

    public void addItem(InventoryItem item) {
        items.add(item);
        saveData();
    }

    public void removeItemById(int id) {
        items.removeIf(item -> item.getId() == id);
        saveData();
    }

    public boolean buyItem(int id, int qty) {
        for (InventoryItem item : items) {
            if (item.getId() == id) {
                if (item.getQuantity() >= qty) {
                    item.setQuantity(item.getQuantity() - qty);
                    saveData();
                    return true;
                } else return false;
            }
        }
        return false;
    }

    public List<InventoryItem> getItems() { return items; }

    @Override
    public void addData() {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            items.clear();
            while ((line = br.readLine()) != null) {
                String[] data = line.trim().split(" {2,}");
                if (data.length < 5) continue;

                String type = data[0];
                int id = Integer.parseInt(data[1]);
                String name = data[2];
                String brand = data[3];
                int qty = Integer.parseInt(data[4]);

                InventoryItem item;
                switch (type) {
                    case "Feed": item = new Feed(id, name, brand, qty); break;
                    case "Toys": item = new Toys(id, name, brand, qty); break;
                    case "Necessities": item = new Necessities(id, name, brand, qty); break;
                    default: continue;
                }
                items.add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (InventoryItem item : items) {
                String type = item.getClass().getSimpleName();
                bw.write(type + "  " + item.getId() + "  " + item.getName() + "  " + item.getBrand() + "  " + item.getQuantity());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public InventoryItem getItemById(int id) {
        for (InventoryItem item : items) {
            if (item.getId() == id) return item;
        }
        return null;
    }


    @Override
    public <T> void deleteFromFile(T data) {
        if (data instanceof Integer id) {
            removeItemById(id);
        }
        saveData();
    }
}

