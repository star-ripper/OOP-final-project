package petstore.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import petstore.AppContext;

public class SignupController extends BaseController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    private final AppContext context = AppContext.getInstance();

    @FXML
    public void handleSignup() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (context.getUserData().user.containsKey(username)) {
            showError("Username already exists!");
            return;
        }
        if (!password.matches(".*[A-Z].*")) {
            showError("Password must contain at least one uppercase letter");
            return;
        }
        if (!password.matches(".*\\d.*")) {
            showError("Password must contain at least one digit");
            return;
        }

        context.getUserData().addUser(username, password);
        showInfo("User successfully created!");
        switchScene("login.fxml", usernameField);
    }

    @FXML
    public void goBack() {
        switchScene("login.fxml");
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
