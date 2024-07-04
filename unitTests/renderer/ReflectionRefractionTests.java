/**
 *
 */
package renderer;

import static java.awt.Color.*;

import geometries.Plane;
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

    @Test
    public void fourBodiesTest() {
        /** RGB for the top half of the */
        final double r1 = 35;
        final double g1 = 120;
        final double b1 = 280;
        /** RGB for the bottom half of the */
        final double r2 = 35;
        final double g2 = 140;
        final double b2 = 240;
        /** kT parameter for all the triangles */
        final double kT = 0.3;
        scene.geometries.add(
                new Plane(new Point(0, 0, 0), new Vector(0, 1, 0))
                        .setEmission(new Color(20, 30, 60))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKr(0.7)),
                new Triangle(Point.ZERO, new Point(30, 60, 30), new Point(30, 60, -30))
                        .setEmission(new Color(r1, g1, b1))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKt(kT)),
                new Triangle(Point.ZERO, new Point(30, 60, -30), new Point(-30, 60, -30))
                        .setEmission(new Color(r1, g1, b1))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKt(kT)),
                new Triangle(Point.ZERO, new Point(-30, 60, -30), new Point(-30, 60, 30))
                        .setEmission(new Color(r1, g1, b1))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKt(kT)),
                new Triangle(Point.ZERO, new Point(-30, 60, 30), new Point(30, 60, 30))
                        .setEmission(new Color(r1, g1, b1))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKt(kT)),
                new Triangle(new Point(0, 120, 0), new Point(30, 60, 30), new Point(30, 60, -30))
                        .setEmission(new Color(r2, g2, b2))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKt(kT)),
                new Triangle(new Point(0, 120, 0), new Point(30, 60, -30), new Point(-30, 60, -30))
                        .setEmission(new Color(r2, g2, b2))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKt(kT)),
                new Triangle(new Point(0, 120, 0), new Point(-30, 60, -30), new Point(-30, 60, 30))
                        .setEmission(new Color(r2, g2, b2))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKt(kT)),
                new Triangle(new Point(0, 120, 0), new Point(-30, 60, 30), new Point(30, 60, 30))
                        .setEmission(new Color(r2, g2, b2))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKt(kT)),
                new Sphere(new Point(0, 60, 0), 20d).setEmission(new Color(100, 100, 100))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKr(0.8)),
                new Sphere(new Point(0,8,70), 8d)
                        .setEmission(new Color(WHITE))
                        .setMaterial(new Material().setKt(0.8))
        );
        scene.lights.add(
                new SpotLight(new Color(100, 180, 210), new Point(100, 80, 0), new Vector(-100, -30, 0))
                        .setKl(4E-5).setKq(2E-7));
        scene.lights.add(
                new PointLight(new Color(WHITE), new Point(0,8,70))
        );

        cameraBuilder.setLocation(new Point(100, 20, 150)).setVpDistance(180)
                .setDirection(new Point(0, 60, 0), new Vector(0, 1, 0))
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("fourBodiesTestFromBelow", 1200, 1200))
                .build()
                .renderImage()
                .writeToImage();

        cameraBuilder.setLocation(new Point(100, 150, 250)).setVpDistance(180)
                .setImageWriter(new ImageWriter("fourBodiesTestFromAbove", 1200, 1200))
                .build()
                .renderImage()
                .writeToImage();
    }
}
