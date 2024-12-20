package geometries;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/*
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 * @author Dan
 */
public class Polygon extends Geometry {
    /**
     * List of polygon's vertices
     */
    protected final List<Point> vertices;
    /**
     * Associated plane in which the polygon lays
     */
    protected final Plane plane;
    /**
     * The size of the polygon - the amount of the vertices in the polygon
     */
    private final int size;

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     *
     * @param vertices list of vertices according to their order by
     *                 edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex)</li>
     *                                  </ul>
     */
    public Polygon(Point... vertices) {
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        this.vertices = List.of(vertices);
        size = vertices.length;

        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        plane = new Plane(vertices[0], vertices[1], vertices[2]);
        // Creating the bounding box
        double minX = Double.POSITIVE_INFINITY, minY = Double.POSITIVE_INFINITY, minZ = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY, maxY = Double.NEGATIVE_INFINITY, maxZ = Double.NEGATIVE_INFINITY;

        for (Point vertex : vertices) {
            minX = Math.min(minX, vertex.getX());
            minY = Math.min(minY, vertex.getY());
            minZ = Math.min(minZ, vertex.getZ());
            maxX = Math.max(maxX, vertex.getX());
            maxY = Math.max(maxY, vertex.getY());
            maxZ = Math.max(maxZ, vertex.getZ());
        }
        boundingBox = new BoundingBox(new Point(minX, minY, minZ), new Point(maxX, maxY, maxZ));
        if (size == 3) return; // no need for more tests for a Triangle

        Vector n = plane.getNormal();
        // Subtracting any subsequent points will throw an IllegalArgumentException
        // because of Zero Vector if they are in the same point
        Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
        Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

        // Cross Product of any subsequent edges will throw an IllegalArgumentException
        // because of Zero Vector if they connect three vertices that lay in the same
        // line.
        // Generate the direction of the polygon according to the angle between last and
        // first edge being less than 180 deg. It is hold by the sign of its dot product
        // with the normal. If all the rest consequent edges will generate the same sign
        // - the polygon is convex ("kamur" in Hebrew).
        boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
        for (var i = 1; i < vertices.length; ++i) {
            // Test that the point is in the same plane as calculated originally
            if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
                throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
            // Test the consequent edges have
            edge1 = edge2;
            edge2 = vertices[i].subtract(vertices[i - 1]);
            if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
                throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
        }
    }

    @Override
    public Vector getNormal(Point point) {
        return plane.getNormal();
    }

    /**
     * findIntersections function finds the intersection points of a ray with the polygon
     * @param ray the ray that intersects the polygon
     * @return a list of the intersection points
     */
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double distance) {
        var intersectionPoint = plane.findGeoIntersections(ray, distance);
        //if the ray doesn't intersect the plane, it doesn't intersect the polygon
        if (intersectionPoint == null)
            return null;
        Point rayHead = ray.getHead();
        Vector rayDir = ray.getDirection();
        Vector[] v = new Vector[size];
        Vector[] n = new Vector[size];
        double[] s = new double[size];
        //entering all the vertices of the polygon into the v array
        for (var i = 0; i < size; ++i) {
            v[i] = vertices.get(i).subtract(rayHead);
        }
        //entering all the normals of the polygon into the n array
        for (var i = 0; i < size; ++i) {
            //System.out.println("v[i] = " + v[i] + "V[i+1] " + v[(i + 1) % size]);
            try {
                n[i] = v[i].crossProduct(v[(i + 1) % size]).normalize();
            } catch (Exception e) {
                return null;
            }
        }
        //entering all the dot products of the normals and the ray direction into the s array
        for (var i = 0; i < size; ++i) {
            s[i] = alignZero(n[i].dotProduct(rayDir));
        }

        //checking if the ray intersects the polygon
        //if the ray intersects the polygon, the dot products of the normals and the ray direction will have the same sign
        if (s[0] < 0) {
            for (var i = 1; i < size; ++i)
                if (s[i] >= 0)
                    return null;
            return List.of(new GeoPoint(this, intersectionPoint.getFirst().point));
        } else if (s[0] > 0) {
            for (var i = 1; i < size; ++i)
                if (s[i] <= 0)
                    return null;
            return List.of(new GeoPoint(this, intersectionPoint.getFirst().point));
        }
        return  null;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return boundingBox;
    }
}
