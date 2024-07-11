package primitives;

/**
 * Material class represents the material of a geometry
 */
public class Material {
    /** The diffuse coefficient */
    public Double3 kD = Double3.ZERO;
    /** The specular coefficient */
    public Double3 kS = Double3.ZERO;
    /** The transparency coefficient */
    public Double3 kT = Double3.ZERO;
    /** The reflection coefficient */
    public Double3 kR = Double3.ZERO;
    /** The shininess coefficient  */
    public int nShininess = 0;
    /** The target area distance from point for the blur */
    public double blurDistance = 0;
    /** The radius of the target area for the blur */
    public double blurRadius = 0;
    /** The number of rays for the blur on the edge of the square */
    public double numEdgeRays = 0;


    /**
     * Material setter for diffuse coefficient, using a Double3 object
     * @param kD the diffuse coefficient (can be Double3 or double)
     * @return the material
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Material setter for diffuse coefficient, using a double
     * @param kd the diffuse coefficient (can be Double3 or double)
     * @return the material
     */
    public Material setKd(double kd) {
        this.kD = new Double3(kd, kd, kd);
        return this;
    }

    /**
     * Material setter for specular coefficient, using a Double3 object
     * @param ks the specular coefficient (can be Double3 or double)
     * @return the material
     */
    public Material setKs(Double3 ks) {
        this.kS = ks;
        return this;
    }

    /**
     * Material setter for specular coefficient, using a double
     * @param ks the specular coefficient (can be Double3 or double)
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

    /**
     * Material setter for transparency coefficient
     * @param kT the transparency coefficient (can be Double3 or double)
     * @return the material
     */
    public Material setKt(Double3 kT) {
        this.kT = kT;
        return this;
    }

    /**
     * Material setter for reflection coefficient
     * @param kR the reflection coefficient (can be Double3 or double)
     * @return the material
     */
    public Material setKr(Double3 kR) {
        this.kR = kR;
        return this;
    }

    /**
     * Material setter for transparency coefficient
     * @param kT the transparency coefficient (can be Double3 or double)
     * @return the material
     */
    public Material setKt(double kT) {
        this.kT = new Double3(kT, kT, kT);
        return this;
    }

    /**
     * Material setter for reflection coefficient
     * @param kR the reflection coefficient (can be Double3 or double)
     * @return the material
     */
    public Material setKr(double kR) {
        this.kR = new Double3(kR, kR, kR);
        return this;
    }

    /**
     * Material setter for the distance of the target area from the point
     * it's for the blurriness - the further it is, the narrower the beam of rays and the less blur
     * @param blurDistance the distance of the target area from the point
     * @return the material
     */
    public Material setBlurDistance(double blurDistance) {
        this.blurDistance = blurDistance;
        return this;
    }

    /**
     * Material setter for the radius of the circle target area
     * it's for the blurriness - the bigger it is, the more blur
     * @param blurRadius the radius of the target area
     * @return the material
     */
    public Material setBlurRadius(double blurRadius) {
        this.blurRadius = blurRadius;
        return this;
    }

    /**
     * Material setter for the number of rays on the edge of the square
     * the target area is the circle that is from edge to edge inside the square
     * it doesn't mean how many inside the target area, but in the rectangle that has the target area as a circle
     * it's for the blurriness - the more rays, the more blur //TODO: correct this
     * @param numEdgeRays the number of rays on the edge of the square
     * @return the material
     */
    public Material setNumEdgeRays(double numEdgeRays) {
        this.numEdgeRays = numEdgeRays;
        return this;
    }
}
