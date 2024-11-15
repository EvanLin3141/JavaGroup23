package api;

import camera.Camera;
import shapes.Sphere;
import lighting.Light;
import camera.Camera;

import java.util.List;


public interface RayTracingAPI {
    List<Object> getSpheres();
    List<Object> getLights();
    Camera getCamera();
    void addSphere(Sphere object);
    void addLight(Light light);
    void setCamera(Camera camera);

}