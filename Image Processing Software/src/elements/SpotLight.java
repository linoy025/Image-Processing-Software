package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Util;
import primitives.Vector;

/**
 * SpotLight Class - Models point light source with direction (such as a luxo
 * lamp)
 * 
 * @author Elinoy Gidoni and Tamara Seban
 */
public class SpotLight extends PointLight {

	/**
	 * fields for spot light
	 */
	Vector direction;

	/**
	 * Constructor of spot light class
	 * 
	 * @param colorIntensity the color intensity
	 * @param position       the position - point3D
	 * @param direction      the director direction
	 */
	public SpotLight(Color colorIntensity, Point3D position, Vector direction) {
		super(colorIntensity, position);
		this.direction = direction.normalized();

	}

	/**
	 * The get intensity function
	 * 
	 * @param p Point3D
	 * @return spotlight intensity
	 */
	@Override
	public Color getIntensity(Point3D p) {
		double projection = direction.dotProduct(getL(p));// dot product between the direction vector and the direction
															// from point p

		if (Util.isZero(projection)) // if the projection distance is zero8
		{
			return Color.BLACK;
		}
		double factor = Math.max(0, projection);// the factor is zero if the projection is zero , else it is the
												// projection value(double)
		Color pointlightIntensity = super.getIntensity(p);// the intensity of Point3D p

		return (pointlightIntensity.scale(factor));// returns the intensity scaled by the projection factor
	}

}
