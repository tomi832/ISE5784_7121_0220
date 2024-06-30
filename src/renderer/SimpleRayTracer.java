package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * SimpleRayTracer class is a basic ray tracer that traces rays in a scene
 * and returns the color of the intersection point
 * @author Yosef Kornfeld and Tomere Kalman
 */
public class SimpleRayTracer extends RayTracerBase{
    /** displacement value for shadow ray calculations */
    private static final double DELTA = 0.1;

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
        return calcColor(closestPoint, ray);
    }

    /**
     * Calculate the color of the intersection point
     * @param geoPoint the closest intersection point
     * @return the color of the intersection point
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return scene.ambientLight.getIntensity()
                .add(calcLocalEffects(geoPoint, ray));
    }

    /**
     * Calculate the local effects of the intersection point
     * @param geoPoint the closest intersection point
     * @return the color of the intersection point
     */
    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray) {
        Vector v = ray.getDirection();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) {
            return Color.BLACK;
        }

        Material material = geoPoint.geometry.getMaterial();
        Color color = geoPoint.geometry.getEmission();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(geoPoint.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0 && unshaded(geoPoint, l, n, lightSource , nl)) {
                Color Il = lightSource.getIntensity(geoPoint.point);
                color = color.add(
                        Il.scale(calcDiffusive(material.kD, nl).add(
                                calcSpecular(material.kS, l, n, nl, v, material.nShininess))));
            }
        }
        return color;
    }

    /**
     * Calculate the diffusive effect of the intersection point
     * @param kD the diffusive attenuation factor
     * @param nl the dot product of n and l
     * @return the color of the intersection point
     */
    private Double3 calcDiffusive(Double3 kD, double nl) {
        return kD.scale(Math.abs(nl));
    }

    /**
     * Calculate the specular effect of the intersection point
     * @param kS the specular attenuation factor
     * @param l the light vector
     * @param n the normal vector
     * @param nl the dot product of n and l
     * @param v the view vector
     * @param nShininess the shininess factor
     * @return the color of the intersection point
     */
    private Double3 calcSpecular(Double3 kS, Vector l, Vector n, double nl, Vector v, int nShininess) {
        Vector r = l.subtract(n.scale(2 * nl));
        double minusVR = -alignZero(r.dotProduct(v));
        if (minusVR <= 0) {
            return Double3.ZERO;
        }
        return kS.scale(Math.pow(minusVR, nShininess));
    }

    /**
     * Check if the intersection point is shaded
     * @param gp the intersection point
     * @param l the light vector
     * @param n the normal vector
     * @param lightSource the light source
     * @param nl the dot product of n and l
     * @return true if the intersection point is unshaded, false otherwise
     */
    private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource lightSource, double nl) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector DELTAVector = n.scale(nl < 0 ? DELTA : -DELTA);
        Point point = gp.point.add(DELTAVector);
        Ray ray = new Ray(point, lightDirection);
        double lightDistance = lightSource.getDistance(gp.point);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray, lightDistance);
        return intersections == null;
    }
}
