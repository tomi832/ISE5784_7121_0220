package primitives;

import static primitives.Util.alignZero;

/**
 * @author Tomere Kalman and Yosef Kornfeld
 * This class will create instances of vectors in space using the Point class
 */
public class Vector extends Point{

    /**
     * Constructor for the class
     * @param x First coordinate
     * @param y Second coordinate
     * @param z Third coordinate
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("Vector(0,0,0) isn't valid");
    }

    /**
     * Constructor that gets a Double3 instance, which holds the 3 coordinates
     * @param xyz a Double3 instance
     */
    public Vector(Double3 xyz) {
        super(xyz);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("Vector(0,0,0) isn't valid");
    }

    @Override
    public String toString() {
        return "Vector{" + xyz + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector vector)) return false;

        return xyz.equals(vector.xyz);
    }

    /**
     * Adds a Vector to this vector
     * @param vector a vector to add to the point
     * @return the new vector created
     */
    public Vector add(Vector vector) {
        return new Vector(xyz.add(vector.xyz));
    }

    /**
     * This function scales up or down the vector
     * @param scalar is the number of times the vector would be scaled as
     * @return
     */
    public Vector scale(double scalar) {
        return new Vector(xyz.scale(scalar));
    }

    /**
     * This function calculates the length of the vector but squared
     * @return the squared length of the vector
     */
    public double lengthSquared() {
        return xyz.d1 * xyz.d1 + xyz.d2 * xyz.d2 + xyz.d3 * xyz.d3;
    }

    /**
     * This function uses the lengthSquared function to calculate the length of the vector
     * @return the length of the vector
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * The function normalizes the vector, which means turning its length to be equal to 1
     * @return the normalized version of the vector
     */
    public Vector normalize() {
        double length = alignZero(length());
        if (length == 0)
            throw new ArithmeticException("Cannot normalize an empty vector");
        return new Vector(xyz.scale(1/length));
    }


    public Vector crossProduct(Vector vector) {
        return new Vector(
                xyz.d2 * vector.xyz.d3 - xyz.d3 * vector.xyz.d2,
                xyz.d3 * vector.xyz.d1 - xyz.d1 * vector.xyz.d3,
                xyz.d1 * vector.xyz.d2 - xyz.d2 * vector.xyz.d1
        );
    }

    public double dotProduct(Vector vector) {
        return xyz.d1 * vector.xyz.d1 + xyz.d2 * vector.xyz.d2 + xyz.d3 * vector.xyz.d3;
    }
}
