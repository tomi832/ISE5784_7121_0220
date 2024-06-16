package renderer;

import geometries.Intersectable.GeoPoint;
import primitives.Color;
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
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null) {
            return scene.background;
        }
        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint);
    }

    /**
     * Calculate the color of the intersection point
     * @param geoPoint the closest intersection point
     * @return the color of the intersection point
     */
    private Color calcColor(GeoPoint geoPoint) {
        Color color = scene.ambientLight.getIntensity();
        color = color.add(geoPoint.geometry.getEmission());
        return color;
    }

}
