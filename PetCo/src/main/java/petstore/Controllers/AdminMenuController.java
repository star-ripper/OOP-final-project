package petstore.Controllers;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import petstore.AppContext;
import petstore.models.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AdminMenuController extends BaseController {

    private final AppContext context = AppContext.getInstance();

    @FXML private TextField petIdField;


    @FXML
    private void removePet() {
        try {
            int id = Integer.parseInt(petIdField.getText().trim());
            context.getPetCatalog().deleteFromFile(id);
            showInfo("Pet removed successfully");
        } catch (Exception e) {
            showError("Invalid Pet ID");
        }
    }

    @FXML
    private void addPet() {
        Stage dialog = new Stage();
        dialog.setTitle("Add New Pet");

        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 15;");
        vbox.setPrefWidth(350);

        ChoiceBox<String> typeChoice = new ChoiceBox<>();
        typeChoice.getItems().addAll("Cat", "Dog", "Bird");
        typeChoice.setValue("Cat");

        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        TextField ageField = new TextField();
        ageField.setPromptText("Age");

        TextField extra1 = new TextField();
        TextField extra2 = new TextField();

        Button submitBtn = new Button("Add Pet");

        vbox.getChildren().addAll(new Label("Pet Type:"), typeChoice,
                nameField, ageField, extra1, extra2, submitBtn);

        Scene scene = new Scene(vbox);
        dialog.setScene(scene);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.show();

        typeChoice.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            switch (newVal) {
                case "Cat":
                    extra1.setVisible(true); extra1.setPromptText("Breed");
                    extra2.setVisible(true); extra2.setPromptText("Fur Color");
                    break;
                case "Dog":
                    extra1.setVisible(true); extra1.setPromptText("Breed");
                    extra2.setVisible(false);
                    break;
                case "Bird":
                    extra1.setVisible(false);
                    extra2.setVisible(true); extra2.setPromptText("Species");
                    break;
            }
        });
        typeChoice.getSelectionModel().select(typeChoice.getValue());

        submitBtn.setOnAction(e -> {
            try {
                String type = typeChoice.getValue();
                String name = nameField.getText().trim();
                int age = Integer.parseInt(ageField.getText().trim());
                Pet pet = null;

                switch (type) {
                    case "Cat": pet = new Cat(name, age, extra1.getText().trim(), extra2.getText().trim(), true); break;
                    case "Dog": pet = new Dog(name, age, extra1.getText().trim(), true); break;
                    case "Bird": pet = new Bird(name, age, extra2.getText().trim(), true); break;
                }

                if (pet != null) {
                    context.getPetCatalog().addPet(pet);

                    try (BufferedWriter bw = new BufferedWriter(new FileWriter("Pets.txt", true))) {
                        if (pet instanceof Cat c) bw.write("Cat:    " + c.getName() + "    " + c.getAge() + "    " + c.getBreed() + "    " + c.getFurColor() + "    " + c.getStatus());
                        if (pet instanceof Dog d) bw.write("Dog:    " + d.getName() + "    " + d.getAge() + "    " + d.getBreed() + "    " + d.getStatus());
                        if (pet instanceof Bird b) bw.write("Bird:    " + b.getName() + "    " + b.getAge() + "    " + b.getSpecies() + "    " + b.getStatus());
                        bw.newLine();
                    }

                    showInfo("Pet added successfully!");
                    dialog.close();
                }

            } catch (NumberFormatException ex) {
                showError("Age must be a number!");
            } catch (IOException ex) {
                showError("Error writing to Pets.txt!");
            }
        });
    }

    @FXML
    private void addInventory() {
        Stage dialog = new Stage();
        dialog.setTitle("Add Inventory Item");

        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 15;");
        vbox.setPrefWidth(350);

        ChoiceBox<String> typeChoice = new ChoiceBox<>();
        typeChoice.getItems().addAll("Feed", "Toys", "Necessities");
        typeChoice.setValue("Feed");

        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        TextField brandField = new TextField();
        brandField.setPromptText("Brand");

        TextField qtyField = new TextField();
        qtyField.setPromptText("Quantity");

        Button submitBtn = new Button("Add Inventory");

        vbox.getChildren().addAll(new Label("Inventory Type:"), typeChoice,
                nameField, brandField, qtyField, submitBtn);

        Scene scene = new Scene(vbox);
        dialog.setScene(scene);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.show();

        submitBtn.setOnAction(e -> {
            try {
                String type = typeChoice.getValue();
                String name = nameField.getText().trim();
                String brand = brandField.getText().trim();
                int qty = Integer.parseInt(qtyField.getText().trim());

                InventoryItem item = switch (type) {
                    case "Feed" -> new Feed(name, brand, qty);
                    case "Toys" -> new Toys(name, brand, qty);
                    case "Necessities" -> new Necessities(name, brand, qty);
                    default -> null;
                };

                if (item != null) {
                    context.getInventory().addItem(item); // auto saves to file
                    showInfo("Inventory item added successfully!");
                    dialog.close();
                }

            } catch (NumberFormatException ex) {
                showError("Quantity must be a valid number!");
            } catch (Exception ex) {
                showError("Error adding inventory item!");
            }
        });
    }

    @FXML
    private void removeInventory() {
        Stage dialog = new Stage();
        dialog.setTitle("Remove Inventory Item");

        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 15;");
        vbox.setPrefWidth(300);

        TextField idField = new TextField();
        idField.setPromptText("Enter Inventory ID");

        Button removeBtn = new Button("Remove");

        vbox.getChildren().addAll(new Label("Inventory ID:"), idField, removeBtn);

        Scene scene = new Scene(vbox);
        dialog.setScene(scene);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.show();

        removeBtn.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                boolean exists = context.getInventory().getItems().stream().anyMatch(item -> item.getId() == id);
                if (!exists) {
                    showError("Inventory item not found!");
                    return;
                }
                context.getInventory().removeItemById(id);
                showInfo("Inventory item removed successfully!");
                dialog.close();
            } catch (NumberFormatException ex) {
                showError("Invalid ID!");
            }
        });
    }

    @FXML
    private void viewPets() {
        Stage dialog = new Stage();
        dialog.setTitle("All Pets");

        TableView<Pet> table = new TableView<>();

        TableColumn<Pet, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Pet, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Pet, Integer> ageCol = new TableColumn<>("Age");
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<Pet, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        table.getColumns().addAll(idCol, nameCol, ageCol, statusCol);

        for (Pet p : context.getPetCatalog().getAllPets()) {
            table.getItems().add(p);
        }

        VBox vbox = new VBox(table);
        vbox.setStyle("-fx-padding: 15;");

        Scene scene = new Scene(vbox, 500, 400);
        dialog.setScene(scene);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.show();
    }
    @FXML
    private void viewInventory() {
        Stage dialog = new Stage();
        dialog.setTitle("Inventory Items");

        TableView<InventoryItem> table = new TableView<>();

        TableColumn<InventoryItem, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<InventoryItem, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<InventoryItem, String> brandCol = new TableColumn<>("Brand");
        brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));

        TableColumn<InventoryItem, Integer> qtyCol = new TableColumn<>("Quantity");
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<InventoryItem, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(param ->
                new ReadOnlyStringWrapper(param.getValue().getClass().getSimpleName())
        );

        table.getColumns().addAll(idCol, nameCol, brandCol, qtyCol, typeCol);

        table.getItems().addAll(context.getInventory().getItems());

        VBox vbox = new VBox(table);
        vbox.setStyle("-fx-padding: 15;");

        Scene scene = new Scene(vbox, 500, 400);
        dialog.setScene(scene);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.show();
    }

    @FXML
    private void logout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/petstore/login.fxml")
            );
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene()
                    .getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle("Login");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    // Helper Methods
    private void showInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
