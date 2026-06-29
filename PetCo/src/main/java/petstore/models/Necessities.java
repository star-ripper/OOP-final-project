package petstore.models;

public class Necessities extends InventoryItem {
    public Necessities(String name, String brand, int quantity) { super(name, brand, quantity); }
    public Necessities(int id, String name, String brand, int quantity) { super(id, name, brand, quantity); }
}
