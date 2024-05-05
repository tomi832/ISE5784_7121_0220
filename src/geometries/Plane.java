package geometries;

import primitives.Vector;
import primitives.Point;

public class Plane implements Geometry{
    private final Point q;
    private final Vector normal;

    public Plane(Point x, Point y, Point z) {
        this.q = x;
        this.normal = null;
    }

    public Plane(Point q, Vector normal) {
        this.q = q;
        this.normal = normal.normalize();
    }

    @Override
    public Vector getNormal(Point point) {
        return normal;
    }
}
