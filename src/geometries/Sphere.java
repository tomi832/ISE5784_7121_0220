package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere extends RadialGeometry{
    private final Point center;

    public Sphere(Point c, double r) {
        super(r);
        center = c;
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
