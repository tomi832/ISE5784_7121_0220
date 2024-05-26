package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * an interface that represents an intersectable geometry
 */
public interface Intersectable {
    /**
     * a method that finds the intersection points of a ray with the geometry
     * @param ray a ray that intersects with the geometry
     * @return a list of intersection points
     */
    public List<Point> findIntersections(Ray ray);
}
