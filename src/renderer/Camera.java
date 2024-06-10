package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import primitives.Color;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.security.cert.CertPathBuilder;
import java.util.MissingResourceException;
import static primitives.Util.isZero;

/**
 * Camera class represents a camera in the scene
 */
public class Camera implements Cloneable {
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;
    private Point location;
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;
    private double distance = 0;
    private double width = 0;
    private double height = 0;


    /**
     * Camera constructor
     */
    private Camera() {}

    /**
     * Camera builder
     * @return Builder object for the camera
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    /** Getters */
    public ImageWriter getImageWriter() {
        return imageWriter;
    }

    public RayTracerBase getRayTracer() {
        return rayTracer;
    }

    public Point getLocation() {
        return location;
    }

    public Vector getVTo() {
        return vTo;
    }

    public Vector getVUp() {
        return vUp;
    }

    public Vector getVRight() {
        return vRight;
    }

    public double getDistance() {
        return distance;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    /**
     * Construct a ray from the camera to a pixel in the view plane
     * @param nX number of pixels in the x axis
     * @param nY number of pixels in the y axis
     * @param j y index of the pixel
     * @param i x index of the pixel
     * @return Ray from the camera to the pixel
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        Point pC = location.add(vTo.scale(distance));
        double rY = height / nY;
        double rX = width / nX;

        double yi = -(i - ((nY-1) / 2d)) * rY;
        double xj = (j - ((nX - 1) / 2d)) * rX;

        Point pIJ = pC;
        if (!isZero(yi)) {
            pIJ = pIJ.add(vUp.scale(yi));
        }
        if (!isZero(xj)) {
            pIJ = pIJ.add(vRight.scale(xj));
        }
        Vector vIJ = pIJ.subtract(location);
        return new Ray(location, vIJ);
    }

    /**
     * Render the image
     * @param interval interval for the grid
     * @param color color of the grid
     */
    public void renderImage(int interval, Color color){
        for(int i = 0; i < imageWriter.getNx(); i++){
            for(int j = 0; j < imageWriter.getNy(); j++){
                Ray ray = constructRay(imageWriter.getNx(), imageWriter.getNy(), j, i);
                Color pixelColor = rayTracer.traceRay(ray);
                imageWriter.writePixel(i, j, pixelColor);
            }
        }
    }

    /**
     * Print the grid
     */
    public void printGrid(int interval, Color color){
        for (int i = 0; i < imageWriter.getNx(); i++)
            for (int j = 0; j < imageWriter.getNy(); j++)
                if (i % interval == 0 || j % 50 == 0)
                    imageWriter.writePixel(i, j, color);

        imageWriter.writeToImage();
    }

    /**
     * Write the image to a file
     */
    public void writeToImage(){
        imageWriter.writeToImage();
    }

    /**
     * Camera builder class
     */
    public static class Builder {
        private final Camera camera = new Camera();

        /**
         * Builder sets the rayTracer to the camera
         */
        public Builder setRayTracer(RayTracerBase  rayTracer) {
            if (rayTracer == null)
                throw new IllegalArgumentException("RayTracer cannot be null");
            camera.rayTracer = rayTracer;
            return this;
        }

        /**
         * Builder sets the imageWriter to the camera
         */
        public Builder setImageWriter(ImageWriter imageWriter) {
            if (imageWriter == null)
                throw new IllegalArgumentException("ImageWriter cannot be null");
            if (isZero(imageWriter.getNx()) || isZero(imageWriter.getNy()))
                throw new IllegalArgumentException("ImageWriter dimensions cannot be zero");
            if (imageWriter.getNx() < 0 || imageWriter.getNy() < 0)
                throw new IllegalArgumentException("ImageWriter dimensions cannot be negative");
            camera.imageWriter = imageWriter;
            return this;
        }

        /**
         * Builder sets the location to the camera
         */
        public Builder setLocation(Point point) {
            if (point == null)
                throw new IllegalArgumentException("Location cannot be null");
            camera.location = point;
            return this;
        }

        /**
         * Builder sets the direction vectors to the camera
         */
        public Builder setDirection(Vector vTo, Vector vUp) {
            if (vTo == null || vUp == null)
                throw new IllegalArgumentException("Direction vectors cannot be null");
            if (vTo.equals(Vector.ZERO) || vUp.equals(Vector.ZERO))
                throw new IllegalArgumentException("Direction vectors cannot be zero");
            camera.vTo = vTo.normalize();
            camera.vUp = vUp.normalize();
            camera.vRight = vTo.crossProduct(vUp).normalize();
            return this;
        }

        /**
         * Builder sets the view plane distance to the camera
         */
        public Builder setVpDistance(double distance) {
            if (isZero(distance))
                throw new IllegalArgumentException("Distance cannot be zero");
            if (distance < 0)
                throw new IllegalArgumentException("Distance cannot be negative");
            camera.distance = distance;
            return this;
        }

        /**
         * Builder sets the view plane size to the camera
         */
        public Builder setVpSize(double width, double height) {
            if (isZero(width) || isZero(height))
                throw new IllegalArgumentException("Width and height cannot be zero");
            if (width < 0 || height < 0)
                throw new IllegalArgumentException("Width and height cannot be negative");
            camera.width = width;
            camera.height = height;
            return this;
        }

        /**
         * Builder builds the camera
         */
        public Camera build() {
            if (camera.distance == 0)
                throw new MissingResourceException("missing rendering element", "Camera",
                        "missing distance");
            if (camera.height == 0)
                throw new MissingResourceException("missing rendering element", "Camera",
                        "missing height");
            if (camera.width == 0)
                throw new MissingResourceException("missing rendering element", "Camera",
                        "missing width");
            if (camera.location == null)
                throw new MissingResourceException("missing rendering element", "Camera",
                        "missing location");
            if (camera.vTo == null)
                throw new MissingResourceException("missing rendering element", "Camera",
                        "missing vTo");
            if (camera.vUp == null)
                throw new MissingResourceException("missing rendering element", "Camera",
                        "missing vUp");
            if (camera.vRight == null)
                throw new MissingResourceException("missing rendering element", "Camera",
                        "missing vRight");
            if (camera.imageWriter == null)
                throw new MissingResourceException("missing rendering element", "Camera",
                        "missing imageWriter");
            if (camera.rayTracer == null)
                throw new MissingResourceException("missing rendering element", "Camera",
                        "missing rayTracer");
            return (Camera) camera.clone();
        }
    }


    /**
     * Clone the camera
     * @return new camera object
     */
    @Override
    public Camera clone() {
        try{
            Camera camera = (Camera) super.clone();
            camera.imageWriter = this.imageWriter;
            camera.rayTracer = this.rayTracer;
            camera.location = this.location;
            camera.vTo = this.vTo;
            camera.vUp = this.vUp;
            camera.vRight = this.vRight;
            camera.distance = this.distance;
            camera.width = this.width;
            camera.height = this.height;
            return camera;
        } catch (CloneNotSupportedException e) {
            throw new MissingResourceException("missing rendering element", "Camera",
                    "missing clone");
        }
    }
}
