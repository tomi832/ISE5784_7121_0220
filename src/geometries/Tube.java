package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

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

    /**
     * a method that returns the normal of the tube at a certain point
     * @param point a point on the surface of the tube
     * @return the normal of the at the passed point
     */
    @Override
    public Vector getNormal(Point point) {
        Vector v = point.subtract(axis.getHead());
        double t = axis.getDirection().dotProduct(v);
        //checking if it's orthogonal to the head
        if (isZero(t))
            return point.subtract(axis.getHead()).normalize();
        Point o = axis.getPoint(t);
        return point.subtract(o).normalize();
    }

    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
