package geometries;

import primitives.Point;
import primitives.Ray;
import static primitives.Util.isZero;
import primitives.Vector;

import java.util.List;

/**
 * a class representing a cylinder in a 3-dimensional space
 */
public class Cylinder extends Tube{
    private final double height;

    /**
     * a constructor for Cylinder
     * @param radius the radius of the cylinder
     * @param axis the direction of the cylinder
     * @param height the length of the cylinder
     */
    public Cylinder(double radius, Ray axis, double height) {
        super(radius, axis);
        this.height = height;
    }

    /**
     * a method that returns the normal of the cylinder at a certain point
     * @param point a point on the surface of the Cylinder
     * @return the normal of the cylinder
     */
    @Override
    public Vector getNormal(Point point) {
        //calculating where the head would "hit" at the other side of the cylinder
        Point otherBaseHead = axis.getHead().add(axis.getDirection().scale(height));
        if (point.equals(axis.getHead()) ||
            point.equals(otherBaseHead) ||
            isZero(point.subtract(axis.getHead()).dotProduct(axis.getDirection())) ||
            isZero(point.subtract(otherBaseHead).dotProduct(axis.getDirection())))
            return axis.getDirection();
        return super.getNormal(point);
    }

    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}