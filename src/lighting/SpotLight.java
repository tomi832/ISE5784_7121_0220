package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight{
    final private Vector direction;

    protected SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction;
    }

    @Override
    public SpotLight setKc(double kc) {
        super.setKc(kc);
        return this;
    }
    @Override
    public SpotLight setKl(double kl) {
        super.setKc(kl);
        return this;
    }
    public SpotLight setKq(double kq) {
        super.setKc(kq);
        return this;
    }

    @Override
    public Color getIntensity(Point p) {
        double dot = direction.dotProduct(getL(p));
        return dot <=0 ? Color.BLACK : super.getIntensity(p).scale(dot);
    }

}
