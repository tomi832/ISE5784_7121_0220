package geometries;

import primitives.Vector;
import primitives.Point;

/**
 * a class that represents a plane (plane as in a 2-dimensional shape in 3-dimensions)
 * @author Yosef Kornfeld and Tomere Kalman
 */
public class Plane implements Geometry{
    private final Point q;
    private final Vector normal;

    /**
     * constructor for a Plane object using 3 points in the Euclidean plane
     * @param point1 a point in the Euclidean plane
     * @param point2 a point in the Euclidean plane
     * @param point3 a point in the Euclidean plane
     */
    public Plane(Point point1, Point point2, Point point3) {
        this.q = point1;
        /**
         * "point1.subtract(point2)" -> first edge
         * "point1.subtract(point3)" -> second edge
         * cross product of the two gives you the normal vector, and we need to normalize it.
         */
        this.normal = point1.subtract(point2).crossProduct(point1.subtract(point3)).normalize();
    }

    /**
     * constructor for a Plane object using a point and a normalized vector
     * (the vector will be normalized in the constructor)
     * @param q a point in the Euclidean plane
     * @param normal a vector of the users choosing (will be normalized in the ctor)
     */
    public Plane(Point q, Vector normal) {
        this.q = q;
        this.normal = normal.normalize();
    }

    /**
     * a method that returns the normal of the plane
     * @param point a point on the surface of the plane
     * @return the normal of the plane
     */
    @Override
    public Vector getNormal(Point point) {
        return normal;
    }

    /**
     * a method that returns the normal of the plane
     * @return the normal of the plane
     */
    public Vector getNormal() {
        return normal;
    }
}
