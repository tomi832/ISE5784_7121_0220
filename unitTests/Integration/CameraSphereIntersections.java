package Integration;

import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;
import geometries.*;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing the integration of Camera and Sphere
 * Authors: Tomer Kalman, Yosef Kornfeld
 */
class CameraSphereIntersections {
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(new Scene("Test")))
            .setImageWriter(new ImageWriter("Test", 1, 1))
            .setLocation(Point.ZERO)
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVpDistance(1);

    /**
     * First test case
     * Sphere is in front of a single pixel's ray
     */
    @Test
    void testIntegrationSphere1() {
        Camera camera1 = cameraBuilder.setVpSize(3, 3).build();
        Sphere sphere1 = new Sphere(new Point(0,0,-3), 1);
        List<Point> intersections = new LinkedList<>();
        List<Point> temp;
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                temp = sphere1.findIntersections(camera1.constructRay(3,3, j, i));
                if (temp != null)
                    intersections.addAll(temp);
            }
        }
        assertEquals(2, intersections.size(), "Wrong number of intersections with sphere1");
    }

    /**
     * Second test case
     * Sphere is in front of a 3x3 grid of rays
     */
    @Test
    void testIntegrationSphere2() {
        Camera camera2 = cameraBuilder.setVpSize(3, 3).setLocation(new Point(0, 0, 0.5)).build();
        Sphere sphere2 = new Sphere(new Point(0,0,-2.5), 2.5);
        List<Point> intersections = new LinkedList<>();
        List<Point> temp;
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                temp = sphere2.findIntersections(camera2.constructRay(3,3, j, i));
                if (temp != null)
                    intersections.addAll(temp);
            }
        }
        assertEquals(18, intersections.size(), "Wrong number of intersections with sphere2");
    }

    /**
     * Third test case
     * Sphere is in front of a 3x3 grid of rays but smaller, so not every ray intersects
     */
    @Test
    void testIntegrationSphere3() {
        Camera camera3 = cameraBuilder.setVpSize(3, 3).setLocation(new Point(0, 0, 0.5)).build();
        Sphere sphere3 = new Sphere(new Point(0,0,-2), 2);
        List<Point> intersections = new LinkedList<>();
        List<Point> temp;
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                temp = sphere3.findIntersections(camera3.constructRay(3,3, j, i));
                if (temp != null)
                    intersections.addAll(temp);
            }
        }
        assertEquals(10, intersections.size(), "Wrong number of intersections with sphere3");
    }

    /**
     * Fourth test case
     * Sphere envelops the camera, so every ray intersects only once
     */
    @Test
    void testIntegrationSphere4() {
        Camera camera4 = cameraBuilder.setVpSize(3, 3).build();
        Sphere sphere4 = new Sphere(new Point(0,0,-1), 1);
        List<Point> intersections = new LinkedList<>();
        List<Point> temp;
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                temp = sphere4.findIntersections(camera4.constructRay(3,3, j, i));
                if (temp != null)
                    intersections.addAll(temp);
            }
        }
        assertEquals(9, intersections.size(), "Wrong number of intersections with sphere4");
    }

    /**
     * Fifth test case
     * Sphere is behind the camera, so no rays intersect
     */
    @Test
    void testIntegrationSphere5() {
        Camera camera5 = cameraBuilder.setVpSize(3, 3).setLocation(new Point(0, 0, 0.5)).build();
        Sphere sphere5 = new Sphere(new Point(0,0,1), 0.5);
        List<Point> intersections = new LinkedList<>();
        List<Point> temp;
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                temp = sphere5.findIntersections(camera5.constructRay(3,3, j, i));
                if (temp != null)
                    intersections.addAll(temp);
            }
        }
        assertEquals(0, intersections.size(), "Wrong number of intersections with sphere5");
    }
}
