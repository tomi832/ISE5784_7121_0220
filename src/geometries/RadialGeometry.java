package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * a class that implements the Geometry interface. it represents a radial shape in a
 * 3-dimensional space.
 */
public abstract class RadialGeometry extends Geometry{
    /**
     * represents the radius of any radial shape (e.g: the radius of a cylinder)
     */
    protected final double radius;

    /**
     * Constructor for RadialGeometry
     * @param radius the radius of the shape
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }

    /**
     * a non-functional method, since this isn't a real geometric body
     * this function is here due to this class inheriting from Geometry
     * @param point a point on the surface of the geometry
     * @return the normal of the geometry
     */
    @Override
    public Vector getNormal(Point point) {
        return null;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return null;
    }
}
