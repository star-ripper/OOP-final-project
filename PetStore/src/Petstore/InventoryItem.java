package Petstore;

public abstract class InventoryItem {
    protected int id;
    protected String name;
    protected String brand;
    protected int quantity;

    private static int counter = 1;

    public InventoryItem(String name, String brand, int quantity) {
        this.id = counter++;
        this.name = name;
        this.brand = brand;
        this.quantity = quantity;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getBrand() { return brand; }
    public int getQuantity() { return quantity; }

    public void reduceQuantity(int amount) {
        this.quantity -= amount;
    }

    public void increaseQuantity(int amount) {
        this.quantity += amount;
    }

    public abstract String getType();
}
