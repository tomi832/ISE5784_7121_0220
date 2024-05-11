package geometries;
//
import primitives.Point;
import primitives.Vector;

/**
 * a class that implements the Geometry interface. it represents a radial shape in a
 * 3-dimensional space.
 */
public class RadialGeometry implements Geometry{
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

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
