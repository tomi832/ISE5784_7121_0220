package renderer;

import geometries.*;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;

import primitives.*;
import scene.Scene;

public class BallsTest {

    private final Scene scene = new Scene("Test scene");

    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setLocation(new Point(100, 20, 3))
            .setVpDistance(900)
            .setDirection(new Point(0, 5, 3), Vector.Y)
            .setRayTracer(new SimpleRayTracer(scene));

    private final Camera.Builder cameraBuilder2 = Camera.getBuilder()
            .setLocation(new Point(20, 30, 5))
            .setVpDistance(100)
            .setDirection(new Point(0, 5, 3), Vector.Y)
            .setRayTracer(new SimpleRayTracer(scene));

    public static Double3 tilt(double x, double y, double z, double angle) {
        double cosA = Math.cos(angle);
        double sinA = Math.sin(angle);
        double yNew = y * cosA - z * sinA;
        double zNew = y * sinA + z * cosA;
        return new Double3(x, yNew, zNew);
    }

    @Test
    public void GalaxyTest() {
        Material ballsMaterial = new Material().setKd(0.2).setKs(0.33).setShininess(10);
        Material wallsMaterial = new Material().setKd(0.35).setKs(0.4).setKt(0.85).setKr(0.2).setShininess(10);
        Color wallColor = new Color(20, 25, 25); //glass
        Color midGalaxy = new Color(255, 110, 70); //orange
        Color sunColor1 = new Color(255, 160, 90); //light orange
        Color sunColor2 = new Color(255, 200, 50); //yellow
        Color starColor1 = new Color(80, 255, 230); //light-blue
        Color starColor2 = new Color (200, 250, 255); //almost-white blue
        Color dustColor1 = new Color(40, 20, 0); //dark brown
        Color dustColor2 = new Color(45, 10, 0); //reddish brown
        Color oldStarsColor = new Color(200, 40, 0); //deep red
        scene.geometries.add(
                new Plane(new Point(0, 0, 0), new Vector(0, 1, 0))
                        .setEmission(new Color(30, 40, 70))
                        .setMaterial(new Material().setKd(1)),
                new Polygon(new Point(-1,0,-1),
                        new Point(-1,0,11),
                        new Point(-1,11,11),
                        new Point(-1,11,-1))
                        .setEmission(wallColor)
                        .setMaterial(wallsMaterial),
                new Polygon(new Point(-1,11, -1),
                        new Point(22,11,-1),
                        new Point(22,0,-1),
                        new Point(-1,0,-1))
                        .setEmission(wallColor)
                        .setMaterial(wallsMaterial),
                new Polygon(new Point(22,0,-1),
                        new Point(22,0,11),
                        new Point(22,11,11),
                        new Point(22,11,-1))
                        .setEmission(wallColor)
                        .setMaterial(wallsMaterial),
                new Polygon(new Point(-1,0,11),
                        new Point(22,0,11),
                        new Point(22,11,11),
                        new Point(-1,11,11))
                        .setEmission(wallColor)
                        .setMaterial(wallsMaterial),
                new Polygon(new Point(-1, 11, -1),
                        new Point(-1, 11, 11),
                        new Point(22, 11, 11),
                        new Point(22, 11, -1))
                        .setEmission(wallColor)
                        .setMaterial(wallsMaterial)

        );

        /** creating the arms of the galaxy */
        double a = 2d; //Distance between spiral arms
        double b = 0.25; //tightness of the spiral
        double r;
        double x;
        double z;
        double y;
        int numOfSpheres = 50;
        double angleIncrement = 0.15;
        int numArms = 10; //number of arms for the galaxy
        double theta;
        double angleOffset;
        for (int arm = 0; arm < numArms; arm++) {
            angleOffset = (2 * Math.PI / numArms) * arm;
            for (int i = 0; i < numOfSpheres; i++) {
                theta = i * angleIncrement;
                r = a * Math.exp(b * theta);
                x = r * Math.cos(theta + angleOffset);
                z = r * Math.sin(theta + angleOffset);
                y = Math.random() * 0.2 - 0.1;
                if (arm == 0)
                    scene.geometries.add(new Sphere(new Point(x + 4, y + 4, z + 4), 0.2)
                        .setEmission(sunColor2)
                        .setMaterial(ballsMaterial));
                else if (arm == 1) {
                    scene.geometries.add(new Sphere(new Point(x + 4, y + 4, z + 4), 0.3)
                            .setEmission(sunColor1)
                            .setMaterial(ballsMaterial));
                }  else if (arm % 2 == 0){
                    scene.geometries.add(new Sphere(new Point(x + 4, y + 4, z + 4), 0.1)
                            .setEmission(starColor1)
                            .setMaterial(ballsMaterial));
                }
                else {
                    scene.geometries.add(new Sphere(new Point(x + 4, y + 4, z + 4), 0.1)
                            .setEmission(starColor2)
                            .setMaterial(ballsMaterial));
                }
            }
        }
        scene.geometries.add(new Sphere(new Point(4, 4, 4), 2)
                .setEmission(midGalaxy)
                .setMaterial(ballsMaterial));

        scene.lights.add(new SpotLight(new Color(255, 255, 255), new Point(4, 30, 4), new Vector(0, -1, 0))
                .setKl(0.002).setKq(0.002));

        cameraBuilder.setVpSize(250, 250)
                .setImageWriter(new ImageWriter("Balls.txt", 1200, 1200))
                .build()
                .renderImage()
                .writeToImage();

        cameraBuilder2.setVpSize(250,250)
                .setImageWriter(new ImageWriter("Balls2.txt", 1200, 1200))
                .build()
                .renderImage()
                .writeToImage();
    }
}


