package Integration;

import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;
import geometries.*;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CameraTriangleIntersections {
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(new Scene("Test")))
            .setImageWriter(new ImageWriter("Test", 1, 1))
            .setLocation(Point.ZERO)
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVpDistance(1);

    //First Test Case
    @Test
    void testIntegrationTriangle1() {
        Camera camera1 = cameraBuilder.setVpSize(3, 3).build();
        Triangle triangle1 = new Triangle(new Point(0,1,-2), new Point(1,-1,-2), new Point(-1,-1,-2));
        List<Point> intersections = new LinkedList<>();
        List<Point> temp;
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                temp = triangle1.findIntersections(camera1.constructRay(3,3, j, i));
                if (temp != null)
                    intersections.addAll(temp);
            }
        }
        assertEquals(1, intersections.size(), "Wrong number of intersections with triangle1");
    }

    //Second Test Case
    @Test
    void testIntegrationTriangle2() {
        Camera camera2 = cameraBuilder.setVpSize(3, 3).build();
        Triangle triangle2 = new Triangle(new Point(0,20,-2), new Point(1,-1,-2), new Point(-1,-1,-2));
        List<Point> intersections = new LinkedList<>();
        List<Point> temp;
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                temp = triangle2.findIntersections(camera2.constructRay(3,3, j, i));
                if (temp != null)
                    intersections.addAll(temp);
            }
        }
        assertEquals(2, intersections.size(), "Wrong number of intersections with triangle2");
    }
}
