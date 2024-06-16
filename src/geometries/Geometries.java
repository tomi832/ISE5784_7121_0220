package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Geometries extends Intersectable{
    private List<Intersectable> bodies = new LinkedList<>();

    public Geometries() {}

    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    public void add(Intersectable... geometries) {
        Collections.addAll(bodies, geometries);
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double distance) {
        List<GeoPoint> intersections = new LinkedList<>();
        List<GeoPoint> currentIntersections;
        for (Intersectable body : bodies) {
            currentIntersections = body.findGeoIntersections(ray, distance);
            if (currentIntersections != null)
                intersections.addAll(currentIntersections);
        }
        return intersections.isEmpty() ? null : intersections;
    }

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
