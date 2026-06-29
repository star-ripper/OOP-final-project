package petstore.Controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import petstore.AppContext;
import petstore.models.InventoryItem;

public class InventoryTableController {

    @FXML private TableView<InventoryItem> inventoryTable;
    @FXML private TableColumn<InventoryItem, Integer> idCol;
    @FXML private TableColumn<InventoryItem, String> nameCol;
    @FXML private TableColumn<InventoryItem, String> brandCol;
    @FXML private TableColumn<InventoryItem, Integer> qtyCol;

    private final AppContext context = AppContext.getInstance();

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        refresh();
    }

    public void refresh() {
        inventoryTable.setItems(FXCollections.observableArrayList(context.getInventory().getItems()));
    }
}
