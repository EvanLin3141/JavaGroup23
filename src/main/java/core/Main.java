package core;

import api.RayTracingObjects;
import controller.Controller;
import api.RayTracingObjects;
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

    RayTracingObjects scene = new RayTracingObjects();

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


    //Sets up everything before button is pressed.
    public void createObjects() {
        // Set up the camera
        Vector3D eye = new Vector3D(1.5f, 10.5f, -1.5f);
        Vector3D lookat = new Vector3D(-0.5f, 0f, -0.5f);
        Camera camera = new Camera(eye, lookat);
        scene.setCamera(camera);

        // Add lights
        scene.addLight(new Light(Light.AMBIENT, 0.2f, 0.2f, 0.2f, null));
        scene.addLight(new Light(Light.DIRECTIONAL, 1.0f, 0.9f, 0.8f, new Vector3D(5, 5, -5)));


        scene.addLight(new Light(Light.AMBIENT, 1.0f, 1.0f, 0.981f, null));
        scene.addLight(new Light(Light.AMBIENT, 0.9f, 0.9f, 0.9f, null));
        scene.addLight(new Light(Light.AMBIENT, 0.745f, 0.859f, 0.224f, null));
        scene.addLight(new Light(Light.DIRECTIONAL, 0.6f, 0.6f, 0.6f, new Vector3D(-1, -1, -1)));

// Add objects
        Surface surface1 = new Surface(0.2f, 0.8f, 0.2f, 0.5f, 0.9f, 0.4f, 10.0f, 0, 0, 1);
        scene.addSphere(new Sphere(surface1, new Vector3D(-0.4f, 0.375f, -0.4f), 0.375f));

        Surface surface2 = new Surface(0.7f, 0.3f, 0.2f, 0.5f, 0.9f, 0.4f, 6.0f, 0, 0, 1);
        scene.addSphere(new Sphere(surface2, new Vector3D(-0.6f, 1.05f, -0.6f), 0.3f));

        Surface surface3 = new Surface(0.2f, 0.3f, 0.8f, 0.5f, 0.9f, 0.4f, 10.0f, 0, 0, 1);
        scene.addSphere(new Sphere(surface3, new Vector3D(-0.8f, 1.575f, -0.8f), 0.125f));

    }

    public static void main(String[] args) {
        launch(args);
    }
}
  /*      // Add objects
        Surface greenSurface = new Surface(0.2f, 0.8f, 0.2f, 0.5f, 0.9f, 0.4f, 10.0f, 0, 0, 1);
        scene.addSphere(new Sphere(greenSurface, new Vector3D(0, 0, -10), 1));

        Surface redSurface = new Surface(0.7f, 0.3f, 0.2f, 0.5f, 0.9f, 0.4f, 6.0f, 0, 0, 1);
        scene.addSphere(new Sphere(redSurface, new Vector3D(-2, 2, -15), 2));
*/
// Add lights