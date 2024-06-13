package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;

public class Scene {
    public String sceneName;
    public Color background = Color.BLACK;
    public AmbientLight ambientLight = AmbientLight.NONE;
    public Geometries geometries = new Geometries();

    public Scene(String sceneName) {
        this.sceneName = sceneName;
    }

    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }


}
