module petstore.petco {
    requires javafx.controls;
    requires javafx.fxml;


    opens petstore to javafx.fxml;
    exports petstore;
    exports petstore.Controllers;
    opens petstore.Controllers to javafx.fxml;
    exports petstore.models;
    opens petstore.models to javafx.fxml;
}