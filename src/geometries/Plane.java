package geometries;

import primitives.Ray;
import primitives.Vector;
import primitives.Point;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * a class that represents a plane (plane as in a 2-dimensional shape in 3-dimensions)
 * @author Yosef Kornfeld and Tomere Kalman
 */
public class Plane extends Geometry{
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
        /*
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

    /**
     * a method that finds the intersections of a ray with the plane
     * @param ray a ray that intersects the plane
     * @return a list of points that the ray intersects with the plane
     */
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double distance) {
        // if the ray starts on the plane or is parallel to the plane, there are no intersections
        if (ray.getHead().equals(q) || isZero(normal.dotProduct(ray.getDirection()))) {
            return null;
        }
        Vector edge = q.subtract(ray.getHead());
        double t = alignZero(normal.dotProduct(edge) / normal.dotProduct(ray.getDirection()));
        return (t <= 0 || alignZero(t - distance) > 0) ? null : List.of(new GeoPoint(this, ray.getPoint(t)));
    }

    @Override
    public BoundingBox getBoundingBox() {
        return null;
    }
}
