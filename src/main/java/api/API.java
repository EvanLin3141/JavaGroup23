package api;

import java.util.List;
import java.util.Objects;

import lighting.Light;
import shapes.Sphere;

public interface API {
    List<Object> getSpheres();
    List<Object> getLights();
    void addSphere(Object sphere);
    void addLight(Object light);
}