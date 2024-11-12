package controller;

import driver.Driver;
import api.SceneObjects;
import camera.Camera;
import shapes.Vector3D;

import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class Controller {
    public ImageView renderedImage;
    private Stage stage;
    private Driver sceneToRender;
    boolean finished = false;
    private SceneObjects scene;
    private final float zoomFactor = 1.1f; // Adjust for zoom scaling


    private final float shiftAmount = 1.0f; // Amount to shift the camera each time

    public void viewFromX() {
        Camera camera = scene.getCamera();
        if (camera != null) {
            Vector3D currentPosition = camera.getEye();
            camera.setPosition(new Vector3D(currentPosition.x + shiftAmount, currentPosition.y, currentPosition.z));
            //camera.setLookAt(new Vector3D(0, 0, 0));
            refreshView();
        }
    }

    public void viewFromY() {
        Camera camera = scene.getCamera();
        if (camera != null) {
            Vector3D currentPosition = camera.getEye();
            camera.setPosition(new Vector3D(currentPosition.x, currentPosition.y + shiftAmount, currentPosition.z));
            //camera.setLookAt(new Vector3D(0, 0, 0));
            refreshView();
        }
    }

    public void viewFromZ() {
        Camera camera = scene.getCamera();
        if (camera != null) {
            Vector3D currentPosition = camera.getEye();
            camera.setPosition(new Vector3D(currentPosition.x, currentPosition.y, currentPosition.z + shiftAmount));
            //camera.setLookAt(new Vector3D(0, 0, 0));
            refreshView();
        }
    }

    public void zoomIn() {
        Camera camera = scene.getCamera();
        if (camera != null) {
            camera.setFov(camera.getFov() / zoomFactor); // Decrease FOV for zoom in
            refreshView();
        }
    }

    public void zoomOut() {
        Camera camera = scene.getCamera();
        if (camera != null) {
            //camera.setFov(camera.getFov() * zoomFactor); // Increase FOV for zoom out
            refreshView();
        }
    }

    private void refreshView() {
        // Rerender the scene with updated camera settings
        sceneToRender = new Driver(800, 600, scene); // Adjust width, height as necessary
        run();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void run() {
        long time = System.currentTimeMillis();
        for (int j = 0; j < sceneToRender.getHeight(); j += 1) {
            for (int i = 0; i < sceneToRender.getWidth(); i += 1) {
                sceneToRender.renderPixel(i, j);
            }
        }
        renderedImage.setImage(sceneToRender.getRenderedImage());
        time = System.currentTimeMillis() - time;
        System.err.println("Rendered in "+(time/60000)+":"+((time%60000)*0.001));
        finished = true;
    }


    public void startRayTrace(ActionEvent actionEvent) {
        sceneToRender = new Driver((int) renderedImage.getFitWidth(),
                                   (int) renderedImage.getFitHeight(),
                scene);
        this.run();
    }

    public void setSceneToRender(SceneObjects scene) {
        this.scene = scene;
    }
}
