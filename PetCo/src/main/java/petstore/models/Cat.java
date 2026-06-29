package petstore.models;

import petstore.IsSold;
import petstore.PetType;

public class Cat extends Pet {
    private String breed;
    private String furColor;
    private boolean isVaccinated;
    PetType petType;
    IsSold isSold;
    public Cat(String name, int age, String breed, String furColor, boolean isVaccinated) {
        super(name, age);
        this.breed = breed;
        this.furColor = furColor;
        this.isVaccinated = isVaccinated;
        petType = PetType.CAT;
    }
    Cat(String name, int age, String breed, String furColor, boolean isVaccinated, IsSold isSold) {
        super(name, age);
        this.breed = breed;
        this.furColor = furColor;
        this.isVaccinated = isVaccinated;
        petType = PetType.CAT;
        isSold = isSold;
    }
    public String getBreed() {return breed;}
    public String getFurColor() {return furColor;}
    boolean isVaccinated() {return isVaccinated;}

    void displayInfo(){
        System.out.print("|Name: " + getName());
        System.out.print("  |Age: " + getAge());
        System.out.print("  |ID: " + getId());
        System.out.print("  |Breed: " + breed);
        System.out.print("  |FurColor: " + furColor);
        System.out.print("  |Price: " + petType.getPrice());
        System.out.print("  |IsVaccinated: ");
        if(isVaccinated)
            System.out.print("Yes");
        else System.out.print("No");
        System.out.println("  |Status: " + super.getStatus() + "|");
    }

}
