package shapes;

import lighting.Light;
import lighting.Ray;

import javafx.scene.paint.Color;
import java.util.List;

/*
ka =  ambient reflection coefficient
kd = diffuse reflection coefficient
ks = specular reflection coefficient
kt = transmission coefficient
kr = reflectance coefficient
ns phong exponent
 */
public class Surface {

    // surface's intrinsic colour
    public float intrinsicColourRed;
    public float intrinsicColourGreen;
    public float intrinsicColourBlue;

    // constants for phong model
    public float ambientReflection;
    public float diffuseReflection;
    public float specularReflection;
    public float phongExponent;
    public float transmission;
    public float reflectance;
    public float refractiveIndex;

    private static final float TINY = 0.001f;
    private static final float I255 = 0.00392156f;  // 1/255

    public Surface(float red, float green, float blue,
                   float ambient, float diffuse, float specular, float phongExp,
                   float reflectance, float transmission, float index) {
        intrinsicColourRed = red;
        intrinsicColourGreen = green;
        intrinsicColourBlue = blue;
        ambientReflection = ambient;
        diffuseReflection = diffuse;
        specularReflection = specular;
        phongExponent = phongExp;
        this.reflectance = reflectance*I255;
        this.transmission = transmission;
        refractiveIndex = index;
    }

    public Color shade(Vector3D intersectionPoint, Vector3D surfaceNormal, Vector3D view, java.util.List<Object> lights, List<Object> objects, Color backgroundColour) {
        float red = 0;
        float green = 0;
        float blue = 0;

        for (Object lightSources : lights) {
            Light light = (Light) lightSources;
            if (light.lightType == Light.AMBIENT) {
                red += ambientReflection*intrinsicColourRed*light.intensityRed;
                green += ambientReflection*intrinsicColourGreen*light.intensityGreen;
                blue += ambientReflection*intrinsicColourBlue*light.intensityBlue;
            } else {
                Vector3D lightDirection;
                if (light.lightType == Light.POINT) {
                    lightDirection = new Vector3D(light.lightVec.x - intersectionPoint.x, light.lightVec.y - intersectionPoint.y, light.lightVec.z - intersectionPoint.z);
                    lightDirection.normalize();
                } else {
                    lightDirection = new Vector3D(-light.lightVec.x, -light.lightVec.y, -light.lightVec.z);
                }

                // Check if the surface point is in shadow
                Vector3D pointOffset = new Vector3D(intersectionPoint.x + TINY*lightDirection.x, intersectionPoint.y + TINY*lightDirection.y, intersectionPoint.z + TINY*lightDirection.z);
                Ray shadowRay = new Ray(pointOffset, lightDirection);
                if (shadowRay.trace(objects))
                    break;

                float lambert = Vector3D.dot(surfaceNormal,lightDirection);
                if (lambert > 0) {
                    if (diffuseReflection > 0) {
                        float diffuse = diffuseReflection*lambert;
                        red += diffuse*intrinsicColourRed*light.intensityRed;
                        green += diffuse*intrinsicColourGreen*light.intensityGreen;
                        blue += diffuse*intrinsicColourBlue*light.intensityBlue;
                    }
                    if (specularReflection > 0) {
                        lambert *= 2;
                        float specular = view.dot(lambert*surfaceNormal.x - lightDirection.x, lambert*surfaceNormal.y - lightDirection.y, lambert*surfaceNormal.z - lightDirection.z);
                        if (specular > 0) {
                            specular = specularReflection*((float) Math.pow((double) specular, (double) phongExponent));
                            red += specular*light.intensityRed;
                            green += specular*light.intensityGreen;
                            blue += specular*light.intensityBlue;
                        }
                    }
                }
            }
        }

        // Compute illumination due to reflection
        if (reflectance > 0) {
            float t = view.dot(surfaceNormal);
            if (t > 0) {
                t *= 2;
                Vector3D reflect = new Vector3D(t*surfaceNormal.x - view.x, t*surfaceNormal.y - view.y, t*surfaceNormal.z - view.z);
                Vector3D pointOffset = new Vector3D(intersectionPoint.x + TINY*reflect.x, intersectionPoint.y + TINY*reflect.y, intersectionPoint.z + TINY*reflect.z);
                Ray reflectedRay = new Ray(pointOffset, reflect);
                if (reflectedRay.trace(objects)) {
                    Color reflectionColour = reflectedRay.shade(lights, objects, backgroundColour);
                    red += reflectance*reflectionColour.getRed();
                    green += reflectance*reflectionColour.getGreen();
                    blue += reflectance*reflectionColour.getBlue();
                } else {
                    red += reflectance*backgroundColour.getRed();
                    green += reflectance*backgroundColour.getGreen();
                    blue += reflectance*backgroundColour.getBlue();
                }
            }
        }

        // Add code for refraction here

        red = (red > 1f) ? 1f : red;
        green = (green > 1f) ? 1f : green;
        blue = (blue > 1f) ? 1f : blue;

        red = (red < 0) ? 0 : red;
        green = (green < 0) ? 0 : green;
        blue = (blue < 0) ? 0 : blue;

        return Color.color(red, green, blue);
    }
}