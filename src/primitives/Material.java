package primitives;

/**
 * Material class represents the material of a geometry
 */
public class Material {
    public Double3 kD = Double3.ZERO;
    public Double3 kS = Double3.ZERO;
    public int nShininess = 0;

    /**
     * Material setter for diffuse coefficient, using a Double3 object
     * @param kD the diffuse coefficient
     * @return the material
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Material setter for diffuse coefficient, using a double
     * @param kd the diffuse coefficient
     * @return the material
     */
    public Material setKd(double kd) {
        this.kD = new Double3(kd, kd, kd);
        return this;
    }

    /**
     * Material setter for specular coefficient, using a Double3 object
     * @param ks the specular coefficient
     * @return the material
     */
    public Material setKs(Double3 ks) {
        this.kS = ks;
        return this;
    }

    /**
     * Material setter for specular coefficient, using a double
     * @param ks the specular coefficient
     * @return the material
     */
    public Material setKs(double ks) {
        this.kS = new Double3(ks, ks, ks);
        return this;
    }

    /**
     * Material setter for shininess
     * @param n the shininess coefficient
     * @return the material
     */
    public Material setShininess(int n) {
        this.nShininess = n;
        return this;
    }
}
