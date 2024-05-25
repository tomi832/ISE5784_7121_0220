package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * a class that represents a sphere in a 3-dimensional space
 */
public class Sphere extends RadialGeometry{
    private final Point center;

    /**
     * a constructor for the class Sphere.
     * @param c a point that represents where the center of sphere is.
     * @param r the radius of the sphere
     */
    public Sphere(Point c, double r) {
        super(r);
        center = c;
    }

    /**
     * a method that returns the normal of the sphere at a certain point
     * @param point a point on the surface of the sphere
     * @return the normal of the sphere
     */
    @Override
    public Vector getNormal(Point point) {
        return point.subtract(center).normalize();
    }
}
