package geometries;

import primitives.*;

import java.util.List;

/**
 * a class that represents a flat Triangle (inherits from the Polygon class)
 */
public class Triangle extends Polygon {

    /**
     * a constructor for Triangle using 3 points
     *
     * @param point1 a point in the Euclidean plane
     * @param point2 a point in the Euclidean plane
     * @param point3 a point in the Euclidean plane
     */
    public Triangle(Point point1, Point point2, Point point3) {
        super(point1, point2, point3);
    }

    public List<Point> findIntersections(Ray ray) {
        var intersectionPoint = plane.findIntersections(ray);
        // if the ray doesn't intersect the plane, return null
        if (intersectionPoint == null)
            return null;
        Point rayHead = ray.getHead();
        Vector rayDir = ray.getDirection();
        Vector v1 = vertices.getFirst().subtract(rayHead);
        Vector v2 = vertices.get(1).subtract(rayHead);
        Vector v3 = vertices.get(2).subtract(rayHead);
        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();
        double[] s = {alignZero(n1.dotProduct(rayDir)),
                      alignZero(n2.dotProduct(rayDir)),
                      alignZero(n3.dotProduct(rayDir))};
        //if the dot products aren't all positive or negative the intersected point of the plane isn't in the triangle
        if (s[0] > 0 && s[1] > 0 && s[2] > 0 || s[0] < 0 && s[1] < 0 && s[2] < 0)
            return intersectionPoint;
        return null;
    }
}
