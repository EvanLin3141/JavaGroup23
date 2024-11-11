package api;

import shapes.Sphere;
import lighting.Light;
import java.util.ArrayList;
import java.util.List;

public class Scene implements API{
    private List<Sphere> spheres = new ArrayList<>();
    private List<Light> lights = new ArrayList<>();

    @Override
    public void addSphere(Sphere sphere) {
        spheres.add(sphere);
    }

    @Override
    public void addLight(Light light) {
        lights.add(light);
    }

    @Override
    public List<Sphere> getSpheres() {
        return spheres;
    }

    @Override
    public List<Light> getLights() {
        return lights;
    }
}
