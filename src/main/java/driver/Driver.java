package driver;

import render.Render;
import shapes.Sphere;
import shapes.Surface;
import shapes.Vector3D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import lighting.Light;
import lighting.Ray;
import api.RayTracing;
import camera.Camera;

import java.util.ArrayList;
import java.util.List;

public class Driver {
	final static int CHUNKSIZE = 100;
	private List<Object> objectList;
	private List<Object> lightList;
	private Canvas canvas;
	private GraphicsContext gc;
	private Camera camera;
	private Vector3D eye, lookat, up;
	private Vector3D Du, Dv, Vp;
	private float fov;
	private Render renderer;
	private Color background;
	private int width, height;

	public Driver(int width, int height, RayTracing scene) {
		this.width = width;
		this.height = height;
		canvas = new Canvas(this.width, this.height);
		gc = canvas.getGraphicsContext2D();

		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, width, height);

		fov = 30;  // default horizontal field of view

		// Initialize lists
		objectList = new ArrayList<>(CHUNKSIZE);
		lightList = new ArrayList<>(CHUNKSIZE);
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
		this.renderer = new Render(gc, background, eye, objectList, lightList, Du, Dv, Vp);
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
	public int getHeight() {
		return this.height;
	}
	public Render getRenderer() {
		return this.renderer;
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

		renderer.setDu(Du);
		renderer.setDv(Dv);
		renderer.setVp(Vp);

		Vp.x = Vp.x * fl - 0.5f * (width * Du.x + height * Dv.x);
		Vp.y = Vp.y * fl - 0.5f * (width * Du.y + height * Dv.y);
		Vp.z = Vp.z * fl - 0.5f * (width * Du.z + height * Dv.z);
	}
}
