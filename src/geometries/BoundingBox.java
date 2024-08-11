package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;
import static primitives.Util.alignZero;

/**
 * a class that represents a bounding box for rendering optimization
 */
public class BoundingBox {
    /**
     * the two points represent two opposing corners of the box,
     * one with the lowest coordinates and the other with the highest
     */
    private final Point minBoxPoint;
    private final Point maxBoxPoint;

    /**
     * a constructor for a BoundingBox for the geometry
     * @param minBoxPoint the point with the lowest coordinates
     * @param maxBoxPoint the point with the highest coordinates
     */
    public BoundingBox(Point minBoxPoint, Point maxBoxPoint) {
        this.minBoxPoint = minBoxPoint;
        this.maxBoxPoint = maxBoxPoint;
    }

    /**
     * @return the center of the box
     */
    public Point getCenter() {
        return new Point(
            (minBoxPoint.getX() + maxBoxPoint.getX()) / 2,
            (minBoxPoint.getY() + maxBoxPoint.getY()) / 2,
            (minBoxPoint.getZ() + maxBoxPoint.getZ()) / 2
        );
    }

    /**
     * a method that checks if a ray intersects the box
     * @param ray the ray in question
     * @param maxDistance the maximum distance the ray can travel
     * @return true if the ray intersects the box, false otherwise
     */
    public boolean intersects(Ray ray, double maxDistance) {
        final Vector direction = ray.getDirection();
        final Point origin = ray.getHead();
        //the arrays represent coordinates like so: [0] = x, [1] = y, [2] = z
        final double[] directionValues = {direction.getX(), direction.getY(), direction.getZ()};
        final double[] originValues = {origin.getX(), origin.getY(), origin.getZ()};
        final double[] minValues = {minBoxPoint.getX(), minBoxPoint.getY(), minBoxPoint.getZ()};
        final double[] maxValues = {maxBoxPoint.getX(), maxBoxPoint.getY(), maxBoxPoint.getZ()};

        double tmin = Double.NEGATIVE_INFINITY;
        double tmax = Double.POSITIVE_INFINITY;


        for (int i = 0; i < 3; i++) {
            if (isZero(directionValues[i])) {
                if (originValues[i] < minValues[i] || originValues[i] > maxValues[i])
                    return false;
            } else {
                double t1 = alignZero((minValues[i] - originValues[i]) / directionValues[i]);
                double t2 = alignZero((maxValues[i] - originValues[i]) / directionValues[i]);
                if (t1 > t2) {
                    double temp = t1;
                    t1 = t2;
                    t2 = temp;
                }
                tmin = Math.max(tmin, t1);
                tmax = Math.min(tmax, t2);
                if (tmin > tmax) return false;
            }
        }
        return tmin < maxDistance && tmax > 0;
    }

    /**
     * a method that creates a bounding box that is the union of two bounding boxes.
     * parametes are two bounding boxes
     * @return a new bounding box that is the union of the two
     */
    public static BoundingBox union(BoundingBox a, BoundingBox b) {
        Point newMin = new Point(
            Math.min(a.minBoxPoint.getX(), b.minBoxPoint.getX()),
            Math.min(a.minBoxPoint.getY(), b.minBoxPoint.getY()),
            Math.min(a.minBoxPoint.getZ(), b.minBoxPoint.getZ())
        );
        Point newMax = new Point(
            Math.max(a.maxBoxPoint.getX(), b.maxBoxPoint.getX()),
            Math.max(a.maxBoxPoint.getY(), b.maxBoxPoint.getY()),
            Math.max(a.maxBoxPoint.getZ(), b.maxBoxPoint.getZ())
        );
        return new BoundingBox(newMin, newMax);
    }
}