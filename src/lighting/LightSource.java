package lighting;

import primitives.Color;
import primitives.Vector;
import primitives.Point;

/**
 * an interface that represents a light source
 */
public interface LightSource {

    /**
     * a method that returns the intensity of the light source at a certain point
     * @param p the point to get the intensity at
     * @return the intensity of the light source at the point
     */
    public Color getIntensity(Point p);
    /**
     * a method that returns the vector from the light source to a certain point
     * @param p the point to get the vector to
     * @return the vector from the light source to the point
     */
    public Vector getL(Point p);
}
