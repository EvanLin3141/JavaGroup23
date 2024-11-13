module raytracing {
    requires java.desktop;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    exports core;
    opens core to javafx.fxml;
    exports controller;
    opens controller to javafx.fxml;
    exports render;
    opens render to javafx.fxml;
    exports lighting;
    opens lighting to javafx.fxml;
    exports shapes;
    opens shapes to javafx.fxml;
    exports api;
    opens api to javafx.fxml;
}