package lighting;

import shapes.Vector3D;
import render.Renderable;

import javafx.scene.paint.Color;
import java.util.List;

public class Ray {
    public static final float MAX_T = Float.MAX_VALUE;
    private Vector3D origin;
    private Vector3D direction;
    private float scalarParam;
    private Renderable object;

    public Ray(Vector3D eye, Vector3D dir) {
        origin = new Vector3D(eye);
        direction = Vector3D.normalize(dir);
    }

    public Vector3D getOrigin() {
        return this.origin;
    }

    public Vector3D getDirection() {
        return this.direction;
    }

    public float getScalarParam() {
       return this.scalarParam;
    }

    public void setScalarParam(float scalarParam){
        this.scalarParam = scalarParam;
    }

    public void setObject(Renderable object) {
        this.object = object;
    }

    public boolean trace(List<Object> objects) {
        this.scalarParam = MAX_T;
        object = null;
        for (Object objList : objects) {
            Renderable object = (Renderable) objList;
            object.intersect(this);
        }
        return (object != null);
    }

    // The following method is not strictly needed, and most likely
    // adds unnecessary overhead, but I prefered the syntax
    //
    //            ray.Shade(...)
    // to
    //            ray.object.Shade(ray, ...)
    //
    public final Color shade(List<Object> lights, List<Object> objects, Color bgnd) {
        return object.shade(this, lights, objects, bgnd);
    }

    public String toString() {
        return ("ray origin = "+origin+"  direction = "+direction+"  t = "+scalarParam);
    }
}
