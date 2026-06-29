package petstore.Controllers;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import petstore.AppContext;
import petstore.IsSold;
import petstore.models.InventoryItem;
import petstore.models.Pet;

import java.io.IOException;

public class CustomerMenuController extends BaseController {

    private final AppContext context = AppContext.getInstance();

    @FXML private Label messageLabel;

    @FXML
    private void viewPets() {
        showPetsTable("All Pets", context.getPetCatalog().getAllPets(), false);
    }

    @FXML
    private void viewInventory() {
        showInventoryTable("Inventory Items", context.getInventory().getItems(), false);
    }

    @FXML
    private void buyPetsPopup() {
        showPetsTable("Buy Pets", context.getPetCatalog().getAllPets(), true);
    }

    @FXML
    private void buyInventoryPopup() {
        showInventoryTable("Buy Inventory", context.getInventory().getItems(), true);
    }


    private void showPetsTable(String title, java.util.List<Pet> pets, boolean isBuyMode) {
        Stage dialog = new Stage();
        dialog.setTitle(title);

        TableView<Pet> table = new TableView<>();
        table.getColumns().addAll(
                column("ID", "id", Integer.class),
                column("Name", "name", String.class),
                column("Age", "age", Integer.class),
                column("Status", "status", String.class)
        );

        table.getItems().addAll(pets);

        Button buyButton = new Button("Buy Selected Pet");
        buyButton.setDisable(!isBuyMode);

        if (isBuyMode) {
            buyButton.setOnAction(e -> {
                Pet selected = table.getSelectionModel().getSelectedItem();
                if (selected == null) {
                    showError("No pet selected");
                    return;
                }
                if (selected.getStatus() != IsSold.AVAILABLE) {
                    showError("This pet is not available");
                    return;
                }
                context.getPetCatalog().buyPet(selected.getId());
                showInfo("Pet purchased successfully!");
                table.refresh();
            });
        }

        VBox root = new VBox(10, table, buyButton);
        root.setStyle("-fx-padding: 15");

        dialog.setScene(new Scene(root, 500, 400));
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.show();
    }

    private void showInventoryTable(String title, java.util.List<InventoryItem> items, boolean isBuyMode) {
        Stage dialog = new Stage();
        dialog.setTitle(title);

        TableView<InventoryItem> table = new TableView<>();
        table.getColumns().addAll(
                column("ID", "id", Integer.class),
                column("Name", "name", String.class),
                column("Brand", "brand", String.class),
                column("Quantity", "quantity", Integer.class),
                new TableColumn<InventoryItem, String>("Type") {{
                    setCellValueFactory(data ->
                            new ReadOnlyStringWrapper(data.getValue().getClass().getSimpleName())
                    );
                }}
        );

        table.getItems().addAll(items);

        Button buyButton = new Button("Buy Selected Item");
        buyButton.setDisable(!isBuyMode);

        if (isBuyMode) {
            buyButton.setOnAction(e -> {
                InventoryItem selected = table.getSelectionModel().getSelectedItem();
                if (selected == null) {
                    showError("No item selected");
                    return;
                }
                if (selected.getQuantity() <= 0) {
                    showError("Item out of stock");
                    return;
                }

                TextInputDialog dialogQty = new TextInputDialog("1");
                dialogQty.setTitle("Quantity");
                dialogQty.setHeaderText("Enter quantity to buy");
                dialogQty.setContentText("Quantity:");

                dialogQty.showAndWait().ifPresent(qtyStr -> {
                    try {
                        int qty = Integer.parseInt(qtyStr);
                        if (qty > selected.getQuantity()) {
                            showError("Quantity exceeds stock");
                            return;
                        }
                        context.getInventory().buyItem(selected.getId(), qty);
                        showInfo("Item purchased successfully!");
                        table.refresh();
                    } catch (NumberFormatException ex) {
                        showError("Invalid quantity");
                    }
                });
            });
        }

        VBox root = new VBox(10, table, buyButton);
        root.setStyle("-fx-padding: 15");

        dialog.setScene(new Scene(root, 600, 400));
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.show();
    }

    @FXML
    private void logout(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/petstore/login.fxml")
            );

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

    private <T> TableColumn<T, ?> column(String title, String property, Class<?> type) {
        TableColumn<T, Object> col = new TableColumn<>(title);
        col.setCellValueFactory(new PropertyValueFactory<>(property));
        return col;
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
