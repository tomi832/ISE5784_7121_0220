package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * SimpleRayTracer class is a basic ray tracer that traces rays in a scene
 * and returns the color of the intersection point
 * @author Yosef Kornfeld and Tomere Kalman
 */
public class SimpleRayTracer extends RayTracerBase{
    /** displacement value for shadow ray calculations */
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final Double3 INITIAL_K = Double3.ONE;

    /**
     * Constructor for SimpleRayTracer
     * @param scene the scene to be traced
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * Find the closest intersection point of a ray with the scene
     * @param ray the ray to intersect with the scene
     * @return the closest intersection point
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        return ray.findClosestGeoPoint(intersections);
    }

    /**
     * Trace a ray in the scene and return the color of the intersection point
     * @param ray the ray to trace
     * @return the color of the intersection point
     */
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
    }

    /**
     * Calculate the color of the intersection point
     * @param geoPoint the closest intersection point
     * @param ray the ray that intersects the point
     * @return the color of the intersection point
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambientLight.getIntensity());
    }

    /**
     * Calculate the color of the intersection point (called from calcColor version without the level and k parameters)
     * @param geoPoint the closest intersection point
     * @param ray the ray that intersects the point
     * @param level the level of recursion
     * @param k the attenuation factor
     * @return the color of the intersection point
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(geoPoint, ray);
        return level == 1 ? color : color.add(calcGlobalEffects(geoPoint, ray, level, k));
    }

    /**
     * Calculate the diffusive and specular effects of the intersection point
     * @param geoPoint the closest intersection point
     * @param ray the ray that intersects the point
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
            if (nl * nv > 0) {
                Double3 ktr = transparency(geoPoint, lightSource, l, n);
                if (ktr != Double3.ZERO) {
                    Color lightIntensity = lightSource.getIntensity(geoPoint.point).scale(ktr);
                    color = color.add(
                        lightIntensity.scale(calcDiffusive(material.kD, nl).add(
                                calcSpecular(material.kS, l, n, nl, v, material.nShininess))));
                }
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
     * Calculate the global effects of the intersection point (calls calcGlobalEffect 2 times, for refracted/reflected rays)
     * @param geoPoint the closest intersection point
     * @param ray the ray that intersects the point
     * @param level the level of recursion
     * @param k the attenuation factor
     * @return the color of the intersection point
     */
    private Color calcGlobalEffects(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Material material = geoPoint.geometry.getMaterial();
        return calcGlobalEffect(constructRefractedRay(geoPoint, ray), material.kT, level, k)
            .add(calcGlobalEffect(constructReflectedRay(geoPoint, ray), material.kR, level, k));
    }

    private Color calcGlobalEffect(Ray ray, Double3 kx, int level, Double3 k) {
        Double3 kkx = kx.product(k);
        if (kkx.lowerThan(MIN_CALC_COLOR_K))
            return Color.BLACK;
        GeoPoint gp = findClosestIntersection(ray);
        return gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx).scale(kx);
    }

    private Ray constructReflectedRay(GeoPoint geoPoint, Ray ray) {
        Vector v = ray.getDirection();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        Vector r = v.subtract(n.scale(2 * n.dotProduct(v)));
        return new Ray(geoPoint.point, r, n);
    }

    private Ray constructRefractedRay(GeoPoint geoPoint, Ray ray) {
        return new Ray(geoPoint.point, ray.getDirection(), geoPoint.geometry.getNormal(geoPoint.point));
    }

    private Double3 transparency(GeoPoint geoPoint, LightSource ls, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, ls.getDistance(geoPoint.point));
        if (intersections == null) {
            return Double3.ONE;
        }
        Double3 ktr = Double3.ONE;
        for (GeoPoint gp : intersections) {
            ktr = ktr.product(gp.geometry.getMaterial().kT);
            if (ktr.lowerThan(MIN_CALC_COLOR_K)) {
                return Double3.ZERO;
            }
        }
        return ktr;
    }
}
