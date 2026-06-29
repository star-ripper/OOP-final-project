package petstore;

import petstore.models.Inventory;
import petstore.models.PetCatalog;

import java.io.File;
import java.io.IOException;

public class AppContext {

    private static AppContext instance;

    private final UserData userData;
    private final PetCatalog petCatalog;
    private final Inventory inventory;

    private AppContext() {

        userData = new UserData();
        petCatalog = new PetCatalog();
        inventory = new Inventory();

        File petFile = new File("Pets.txt");
        if (!petFile.exists()) {
            try { petFile.createNewFile(); }
            catch (IOException e) { e.printStackTrace(); }
        }
        petCatalog.addData();

        File invFile = new File("Inventory.txt");
        if (!invFile.exists()) {
            try { invFile.createNewFile(); }
            catch (IOException e) { e.printStackTrace(); }
        }
        inventory.addData();
    }

    public static AppContext getInstance() {
        if (instance == null) {
            instance = new AppContext();
        }
        return instance;
    }

    public UserData getUserData() {
        return userData;
    }

    public PetCatalog getPetCatalog() {
        return petCatalog;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
