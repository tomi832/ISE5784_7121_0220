package renderer;

import primitives.Point;
import primitives.Ray;
import geometries.*;

import java.util.LinkedList;
import java.util.List;

/**
 * BVH class represents a bounding volume hierarchy for a collection of geometries.
 */
public class BVH extends Intersectable {
    private final Geometries root;

    public BVH(Geometries geometries) {
        this.root = geometries;
        buildBVH(root);
    }

    private void buildBVH(Geometries geometries) {
        if (geometries == null || geometries.getGeometries().isEmpty()) {
            return;
        }

        for (Intersectable geo : geometries.getGeometries()) {
            if (geo instanceof Geometries) {
                buildBVH((Geometries) geo);
            }
        }

        BoundingBox bbox = computeBoundingBox(geometries);
        geometries.setBoundingBox(bbox);
    }

    /**
     * @param geometries the collection of geometries
     * @return the bounding box of the geometries
     */
    private BoundingBox computeBoundingBox(Geometries geometries) {
        if (geometries.getGeometries().isEmpty()) {
            return new BoundingBox(new Point(0, 0, 0), new Point(0, 0, 0));
        }
        //BoundingBox bbox = geometries.getGeometries().get(0).getBoundingBox();
        BoundingBox bbox = geometries.getBoundingBoxOfGeometry(0);
        BoundingBox temp;
        for (int i = 1; i < geometries.getGeometries().size(); i++) {
            temp = geometries.getBoundingBoxOfGeometry(i);
            if (temp != null)
                bbox = BoundingBox.union(bbox, temp);
        }
        return bbox;
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        return findIntersections(root, ray, maxDistance);
    }

    private static List<GeoPoint> findIntersections(Geometries geometries, Ray ray, double maxDistance) {
        if (geometries.getBoundingBox() == null || !geometries.getBoundingBox().intersects(ray, maxDistance)) {
            return null;
        }

        List<GeoPoint> intersections = new LinkedList<>();

        for (Intersectable geo : geometries.getGeometries()) {
            if (geo instanceof Geometries) {
                List<GeoPoint> nestedIntersections = findIntersections((Geometries) geo, ray, maxDistance);
                if (nestedIntersections != null) {
                    intersections.addAll(nestedIntersections);
                }
            } else {
                if (geo.getBoundingBox() != null && !geo.getBoundingBox().intersects(ray, maxDistance))
                    continue;

                List<GeoPoint> geoIntersections = geo.findGeoIntersections(ray, maxDistance);
                if (geoIntersections != null) {
                    intersections.addAll(geoIntersections);
                }
            }
        }

        return intersections.isEmpty() ? null : intersections;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return root.getBoundingBox();
    }
}