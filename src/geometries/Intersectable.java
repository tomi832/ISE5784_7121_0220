package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * an interface that represents an intersectable geometry
 */
public abstract class Intersectable {
    public static class GeoPoint {
        /** the point of intersection */
        public final Point point;
        /** the geometry that the point intersects with */
        public final Geometry geometry;

        /**
         * a constructor for GeoPoint
         * @param geometry the geometry that the point intersects with
         * @param point the point of intersection
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.point = point;
            this.geometry = geometry;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GeoPoint geoPoint)) return false;
            return geometry == geoPoint.geometry && point.equals(geoPoint.point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "point=" + point +
                    ", geometry=" + geometry +
                    '}';
        }
    }
    /**
     * a method that finds the intersection points of a ray with the geometry
     * @param ray a ray that intersects with the geometry
     * @return a list of intersection points
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList
                .stream()
                .map(gp -> gp.point)
                .toList();
    }

    /**
     * a method that finds the intersection points of a ray with the geometry
     * @param ray a ray that intersects with the geometry
     * @return a list of intersection points
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * a method that finds the intersection points of a ray with the geometry
     * @param ray a ray that intersects with the geometry
     * @param distance the maximum distance to find intersections
     * @return a list of intersection points
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray, double distance) {
        return findGeoIntersectionsHelper(ray, distance);
    }

    /**
     * a method that finds the intersection points of a ray with the geometry
     * @param ray a ray that intersects with the geometry
     * @param distance the maximum distance to find intersections
     * @return a list of intersection points
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double distance);

    /**
     * a method that returns the bounding box of the geometry
     * @return the bounding box of the geometry
     */
    public abstract BoundingBox getBoundingBox();
}
