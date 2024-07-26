package geometries;

import primitives.Ray;
import renderer.BVH;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Geometries class represents a collection of geometries.
 * It extends Intersectable and contains a list of Intersectable objects.
 */
public class Geometries extends Intersectable {
    private List<Intersectable> bodies = new LinkedList<>();
    private BVH bvh;

    /**
     * Default constructor for Geometries
     */
    public Geometries() {}

    /**
     * Constructor for Geometries
     * @param geometries a collection of geometries
     */
    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    /**
     * Add geometries to the list of geometries
     * @param geometries a collection of geometries
     */
    public void add(Intersectable... geometries) {
        Collections.addAll(bodies, geometries);
        bvh = null; // Invalidate BVH when new geometries are added
    }

    /**
     * Get the list of geometries
     * @return the list of geometries
     */
    public List<Intersectable> getGeometries() {
        return bodies;
    }

    /**
     * Build the BVH for this Geometries object
     */
    public void buildBVH() {
        if (bvh == null) {
            bvh = new BVH(this);
        }
    }

    /**
     * Find intersections of a ray with the geometries
     * this function will use the BVH, and in case it is null - will fall back to the default implementation
     * @param ray the ray to find intersections with
     * @param maxDistance the maximum distance to check for intersections
     * @return a list of points of intersections
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        if (bvh != null) {
            return bvh.findGeoIntersectionsHelper(ray, maxDistance);
        } else {
            List<GeoPoint> intersections = new LinkedList<>();
            for (Intersectable body : bodies) {
                List<GeoPoint> currentIntersections = body.findGeoIntersections(ray, maxDistance);
                if (currentIntersections != null)
                    intersections.addAll(currentIntersections);
            }
            return intersections.isEmpty() ? null : intersections;
        }
    }

    /**
     * Get the bounding box for this Geometries object
     * @return the bounding box
     */
    @Override
    public BoundingBox getBoundingBox() {
//        if (boundingBox == null) {
//            if (bodies.isEmpty()) {
//                boundingBox = new BoundingBox(new Point(0, 0, 0), new Point(0, 0, 0));
//            } else {
//                BoundingBox bbox = bodies.get(0).getBoundingBox();
//                for (int i = 1; i < bodies.size(); i++) {
//                    bbox = BoundingBox.union(bbox, bodies.get(i).getBoundingBox());
//                }
//                boundingBox = bbox;
//            }
//        }
        return boundingBox;
    }

    /**
     * @param index the index of the geometry in the list
     * @return the bounding box of that geometry
     */
    public BoundingBox getBoundingBoxOfGeometry(int index) {
        return bodies.get(index).getBoundingBox();
    }

    public void setBoundingBox(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }
}