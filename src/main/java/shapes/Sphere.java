package shapes;

import lighting.Ray;
import render.Renderable;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.List;


/**
 * Represents a 3D sphere object  that can interact with rays
 */
// An example "Renderable" object
public class Sphere implements Renderable {
    Surface sphereSurface;
    Vector3D sphereCenter;
    float sphereRadius;
    float radiusSquared;


    /**
     * Constructs a new Sphere object
     * @param surface Defines the material and appearance of the sphere's surface
     * @param center Defines the position of the sphere in the scene
     * @param radius The radius of the sphere
     */
    public Sphere(Surface surface, Vector3D center, float radius) {
        sphereSurface = surface;
        sphereCenter = center;
        sphereRadius = radius;
        radiusSquared = radius*radius;
    }


    /**
     * Checks if a given ray intersects the sphere
     * @param ray The ray being tested for intersection
     * @return True if the ray intersects the sphere, false if it doesn't
     */
    @Override
    public boolean intersect(Ray ray) {
        float dx = sphereCenter.x - ray.getOrigin().x;
        float dy = sphereCenter.y - ray.getOrigin().y;
        float dz = sphereCenter.z - ray.getOrigin().z;
        float distanceToClosestPoint = ray.getDirection().dot(dx, dy, dz);

        // Do the following quick check to see if there is even a chance
        // that an intersection here might be closer than a previous one
        if (distanceToClosestPoint - sphereRadius > ray.getScalarParam())
            return false;

        // Test if the ray actually intersects the sphere
        float t = radiusSquared + distanceToClosestPoint*distanceToClosestPoint - dx*dx - dy*dy - dz*dz;
        if (t < 0)
            return false;

        // Test if the intersection is in the positive
        // ray direction, and it is the closest so far
        t = distanceToClosestPoint - ((float) Math.sqrt(t));
        if ((t > ray.getScalarParam()) || (t < 0))
            return false;

        ray.setScalarParam(t);
        ray.setIntersectedObject(this);
        return true;
    }

    /**
     * Calculates the colour of the sphere at the point of intersection
     * @param ray The ray intersecting the sphere
     * @param lights The list of lights in the scene
     * @param objects The list of objects in the scene
     * @param backgroundColour The background colour of the scene
     * @return The colour of the sphere at the point of intersection
     */
    @Override
    public Color shade(Ray ray, java.util.List<Object> lights, List<Object> objects, Color backgroundColour) {
        // An object shader doesn't really do too much other than
        // supply a few critical bits of geometric information
        // for a surface shader. It must compute:
        //
        //   1. the point of intersection (p)
        //   2. a unit-length surface normal (n)
        //   3. a unit-length vector towards the ray's origin (v)
        //
        float intersectionX = ray.getOrigin().x + ray.getScalarParam()*ray.getDirection().x;
        float intersectionY = ray.getOrigin().y + ray.getScalarParam()*ray.getDirection().y;
        float intersectionZ = ray.getOrigin().z + ray.getScalarParam()*ray.getDirection().z;

        Vector3D intersectionPoint = new Vector3D(intersectionX, intersectionY, intersectionZ);
        Vector3D viewVector = new Vector3D(-ray.getDirection().x, -ray.getDirection().y, -ray.getDirection().z);
        Vector3D surfaceNormal = new Vector3D(intersectionX - sphereCenter.x, intersectionY - sphereCenter.y, intersectionZ - sphereCenter.z);
        surfaceNormal.normalize();

        // The illumination model is applied
        // by the surface's Shade() method
        return sphereSurface.shade(intersectionPoint, surfaceNormal, viewVector, lights, objects, backgroundColour);
    }

    /**
     * @return A string representation of the sphere
     */
    @Override
    public String toString() {
        return ("sphere "+sphereCenter+" "+sphereRadius);
    }
}