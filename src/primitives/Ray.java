package primitives;

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
}
