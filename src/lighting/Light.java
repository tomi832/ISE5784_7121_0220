package lighting;

import primitives.Color;

/**
 * This class will be for instances of a light source
 */
abstract class Light {
    protected Color intensity;

    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    public Color getIntensity() {
        return intensity;
    }

    public void setIntensity(Color intensity) {
        this.intensity = intensity;
    }

}
