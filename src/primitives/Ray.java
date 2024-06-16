package primitives;

import geometries.Intersectable.GeoPoint;
import java.util.List;
import static primitives.Util.isZero;

/**
 * Class Ray is the basic class representing a ray of Euclidean geometry in Cartesian
 * * 3-Dimensional coordinate system.
 * @author Yosef Kornfeld and Tomere Kalman
 */
public class Ray {
    private final Point head;
    private final Vector direction;

    /**
     * constructor for Ray
     * @param head a point representing the start of the ray
     * @param direction a vector representing the direction of the ray
     */
    public Ray(Point head, Vector direction) {
        this.head = head;
        this.direction = direction.normalize();
    }

    public Point getHead() {
        return head;
    }

    public Vector getDirection() {
         return direction;
    }


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray ray)) return false;

        return head.equals(ray.head) && direction.equals(ray.direction);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "head=" + head +
                ", direction=" + direction +
                '}';
    }

    /**
     * Returns a point on the ray at a distance of t1 from the head of the ray
     * @param t1 the distance from the head of the ray
     * @return a point on the ray at a distance of t1 from the head of the ray
     */
    public Point getPoint(double t1) {
        if (isZero(t1))
            throw new IllegalArgumentException("t1 cannot be 0");
        return head.add(direction.scale(t1));
    }

    /**
     * Finds the closest point to the head of the ray from a list of geoPoints
     * @param geoPoints a list of geoPoints
     * @return the closest point to the head of the ray from the list of geoPoints
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPoints) {
        if (geoPoints.isEmpty())
            return null;
        GeoPoint closest = null;
        double distance = Double.POSITIVE_INFINITY;
        for (GeoPoint geoPoint : geoPoints) {
            double d = head.distanceSquared(geoPoint.point);
            if (d < distance) {
                distance = d;
                closest = geoPoint;
            }
        }
        return closest;
    }

    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream()
                .map(p -> new GeoPoint(null, p))
                .toList()).point;
    }
}
