package camera;

import shapes.Vector3D;

public class Camera {
    private Vector3D position;
    private Vector3D lookAt;
    private float fov = 30;

    public Camera(Vector3D position, Vector3D lookAt) {
        this.position = position;
        this.lookAt = lookAt;
    }

    public Vector3D getPosition() {
        return position;
    }

    public Vector3D getLookAt() {
        return lookAt;
    }

    public float getFov() {
        return fov;
    }

    public void setPosition(Vector3D position) {
        this.position = position;
    }

    public void setLookAt(Vector3D lookAt) {
        this.lookAt = lookAt;
    }

    public void setFov(float fov) {
        this.fov = fov;
    }
}
