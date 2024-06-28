package lighting;

import primitives.Color;

/**
 * This class will be for instances of a light source
 */
abstract class Light {
    protected Color intensity;

    /**
     * Constructor for the class
     * @param intensity the intensity of the light
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * a getter for the intensity of the light
     * @return the intensity of the light
     */
    public Color getIntensity() {
        return intensity;
    }

    /**
     * a setter for the intensity of the light
     * @param intensity the intensity of the light
     */
    public void setIntensity(Color intensity) {
        this.intensity = intensity;
    }

}
