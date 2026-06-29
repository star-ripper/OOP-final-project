package petstore.models;

public class Feed extends InventoryItem {
    public Feed(String name, String brand, int quantity) { super(name, brand, quantity); }
    public Feed(int id, String name, String brand, int quantity) { super(id, name, brand, quantity); }
}
