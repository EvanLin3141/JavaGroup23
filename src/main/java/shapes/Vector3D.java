package shapes;

/**
 *A simple vector class
  */
public class Vector3D {
    //vector co-ordinates
    public float x, y, z;

    public Vector3D( ) {
    }

    /**
     * Constructs a vector with the specified co-ordinates
     * @param x The x-coordinate
     * @param y The y-coordinate
     * @param z The z-coordinate
     */
    public Vector3D(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Constructs a vector by copying another vector
     * @param vector The vector being copied
     */
    public Vector3D(Vector3D vector) {
        x = vector.x;
        y = vector.y;
        z = vector.z;
    }

    /**
     * Calculates the dot product of this vector with another vector
     * @param vectorB The other vector
     * @return The dot product of the two vectors
     */
    public final float dot(Vector3D vectorB) {
        return (x*vectorB.x + y*vectorB.y + z*vectorB.z);
    }

    /**
     * Calculates the dot product of this vector
     * @param Bx The x-coordinate of the other vector
     * @param By The y-coordinate of the other vector
     * @param Bz The z-coordinate of the other vector
     * @return The dot product of the vectors
     */
    public final float dot(float Bx, float By, float Bz) {
        return (x*Bx + y*By + z*Bz);
    }

    /**
     * Calculates the dot product of two vectors
     * @param vectorA The first vector
     * @param vectorB The second vector
     * @return The dot product of the two vectors
     */
    public static final float dot(Vector3D vectorA, Vector3D vectorB) {
        return (vectorA.x*vectorB.x + vectorA.y*vectorB.y + vectorA.z*vectorB.z);
    }

    /**
     * Calculates the cross product of this vector with another vector
     * @param vectorB The other vector
     * @return T new vector representing the cross product
     */
    public final Vector3D cross(Vector3D vectorB) {
        return new Vector3D(this.y*vectorB.z - this.z*vectorB.y, this.z*vectorB.x - this.x*vectorB.z, this.x*vectorB.y - this.y*vectorB.x);
    }

    /**
     * Calculates the cross product of this vector
     * @param Bx The x-coordinate of the other vector
     * @param By The y-coordinate of the other vector
     * @param Bz The z-coordinate of the other vector
     * @return A new vector representing the cross product
     */
    public final Vector3D cross(float Bx, float By, float Bz) {
        return new Vector3D(y*Bz - z*By, z*Bx - x*Bz, x*By - y*Bx);
    }

    /**
     * Calculates the cross product of two vectors
     * @param vectorA The first vector
     * @param vectorB The second vector
     * @return A new vector representing the cross product
     */
    public final static Vector3D cross(Vector3D vectorA, Vector3D vectorB) {
        return new Vector3D(vectorA.y*vectorB.z - vectorA.z*vectorB.y, vectorA.z*vectorB.x - vectorA.x*vectorB.z, vectorA.x*vectorB.y - vectorA.y*vectorB.x);
    }

    /**
     * Calculates the length of the vector
     * @return The length of the vector
     */
    public final float length( ) {
        return (float) Math.sqrt(x*x + y*y + z*z);
    }

    /**
     * Calculates the length of the inputted vector
     * @param vectorA The vector whose length is being calculated
     * @return The length of the vector
     */
    public final static float length(Vector3D vectorA) {
        return (float) Math.sqrt(vectorA.x*vectorA.x + vectorA.y*vectorA.y + vectorA.z*vectorA.z);
    }

    /**
     * Normalizes this vector
     * Changes the co-ordinates of the vector in-place
     */
    public final void normalize( ) {
        float t = x*x + y*y + z*z;
        if (t != 0 && t != 1) t = (float) (1 / Math.sqrt(t));
        x *= t;
        y *= t;
        z *= t;
    }

    /**
     * Calculates a normalized vector from the inputted vector
     * @param vectorA The vector be normalized
     * @return A new vector representing the normalized vector
     */
    public final static Vector3D normalize(Vector3D vectorA) {
        float t = vectorA.x*vectorA.x + vectorA.y*vectorA.y + vectorA.z*vectorA.z;
        if (t != 0 && t != 1) t = (float)(1 / Math.sqrt(t));
        return new Vector3D(vectorA.x*t, vectorA.y*t, vectorA.z*t);
    }

    /**
     * @return A string representation of the vector
     */
    public String toString() {
        return new String("["+x+", "+y+", "+z+"]");
    }
}