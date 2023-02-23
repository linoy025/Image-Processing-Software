package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * directional light class - Light source is far away at infinity like Sun
 *  Elinoy Gidoni and Tamara Seban
 */
public class DirectionalLight extends Light implements LightSource  {
    /**
     * field of class directional light- the direction of the light
     */
	
    private Vector direction;
    
    /**
     * Constructor for directional light
     *
     * @param intensity color
     * @param direction  vector to be normalized
     */
    public DirectionalLight(Color intensity, Vector _direction) {
    	super(intensity) ;
        direction = _direction.normalized();
    }

    
    /**
     * get color intensity function
     * @param p point3D - not used
     * @return fixed intensity of the directional light
     */
    @Override
    public Color getIntensity(Point3D p) {
        return super.getIntensity();
    }

    
    /**
     * get direction function
     * @param p Point3D
     * @return direction vector
     */
    @Override
    public Vector getL(Point3D p) {
        return direction;
    }


    /**
     * returns distance between light and a point3D
     * @param point Point3D
     * @return double distance
     */
    @Override
    public double getDistance(Point3D point)
    {
        return Double.POSITIVE_INFINITY;// like the distance between the sun and the earth the distance is to far to calculate
    }

}
