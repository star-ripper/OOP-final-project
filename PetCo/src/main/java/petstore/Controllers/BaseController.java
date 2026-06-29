package petstore.Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import petstore.Main;

import java.io.IOException;
import java.net.URL;

public abstract class BaseController {

    protected void switchScene(String fxmlFile, Node currentNode) {
        try {
            URL fxml = Main.class.getResource(fxmlFile);
            if (fxml == null) {
                throw new IllegalStateException("FXML not found: " + fxmlFile +
                        ". Make sure the file is in resources/petstore/");
            }
            Parent root = FXMLLoader.load(fxml);
            Stage stage = (Stage) currentNode.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void switchScene(String fxmlFile) {
        try {
            URL fxml = Main.class.getResource(fxmlFile);

            if (fxml == null) {
                throw new IllegalStateException("FXML not found: " + fxmlFile);
            }

            Parent root = FXMLLoader.load(fxml);
            Stage stage = new Stage(); // or reuse existing stage if you have a reference
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
