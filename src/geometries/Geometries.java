package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Geometries class represents a collection of geometries.
 * It is an abstract class that extends Intersectable.
 * It contains a list of Intersectable objects.
 */
public class Geometries extends Intersectable{
    private List<Intersectable> bodies = new LinkedList<>();

    /**
     * Default constructor for Geometries
     */
    public Geometries() {}

    /**
     * Constructor for Geometries
     * @param geometries a collection of geometries
     */
    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    /**
     * Add geometries to the list of geometries
     * @param geometries a collection of geometries
     */
    public void add(Intersectable... geometries) {
        Collections.addAll(bodies, geometries);
    }

    /**
     * Find intersections of a ray with the geometries, it's for backwards compatibility
     * @param ray the ray to find intersections with
     * @return a list of points of intersections
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double distance) {
        List<GeoPoint> intersections = new LinkedList<>();
        List<GeoPoint> currentIntersections;
        for (Intersectable body : bodies) {
            currentIntersections = body.findGeoIntersections(ray, distance);
            if (currentIntersections != null)
                intersections.addAll(currentIntersections);
        }
        return intersections.isEmpty() ? null : intersections;
    }

    /**
     * Adds the intersections of a ray with the geometries to a list of points
     * @param intersections the list of points to add the intersections to
     * @param currentIntersections the list of points of the current intersections
     * @return the list of points of intersections
     */
    private List<Point> addIntersection(List<Point> intersections, List<Point> currentIntersections) {
        if (currentIntersections != null) {
            if (intersections == null)
                intersections = new LinkedList<>(currentIntersections);
            else
                intersections.addAll(currentIntersections);
        }
        return intersections;
    }
}
