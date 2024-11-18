package render;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import lighting.Ray;
import shapes.Vector3D;

import java.util.List;

public class Render {
    private final GraphicsContext gc;
    private final Color background;
    private final Vector3D eye;
    private final List<Object> objectList;
    private final List<Object> lightList;
    private Vector3D Du, Dv, Vp;
    private final int width, height;
    private Canvas canvas;

    public Render(GraphicsContext gc, Color background, Vector3D eye,
                    List<Object> objectList, List<Object> lightList,
                    Vector3D Du, Vector3D Dv, Vector3D Vp, int width, int height, Canvas canvas) {
        this.gc = gc;
        this.background = background;
        this.eye = eye;
        this.objectList = objectList;
        this.lightList = lightList;
        this.Du = Du;
        this.Dv = Dv;
        this.Vp = Vp;
        this.width = width;
        this.height = height;
        this.canvas = canvas;
    }

    public void getRenderer() {
        long time = System.currentTimeMillis();
        for (int j = 0; j < height; j += 1) {
            for (int i = 0; i < width; i += 1) {
                renderPixel(i, j);
            }
        }
        time = System.currentTimeMillis() - time;
        System.err.println("Rendered in "+(time/60000)+":"+((time%60000)*0.001));
    }

    public void renderPixel(int i, int j) {
        Vector3D dir = new Vector3D(
                i * Du.x + j * Dv.x + Vp.x,
                i * Du.y + j * Dv.y + Vp.y,
                i * Du.z + j * Dv.z + Vp.z
        );
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

    public Image getRenderedImage() {
        return canvas.snapshot(null, null);
    }
    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }
}
