package elements;

import primitives.Color;

/**
 * ambient light class - represents a fixed-intensity and fixedcolor light
 * source that affects all objects in the scene equally.
 * 
 * @author Elinoy Gidoni and Tamara Seban
 */
public class AmbientLight extends Light {

	/**
	 * constructor for ambient light class
	 * 
	 * @param iA - color intensity
	 * @param kA - real number
	 */
	public AmbientLight(Color iA, double kA) {
		super(iA.scale(kA));// scales the color intensity with kA

	}

	/**
	 * Default constructor, transfers the color black to light's constructor
	 */
	public AmbientLight() {
		super(Color.BLACK);
		// TODO Auto-generated constructor stub
	}
	
	

}