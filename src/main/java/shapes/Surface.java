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
    public float ir, ig, ib;        // surface's intrinsic color
    public float ka, kd, ks, ns;    // constants for phong model
    public float kt, kr, nt;
    private static final float TINY = 0.001f;
    private static final float I255 = 0.00392156f;  // 1/255

    public Surface(float rval, float gval, float bval,
                   float a, float d, float s, float n,
                   float r, float t, float index) {
        ir = rval; ig = gval; ib = bval;
        ka = a; kd = d; ks = s; ns = n;
        kr = r*I255; kt = t; nt = index;
    }

    public Color shade(Vector3D p, Vector3D n, Vector3D v, java.util.List<Object> lights, List<Object> objects, Color bgnd) {
        float r = 0;
        float g = 0;
        float b = 0;
        for (Object lightSources : lights) {
            Light light = (Light) lightSources;
            if (light.lightType == Light.AMBIENT) {
                r += ka*ir*light.intensityRed;
                g += ka*ig*light.intensityGreen;
                b += ka*ib*light.intensityBlue;
            } else {
                Vector3D l;
                if (light.lightType == Light.POINT) {
                    l = new Vector3D(light.lightVec.x - p.x, light.lightVec.y - p.y, light.lightVec.z - p.z);
                    l.normalize();
                } else {
                    l = new Vector3D(-light.lightVec.x, -light.lightVec.y, -light.lightVec.z);
                }

                // Check if the surface point is in shadow
                Vector3D poffset = new Vector3D(p.x + TINY*l.x, p.y + TINY*l.y, p.z + TINY*l.z);
                Ray shadowRay = new Ray(poffset, l);
                if (shadowRay.trace(objects))
                    break;

                float lambert = Vector3D.dot(n,l);
                if (lambert > 0) {
                    if (kd > 0) {
                        float diffuse = kd*lambert;
                        r += diffuse*ir*light.intensityRed;
                        g += diffuse*ig*light.intensityGreen;
                        b += diffuse*ib*light.intensityBlue;
                    }
                    if (ks > 0) {
                        lambert *= 2;
                        float spec = v.dot(lambert*n.x - l.x, lambert*n.y - l.y, lambert*n.z - l.z);
                        if (spec > 0) {
                            spec = ks*((float) Math.pow((double) spec, (double) ns));
                            r += spec*light.intensityRed;
                            g += spec*light.intensityGreen;
                            b += spec*light.intensityBlue;
                        }
                    }
                }
            }
        }

        // Compute illumination due to reflection
        if (kr > 0) {
            float t = v.dot(n);
            if (t > 0) {
                t *= 2;
                Vector3D reflect = new Vector3D(t*n.x - v.x, t*n.y - v.y, t*n.z - v.z);
                Vector3D poffset = new Vector3D(p.x + TINY*reflect.x, p.y + TINY*reflect.y, p.z + TINY*reflect.z);
                Ray reflectedRay = new Ray(poffset, reflect);
                if (reflectedRay.trace(objects)) {
                    Color rcolor = reflectedRay.shade(lights, objects, bgnd);
                    r += kr*rcolor.getRed();
                    g += kr*rcolor.getGreen();
                    b += kr*rcolor.getBlue();
                } else {
                    r += kr*bgnd.getRed();
                    g += kr*bgnd.getGreen();
                    b += kr*bgnd.getBlue();
                }
            }
        }

        // Add code for refraction here

        r = (r > 1f) ? 1f : r;
        g = (g > 1f) ? 1f : g;
        b = (b > 1f) ? 1f : b;

        r = (r < 0) ? 0 : r;
        g = (g < 0) ? 0 : g;
        b = (b < 0) ? 0 : b;

        return Color.color(r, g, b);
    }
}