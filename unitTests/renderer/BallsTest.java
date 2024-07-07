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

public class BallsTest {

    private final Scene scene = new Scene("Test scene");

    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setLocation(new Point(100, 4, 3)).setVpDistance(1000)
            .setDirection(new Point(0, 5, 3), Vector.Y)
            .setRayTracer(new SimpleRayTracer(scene));

    @Test
    public void BallsTest1() {
        Material ballsMaterial = new Material().setKd(0.2).setKs(0.5).setShininess(400).setKr(0.3);
        Material wallsMaterial = new Material().setKd(0.35).setKs(0.4).setShininess(10);
        scene.geometries.add(
                new Plane(new Point(0, 0, 0), new Vector(0, 1, 0))
                        .setEmission(new Color(50, 50, 50))
                        .setMaterial(new Material().setKd(1).setShininess(30)),
                new Sphere(new Point(1, 2.5, 5), 2.5)
                        .setEmission(new Color(0, 100, 255))
                        .setMaterial(ballsMaterial),
                new Sphere(new Point(0, 1.5, 1), 1.5)
                        .setEmission(new Color(170, 30, 210))
                        .setMaterial(ballsMaterial),
                new Triangle(new Point(-30, 0, -30), new Point(-30, 0, 30), new Point(0, 20, 0))
                        .setEmission(new Color(30,170,60))
                        .setMaterial(wallsMaterial),
                new Triangle(new Point(-30, 0, 30), new Point(30, 0, 30), new Point(0, 20, 0))
                        .setEmission(new Color(80,80,80))
                        .setMaterial(wallsMaterial),
//                new Triangle(new Point(50, 0, 50), new Point(50, 0, -50), new Point(0, 20, 0))
//                        .setEmission(new Color(255, 255, 255))
//                        .setMaterial(wallsMaterial),
                new Triangle(new Point(30, 0, -30), new Point(-30, 0, -30), new Point(0, 20, 0))
                        .setEmission(new Color(195,100,30))
                        .setMaterial(wallsMaterial)
        );
        scene.lights.add(new PointLight(new Color(255, 255, 255), new Point(10, 15, -9)).setKl(0.001).setKq(0.0001));

        cameraBuilder.setVpSize(250, 250)
                .setImageWriter(new ImageWriter("Balls.txt", 1200, 1200))
                .build()
                .renderImage()
                .writeToImage();
    }
}


//                new Polygon(
//                        new Point(-2, 0, -2),
//                        new Point(2, 0, -2),
//                        new Point(2, 10, -2),
//                        new Point(-2, 10, -2))
//                        .setEmission(new Color(30,170,60))
//                        .setMaterial(wallsMaterial),
//
//                new Polygon(
//                        new Point(-2, 0, -2),
//                        new Point(-2,0,9),
//                        new Point(-2, 10, 9),
//                        new Point(-2, 10, -2))
//                        .setEmission(new Color(80,80,80))
//                        .setMaterial(wallsMaterial),
//
//                new Polygon(
//                        new Point(-2, 0, 9),
//                        new Point(20, 0, 9),
//                        new Point(20, 10, 9),
//                        new Point(-2, 10, 9))
//                        .setEmission(new Color(195,100,30))
//                        .setMaterial(wallsMaterial)