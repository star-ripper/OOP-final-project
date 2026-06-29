package petstore.models;

public abstract class InventoryItem {
    protected int id;
    protected String name;
    protected String brand;
    protected int quantity;
    static int count;

    public InventoryItem(int id, String name, String brand, int quantity) {
        this.id = ++count;
        this.name = name;
        this.brand = brand;
        this.quantity = quantity;
    }
    public InventoryItem(String name, String brand, int quantity) {
        this.name = name;
        this.brand = brand;
        this.quantity = quantity;
        this.id = ++count;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getBrand() { return brand; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}

