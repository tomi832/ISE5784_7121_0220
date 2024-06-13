package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * Abstract class for ray tracing
 * @author Yosef Kornfeld and Tomere Kalman
 */
public abstract class RayTracerBase {

    protected Scene scene;

    /**
     * Constructor for RayTracerBase
     * @param scene the scene to be traced
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Traces a ray and returns the color of the intersection point
     * @param ray the ray to be traced
     * @return the color of the intersection point
     */
    public abstract Color traceRay(Ray ray);
}
