package petstore.models;

public class Toys extends InventoryItem {
    public Toys(String name, String brand, int quantity) { super(name, brand, quantity); }
    public Toys(int id, String name, String brand, int quantity) { super(id, name, brand, quantity); }
}
