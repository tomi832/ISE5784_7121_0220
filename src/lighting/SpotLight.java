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
    public PointLight setKc(double kc) {
        super.setKc(kc);
        return (SpotLight) this;
    }
    @Override
    public PointLight setKl(double kl) {
        super.setKc(kl);
        return (SpotLight) this;
    }
    public PointLight setKq(double kq) {
        super.setKc(kq);
        return (SpotLight) this;
    }

}
