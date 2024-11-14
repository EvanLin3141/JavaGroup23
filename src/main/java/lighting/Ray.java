package lighting;

import shapes.Vector3D;
import render.Renderable;

import javafx.scene.paint.Color;
import java.util.List;

public class Ray {
    public static final float MAX_T = Float.MAX_VALUE;
    private Vector3D rayOrigin;
    private Vector3D rayDirection;
    private float scalarParameter;
    private Renderable intersectedObject;

    public Ray(Vector3D origin, Vector3D direction) {
        rayOrigin = new Vector3D(origin);
        rayDirection = Vector3D.normalize(direction);
    }

    public Vector3D getOrigin() {
        return this.rayOrigin;
    }

    public Vector3D getDirection() {
        return this.rayDirection;
    }

    public float getScalarParam() {
       return this.scalarParameter;
    }

    public void setScalarParam(float scalarParameter){
        this.scalarParameter = scalarParameter;
    }

    public void setIntersectedObject(Renderable object) {
        this.intersectedObject = object;
    }

    public boolean trace(List<Object> objects) {
        this.scalarParameter = MAX_T;
        intersectedObject = null;
        for (Object objList : objects) {
            Renderable object = (Renderable) objList;
            object.intersect(this);
        }
        return (intersectedObject != null);
    }

    // The following method is not strictly needed, and most likely
    // adds unnecessary overhead, but I preferred the syntax
    //
    //            ray.Shade(...)
    // to
    //            ray.object.Shade(ray, ...)
    //
    public final Color shade(List<Object> lights, List<Object> objects, Color backgroundColour) {
        return intersectedObject.shade(this, lights, objects, backgroundColour);
    }

    public String toString() {
        return ("ray origin = "+rayOrigin+"  ray direction = "+rayDirection+"  t = "+scalarParameter);
    }
}
