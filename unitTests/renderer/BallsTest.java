package renderer;

import geometries.*;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;

import primitives.*;
import scene.Scene;

public class BallsTest {

    private final Scene scene = new Scene("Test scene");

    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(scene));

    private final Camera.Builder cameraBuilder2 = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(scene));

    private final Camera.Builder cameraBuilder3 = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(scene));

    public static double[] tilt(double x, double y, double z, double angle) {
        double cosA = Math.cos(angle);
        double sinA = Math.sin(angle);
        double yNew = y * cosA - z * sinA;
        double zNew = y * sinA + z * cosA;
        return new double[]{x + 4, yNew + 8, zNew + 4};
    }

    @Test
    public void GalaxyTest() {
        final Material ballsMaterial = new Material().setKd(0.2).setKs(0.33).setShininess(10);
        final Material glassMaterial = new Material().setKd(0.35).setKs(0.4).setKt(0.85).setKr(0.2).setShininess(10);
        final Material pedestalMaterial = new Material().setKd(0.5).setKs(0.35).setShininess(10);
        final Color glassColor = new Color(20, 25, 25); //glass
        final Color pedestalColor = new Color(80, 40, 5); //brown
        final Color midGalaxy = new Color(255, 220, 110); //light yellow
        final Color sunColor1 = new Color(255, 150, 90); //light orange
        final Color sunColor2 = new Color(255, 200, 50); //yellow
        final Color starColor1 = new Color(80, 255, 230); //light-blue
        final Color starColor2 = new Color (200, 250, 255); //almost-white blue
        final Color dustColor1 = new Color(40, 20, 0); //dark brown
        final Color dustColor2 = new Color(65, 10, 0); //reddish brown
        final Color oldStarsColor = new Color(200, 40, 0); //deep red

        /** coordinates for the walls of the box */
        final double x1 =-6.5d, x2 = 14.5d;
        final double z1 =-6.5d, z2 = 14.5d;
        final double y1 = 4d, y2 = 15d;

        /** coordinates for the top level of the pedestal */
        final double x3 = -7, x4 = 15;
        final double z3 = -7, z4 = 15;
        final double y3 = 2d, y4 = 4d;

        /** coordinates for the bottom level of the pedestal */
        final double x5 = -7.5, x6 = 15.5;
        final double z5 = -7.5, z6 = 15.5;
        final double y5 = 0, y6 = 2;
        scene.geometries.add(
                new Plane(new Point(0, 0, 0), new Vector(0, 1, 0))
                        .setEmission(new Color(30, 40, 70))
                        .setMaterial(new Material().setKd(1)),

                //Glass box (with regards for galaxy.txt)
                new Polygon(
                        new Point(x1,y1,z2),
                        new Point(x2,y1,z2),
                        new Point(x2,y2,z2),
                        new Point(x1,y2,z2))
                        .setEmission(glassColor)
                        .setMaterial(glassMaterial),
                new Polygon(
                        new Point(x1,y1, z1),
                        new Point(x1,y1,z2),
                        new Point(x1,y2,z2),
                        new Point(x1,y2,z1))
                        .setEmission(glassColor)
                        .setMaterial(glassMaterial),
                new Polygon(
                        new Point(x1,y1,z1),
                        new Point(x2,y1,z1),
                        new Point(x2,y2,z1),
                        new Point(x1,y2,z1))
                        .setEmission(glassColor)
                        .setMaterial(glassMaterial),
                new Polygon(
                        new Point(x2,y1,z1),
                        new Point(x2,y1,z2),
                        new Point(x2,y2,z2),
                        new Point(x2,y2,z1))
                        .setEmission(glassColor)
                        .setMaterial(glassMaterial),
                //Ceiling
                new Polygon(
                        new Point(x1,y2,z1),
                        new Point(x1,y2,z2),
                        new Point(x2,y2,z2),
                        new Point(x2,y2,z1))
                        .setEmission(glassColor)
                        .setMaterial(glassMaterial),

                //top level of the pedestal
                new Polygon(
                        new Point(x3,y3,z4),
                        new Point(x4,y3,z4),
                        new Point(x4,y4,z4),
                        new Point(x3,y4,z4))
                        .setEmission(pedestalColor)
                        .setMaterial(pedestalMaterial),
                new Polygon(
                        new Point(x3,y3,z3),
                        new Point(x3,y3,z4),
                        new Point(x3,y4,z4),
                        new Point(x3,y4,z3))
                        .setEmission(pedestalColor)
                        .setMaterial(pedestalMaterial),
                new Polygon(
                        new Point(x3,y3,z3),
                        new Point(x4,y3,z3),
                        new Point(x4,y4,z3),
                        new Point(x3,y4,z3))
                        .setEmission(pedestalColor)
                        .setMaterial(pedestalMaterial),
                new Polygon(
                        new Point(x4,y3,z3),
                        new Point(x4,y3,z4),
                        new Point(x4,y4,z4),
                        new Point(x4,y4,z3))
                        .setEmission(pedestalColor)
                        .setMaterial(pedestalMaterial),
                new Polygon(
                        new Point(x3,y4,z3),
                        new Point(x3,y4,z4),
                        new Point(x4,y4,z4),
                        new Point(x4,y4,z3))
                        .setEmission(pedestalColor)
                        .setMaterial(pedestalMaterial.setKr(0)),

                //bottom level of the pedestal
                new Polygon(
                        new Point(x5,y5,z6),
                        new Point(x6,y5,z6),
                        new Point(x6,y6,z6),
                        new Point(x5,y6,z6))
                        .setEmission(pedestalColor)
                        .setMaterial(pedestalMaterial),
                new Polygon(
                        new Point(x5,y5,z5),
                        new Point(x5,y5,z6),
                        new Point(x5,y6,z6),
                        new Point(x5,y6,z5))
                        .setEmission(pedestalColor)
                        .setMaterial(pedestalMaterial),
                new Polygon(
                        new Point(x5,y5,z5),
                        new Point(x6,y5,z5),
                        new Point(x6,y6,z5),
                        new Point(x5,y6,z5))
                        .setEmission(pedestalColor)
                        .setMaterial(pedestalMaterial),
                new Polygon(
                        new Point(x6,y5,z5),
                        new Point(x6,y5,z6),
                        new Point(x6,y6,z6),
                        new Point(x6,y6,z5))
                        .setEmission(pedestalColor)
                        .setMaterial(pedestalMaterial),
                new Polygon(
                        new Point(x5,y6,z5),
                        new Point(x5,y6,z6),
                        new Point(x6,y6,z6),
                        new Point(x6,y6,z5))
                        .setEmission(pedestalColor)
                        .setMaterial(pedestalMaterial)
        );

        /** creating the arms of the galaxy */
        final double a = 1.7; //Distance between spiral arms
        final double b = 0.25; //tightness of the spiral
        final int numOfSpheres = 50;
        final double angleIncrement = 0.15;
        final int numArms = 21; //number of arms for the galaxy
        final double tiltAngle = Math.toRadians(20);  // Tilt angle in radians
        double r, x, z, y, theta, angleOffset, rand;
        int temp;
        double[] tiltedParams;
        for (int arm = 0; arm < numArms; arm++) {
            angleOffset = (2 * Math.PI / numArms) * arm;
            temp = arm % 7;
            for (int i = 0; i < numOfSpheres; i++) {
                theta = i * angleIncrement; //angle from the radian 0
                r = a * Math.exp(b * theta); //r = distance from the center
                x = r * Math.cos(theta + angleOffset) + (Math.random() % 0.2 - 0.1) * Math.floor((float)i / 8);
                z = r * Math.sin(theta + angleOffset) + (Math.random() % 0.2 - 0.1) * Math.floor((float)i / 8);
                y = Math.random() * 0.2 - 0.1;
                tiltedParams = tilt(x, y, z, tiltAngle);
                if (temp == 4) {
                    rand = Math.random();
                    if (rand < 0.1) {
                    }
                    else if (rand < 0.5) {
                        scene.geometries.add(new Sphere(new Point(tiltedParams[0], tiltedParams[1], tiltedParams[2]), 0.2 + Math.random() % 0.03)
                                .setEmission(sunColor1.scale(0.9 + (rand % 0.2)))
                                .setMaterial(ballsMaterial));
                    } else {
                        scene.geometries.add(new Sphere(new Point(tiltedParams[0], tiltedParams[1], tiltedParams[2]), 0.16+ Math.random() % 0.03)
                                .setEmission(sunColor2.scale(0.9 + (rand % 0.2)))
                                .setMaterial(ballsMaterial));
                    }
                }
                else {
                    if (Math.random() < 0.5)
                        scene.geometries.add(new Sphere(new Point(tiltedParams[0], tiltedParams[1], tiltedParams[2]), 0.05)
                            .setEmission(starColor1)
                            .setMaterial(ballsMaterial));
                    else
                        scene.geometries.add(new Sphere(new Point(tiltedParams[0], tiltedParams[1], tiltedParams[2]), 0.05)
                                .setEmission(starColor2)
                                .setMaterial(ballsMaterial));
                }
            }
        }

        scene.geometries.add(
                new Sphere(new Point(4, 8, 4), 1)
                .setEmission(midGalaxy)
                .setMaterial(ballsMaterial),
                new Sphere(new Point(4, 8, 4), 1.3)
                .setEmission(midGalaxy.scale(0.3))
                .setMaterial(new Material().setKt(0.9).setKd(0.1).setKs(0.9).setShininess(10))
        );

        scene.lights.add(
                new SpotLight(new Color(0, 120, 120), new Point(20, 30, 4), new Vector(-1.8, -3, 0))
                        .setKl(0.002).setKq(0.002));
        scene.lights.add(
                new SpotLight(new Color(140, 100, 0), new Point(0, 30, 30), new Vector(0, -3, -1.8))
                        .setKl(0.002).setKq(0.002));
        scene.lights.add(
                new SpotLight(new Color(120, 0, 120), new Point(0, 30, -20), new Vector(0, -3, 1.8))
                        .setKl(0.002).setKq(0.002));

        cameraBuilder.
                setLocation(new Point(100, 27, 22))
                .setVpDistance(550)
                .setDirection(new Point(4, 8, 4), Vector.Y)
                .setVpSize(220, 220)
                .setImageWriter(new ImageWriter("Galaxy.txt", 1000, 1000))
                .build()
                .renderImage()
                .writeToImage();

//        cameraBuilder
//                .setLocation(new Point(4, 30, 6))
//                .setVpDistance(300)
//                .setVpSize(250,250)
//                .setImageWriter(new ImageWriter("Galaxy2.txt", 800, 800))
//                .build()
//                .renderImage()
//                .writeToImage();

//        cameraBuilder
//                .setLocation(new Point(7,4.7,3))
//                .setVpDistance(1)
//                .setDirection(new Point(4.6,3.6,4), new Vector(0,1,0.3))
//                .setVpSize(3,3)
//                .setImageWriter(new ImageWriter("Galaxy3.txt", 800, 800))
//                .build()
//                .renderImage()
//                .writeToImage();
    }
}


