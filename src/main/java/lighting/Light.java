package lighting;

import shapes.Vector3D;

// All the public variables here are ugly, but I
// wanted Lights and Surfaces to be "friends"
public class Light {
    public static final int AMBIENT = 0;
    public static final int DIRECTIONAL = 1;
    public static final int POINT = 2;

    public int lightType;
    public Vector3D lightVec;           // the position of a point light or
    // the direction to a directional light
    public float intensityRed;
    public float intensityGreen;
    public float intensityBlue;        // intensity of the light source

    public Light(int type, Vector3D v, float r, float g, float b) {
        lightType = type;
        intensityRed = r;
        intensityGreen = g;
        intensityBlue = b;
        if (type != AMBIENT) {
            lightVec = v;
            if (type == DIRECTIONAL) {
                lightVec.normalize();
            }
        }
    }
}