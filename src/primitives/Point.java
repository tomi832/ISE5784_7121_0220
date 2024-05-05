package primitives;

/**
 * @author Tomere Kalman and Yosef Kornfeld
 * This class will be for instances of a point in space
 */
public class Point {
    /** Represents a constant Point object initialized at the origin (0, 0, 0) */
    public static final Point ZERO = new Point(0, 0, 0);
    /** an instance of the double3 class which holds the coordinates of the point */
    final Double3 xyz;

    /**
     * Constructor for the class
     * @param x First coordinate
     * @param y Second coordinate
     * @param z Third coordinate
     */
    public Point(double x, double y, double z) {
        xyz = new Double3(x,y,z);
    }

    /**
     * Constructor that gets a Double3 instance, which holds the 3 coordinates
     * @param xyz a Double3 instance
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point point)) return false;

        return xyz.equals(point.xyz);
    }

    @Override
    public int hashCode() {
        return xyz.hashCode();
    }

    @Override
    public String toString() {
        return "Point{" + xyz + '}';
    }

    /**
     * This function calculates the distance of this point with the point entered, but squared
     * @param point is another point to check with
     * @return the squared distance between the points
     */
    public double distanceSquared(Point point) {
        double dx = xyz.d1 - point.xyz.d1;
        double dy = xyz.d2 - point.xyz.d2;
        double dz = xyz.d3 - point.xyz.d3;

        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * this function uses the distanceSquared function to calculate the distance
     * @param point another point to check with
     * @return the distance between the two points
     */
    public double distance(Point point) {
        return Math.sqrt(distanceSquared(point));
    }

    /**
     * this function adds a vector to the point and returns the new point
     * @param vector a vector to add to the point
     * @return the new point after the vector was added
     */
    public Point add(Vector vector) {
        return new Point(xyz.add(vector.xyz));
    }

    /**
     * this function subtracts another point from this point and returns a new vector
     * @param point another point to subtract the current point with
     * @return the new vector that was created
     */
    public Vector subtract(Point point) {
        return new Vector(xyz.subtract(point.xyz));
    }
}
