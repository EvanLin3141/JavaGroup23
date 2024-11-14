package lighting;

import shapes.Vector3D;
import render.Renderable;

import javafx.scene.paint.Color;
import java.util.List;

/**
 * Handles rays in a 3D space, including tracing the ray through objects in the scene
 * and shading objects that the ray intersects
 */
public class Ray {

    /** The maximum distance along a ray's path where an intersection is taken into account*/
    public static final float MAX_T = Float.MAX_VALUE;

    private Vector3D rayOrigin;

    private Vector3D rayDirection;

    /** Used to determine where along the ray's path the intersection occurs */
    private float scalarParameter;

    private Renderable intersectedObject;


    /**
     * Constructs a new Ray object
     * @param origin The origin of the ray
     * @param direction The direction of the ray
     */
    public Ray(Vector3D origin, Vector3D direction) {
        rayOrigin = new Vector3D(origin);
        rayDirection = Vector3D.normalize(direction);
    }

    /** @return The origin of the ray */
    public Vector3D getOrigin() {
        return this.rayOrigin;
    }

    /** @return The direction of the ray */
    public Vector3D getDirection() {
        return this.rayDirection;
    }

    /** @return The scalar parameter of the ray */
    public float getScalarParam() {
       return this.scalarParameter;
    }


    /**
     * Sets the scalar parameter for the ray
     * @param scalarParameter
     */
    public void setScalarParam(float scalarParameter){
        this.scalarParameter = scalarParameter;
    }

    /**
     * Sets the object that the ray intersects
     * @param object
     */
    public void setIntersectedObject(Renderable object) {
        this.intersectedObject = object;
    }

    /**
     * Traces the ray through a list of objects to check for intersections
     * @param objects The list of objects to check for intersection
     * @return True if the ray intersects with an object, false if it doesn't
     */
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

    /**
     * Calculates the colour of the ray based on lighting and objects in  the scene
     * @param lights The list of light sources in the scene
     * @param objects The list of objects in the scene
     * @param backgroundColour The background colour of the scene
     * @return The colour of the ray after shading
     */
    public final Color shade(List<Object> lights, List<Object> objects, Color backgroundColour) {
        return intersectedObject.shade(this, lights, objects, backgroundColour);
    }

    /** @return A string representing the ray */
    public String toString() {
        return ("ray origin = "+rayOrigin+"  ray direction = "+rayDirection+"  t = "+scalarParameter);
    }
}
