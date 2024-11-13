package controller;

import api.RayTracing;
import driver.Driver;
import camera.Camera;
import shapes.Vector3D;

import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class Controller {
    public ImageView renderedImage;
    private Driver sceneToRender;
    boolean finished = false;
    private RayTracing scene;
    private static Camera camera;
    private final float zoomFactor = 1.1f; // Adjust for zoom scaling


    private final float shiftAmount = 1.0f; // Amount to shift the camera each time

    public void run() {
        camera = scene.getCamera();
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

    //Starts RayTracing when button is pressed.
    public void startRayTrace(ActionEvent actionEvent) {
        sceneToRender = new Driver((int) renderedImage.getFitWidth(),
                                   (int) renderedImage.getFitHeight(),
                scene);
        this.run();
    }

    public void setSceneToRender(RayTracing scene) {
        this.scene = scene;
    }

    public void viewFromX() {
        if (camera != null) {
            Vector3D currentPosition = camera.getEye();
            camera.setPosition(new Vector3D(currentPosition.x + shiftAmount, currentPosition.y, currentPosition.z));
            refreshView();
        }
    }

    public void viewFromY() {
        if (camera != null) {
            Vector3D currentPosition = camera.getEye();
            camera.setPosition(new Vector3D(currentPosition.x, currentPosition.y + shiftAmount, currentPosition.z));
            refreshView();
        }
    }

    public void viewFromZ() {
        if (camera != null) {
            Vector3D currentPosition = camera.getEye();
            camera.setPosition(new Vector3D(currentPosition.x, currentPosition.y, currentPosition.z + shiftAmount));
            refreshView();
        }
    }

    public void zoomIn() {
        if (camera != null) {
            camera.setFov(camera.getFov() / zoomFactor); // Decrease FOV for zoom in
            refreshView();
        }
    }

    public void zoomOut() {
        if (camera != null) {
            camera.setFov(camera.getFov() * zoomFactor); // Increase FOV for zoom out
            refreshView();
        }
    }

    private void refreshView() {
        sceneToRender = new Driver(800, 600, scene); // Adjust width, height as necessary
        run();
    }

}
