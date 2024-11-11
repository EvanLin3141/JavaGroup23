package api;

import java.util.List;
import lighting.Light;
import shapes.Sphere;

public interface API {
    List<Sphere> getSpheres();
    List<Light> getLights();
    void addSphere(Sphere sphere);
    void addLight(Light light);
}