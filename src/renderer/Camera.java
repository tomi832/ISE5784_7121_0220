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

    /**
     * Generate a beam of rays from a point in a direction
     * the function works in the way that it's a square that holds a circle from edge to edge.
     * the function creates a grid of rays in the square and checks if they're inside the circle.
     * the numEdgeSamples doesn't mean how many rays inside the circle, but how many rays on the edge of the square
     * a circle is Pi/4 the area of a square that holds it from edge to edge
     * that equals to about 78.5398% of the area of the square,(so about 78% of the rays will be inside the circle)
     * that means it will return ~0.785 * numEdgeSamples^2 rays
     * @param ray the main ray
     * @param n the normal to the surface
     * @param distance the distance from the point to the center of the circle
     * @param radius the radius of the circle
     * @param numEdgeSamples the number of rays on the edge of the square the holds the circle
     * @return the list of rays that are the beam
     */
    public List<Ray> generateRayBeam(Ray ray, Vector n, double distance, double radius, int numEdgeSamples) {
        if (numEdgeSamples < 1)
            throw new IllegalArgumentException("Number of edge samples must be at least 1");
        if (distance < 0)
            throw new IllegalArgumentException("Distance cannot be negative");
        if (radius < 0)
            throw new IllegalArgumentException("Radius cannot be negative");

        List<Ray> rays = new LinkedList<>();
        rays.add(ray); // Add the main ray
        if (isZero(radius) || isZero(distance) || numEdgeSamples == 1)
            return rays;

        Vector v, dir = ray.getDirection();
        Point head = ray.getHead();
        double gridSpacing = radius * 2 / numEdgeSamples;
        double x, y, radiusSquared = radius * radius;
        Point randomPoint, centerCircle = head.add(dir.scale(distance));

        // the 2 vectors that create the virtual grid for the beam
        Vector nX, nY;
        if (dir.equals(Vector.Y)) {
            nX = new Vector(1, 0, 0);
            nY = new Vector(0, 0, 1);
        } else {
            nX = dir.crossProduct(Vector.Y).normalize();
            nY = dir.crossProduct(nX).normalize();
        }

        double nd = n.dotProduct(dir); // Dot product of normal and original ray direction

        //calculating each x and y in the grid and checking if they're inside the circle/
        //if they are, we randomize them a bit inside their "zone" and then it creates the ray
        for (int i = 0; i < numEdgeSamples; i++) {
            for (int j = 0; j < numEdgeSamples; j++) {
                // Calculate the x and y coordinates in the grid
                x = (i - numEdgeSamples / 2d) * gridSpacing;
                y = (j - numEdgeSamples / 2d) * gridSpacing;

                // Check if the point (x, y) lies within the circle of given radius
                if (x * x + y * y <= radiusSquared) {
                    x += (Math.random() - 0.5) * gridSpacing;
                    y += (Math.random() - 0.5) * gridSpacing;
                    try {
                        randomPoint = centerCircle.add(nX.scale(x)).add(nY.scale(y));
                        v = randomPoint.subtract(head).normalize();
                        double nv = n.dotProduct(v); // Dot product of normal and new ray direction
                        if (nv * nd > 0)
                            rays.add(new Ray(head, v));
                    } catch (Exception e) {
                        j--;
                    }
                }
            }
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