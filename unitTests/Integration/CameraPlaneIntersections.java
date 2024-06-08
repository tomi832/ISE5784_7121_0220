package Integration;

import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;
import geometries.*;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing the integration of Camera and Plane
 * @author Tomere Kalman and Yosef Kornfeld
 */
class CameraPlaneIntersections {
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(new Scene("Test")))
            .setImageWriter(new ImageWriter("Test", 1, 1))
            .setLocation(Point.ZERO)
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVpDistance(1);

    /**
     * first test case
     * plane is parallel to the view plane and the camera is looking straight at it
     */
    @Test
    void testIntegrationPlane1() {
        Camera camera1 = cameraBuilder.setVpSize(3, 3).build();
        Plane plane1 = new Plane(new Point(0,0,-3), new Vector(0,0,1));
        List<Point> intersections = new LinkedList<>();
        List<Point> temp;
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                temp = plane1.findIntersections(camera1.constructRay(3,3, j, i));
                if (temp != null)
                    intersections.addAll(temp);
            }
        }
        assertEquals(9, intersections.size(), "Wrong number of intersections with plane1");
    }

    /**
     * second test case
     * plane is angled to the view plane and the camera is looking straight at it
     */
    @Test
    void testIntegrationPlane2() {
        Camera camera2 = cameraBuilder.setVpSize(3, 3).build();
        Plane plane2 = new Plane(new Point(0,0,-2.5), new Vector(0,-0.5,1));
        List<Point> intersections = new LinkedList<>();
        List<Point> temp;
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                temp = plane2.findIntersections(camera2.constructRay(3,3, j, i));
                if (temp != null)
                    intersections.addAll(temp);
            }
        }
        assertEquals(9, intersections.size(), "Wrong number of intersections with plane2");
    }

    /**
     * third test case
     * plane is angled to the view plane and the camera is looking straight at it
     * the plane is angled in a way which escapes the lower rays from the camera
     */
    @Test
    void testIntegrationPlane3() {
        Camera camera3 = cameraBuilder.setVpSize(3, 3).setLocation(new Point(0, 0, 1)).build();
        Plane plane3 = new Plane(new Point(0,0,-2), new Vector(0,-1,1));
        List<Point> intersections = new LinkedList<>();
        List<Point> temp;
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                temp = plane3.findIntersections(camera3.constructRay(3,3, j, i));
                if (temp != null)
                    intersections.addAll(temp);
            }
        }
        assertEquals(6, intersections.size(), "Wrong number of intersections with plane3");
    }
}
