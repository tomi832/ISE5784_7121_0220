package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.alignZero;
import java.util.List;

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

    /**
     * a method that finds the intersections of a ray with the sphere
     * @param ray the ray that intersects with the sphere
     * @param distance no need
     * @return a list of the intersections of the ray with the sphere
     */
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double distance) {
        if (center == ray.getHead())
            return List.of(new GeoPoint(this, ray.getPoint(radius)));
        Vector u = center.subtract(ray.getHead());
        double tm = alignZero(ray.getDirection().dotProduct(u));
        double d = alignZero(Math.sqrt(u.lengthSquared() - tm * tm));
        if (d >= radius)
            return null;

        double th = alignZero(Math.sqrt(radius * radius - d * d));
        double t1 = alignZero(tm + th);
        double t2 = alignZero(tm - th);

        if (t1 > 0 && t2 > 0) {
            return List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
        }

        if (t1 > 0)
            return List.of(new GeoPoint(this, ray.getPoint(t1)));

        if (t2 > 0)
            return List.of(new GeoPoint(this, ray.getPoint(t2)));
        return null;
    }
}
