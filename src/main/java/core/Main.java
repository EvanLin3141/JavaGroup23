package core;

import controller.Controller;
import api.SceneObjects;
import lighting.Light;
import shapes.Vector3D;
import shapes.Surface;
import shapes.Sphere;
import camera.Camera;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    SceneObjects scene = new SceneObjects();

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/render.fxml"));

        Parent root = loader.load();
        Controller controller = (Controller) loader.getController();

        primaryStage.setTitle("Simple Ray Tracing");
        primaryStage.setScene(new Scene(root, 860, 640));

        controller.setSceneToRender(scene);

        createObjects();
        primaryStage.show();
    }

    public void createObjects() {
        // Set up the camera
        Vector3D eye = new Vector3D(10.5f, 10.5f, -10.5f);
        Vector3D lookat = new Vector3D(-0.5f, 0f, -0.5f);
        Camera camera = new Camera(eye, lookat);

        // Add lights
        scene.addLight(new Light(Light.AMBIENT, 0.2f, 0.2f, 0.2f, null));
        scene.addLight(new Light(Light.DIRECTIONAL, 1.0f, 0.9f, 0.8f, new Vector3D(5, 5, -5)));

        // Add objects
        Surface greenSurface = new Surface(0.2f, 0.8f, 0.2f, 0.5f, 0.9f, 0.4f, 10.0f, 0, 0, 1);
        scene.addSphere(new Sphere(greenSurface, new Vector3D(0, 0, -10), 1));

        Surface redSurface = new Surface(0.7f, 0.3f, 0.2f, 0.5f, 0.9f, 0.4f, 6.0f, 0, 0, 1);
        scene.addSphere(new Sphere(redSurface, new Vector3D(-2, 2, -15), 2));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
