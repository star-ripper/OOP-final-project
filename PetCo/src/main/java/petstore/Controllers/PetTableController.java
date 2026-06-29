package petstore.Controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import petstore.AppContext;
import petstore.models.Pet;

public class PetTableController {

    @FXML private TableView<Pet> petTable;
    @FXML private TableColumn<Pet, Integer> idCol;
    @FXML private TableColumn<Pet, String> nameCol;
    @FXML private TableColumn<Pet, Integer> ageCol;
    @FXML private TableColumn<Pet, String> statusCol;

    private final AppContext context = AppContext.getInstance();

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        refresh();
    }

    public void refresh() {
        petTable.setItems(FXCollections.observableArrayList(context.getPetCatalog().pets));
    }
}
