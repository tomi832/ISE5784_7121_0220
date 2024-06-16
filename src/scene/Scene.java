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

    public static class Builder {
        private Scene scene = new Scene("Scene");

        public Builder setBackground(Color background) {
            if (background == null)
                throw new IllegalArgumentException("Background color cannot be null");
            scene.background = background;
            return this;
        }

        public Builder setAmbientLight(AmbientLight ambientLight) {
            if (ambientLight == null)
                throw new IllegalArgumentException("Ambient light cannot be null");
            scene.ambientLight = ambientLight;
            return this;
        }

        public Builder setGeometries(Geometries geometries) {
            scene.geometries = geometries;
            return this;
        }

        public Scene build() {
            if (scene.ambientLight == null)
                throw new IllegalArgumentException("Ambient light cannot be null");
            if (scene.background == null)
                throw new IllegalArgumentException("Background color cannot be null");
            return (Scene) scene.clone();
        }
    }

    @Override
    public Scene clone() {
        try {
            Scene scene = (Scene) super.clone();
            scene.sceneName = this.sceneName;
            scene.background = this.background;
            scene.ambientLight = this.ambientLight;
            scene.geometries = this.geometries;
            return scene;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

}
