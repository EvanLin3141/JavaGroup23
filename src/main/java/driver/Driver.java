package driver;

import shapes.Sphere;
import shapes.Surface;
import shapes.Vector3D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import lighting.Light;
import lighting.Ray;
import api.RayTracingObjects;
import camera.Camera;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Driver  {
    final static int CHUNKSIZE = 100;
    List<Object> objectList;
    List<Object> lightList;
    Surface currentSurface;
	Canvas canvas;
	GraphicsContext gc;
	Camera camera;
    Vector3D eye, lookat, up;
    Vector3D Du, Dv, Vp;
    float fov;
	RayTracingObjects scene;

    Color background;

    int width, height;

    public Driver(int width, int height, RayTracingObjects scene) {
        this.width = width;
        this.height = height;
		this.scene = scene;
		canvas = new Canvas(this.width, this.height);
		gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, width, height);

        fov = 30;               // default horizonal field of view

        // Initialize various lists
        objectList = new ArrayList<>(CHUNKSIZE);
        lightList = new ArrayList<>(CHUNKSIZE);
        currentSurface = new Surface(0.8f, 0.2f, 0.9f, 0.2f, 0.4f, 0.4f, 10.0f, 0f, 0f, 1f);
		this.objectList = scene.getSpheres();
		this.lightList = scene.getLights();

		Camera camera = scene.getCamera();
		if (camera != null) {
			this.eye = camera.getEye();
			this.lookat = camera.getLookAt();
			this.fov = camera.getFov();
			this.background = Color.rgb(0, 0, 0);
			System.out.println(eye);
			System.out.println(lookat);
		}
		nullViewPoint();  // Set default values if the camera is missing
		viewDirection();
    }

	public void nullViewPoint() {
		// Initialize more defaults if they weren't specified
		if (eye == null) eye = new Vector3D(0, 0, 10);
		if (lookat == null) lookat = new Vector3D(0, 0, 0);
		if (up  == null) up = new Vector3D(0, 1, 0);
		if (background == null) background = Color.rgb(0,0,0);

	}

	public Image getRenderedImage() {
    	return canvas.snapshot(null, null);
	}

	public void renderPixel(int i, int j) {
		Vector3D dir = new Vector3D(
				i*Du.x + j*Dv.x + Vp.x,
				i*Du.y + j*Dv.y + Vp.y,
				i*Du.z + j*Dv.z + Vp.z);
		Ray ray = new Ray(eye, dir);
		if (ray.trace(objectList)) {
			java.awt.Color bg = toAWTColor(background);
			gc.setFill(toFXColor(ray.shade(lightList, objectList, bg)));
		} else {
			gc.setFill(background);
		}
		gc.fillOval(i, j, 1, 1);
	}

	private java.awt.Color toAWTColor(Color c) {
    	return new java.awt.Color((float) c.getRed(),
				(float) c.getGreen(),
				(float) c.getBlue(),
				(float) c.getOpacity());
	}

	private Color toFXColor(java.awt.Color c) {
		return Color.rgb(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha() / 255.0);
	}

	public int getHeight() {
		return this.height;
	}

	public int getWidth() {
		return this.width;
	}

	public void viewDirection (){
		// Compute viewing matrix that maps a
		// screen coordinate to a ray direction
		Vector3D look = new Vector3D(lookat.x - eye.x, lookat.y - eye.y, lookat.z - eye.z);
		Du = Vector3D.normalize(look.cross(up));
		Dv = Vector3D.normalize(look.cross(Du));
		float fl = (float)(width / (2*Math.tan((0.5*fov)*Math.PI/180)));
		Vp = Vector3D.normalize(look);
		Vp.x = Vp.x*fl - 0.5f*(width*Du.x + height*Dv.x);
		Vp.y = Vp.y*fl - 0.5f*(width*Du.y + height*Dv.y);
		Vp.z = Vp.z*fl - 0.5f*(width*Du.z + height*Dv.z);
	}
}