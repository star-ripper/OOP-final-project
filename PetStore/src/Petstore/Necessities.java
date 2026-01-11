package Petstore;

public class Necessities extends InventoryItem {
    public Necessities(String name, String brand, int quantity) {
        super(name, brand, quantity);
    }

    @Override
    public String getType() {
        return "Toy";
    }
}
