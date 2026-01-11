package Petstore;
import java.util.*;
import java.io.*;

public class Main{
    static Scanner sc = new Scanner(System.in);
    static void addCat(PetCatalog catalog){
        System.out.println("--------------------------------------");
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Age: ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter FurColor: ");
        String furColor = sc.nextLine();
        System.out.print("Breed: ");
        String breed = sc.nextLine();
        Pet p = new Cat(name, age, breed, furColor, true);
        System.out.println("---------------------------------------");
        catalog.addPet(p);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Pets.txt", true))) {
            bw.write("Cat:    " + name + "    " + age + "    " + breed + "    " + furColor + "    " + p.getStatus());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void addDog(PetCatalog catalog){
        System.out.println("--------------------------------------");
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Age: ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("Breed: ");
        String breed = sc.nextLine();
        Pet p = new Dog(name, age, breed, true);
        System.out.println("---------------------------------------");
        catalog.addPet(p);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Pets.txt", true))) {
            bw.write("Dog:    " + name + "    " + age + "    " + breed + "    " + p.getStatus());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void addBird(PetCatalog catalog){

        System.out.println("--------------------------------------");
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Age: ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("Species: ");
        String species = sc.nextLine();
        Pet p = new Bird(name, age, species, true);
        System.out.println("---------------------------------------");
        catalog.addPet(p);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Pets.txt", true))) {
            bw.write("Bird:    " + name + "    " + age + "    " + species + "    " + p.getStatus());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void addPets(PetCatalog catalog) {
        System.out.println("Enter 1 to add Cat \nEnter 2 to add Dog\nEnter 3 to add Bird");
        int choice = -1;
        try {
            choice = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a number.");
            sc.nextLine();
            return;
        }
        sc.nextLine();

        switch (choice) {
            case 1: addCat(catalog);    break;
            case 2: addDog(catalog);    break;
            case 3: addBird(catalog);   break;
            default: System.out.println("Invalid choice."); break;
        }
    }

    public static void adminMenu(PetCatalog petCatalog, Inventory inventory) {

        while (true) {
            System.out.println("\n========== ADMIN MENU ==========");
            System.out.println("1. Add Pet");
            System.out.println("2. Remove Pet");
            System.out.println("3. List All Pets");
            System.out.println("4. Search Pet by Name");
            System.out.println("5. Search Pet by ID");
            System.out.println("6. Add Inventory Item");
            System.out.println("7. Remove Inventory Item");
            System.out.println("8. View Inventory");
            System.out.println("9. Exit");
            System.out.print("Choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    addPets(petCatalog);
                    break;
                case 2:
                    System.out.print("Enter Pet ID to remove: ");
                    int petId = sc.nextInt();
                    sc.nextLine();
                    petCatalog.deleteFromFile(petId);
                    break;
                case 3:
                    petCatalog.displayAll();
                    break;
                case 4:
                    System.out.print("Enter Pet Name: ");
                    String petName = sc.nextLine();
                    petCatalog.search(petName);
                    break;
                case 5:
                    System.out.print("Enter Pet ID: ");
                    petId = sc.nextInt();
                    sc.nextLine();
                    petCatalog.search(petId);
                    break;
                case 6:
                    addInventoryItem(inventory);
                    break;

                case 7:
                    removeInventoryItem(inventory);
                    break;

                case 8:
                    viewInventory(inventory);
                    break;

                case 9:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public static void customerMenu(PetCatalog petCatalog, Inventory inventory) {

        while (true) {
            System.out.println("\n========= CUSTOMER MENU =========");
            System.out.println("1. List All Pets");
            System.out.println("2. Search Pet by Name");
            System.out.println("3. Search Pet by ID");
            System.out.println("4. Buy a Pet");
            System.out.println("5. View Inventory");
            System.out.println("6. Buy Inventory Item");
            System.out.println("7. Exit");
            System.out.print("Choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    petCatalog.displayAll();
                    break;

                case 2:
                    System.out.print("Enter Pet Name: ");
                    String petName = sc.nextLine();
                    petCatalog.search(petName);
                    break;

                case 3:
                    System.out.print("Enter Pet ID: ");
                    int petId = sc.nextInt();
                    sc.nextLine();
                    petCatalog.search(petId);
                    break;

                case 4:
                    System.out.println("Buy by:");
                    System.out.println("1. Name");
                    System.out.println("2. ID");
                    int buyChoice = sc.nextInt();
                    sc.nextLine();

                    if (buyChoice == 1) {
                        System.out.print("Enter Pet Name: ");
                        petName = sc.nextLine();
                        petCatalog.buyPet(petName);
                    } else if (buyChoice == 2) {
                        System.out.print("Enter Pet ID: ");
                        petId = sc.nextInt();
                        sc.nextLine();
                        petCatalog.buyPet(petId);
                    } else {
                        System.out.println("Invalid choice.");
                    }
                    break;
                    case 5:
                    viewInventory(inventory);
                    break;

                case 6:
                    buyInventoryItem(inventory);
                    break;

                case 7:
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    //_________Inventory
    static void addInventoryItem(Inventory inventory) {

        System.out.println("1. Feed");
        System.out.println("2. Toy");
        System.out.println("3. Necessity");
        System.out.print("Choose item type: ");

        int type = sc.nextInt();
        sc.nextLine();

        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Brand: ");
        String brand = sc.nextLine();

        System.out.print("Quantity: ");
        int qty = sc.nextInt();
        sc.nextLine();

        switch (type) {
            case 1:
                inventory.addItem(new Feed(name, brand, qty));
                break;
            case 2:
                inventory.addItem(new Toys(name, brand, qty));
                break;
            case 3:
                inventory.addItem(new Necessities(name, brand, qty));
                break;
            default:
                System.out.println("Invalid item type.");
        }
    }
    static void removeInventoryItem(Inventory inventory) {
        inventory.displayAll();

        System.out.print("Enter Inventory ID to remove: ");
        int id = sc.nextInt();
        sc.nextLine();

        inventory.removeItemById(id);
    }
    static void viewInventory(Inventory inventory) {
        inventory.displayAll();
    }
    static void buyInventoryItem(Inventory inventory) {

        inventory.displayAll();

        System.out.print("Enter Inventory ID: ");
        int id = sc.nextInt();

        System.out.print("Enter quantity to buy: ");
        int qty = sc.nextInt();
        sc.nextLine();

        inventory.buyItem(id, qty);
    }



    public static void main(String[] args) {
        UserData data = new UserData();

        data.addAdminData();
        data.addData();


        File file = new File("Pets.txt");
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
       PetCatalog petCatalog = new PetCatalog();
        Inventory inventory = new Inventory();
        File invFile = new File("Inventory.txt");
        if(!invFile.exists()) {
            try {
                invFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        inventory.addData();
        petCatalog.addData();
        while (true) {
            System.out.println("Enter 1 to login \nEnter 2 to sign up");
            int choice = sc.nextInt();
            sc.nextLine();
            String user, password;
            switch(choice) {
                case 1:
                    System.out.print("Enter user name: ");
                    user = sc.nextLine();
                    System.out.print("Enter password: ");
                    password = sc.nextLine();
                    if(data.admin.containsKey(user) && data.admin.get(user).equals(password))
                    adminMenu(petCatalog, inventory);
                else if(data.user.containsKey(user) && data.user.get(user).equals(password)) {
                    customerMenu(petCatalog, inventory);
                }
                else {
                    System.out.println("Invalid username or password");
                }; break;
                case 2:
                    while(true) {
                        System.out.print("Enter user name: ");
                        user = sc.nextLine();
                        if(data.user.containsKey(user)) {
                            System.out.println("Username already exists. Try again.");
                            continue;
                        }

                        System.out.print("Enter password: ");
                        password = sc.nextLine();

                        boolean hasUppercase = false;
                        boolean hasDigit = false;
                        boolean digitCheck = false;
                        for (char ch : password.toCharArray()) {
                            if (Character.isUpperCase(ch)) hasUppercase = true;
                            if (Character.isDigit(ch)) hasDigit = true;
                            if (password.length() < 9) digitCheck = true;
                        }

                        if (!hasUppercase) {
                            System.out.println("Password should contain at least one uppercase letter.");
                            continue;
                        }
                        if(!digitCheck) {
                            System.out.println("Password should be longer than 8 .");
                            continue;
                        }
                        if (!hasDigit) {
                            System.out.println("Password should contain at least one digit.");
                            continue;
                        }


                        data.user.put(user, password);
                        data.saveUserData();
                        System.out.println("User successfully created!");
                        break;
                    }
                    default: System.out.println("Invalid choice");
            }
        }
    }
}

