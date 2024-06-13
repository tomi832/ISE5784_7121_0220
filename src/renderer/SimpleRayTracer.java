package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * SimpleRayTracer class is a basic ray tracer that traces rays in a scene
 * and returns the color of the intersection point
 * @author Yosef Kornfeld and Tomere Kalman
 */
public class SimpleRayTracer extends RayTracerBase{

    /**
     * Constructor for SimpleRayTracer
     * @param scene the scene to be traced
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        return null;
    }

}
