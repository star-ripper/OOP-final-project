package petstore.models;

import petstore.IsSold;
import petstore.PetType;

public class Dog extends Pet {
    private String breed;
    private boolean isVaccinated;
    PetType petType;
    IsSold status;
    public Dog(String name, int age, String breed, boolean isVaccinated) {
        super(name, age);
        this.breed = breed;
        this.isVaccinated = isVaccinated;
        this.petType = PetType.DOG;

    }
    Dog(String name, int age, String breed, boolean isVaccinated, IsSold status) {
        super(name, age);
        this.breed = breed;
        this.isVaccinated = isVaccinated;
        this.petType = PetType.DOG;
        this.status = status;
    }
    public String getBreed() {return breed;}
    void displayInfo(){
        System.out.print("|Name: " + getName());
        System.out.print("  |Age: " + getAge());
        System.out.print("  |ID: " + getId());
        System.out.print("  |Breed: " + breed);
        System.out.print("  |Price: " + petType.getPrice());
        System.out.print("    |IsVaccinated: ");
        if(isVaccinated)
            System.out.print("Yes|");
        else System.out.print("No|");
        System.out.println("  |Status: " + super.getStatus() + "|");
    }
}
