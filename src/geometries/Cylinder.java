package geometries;
//
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
