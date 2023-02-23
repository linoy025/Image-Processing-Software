package elements;

import primitives.*;

/**
 * light source interface
 * 
 * @author Elinoy Gidoni and Tamara Seban
 */
public interface LightSource {
	/**
	 * get color intensity function
	 * 
	 * @param p point3D
	 * @return the color intensity of the point
	 */
	public Color getIntensity(Point3D p);

	/**
	 * get light direction function
	 * 
	 * @param p Point3D
	 * @return vector light direction
	 */
	public Vector getL(Point3D p);
	
	 /**
     * get distance between a point and a light source
     * @param point Point3D
     * @return double distance between the point and the light source
     */
    double getDistance(Point3D point);

}
