package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;
import static primitives.Util.alignZero;

public class BoundingBox {
    private Point min;
    private Point max;

    public BoundingBox(Point min, Point max) {
        this.min = min;
        this.max = max;
    }

    public Point getCenter() {
        return new Point(
            (min.getX() + max.getX()) / 2,
            (min.getY() + max.getY()) / 2,
            (min.getZ() + max.getZ()) / 2
        );
    }

    public boolean intersects(Ray ray, double maxDistance) {
        Vector dir = ray.getDirection();
        Point origin = ray.getHead();
        double[] dirValues = {dir.getX(), dir.getY(), dir.getZ()};
        double[] originValues = {origin.getX(), origin.getY(), origin.getZ()};
        double[] minValues = {min.getX(), min.getY(), min.getZ()};
        double[] maxValues = {max.getX(), max.getY(), max.getZ()};

        double tmin = Double.NEGATIVE_INFINITY;
        double tmax = Double.POSITIVE_INFINITY;


        for (int i = 0; i < 3; i++) {
            if (isZero(dirValues[i])) {
                if (originValues[i] < minValues[i] || originValues[i] > maxValues[i])
                    return false;
            } else {
                double t1 = alignZero((minValues[i] - originValues[i]) / dirValues[i]);
                double t2 = alignZero((maxValues[i] - originValues[i]) / dirValues[i]);
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

    public static BoundingBox union(BoundingBox a, BoundingBox b) {
        Point newMin = new Point(
            Math.min(a.min.getX(), b.min.getX()),
            Math.min(a.min.getY(), b.min.getY()),
            Math.min(a.min.getZ(), b.min.getZ())
        );
        Point newMax = new Point(
            Math.max(a.max.getX(), b.max.getX()),
            Math.max(a.max.getY(), b.max.getY()),
            Math.max(a.max.getZ(), b.max.getZ())
        );
        return new BoundingBox(newMin, newMax);
    }
}