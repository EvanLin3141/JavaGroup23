package api;

import java.util.ArrayList;
import java.util.List;
import camera.Camera;

public class RayTracingObjects implements RayTracingAPI {
    private List<Object> spheres = new ArrayList<>();
    private List<Object> lights = new ArrayList<>();
    private Camera camera;

    @Override
    public void addSphere(Object sphere) {
        spheres.add(sphere);
    }

    @Override
    public void addLight(Object light) {
        lights.add(light);
    }

    @Override
    public List<Object> getSpheres() {
        return spheres;
    }

    @Override
    public List<Object> getLights() {
        return lights;
    }

    @Override
    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Camera getCamera() {
        return camera;
    }

}
