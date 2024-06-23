package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource {
    Point position;
    double Kc = 1, Kl = 0, Kq = 0;

    protected PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }


    @Override
    public Color getIntensity(Point p) {
        return null;
    }

    @Override
    public Vector getL(Point p) {
        return null;
    }

    public PointLight setKc(double kc) {
        this.Kc = kc;
        return this;
    }
    public PointLight setKl(double kl) {
        this.Kl = kl;
        return this;
    }
    public PointLight setKq(double kq) {
        this.Kq = kq;
        return this;
    }

}
