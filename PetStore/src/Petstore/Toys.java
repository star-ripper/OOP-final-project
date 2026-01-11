package Petstore;

public class Toys extends InventoryItem {
    public Toys(String name, String brand, int quantity) {
        super(name, brand, quantity);
    }

    @Override
    public String getType() {
        return "Toy";
    }
}
