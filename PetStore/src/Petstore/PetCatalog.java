package Petstore;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PetCatalog implements  FileEditing {
    List<Pet> pets ;
    public void addData() {
        try {
            IsSold status = null;
            FileReader fr = new FileReader("Pets.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null){
                Pet pet;
                String[] data = line.trim().split(" {2,}");
                if(Objects.equals(data[data.length-1], "Available")){
                    status = IsSold.AVAILABLE;
                }
                else if(Objects.equals(data[data.length-1], "Sold")){
                    status = IsSold.SOLD;
                }
                else if(Objects.equals(data[data.length-1], "Reserved")){
                    status = IsSold.RESERVED;
                }
                switch(data[0].trim()) {
                    case "Cat:":
                        pet = new Cat(data[1], Integer.parseInt(data[2]), data[3], data[4], true, status);
                        break;
                    case "Dog:":
                        pet = new Dog(data[1], Integer.parseInt(data[2]), data[3], true, status);
                        break;
                    case "Bird:":
                        pet = new Bird(data[1], Integer.parseInt(data[2]), data[3], true);
                        break;
                    default:
                        continue;
                }
                pets.add(pet);

            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public <K> void buyPet(K data) {
        boolean found = false;

        if(data instanceof String a) {
            for(Pet p : pets) {
                if(p.getName().equals(a)) {
                    p.setStatus(IsSold.SOLD);
                    found = true;
                    break;
                }
            }
        }
        else if(data instanceof Integer i) {
            for(Pet p : pets) {
                if(p.getId() == i) {
                    p.setStatus(IsSold.SOLD);
                    found = true;
                    break;
                }
            }
        }
        if(!found)
            System.out.println("Pet could not be found");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Pets.txt"))) {
            for (Pet p : pets) {
                if (p instanceof Cat c) {
                    bw.write("Cat:    " + c.getName() + "    " + c.getAge() + "    " + c.getBreed() + "    " + c.getFurColor() + "    " + c.getStatus());
                } else if (p instanceof Dog d) {
                    bw.write("Dog:    " + d.getName() + "    " + d.getAge() + "    " + d.getBreed() + "    " + d.getStatus());
                } else if (p instanceof Bird b) {
                    bw.write("Bird:    " + b.getName() + "    " + b.getAge() + "    " + b.getSpecies() + "    " + b.getStatus());
                }
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    PetCatalog(){
        pets = new ArrayList<>();

    }
    PetCatalog(Pet p){
        pets = new ArrayList<>();
        pets.add(p);
    }
    void addPet(Pet p){
        pets.add(p);
    }
    void removePet(int id){
        for(int i=0;i<pets.size();i++){
            if(pets.get(i).getId()==id){
                pets.remove(i);
                System.out.println("Pet removed");
                return;
            }
        }
        System.out.println("Pet cannot be Found");
    }
    void search(int id){
        for(Pet p : pets){
            if(p.getId() == id){
                p.displayInfo();
                return;
            }
        }
        System.out.println("Unable to find pet with id " + id);
    }
     public <T> void deleteFromFile(T data){
        if(data instanceof Integer id){
            removePet(id);
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Pets.txt"))) {
            for (Pet p : pets) {
                if (p instanceof Cat c) {
                    bw.write("Cat:    " + c.getName() + "    " + c.getAge() + "    " + c.getBreed() + "    " + c.getFurColor());
                } else if (p instanceof Dog d) {
                    bw.write("Dog:    " + d.getName() + "    " + d.getAge() + "    " + d.getBreed());
                } else if (p instanceof Bird b) {
                    bw.write("Bird:    " + b.getName() + "    " + b.getAge() + "    " + b.getSpecies());
                }
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void search(String name){
        for(Pet p : pets){
            if(p.getName().equalsIgnoreCase(name)){
                p.displayInfo();
            }
        }
        System.out.println();
    }
    void displayAll(){
        for(Pet p : pets){
                p.displayInfo();
        }
    }
    void sellPet(int id){
        for(Pet p : pets){
            if(p.getId() == id){
                p.setStatus(IsSold.SOLD);
                break;
            }
        }
        System.out.println("Sold pet");
        search(id);
    }
    void reservePet(int id) {
        for (Pet p : pets) {
            if (p.getId() == id) {
                p.setStatus(IsSold.RESERVED);
                break;
            }
        }
        System.out.println("Reserved pet");
        search(id);
    }
}
