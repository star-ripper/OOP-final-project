package petstore.models;

import petstore.IsSold;

public abstract class Pet {
    private String name;
    private IsSold status;
    private int age;
    private int id;
    private double price;
    private static int count;

    public Pet(){};
    public Pet(String name, int age) {
        this.name = name;
        this.age = age;
        id = ++count;
        status = IsSold.AVAILABLE;
    }

    public String getName() {return name;}
    public int getAge() {return age;}
    public int getId() {return id;}
    void setPrice(double price) {this.price = price;}
    double getPrice() {return price;}
    abstract void displayInfo();
    public IsSold getStatus() {return status;}
    void setStatus(IsSold status) {this.status = status;}

}
