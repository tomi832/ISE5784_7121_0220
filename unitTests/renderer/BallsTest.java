package renderer;

import geometries.*;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;

import primitives.*;
import scene.Scene;

public class BallsTest {

    private final Scene scene = new Scene("Test scene");

    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setLocation(new Point(100, 27, 3))
            .setVpDistance(900)
            .setDirection(new Point(0, 4, 4), Vector.Y)
            .setRayTracer(new SimpleRayTracer(scene));

    private final Camera.Builder cameraBuilder2 = Camera.getBuilder()
            .setLocation(new Point(4, 30, 6))
            .setVpDistance(300)
            .setDirection(new Point(4, 4, 4), Vector.Y)
            .setRayTracer(new SimpleRayTracer(scene));

    private final Camera.Builder cameraBuilder3 = Camera.getBuilder()
            .setLocation(new Point(7,4.7,3))
            .setVpDistance(1)
            .setDirection(new Point(4.6,3.6,4), new Vector(0,1,0.3))
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
        Color midGalaxy = new Color(255, 220, 110); //light yellow
        Color sunColor1 = new Color(255, 150, 90); //light orange
        Color sunColor2 = new Color(255, 200, 50); //yellow
        Color starColor1 = new Color(80, 255, 230); //light-blue
        Color starColor2 = new Color (200, 250, 255); //almost-white blue
        Color dustColor1 = new Color(40, 20, 0); //dark brown
        Color dustColor2 = new Color(65, 10, 0); //reddish brown
        Color oldStarsColor = new Color(200, 40, 0); //deep red

        double x1 =-6d, x2 = 14d;
        double z1 =-6d, z2 = 14d;
        double y1 = 0d, y2 = 11d;
        scene.geometries.add(
                new Plane(new Point(0, 0, 0), new Vector(0, 1, 0))
                        .setEmission(new Color(30, 40, 70))
                        .setMaterial(new Material().setKd(1)),

                //extreme values for x and z: -6, 14
                //extreme values for y: 0, 11

                new Polygon(
                        new Point(x1,y1,z2),
                        new Point(x2,y1,z2),
                        new Point(x2,y2,z2),
                        new Point(x1,y2,z2))
                        .setEmission(wallColor)
                        .setMaterial(wallsMaterial),
                new Polygon(
                        new Point(x1,y1, z1),
                        new Point(x1,y1,z2),
                        new Point(x1,y2,z2),
                        new Point(x1,y2,z1))
                        .setEmission(wallColor)
                        .setMaterial(wallsMaterial),
                new Polygon(
                        new Point(x1,y1,z1),
                        new Point(x2,y1,z1),
                        new Point(x2,y2,z1),
                        new Point(x1,y2,z1))
                        .setEmission(wallColor)
                        .setMaterial(wallsMaterial),
                new Polygon(
                        new Point(x2,y1,z1),
                        new Point(x2,y1,z2),
                        new Point(x2,y2,z2),
                        new Point(x2,y2,z1))
                        .setEmission(wallColor)
                        .setMaterial(wallsMaterial),
                new Polygon(
                        new Point(x1,y2,z1),
                        new Point(x1,y2,z2),
                        new Point(x2,y2,z2),
                        new Point(x2,y2,z1))
                        .setEmission(wallColor)
                        .setMaterial(wallsMaterial)

        );

        /** creating the arms of the galaxy */
        double a = 1.8d; //Distance between spiral arms
        double b = 0.25; //tightness of the spiral
        double r;
        double x;
        double z;
        double y;
        int numOfSpheres = 50;
        double angleIncrement = 0.15;
        int numArms = 21; //number of arms for the galaxy
        int temp;
        double theta;
        double angleOffset;
        double rand;
        for (int arm = 0; arm < numArms; arm++) {
            angleOffset = (2 * Math.PI / numArms) * arm;
            temp = arm % 7;
            for (int i = 0; i < numOfSpheres; i++) {
                theta = i * angleIncrement;
                r = a * Math.exp(b * theta);
                x = r * Math.cos(theta + angleOffset) + (Math.random() % 0.2 - 0.1) * Math.floor((float)i / 15);
                z = r * Math.sin(theta + angleOffset) + (Math.random() % 0.2 - 0.1) * Math.floor((float)i / 15);
                y = Math.random() * 0.2 - 0.1;
                if (temp == 4) {
                    rand = Math.random();
                    if (rand < 0.1)
                        continue;
                    else if (rand < 0.5) {
                        scene.geometries.add(new Sphere(new Point(x + 4, y + 4, z + 4), 0.22)
                                .setEmission(sunColor1.scale(0.9 + (rand % 0.2)))
                                .setMaterial(ballsMaterial));
                    } else {
                        scene.geometries.add(new Sphere(new Point(x + 4, y + 4, z + 4), 0.18)
                                .setEmission(sunColor2.scale(0.9 + (rand % 0.2)))
                                .setMaterial(ballsMaterial));
                    }
                }
                else {
                    if (Math.random() < 0.5)
                        scene.geometries.add(new Sphere(new Point(x + 4, y + 4, z + 4), 0.05)
                            .setEmission(starColor1)
                            .setMaterial(ballsMaterial));
                    else
                        scene.geometries.add(new Sphere(new Point(x + 4, y + 4, z + 4), 0.05)
                                .setEmission(starColor2)
                                .setMaterial(ballsMaterial));
                }
            }
        }

        scene.geometries.add(new Sphere(new Point(4, 4, 4), 1)
                .setEmission(midGalaxy)
                .setMaterial(ballsMaterial));
//        scene.geometries.add(new Sphere(new Point(4, 4, 4), 1.1)
//                .setEmission(midGalaxy.scale(0.5))
//                .setMaterial(new Material().setKt(0.9)));
        scene.geometries.add(new Sphere(new Point(4, 4, 4), 1.2)
                .setEmission(midGalaxy.scale(0.3))
                .setMaterial(new Material().setKt(0.9)));

        scene.lights.add(new SpotLight(new Color(255, 100, 40), new Point(4, 30, 4), new Vector(0, -1, 0))
                .setKl(0.002).setKq(0.002));

        cameraBuilder.setVpSize(220, 220)
                .setImageWriter(new ImageWriter("Balls.txt", 1000, 1000))
                .build()
                .renderImage()
                .writeToImage();

        cameraBuilder2.setVpSize(250,250)
                .setImageWriter(new ImageWriter("Balls2.txt", 800, 800))
                .build()
                .renderImage()
                .writeToImage();

//        cameraBuilder3.setVpSize(3,3)
//                .setImageWriter(new ImageWriter("Balls3.txt", 800, 800))
//                .build()
//                .renderImage()
//                .writeToImage();
    }
}


