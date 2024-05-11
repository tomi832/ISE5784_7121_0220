package geometries;
//
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
        this.normal = null;
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

    @Override
    public Vector getNormal(Point point) {
        return normal;
    }

    public Vector getNormal() {
        return normal;
    }
}
