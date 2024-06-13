package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

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
        List<Point> intersections = scene.geometries.findIntersections(ray);
        if (intersections == null) {
            return scene.background;
        }
        Point closestPoint = ray.findClosestPoint(intersections);
        return calcColor(closestPoint);
    }

    /**
     * Calculate the color of the intersection point
     * @param point the closest intersection point
     * @return the color of the intersection point
     */
    private Color calcColor(Point point) {
        return scene.ambientLight.getIntensity();
    }

}
