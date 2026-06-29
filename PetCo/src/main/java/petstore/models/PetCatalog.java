package petstore.models;

import petstore.FileEditing;
import petstore.IsSold;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PetCatalog implements FileEditing {
    private static final String PETS_FILE = "Pets.txt";

    public List<Pet> pets;

    public void addData() {
        pets.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(PETS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.trim().split(" {2,}");
                if (data.length < 4) {
                    continue;
                }

                IsSold status = parseStatus(data[data.length - 1]);
                if (status == null) {
                    status = IsSold.AVAILABLE;
                }

                Pet pet;
                switch (data[0].trim()) {
                    case "Cat:":
                        if (data.length < 6) continue;
                        pet = new Cat(data[1], Integer.parseInt(data[2]), data[3], data[4], true);
                        break;
                    case "Dog:":
                        if (data.length < 5) continue;
                        pet = new Dog(data[1], Integer.parseInt(data[2]), data[3], true);
                        break;
                    case "Bird:":
                        if (data.length < 5) continue;
                        pet = new Bird(data[1], Integer.parseInt(data[2]), data[3], true);
                        break;
                    default:
                        continue;
                }

                pet.setStatus(status);
                pets.add(pet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Pet> getAllPets() {
        return new ArrayList<>(pets);
    }

    public <K> void buyPet(K data) {
        boolean found = false;

        if (data instanceof String a) {
            for (Pet p : pets) {
                if (p.getName().equals(a)) {
                    p.setStatus(IsSold.SOLD);
                    found = true;
                    break;
                }
            }
        } else if (data instanceof Integer i) {
            for (Pet p : pets) {
                if (p.getId() == i) {
                    p.setStatus(IsSold.SOLD);
                    found = true;
                    break;
                }
            }
        }
        if (!found)
            System.out.println("Pet could not be found");

        savePetsData();
    }

    public PetCatalog() {
        pets = new ArrayList<>();

    }

    PetCatalog(Pet p) {
        pets = new ArrayList<>();
        pets.add(p);
    }

    public void addPet(Pet p) {
        pets.add(p);
    }

    void removePet(int id) {
        for (int i = 0; i < pets.size(); i++) {
            if (pets.get(i).getId() == id) {
                pets.remove(i);
                System.out.println("Pet removed");
                return;
            }
        }
        System.out.println("Pet cannot be Found");
    }

    void search(int id) {
        for (Pet p : pets) {
            if (p.getId() == id) {
                p.displayInfo();
                return;
            }
        }
        System.out.println("Unable to find pet with id " + id);
    }

    public <T> void deleteFromFile(T data) {
        if (data instanceof Integer id) {
            removePet(id);
        }
        savePetsData();
    }

    void search(String name) {
        for (Pet p : pets) {
            if (p.getName().equalsIgnoreCase(name)) {
                p.displayInfo();
            }
        }
        System.out.println();
    }

    public void displayAll() {
        for (Pet p : pets) {
            p.displayInfo();
        }
    }

    void sellPet(int id) {
        for (Pet p : pets) {
            if (p.getId() == id) {
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

    public Pet getPetById(int id) {
        for (Pet p : pets) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    private void savePetsData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PETS_FILE))) {
            for (Pet p : pets) {
                bw.write(toFileLine(p));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String toFileLine(Pet pet) {
        if (pet instanceof Cat c) {
            return "Cat:    " + c.getName() + "    " + c.getAge() + "    " + c.getBreed() + "    " + c.getFurColor() + "    " + c.getStatus();
        }
        if (pet instanceof Dog d) {
            return "Dog:    " + d.getName() + "    " + d.getAge() + "    " + d.getBreed() + "    " + d.getStatus();
        }
        if (pet instanceof Bird b) {
            return "Bird:    " + b.getName() + "    " + b.getAge() + "    " + b.getSpecies() + "    " + b.getStatus();
        }
        return "";
    }

    private IsSold parseStatus(String rawStatus) {
        if (rawStatus == null) return null;
        String normalized = rawStatus.trim().toUpperCase(Locale.ROOT);
        try {
            return IsSold.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
