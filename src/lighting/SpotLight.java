package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * This class will be for instances of a spot light source
 */
public class SpotLight extends PointLight{
    final private Vector direction;
    private double narrowness = 0;

    /**
     * Constructor for the class
     * @param intensity the intensity of the light
     * @param position the position of the light
     * @param direction the direction of the light
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    /** a setter for Kc
     * @param kc the attenuation factor
     * @return the object
     */
    @Override
    public SpotLight setKc(double kc) {
        super.setKc(kc);
        return this;
    }

    /** a setter for Kl
     * @param kl the attenuation factor
     * @return the object
     */
    @Override
    public SpotLight setKl(double kl) {
        super.setKl(kl);
        return this;
    }

    /** a setter for Kq
     * @param kq the attenuation factor
     * @return the object
     */
    public SpotLight setKq(double kq) {
        super.setKq(kq);
        return this;
    }

    /**
     * a setter for the narrowness of the beam
     * @param narrowness the narrowness of the beam
     * @return the object
     */
    public SpotLight setNarrowBeam(double narrowness) {
        this.narrowness = narrowness;
        return this;
    }

    /**
     * this funciton will return the intensity of the light at a point
     * @param p the point to check
     * @return the intensity of the light at the point
     */
    @Override
    public Color getIntensity(Point p) {
        double dot = direction.dotProduct(getL(p));
        if (narrowness <= 1)
            return dot <= 0 ? Color.BLACK : super.getIntensity(p).scale(dot);
        return dot <=0 ? Color.BLACK : super.getIntensity(p).scale(Math.pow(dot, narrowness));
    }
}
