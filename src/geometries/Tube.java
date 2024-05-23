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
        Vector v = point.subtract(axis.getHead());
        double t = axis.getDirection().dotProduct(v);
        //checking if it's orthogonal to the head
        if (t == 0)
            return point.subtract(axis.getHead()).normalize();
        Point o = axis.getHead().add(axis.getDirection().scale(t));
        return point.subtract(o).normalize();
    }
}
