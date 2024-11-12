package api;

import camera.Camera;

import java.util.List;


public interface ObjectScene {
    List<Object> getSpheres();
    List<Object> getLights();
    void addSphere(Object sphere);
    void addLight(Object light);
    void setCamera(Camera camera);
}