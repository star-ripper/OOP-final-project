package Petstore;

public enum PetType {
    CAT(20000),
    DOG(25000),
    BIRD(15000);
    private double price;
    public double getPrice() {
        return price;
    }
    PetType(double price) {
        this.price = price;
    }
}
