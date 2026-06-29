package petstore.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import petstore.AppContext;

public class LoginController extends BaseController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    private final AppContext context = AppContext.getInstance();

    @FXML
    public void login(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        try {
            if (context.getUserData().isAdmin(username, password)) {
                // Use absolute resource path
                switchScene("/petstore/adminMenu.fxml", usernameField);
            } else if (context.getUserData().isUser(username, password)) {
                switchScene("/petstore/customerMenu.fxml", usernameField);
            } else {
                showError("Invalid username or password");
            }
        } catch (Exception e) {
            showError("Error loading scene: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void goToSignUp(ActionEvent event) {
        try {
            switchScene("/petstore/signup.fxml", usernameField);
        } catch (Exception e) {
            showError("Error loading signup: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
