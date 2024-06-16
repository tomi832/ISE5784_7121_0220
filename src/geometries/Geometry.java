package geometries;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public abstract class Geometry extends Intersectable {
    protected Color emission = Color.BLACK;
    /**
     * this function receives a point on the surface of the geometry
     * and returns a vector perpendicular to the surface at that point
     * @param point a point on the surface of the geometry
     * @return a vector perpendicular to the surface at that point
     */
    public abstract Vector getNormal(Point point);

    /**
     * this function returns the emission color of the geometry
     * @return the emission color of the geometry
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * this function sets the emission color of the geometry.
     * the function is built in the style of a builder, returning the geometry.
     * @param emission the emission color of the geometry
     * @return the geometry with the new emission color
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }
}
