package scene;

import java.util.LinkedList;
import java.util.List;

import elements.AmbientLight;
import elements.LightSource;
import geometries.Geometries;
import primitives.Color;

/**
 * class scene
 */
public class Scene {

	/**
	 * fields for class scene -Scene is the data structure representing geometries,
	 * textures, lights, shading, and view point information
	 * 
	 * @author Elinoy Gidoni and Tamara Seban
	 */
	public String name;
	public Color background =Color.BLACK;
	public AmbientLight ambientLight = new AmbientLight(background,0) ;
	public Geometries geometries = null;
	public List<LightSource> lights = new LinkedList<LightSource>();

	    /**
	     * get light sources function
	     * @return list of light sources
	     */
	    public Scene setLightSources(List<LightSource> lights) {	    	
	    	  this.lights = lights;
	            return this;
	    }
	    
	   
	    
	/**
	 * constructor
	 * 
	 * @param sceneName the scene's name
	 */
	public Scene(String sceneName) {
		this.name = sceneName;
		geometries = new Geometries();
	}

	/**
	 * set background color function
	 * 
	 * @param background color
	 */
	public Scene setBackground(Color background) {
		this.background = background;
		return this;
	}

	/**
	 * set ambient light function
	 * 
	 * @param ambientLight ambient light
	 */
	public Scene setAmbientLight(AmbientLight ambientLight) {
		this.ambientLight = ambientLight;
		return this;
	}

	/**
	 * set geometries function
	 * 
	 * @param geometries
	 */
	public Scene setGeometries(Geometries geometries) {
		this.geometries = geometries;
		return this;
	}
	
	
	

}
