package lighting;

import shapes.Vector3D;

/**
 * Represents a light source in a scene with various light types, light intensities and light positions
 */


public class Light {
    /* Types of light, ambient, directional or point.*/
    public static final int AMBIENT = 0;
    public static final int DIRECTIONAL = 1;
    public static final int POINT = 2;

    public int lightType;

    /** The position of a point light or the direction of a directional light*/
    public Vector3D lightVec;

    /* intensities of the red, green, blue colour components of the light source */
    public float intensityRed;
    public float intensityGreen;
    public float intensityBlue;

    /**
     * Constructs a new Light object
     * @param type The type of light (ambient, directional or point)
     * @param red The amount of red in the light
     * @param green The amount of green in the light
     * @param blue The amount of blue in the light
     * @param positionOrDirection A {@code Vector3D} representing the light's position (for point light) or direction (for directional light)
     */
    public Light(int type, float red, float green, float blue, Vector3D positionOrDirection) {
        lightType = type;
        intensityRed = red;
        intensityGreen = green;
        intensityBlue = blue;
        if (type != AMBIENT) {
            lightVec = positionOrDirection;
            if (type == DIRECTIONAL) {
                lightVec.normalize();
            }
        }
    }


}