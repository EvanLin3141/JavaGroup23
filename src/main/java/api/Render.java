package api;

import shapes.Surface;
import shapes.Vector3D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import lighting.Ray;
import camera.Camera;

import java.util.ArrayList;
import java.util.List;

public class Render {
	final static int CHUNKSIZE = 100;
	private List<Object> objectList;
	private List<Object> lightList;
	private Surface currentSurface;
	private Canvas canvas;
	private GraphicsContext gc;
	private Camera camera;
	private Vector3D eye, lookat, up;
	private Vector3D Du, Dv, Vp;
	private float fov;
	private RayTracing scene;
	private Color background;
	private int width, height;

	public Render(int width, int height, RayTracing scene) {
		this.width = width;
		this.height = height;
		this.scene = scene;
		canvas = new Canvas(this.width, this.height);
		gc = canvas.getGraphicsContext2D();

		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, width, height);

		fov = 30;  // default horizontal field of view

		// Initialize lists
		objectList = new ArrayList<>(CHUNKSIZE);
		lightList = new ArrayList<>(CHUNKSIZE);
		currentSurface = new Surface(0.8f, 0.2f, 0.9f, 0.2f, 0.4f, 0.4f, 10.0f, 0f, 0f, 1f);
		this.objectList = scene.getSpheres();
		this.lightList = scene.getLights();

		this.camera = scene.getCamera();
		if (camera != null) {
			this.eye = camera.getEye();
			this.lookat = camera.getLookAt();
			this.fov = camera.getFov();
			this.background = Color.BLACK; // Using JavaFX Color directly
			System.out.println(eye);
			System.out.println(lookat);
		}
		nullViewPoint();
		viewDirection();
	}

	public void nullViewPoint() {
		if (eye == null) eye = new Vector3D(0, 0, 10);
		if (lookat == null) lookat = new Vector3D(0, 0, 0);
		if (up == null) up = new Vector3D(0, 1, 0);
	}

	public Image getRenderedImage() {
		return canvas.snapshot(null, null);
	}

	public void renderPixel(int i, int j) {
		Vector3D dir = new Vector3D(
				i * Du.x + j * Dv.x + Vp.x,
				i * Du.y + j * Dv.y + Vp.y,
				i * Du.z + j * Dv.z + Vp.z);
		Ray ray = new Ray(eye, dir);

		if (ray.trace(objectList)) {
			Color shadedColor = ray.shade(lightList, objectList, background); // Assume shade returns JavaFX Color
			gc.setFill(shadedColor);
		} else {
			gc.setFill(background); // Directly use JavaFX background color
		}
		gc.fillOval(i, j, 1, 1);
	}

	public int getHeight() {
		return this.height;
	}

	public int getWidth() {
		return this.width;
	}

	public void viewDirection() {
		Vector3D look = new Vector3D(lookat.x - eye.x, lookat.y - eye.y, lookat.z - eye.z);
		Du = Vector3D.normalize(look.cross(up));
		Dv = Vector3D.normalize(look.cross(Du));
		float fl = (float) (width / (2 * Math.tan((0.5 * fov) * Math.PI / 180)));
		Vp = Vector3D.normalize(look);
		Vp.x = Vp.x * fl - 0.5f * (width * Du.x + height * Dv.x);
		Vp.y = Vp.y * fl - 0.5f * (width * Du.y + height * Dv.y);
		Vp.z = Vp.z * fl - 0.5f * (width * Du.z + height * Dv.z);
	}
}
