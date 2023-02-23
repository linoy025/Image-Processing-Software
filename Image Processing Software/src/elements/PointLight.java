package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * point light class - Models omni-directional point source (such as a bulb)

 */
public class PointLight  extends Light implements LightSource {
    /**
     *
     */
	private Point3D position;
    private double kC = 1 ;// Constant attenuation
    private double kL = 0; // Linear attenuation
    private double kQ = 0; // Quadratic attenuation

    /**
     * copy Constructor of point light class
     * @param colorIntensity the color intensity
     * @param position the position
     * @param kC constant attenuation
     * @param kL linear attenuation
     * @param kQ quadratic attenuation
     */
    public PointLight(Color colorIntensity, Point3D position) {
    	super(colorIntensity) ;
        this.position = position;
       
    }
	
	
	
    /**
     * Get intensity function
     * @param p point3D
     * @return color
     */
    @Override
    public Color getIntensity(Point3D p) {
        double dsquared = p.distanceSquared(position);//distance between the point and the position of the spot light squared
        double d = p.distance(position);//distance between the point and the position of the spot light
        return (intensity.reduce(kC + kL * d + kQ * dsquared));
    }

  
	/**
	 * @param kC the kC to set
	 */
	public PointLight setkC(double kC) {
		this.kC = kC;
		return this;
	}



	/**
	 * @param kL the kL to set
	 */
	public PointLight setkL(double kL) {
		this.kL = kL;
		return this;
	}




	/**
	 * @param kQ the kQ to set
	 */
	public PointLight setkQ(double kQ) {
		this.kQ = kQ;
		return this;
	}




	/**
     * Get light function
     * @param p Point3D
     * @return light vector
     */
    @Override
    public Vector getL(Point3D p) {
        if (p.equals(position))//if the point and the position of the light are the same there is no distance
        {
            return null;
        }
       else
           return p.subtract(position).normalize();//finds the distance between the point and the position of the light and normalizes it
    }



    /**
     * returns distance between point light and point3D
     * @param point Point3D
     * @return double distance
     */
    @Override
    public double getDistance(Point3D point) {
        return position.distance(point);// the distance between point position and the point the function received
    }

}


