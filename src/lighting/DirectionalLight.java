package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource {
    final private Vector direction;

    /**
     * a constructor for DirectionalLight
     * @param intensity the color of the light
     * @param direction the direction of the light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    /**
     * this function will return the intensity of the light at a given point
     * @param p the point to get the intensity at
     * @return the intensity of the light at the point
     */
    @Override
    public Color getIntensity(Point p) {
        return intensity;
    }

    /**
     * it will return the vector from the light source to the point, here it's just the direction of the light
     * @param p the point to get the vector to
     * @return the direction of the light
     */
    @Override
    public Vector getL(Point p) {
        return direction;
    }

    /**
     * it will return the distance from the light source to the point, here it's infinity
     * @param point the point to get the distance to
     * @return infinity
     */
    @Override
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }
}
