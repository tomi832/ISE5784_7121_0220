/**
 *
 */
package renderer;

import static java.awt.Color.*;

import geometries.Plane;
import geometries.Polygon;
import lighting.DirectionalLight;
import lighting.PointLight;
import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 *
 * @author dzilb
 */
public class ReflectionRefractionTests {
    /**
     * Scene for the tests
     */
    private final Scene scene = new Scene("Test scene");
    /**
     * Camera builder for the tests with triangles
     */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
            .setDirection(Point.ZERO, Vector.Y)
            .setRayTracer(new SimpleRayTracer(scene));

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheres() {
        scene.geometries.add(
                new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(new Point(0, 0, -50), 25d).setEmission(new Color(RED))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
        scene.lights.add(
                new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2))
                        .setKl(0.0004).setKq(0.0000006));

        cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
                .setVpSize(150, 150)
                .setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() {
        scene.geometries.add(
                new Sphere(new Point(-950, -900, -1000), 400d).setEmission(new Color(0, 50, 100))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                                .setKt(new Double3(0.5, 0, 0))),
                new Sphere(new Point(-950, -900, -1000), 200d).setEmission(new Color(100, 50, 20))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(670, 670, 3000))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setKr(1)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(-1500, -1500, -2000))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setKr(new Double3(0.5, 0, 0.4))));
        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4))
                .setKl(0.00001).setKq(0.000005));

        cameraBuilder.setLocation(new Point(0, 0, 10000)).setVpDistance(10000)
                .setVpSize(2500, 2500)
                .setImageWriter(new ImageWriter("reflectionTwoSpheresMirrored", 500, 500))
                .build()
                .renderImage()
                .writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a
     * partially
     * transparent Sphere producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        scene.geometries.add(
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                        new Point(75, 75, -150))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Sphere(new Point(60, 50, -50), 30d).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
        scene.lights.add(
                new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1))
                        .setKl(4E-5).setKq(2E-7));

        cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("refractionShadow", 600, 600))
                .build()
                .renderImage()
                .writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void allFeaturesTest() {
        /* RGB Colors for the bodies */
        /* Bi-pyramid: */
        Color colorTriTop = new Color(35, 120, 280);
        Color colorTriBottom = new Color(35, 140, 240);
        /* Cube: */
        Color colorPoly1 = new Color(255, 50, 50);
        Color colorPoly2 = new Color(300, 20, 20);
        /* Points for the polygons */
        final Point[] points = {
                new Point(50, 5, 50),
                new Point(30, 5, 50),
                new Point(30, 5, 30),
                new Point(50, 5, 30),
                new Point(50, 25, 50),
                new Point(30, 25, 50),
                new Point(30, 25, 30),
                new Point(50, 25, 30)
        };
        /* materials for the bodies */
        Material materialTri = new Material().setKd(0.5).setKs(0.5).setShininess(60).setKt(0.3);
        Material materialPoly = new Material().setKt(0.45).setKs(0.3).setKd(0.5);
        scene.geometries.add(
                new Plane(new Point(0, 0, 0), new Vector(0, 1, 0))
                        .setEmission(new Color(20, 30, 60))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKr(0.7)),
                new Triangle(Point.ZERO, new Point(30, 60, 30), new Point(30, 60, -30))
                        .setEmission(colorTriTop)
                        .setMaterial(materialTri),
                new Triangle(Point.ZERO, new Point(30, 60, -30), new Point(-30, 60, -30))
                        .setEmission(colorTriTop)
                        .setMaterial(materialTri),
                new Triangle(Point.ZERO, new Point(-30, 60, -30), new Point(-30, 60, 30))
                        .setEmission(colorTriTop)
                        .setMaterial(materialTri),
                new Triangle(Point.ZERO, new Point(-30, 60, 30), new Point(30, 60, 30))
                        .setEmission(colorTriTop)
                        .setMaterial(materialTri),
                new Triangle(new Point(0, 120, 0), new Point(30, 60, 30), new Point(30, 60, -30))
                        .setEmission(colorTriBottom)
                        .setMaterial(materialTri),
                new Triangle(new Point(0, 120, 0), new Point(30, 60, -30), new Point(-30, 60, -30))
                        .setEmission(colorTriBottom)
                        .setMaterial(materialTri),
                new Triangle(new Point(0, 120, 0), new Point(-30, 60, -30), new Point(-30, 60, 30))
                        .setEmission(colorTriBottom)
                        .setMaterial(materialTri),
                new Triangle(new Point(0, 120, 0), new Point(-30, 60, 30), new Point(30, 60, 30))
                        .setEmission(colorTriBottom)
                        .setMaterial(materialTri),
                new Sphere(new Point(0, 60, 0), 20d)
                        .setEmission(new Color(100, 100, 100))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKr(0.8)),
                new Polygon(points[0], points[1], points[2], points[3])
                        .setEmission(colorPoly1)
                        .setMaterial(materialPoly),
                new Polygon(points[4], points[5], points[6], points[7])
                        .setEmission(colorPoly2)
                        .setMaterial(materialPoly),
                new Polygon(points[0], points[1], points[5], points[4])
                        .setEmission(colorPoly1)
                        .setMaterial(materialPoly),
                new Polygon(points[1], points[2], points[6], points[5])
                        .setEmission(colorPoly2)
                        .setMaterial(materialPoly),
                new Polygon(points[2], points[3], points[7], points[6])
                        .setEmission(colorPoly1)
                        .setMaterial(materialPoly),
                new Polygon(points[3], points[0], points[4], points[7])
                        .setEmission(colorPoly2)
                        .setMaterial(materialPoly),
                new Sphere(new Point(40,13,40), 3d)
                        .setEmission(new Color(50, 140, 50))
                        .setMaterial(new Material().setKt(0.8))
        );

        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.005));
        scene.lights.add(
                new SpotLight(new Color(100, 180, 210), new Point(100, 80, 0), new Vector(-100, -30, 0))
                        .setKl(4E-5).setKq(2E-7));
        scene.lights.add(
                new PointLight(new Color(100, 100, 100), new Point(40,13,40)).setKl(0.01).setKq(0.001));
        scene.lights.add(new DirectionalLight(new Color(25, 0, 45), new Vector(1, -1, 1)));

        cameraBuilder.setLocation(new Point(100, 20, 150)).setVpDistance(180)
                .setDirection(new Point(0, 60, 0), new Vector(0, 1, 0))
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("allFeaturesTestFromBelow", 1200, 1200))
                .build()
                .renderImage()
                .writeToImage();

         cameraBuilder.setLocation(new Point(90, 115, 200)).setVpDistance(180)
                .setDirection(new Point(0, 60, 0), new Vector(0, 1, 0))
                .setImageWriter(new ImageWriter("allFeaturesTestFromAbove", 600, 600))
                .build()
                .renderImage()
                .writeToImage();
    }
}