package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight{
    final private Vector direction;
    private double narrowness = 0;

    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    @Override
    public SpotLight setKc(double kc) {
        super.setKc(kc);
        return this;
    }
    @Override
    public SpotLight setKl(double kl) {
        super.setKl(kl);
        return this;
    }
    public SpotLight setKq(double kq) {
        super.setKq(kq);
        return this;
    }

    public SpotLight setNarrowBeam(double narrowness) {
        this.narrowness = narrowness;
        return this;
    }

    @Override
    public Color getIntensity(Point p) {
        double dot = direction.dotProduct(getL(p));
        if (narrowness <= 1)
            return dot <= 0 ? Color.BLACK : super.getIntensity(p).scale(dot);
        return dot <=0 ? Color.BLACK : super.getIntensity(p).scale(Math.pow(dot, narrowness));
    }
}
