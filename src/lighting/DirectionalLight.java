package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource {
    final private Vector direction;

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


}
