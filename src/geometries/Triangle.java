package geometries;

import primitives.Point;
import primitives.Ray;

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
        return null;
    }
}
