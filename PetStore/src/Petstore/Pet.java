package Petstore;

abstract class Pet {
    private String name;
    private IsSold status;
    private int age;
    private int id;
    private double price;
    private static int count;

    Pet(){};
    public Pet(String name, int age) {
        this.name = name;
        this.age = age;
        id = ++count;
        status = IsSold.AVAILABLE;
    }
    String getName() {return name;}
    int getAge() {return age;}
    int getId() {return id;}
    void setPrice(double price) {this.price = price;}
    double getPrice() {return price;}
    abstract void displayInfo();
    IsSold getStatus() {return status;}
    void setStatus(IsSold status) {this.status = status;}

}
