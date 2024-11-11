package api;

import shapes.Sphere;
import lighting.Light;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SceneObjects implements API{
    private List<Object> spheres = new ArrayList<>();
    private List<Object> lights = new ArrayList<>();

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
}
