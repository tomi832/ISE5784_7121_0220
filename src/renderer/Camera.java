package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.security.cert.CertPathBuilder;

public class Camera implements Cloneable {
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;
    private Point location;
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;
    private  double distance;
    private double width;
    private final double height;

    public Camera(Builder builder) {
        this.imageWriter = builder.imageWriter;
        this.rayTracer = builder.rayTracer;
        this.location = builder.location;
        this.distance = builder.distance;
        this.vRight = builder.vRight;
        this.vTo = builder.vTo;
        this.vUp = builder.vUp;
        this.width = builder.width;
        this.height = builder.height;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public Ray constructRay(int nX, int nY, int j, int i) {
        Point pC = location.add(vTo.scale(distance));
        double rY = height / imageWriter.getNy();
        double rX = width / imageWriter.getNx();

        double yi = (i - nY / 2d) * rY + rY / 2d;
        double xj = (j - nX / 2d) * rX + rX / 2d;
        Point pIJ = pC;
        if (yi != 0) {
            pIJ = pIJ.add(vUp.scale(-yi));
        }
        if (xj != 0) {
            pIJ = pIJ.add(vRight.scale(xj));
        }
        Vector vIJ = pIJ.subtract(location).normalize();
        return new Ray(location, vIJ);
    }

    public static class Builder {

        private ImageWriter imageWriter;
        private RayTracerBase rayTracer;
        private Point location;
        private Vector vTo;
        private Vector vUp;
        private Vector vRight;
        private double distance;
        private double width;
        private double height;

        public Builder setRayTracer(RayTracerBase  rayTracer) {
            this.rayTracer = rayTracer;
            return this;
        }

        public Builder setImageWriter(ImageWriter imageWriter) {
            this.imageWriter = imageWriter;
            return this;
        }

        public Builder setLocation(Point point) {
            this.location = point;
            return this;
        }

        public Builder setDirection(Vector vTo, Vector vUp) {
            this.vTo = vTo.normalize();
            this.vUp = vUp.normalize();
            this.vRight = vTo.crossProduct(vUp).normalize();
            return this;
        }

        public Builder setVpDistance(double distance) {
            this.distance = distance;
            return this;
        }

        public Builder setVpSize(double width, double height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public Camera build() {
            Camera camera = new Camera(this);
            return camera;
        }
    }
    @Override
    public Camera clone() {
        return null;
    }
}
