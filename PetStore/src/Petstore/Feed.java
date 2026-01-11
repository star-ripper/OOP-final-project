package Petstore;

class Feed extends InventoryItem {
    public Feed(String name, String brand, int quantity) {
        super(name, brand, quantity);
    }

    @Override
    public String getType() {
        return "Feed";
    }
}
