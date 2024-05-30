package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable{
    private List<Intersectable> bodies = new LinkedList<>();

    public Geometries() {}

    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    public void add(Intersectable... geometries) {
        Collections.addAll(bodies, geometries);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersections = null;
        List<Point> currentIntersections;
        for (Intersectable body : bodies) {
            currentIntersections = body.findIntersections(ray);
            intersections = addIntersection(intersections, currentIntersections);
        }
        return intersections;
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
