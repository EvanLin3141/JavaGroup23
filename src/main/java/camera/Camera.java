package camera;

import shapes.Vector3D;

public class Camera {
    private Vector3D eye;
    private Vector3D lookAt;
    private float fov = 30;

    public Camera(Vector3D eye, Vector3D lookAt) {
        this.eye = eye;
        this.lookAt = lookAt;
    }

    public Vector3D getEye() {
        return eye;
    }

    public Vector3D getLookAt() {
        return lookAt;
    }

    public float getFov() {
        return fov;
    }

    public void setPosition(Vector3D eye) {
        this.eye = eye;
    }

    public void setLookAt(Vector3D lookAt) {
        this.lookAt = lookAt;
    }

    public void setFov(float fov) {
        this.fov = fov;
    }
}
