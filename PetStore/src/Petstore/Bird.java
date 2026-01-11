package Petstore;

public class Bird extends Pet{
    private String species;
    private boolean canFly;
    PetType petType;
    IsSold status;
    Bird(String name, int age, String species, boolean canFly) {
        super(name, age);
        this.species = species;
        this.canFly = canFly;
        this.petType = PetType.BIRD;
    }
    Bird(String name, int age, String species, boolean canFly, IsSold status) {
        super(name, age);
        this.species = species;
        this.canFly = canFly;
        this.petType = PetType.BIRD;
        this.status = status;
    }
    String getSpecies() {
        return species;
    }
    void displayInfo(){
        System.out.print("|Name: " + getName());
        System.out.print("  |Age: " + getAge());
        System.out.print("  |ID: " + getId());
        System.out.print("  |Species: " + species);
        System.out.print("  |Price: " + petType.getPrice());
        System.out.print("    |Can Fly: ");
        if(canFly)
            System.out.print("Yes");
        else System.out.print("No");
        System.out.println("  |Status: " + super.getStatus() + "|");
    }
}
