package api;

import camera.Camera;
import shapes.Sphere;
import lighting.Light;
import camera.Camera;

import java.util.List;


public interface RayTracingAPI {
    void addSphere(Sphere object);
    void addLight(Light light);
    void setCamera(Camera camera);
    List<Object> getSpheres();
    List<Object> getLights();
    Camera getCamera();
}