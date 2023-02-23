package elements;

import primitives.Color;

/**
 * abstract Light Class - all light sources in scene extend light 
 * @author Elinoy Gidoni and Tamara Seban
 */
abstract class Light {
	/**
	 * field for class light- the color of the light's intensity
	 */
	protected Color intensity;

	/**
	 * Constructor
	 * 
	 * @param intensity
	 */
	public Light(Color intensity) {
		this.intensity = intensity;
	}

	/**
	 * 
	 * function Get light color intensity
	 * 
	 * @return intensity color
	 */
	public Color getIntensity() {
		return intensity;
	}

}