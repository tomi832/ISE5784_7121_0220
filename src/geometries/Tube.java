package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * a class that represents a Tube in a 3-dimensional space
 */
public class Tube extends RadialGeometry{
    /**
     * axis is a ray representing the direction of the tube
     */
    protected final Ray axis;

    /**
     * a constructor for the class Tube
     * @param radius the distance from any point in the tube to the axis ray
     * @param axis the direction of the tube
     */
    public Tube(double radius, Ray axis) {
        super(radius);
        this.axis = axis;
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
