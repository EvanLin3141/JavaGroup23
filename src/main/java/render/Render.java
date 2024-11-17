package render;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lighting.Ray;
import shapes.Vector3D;

import java.util.List;

public class Render {
    private GraphicsContext gc;
    private Color background;
    private Vector3D eye;
    private List<Object> objectList;
    private List<Object> lightList;
    private Vector3D Du, Dv, Vp;

    public Render(GraphicsContext gc, Color background, Vector3D eye,
                    List<Object> objectList, List<Object> lightList,
                    Vector3D Du, Vector3D Dv, Vector3D Vp) {
        this.gc = gc;
        this.background = background;
        this.eye = eye;
        this.objectList = objectList;
        this.lightList = lightList;
        this.Du = Du;
        this.Dv = Dv;
        this.Vp = Vp;
    }

    public void renderPixel(int i, int j) {
        Vector3D dir = new Vector3D(
                i * Du.x + j * Dv.x + Vp.x,
                i * Du.y + j * Dv.y + Vp.y,
                i * Du.z + j * Dv.z + Vp.z
        );
        System.out.println(eye);
        Ray ray = new Ray(eye, dir);

        if (ray.trace(objectList)) {
            Color shadedColor = ray.shade(lightList, objectList, background); // Assume shade returns JavaFX Color
            gc.setFill(shadedColor);
        } else {
            gc.setFill(background); // Directly use JavaFX background color
        }
        gc.fillOval(i, j, 1, 1);

    }

    public void setDu(Vector3D Du) {
        this.Du = Du;
    }
    public void setDv(Vector3D Dv) {
        this.Dv = Dv;
    }
    public void setVp(Vector3D Vp) {
        this.Vp = Vp;
    }
}
