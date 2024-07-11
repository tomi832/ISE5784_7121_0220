package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;
import static primitives.Util.isZero;

/**
 * Camera class represents a camera in the scene
 */
public class Camera implements Cloneable {
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;
    private Point location = null;
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;
    private double distance = 0;
    private double width = 0;
    private double height = 0;
    private Point pC;
    private Point dirPoint = null;

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
     * @param j x index of the pixel
     * @param i y index of the pixel
     * @return Ray from the camera to the pixel
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        double rY = height / nY;
        double rX = width / nX;

        double xj = (j - ((nX - 1) / 2d)) * rX;
        double yi = -(i - ((nY-1) / 2d)) * rY;

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
     */
    public Camera renderImage(){
        for(int i = 0; i < imageWriter.getNy(); i++){
            for(int j = 0; j < imageWriter.getNx(); j++){
                castRay(imageWriter.getNx(), imageWriter.getNy(), j, i);
            }
        }
        return this;
    }

    /**
     * Cast a ray from the camera to a pixel in the view plane
     * @param Nx number of pixels in the x axis
     * @param Ny number of pixels in the y axis
     * @param j x index of the pixel
     * @param i y index of the pixel
     */
    private void castRay(int Nx, int Ny, int j, int i){
        Ray ray = constructRay(Nx, Ny, j, i);
        Color pixelColor = rayTracer.traceRay(ray);
        imageWriter.writePixel(j, i, pixelColor);
    }

    /**
     * Print the grid
     */
    public Camera printGrid(int interval, Color color){
        for (int i = 0; i < imageWriter.getNx(); i++)
            for (int j = 0; j < imageWriter.getNy(); j++)
                if (i % interval == 0 || j % interval == 0)
                    imageWriter.writePixel(i, j, color);

        imageWriter.writeToImage();
        return this;
    }

    /**
     * Write the image to a file
     */
    public Camera writeToImage(){
        imageWriter.writeToImage();
        return this;
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
            if (camera.dirPoint != null)
                return setDirection(camera.dirPoint, camera.vUp);
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
            if (!isZero(vTo.dotProduct(vUp)))
                throw new IllegalArgumentException("Direction vectors must be orthogonal");
            camera.vTo = vTo.normalize();
            camera.vUp = vUp.normalize();
            return this;
        }

        /**
         * Builder sets the direction vectors to the camera
         */
        public Builder setDirection(Point p0, Vector vUp) {
            if (p0 == null || vUp == null)
                throw new IllegalArgumentException("Direction vectors cannot be null");
            if (vUp.equals(Vector.ZERO))
                throw new IllegalArgumentException("Direction vectors cannot be zero");
            camera.dirPoint = p0;
            if (camera.location == null)
                return this;
            Vector vTo = p0.subtract(camera.location);
            Vector vRight = vTo.crossProduct(vUp);
            camera.vTo = vTo.normalize();
            camera.vUp = vRight.crossProduct(vTo).normalize();
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
            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
            if (camera.distance <= 0)
                throw new IllegalArgumentException("Distance cannot be zero or negative");
            if (camera.height <= 0)
                throw new IllegalArgumentException("Plane height cannot be zero or negative");
            if (camera.width <= 0)
                throw new IllegalArgumentException("Plane width cannot be zero or negative");
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
            camera.pC = camera.location.add(camera.vTo.scale(camera.distance));
            return (Camera) camera.clone();
        }
    }

    public List<Ray> generateRayBeam(Ray ray, Vector n, double radius, int numSamples) {
        List<Ray> rays = new LinkedList<>();
        Vector dir = ray.getDirection();
        Point head = ray.getHead();
        Vector v;

        // the 2 vectors that create the virtual grid for the beam
        Vector nX, nY;
        if (dir.equals(Vector.Y)) {
            nX = new Vector(1, 0, 0);
            nY = new Vector(0, 0, 1);
        } else {
            nX = dir.crossProduct(Vector.Y);
            nY = dir.crossProduct(nX).normalize();
        }

        Point centerCircle = head.add(dir.scale(distance));
        rays.add(ray); // Add the main ray
        numSamples = (int)Math.floor(Math.sqrt(numSamples));

        for (int i = 0; i < numSamples; i++) {
            for (int j = 0; j < numSamples; j++) {

            }
        }

        for (int k = 0; k < numSamples; k++) {
            Point randomPoint = centerCircle;
            double randX = (Math.random() * 2 - 1) * radius;
            double randY = (Math.random() * 2 - 1) * Math.sqrt(radius * radius - randX * randX);

            try {
                randomPoint = randomPoint.add(nX.scale(randX));
            } catch (Exception ex) {
            }

            try {
                randomPoint = randomPoint.add(nY.scale(randY));
            } catch (Exception ex) {
            }

            Vector v12 = randomPoint.subtract(head).normalize();
            rays.add(new Ray(head, v12));
        }

        return rays;
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
            camera.pC = this.pC;
            camera.dirPoint = this.dirPoint;
            return camera;
        } catch (CloneNotSupportedException e) {
            throw new MissingResourceException("missing rendering element", "Camera",
                    "missing clone");
        }
    }
}