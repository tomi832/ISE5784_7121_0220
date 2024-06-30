package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * This class will be for instances of a point light source
 */
public class PointLight extends Light implements LightSource {
    final private Point position;
    private double Kc = 1, Kl = 0, Kq = 0;

    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * this function will return the intensity of the light at a given point
     * @param p the point to get the intensity at
     * @return the intensity of the light at the point
     */
    @Override
    public Color getIntensity(Point p) {
        double distance = position.distance(p);
        return intensity.reduce((Kc + Kl * distance + Kq * distance * distance));
    }

    /**
     * it will return the vector from the light source to the point
     * @param p the point to get the vector to
     * @return the vector from the light source to the point
     */
    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }

    /** a setter for Kc
     * @param kc the attenuation factor
     * @return the object
     */
    public PointLight setKc(double kc) {
        this.Kc = kc;
        return this;
    }

    /** a setter for Kl
     * @param kl the attenuation factor
     * @return the object
     */
    public PointLight setKl(double kl) {
        this.Kl = kl;
        return this;
    }

    /** a setter for Kq
     * @param kq the attenuation factor
     * @return the object
     */
    public PointLight setKq(double kq) {
        this.Kq = kq;
        return this;
    }

    /**
     * it will return the distance from the light source to the point
     * @param point the point to get the distance to
     * @return the distance from the light source to the point
     */
    @Override
    public double getDistance(Point point) {
        return position.distance(point);
    }
}
