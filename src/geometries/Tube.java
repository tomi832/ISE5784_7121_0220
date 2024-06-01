package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
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

    //work in progress, doesn't fully work yet
    public List<Point> findIntersections(Ray ray) {
        Vector va = axis.getDirection();
        Vector v = ray.getDirection();
        Point p0 = ray.getHead();
        Point pa = axis.getHead();
        boolean changed = false;

        Vector deltaP = p0.subtract(pa);


        // Calculate the components of the quadratic equation
        Vector vMinusVaScaleVDotVa = v;
        if (!isZero(v.dotProduct(va))) {
            vMinusVaScaleVDotVa = v.subtract(va.scale(v.dotProduct(va)));
        }

        Vector deltaPMinusVaScaleDeltaPDotVa = deltaP;
        if (!isZero(deltaP.dotProduct(va))) {
            Vector temp = va.scale(deltaP.dotProduct(va));
            if (deltaP.equals(temp)) {
                deltaP = changeP0(ray);
                temp = va.scale(deltaP.dotProduct(va));
                changed = true;
            }
            deltaPMinusVaScaleDeltaPDotVa = deltaP.subtract(temp);
        }

        // Calculate the coefficients of the quadratic equation
        double a = vMinusVaScaleVDotVa.lengthSquared();
        double b = 2 * vMinusVaScaleVDotVa.dotProduct(deltaPMinusVaScaleDeltaPDotVa);
        double c = deltaPMinusVaScaleDeltaPDotVa.lengthSquared() - radius * radius;

        double discriminant = b * b - 4 * a * c;

        if (discriminant < 0) {
            // No intersections
            return null;
        }

        if (isZero(discriminant)) {
            // One intersection
            double t = -b / (2 * a);
            if (t > 0) {
                return List.of(ray.getPoint(t));
            } else if(changed) {
                return List.of(ray.getPoint(-t));
            }
            return null;
        }

        double t1 = (-b + Math.sqrt(discriminant)) / (2 * a);
        double t2 = (-b - Math.sqrt(discriminant)) / (2 * a);

        List<Point> intersections = new LinkedList<>();

        if (t1 > 0) {
            Point p = ray.getPoint(t1);
            intersections.add(p);
        }
        if (t2 > 0 && !changed) {
            intersections.add(ray.getPoint(t2));
        }

        return intersections.isEmpty() ? null : intersections;
    }

    private Vector changeP0(Ray ray) {
        Point p = ray.getPoint(radius / 2);
        return p.subtract(axis.getHead());
    }
}
