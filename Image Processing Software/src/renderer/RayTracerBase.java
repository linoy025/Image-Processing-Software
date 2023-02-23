package renderer;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

/**
 * Class RayTracerBase - An abstract class that contains a method by which we can
 * paint in a suitable color points in objects where there is no direct point of
 * intersection with the ray
 * 
 * @author Elinoy Gidoni and Tamara Seban
 */
public abstract class RayTracerBase {
	protected Scene scene;

	/**
	 * Constructor
	 * 
	 * @param scene
	 */
	public RayTracerBase(Scene scene) {
		this.scene = scene;
	}

	/**
	 * Find the intersection and the sceneís geometries »»If there is no
	 * intersection, return the background color Find the closest intersection point
	 * Find the color of the intersection point (Ambient light)
	 * 
	 * @param ray
	 * @return Color
	 */
	public abstract Color traceRay(Ray ray);

}
