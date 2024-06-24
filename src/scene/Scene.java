package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;
/**
 * a class that represents a scene
 */
public class Scene {
    public String sceneName;
    public Color background = Color.BLACK;
    public AmbientLight ambientLight = AmbientLight.NONE;
    public Geometries geometries = new Geometries();
    public List<LightSource> lights = new LinkedList<>();

    /**
     * consructor for the scene, takes the name of the scene only. rest
     * of the fields will be initialized in the setters.
     * @param sceneName the name of the scene
     */
    public Scene(String sceneName) {
        this.sceneName = sceneName;
    }

    /**
     * @param background the background color of the scene
     * @return the scene
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * @param ambientLight the ambient light of the scene
     * @return the scene
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * @param geometries the geometries of the scene
     * @return the scene
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * @param lights the lights of the scene
     * @return the scene
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

}
