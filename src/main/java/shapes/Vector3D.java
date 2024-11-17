package shapes;

// A simple vector class
public class Vector3D {
    public float x, y, z;

    // constructors
    public Vector3D( ) {
    }

    public Vector3D(float x, float y, float z) {
        this.x = x; this.y = y; this.z = z;
    }

    public Vector3D(Vector3D vector) {
        x = vector.x;
        y = vector.y;
        z = vector.z;
    }

    // methods
    public final float dot(Vector3D vectorB) {
        return (x*vectorB.x + y*vectorB.y + z*vectorB.z);
    }

    public final float dot(float Bx, float By, float Bz) {
        return (x*Bx + y*By + z*Bz);
    }

    public static final float dot(Vector3D vectorA, Vector3D vectorB) {
        return (vectorA.x*vectorB.x + vectorA.y*vectorB.y + vectorA.z*vectorB.z);
    }

    public final Vector3D cross(Vector3D vectorB) {
        return new Vector3D(this.y*vectorB.z - this.z*vectorB.y, this.z*vectorB.x - this.x*vectorB.z, this.x*vectorB.y - this.y*vectorB.x);
    }

    public final Vector3D cross(float Bx, float By, float Bz) {
        return new Vector3D(y*Bz - z*By, z*Bx - x*Bz, x*By - y*Bx);
    }

    public final static Vector3D cross(Vector3D vectorA, Vector3D vectorB) {
        return new Vector3D(vectorA.y*vectorB.z - vectorA.z*vectorB.y, vectorA.z*vectorB.x - vectorA.x*vectorB.z, vectorA.x*vectorB.y - vectorA.y*vectorB.x);
    }

    public final float length( ) {
        return (float) Math.sqrt(x*x + y*y + z*z);
    }

    public final static float length(Vector3D vectorA) {
        return (float) Math.sqrt(vectorA.x*vectorA.x + vectorA.y*vectorA.y + vectorA.z*vectorA.z);
    }

    public final void normalize( ) {
        float t = x*x + y*y + z*z;
        if (t != 0 && t != 1) t = (float) (1 / Math.sqrt(t));
        x *= t;
        y *= t;
        z *= t;
    }

    public final static Vector3D normalize(Vector3D vectorA) {
        float t = vectorA.x*vectorA.x + vectorA.y*vectorA.y + vectorA.z*vectorA.z;
        if (t != 0 && t != 1) t = (float)(1 / Math.sqrt(t));
        return new Vector3D(vectorA.x*t, vectorA.y*t, vectorA.z*t);
    }

    public String toString() {
        return new String("["+x+", "+y+", "+z+"]");
    }
}