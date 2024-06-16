package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * This class will be for instances of an ambient light source
 */
public class AmbientLight extends Light {
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, 0);

    /**
     * Constructor for the class (Ka type == double)
     * @param Ia the intensity of the light
     * @param Ka the attenuation factor
     */
    public AmbientLight(Color Ia, Double3 Ka) {super(Ia.scale(Ka)); }

    /**
     * Constructor for the class (Ka type == double)
     * @param Ia the intensity of the light
     * @param Ka the attenuation factor
     */
    public AmbientLight(Color Ia, double Ka) {super(Ia.scale(Ka)); }
}
