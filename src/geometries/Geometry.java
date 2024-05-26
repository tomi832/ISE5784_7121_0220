package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public interface Geometry {
    /**
     * this function receives a point on the surface of the geometry
     * and returns a vector perpendicular to the surface at that point
     * @param point a point on the surface of the geometry
     * @return a vector perpendicular to the surface at that point
     */
    public Vector getNormal(Point point);
}
