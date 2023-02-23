package primitives;

import java.util.LinkedList;


import java.util.List;
import static primitives.Util.random;

import geometries.Intersectable.GeoPoint;
import static primitives.Util.alignZero;

/**
 * This class represents a Foundry object in geometry - the group of points on a
 * straight line that are on one relatively side To a given point on the line
 * called the beginning of the foundation. Defined by point and direction (unit
 * vector)
 * 
 * @author Elinoy Gidoni and Tamara Seban
 */
public class Ray {

	Point3D p0;
	Vector dir;
  
	/**
	 * constant for size moving rays' head for shading rays
	 * 
	 */
	private static double DELTA = 0.1;

	/**
	 * constructor
	 * 
	 * @param p0  - Point3D
	 * @param dir - Vector
	 */
	public Ray(Point3D p0, Vector dir) {
		this.p0 = p0;
		this.dir = dir;
		this.dir.normalize();
	}

	
	/**
	 *  constructor
	 * 
	 * @param head Point3D -of the ray
	 * @param direction Vector -dir of ray
	 * @param normal Vector -normal for find the delta
	 * 
	 */
	public Ray(Point3D head, Vector direction, Vector normal) {
		this.dir = direction.normalized();
		double sign = alignZero(dir.dotProduct(normal));
		head = head.add(normal.scale(sign > 0 ? DELTA : -DELTA));
		this.p0 = head;
	}
	/**
	 * Returns a 3D point
	 * 
	 * @return p0
	 */
	public Point3D getP0() {
		return p0;
	}

	/**
	 * @return vector direction
	 */
	public Vector getDir() {
		return dir;
	}

	/**
	 * returns the target point of a ray
	 *
	 * @param t- length of the ray
	 * @return Point3D- ray's target point
	 */
	public Point3D getPoint(double t) {
		return Util.isZero(t) ? p0 : p0.add(dir.scale(t));
	}

	@Override
	public String toString() {
		return this.p0.toString() + this.dir.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ray other = (Ray) obj;
		return dir.equals(other.dir) && p0.equals(other.p0);
	}

	/**
	 * Finding the closest point to the P0 of the ray.
	 * 
	 * @param intersectionPoints list of points, the function should find from this
	 *                           list the closet point to P0 of the ray in the scene
	 * @return the closest point to the ray
	 */

	public GeoPoint findClosestGeoPoint(List<GeoPoint> intersections) {

		GeoPoint result = null;
		if (intersections != null) {
			double smallestDistance = Double.MAX_VALUE;

			for (GeoPoint p : intersections) // for each point finds distance from ray starting point and checks if it
												// is
			// closer to the point we want than previous points
			{
				GeoPoint pt = p;
				double distance = this.getP0().distance(pt.point);
				if (distance < smallestDistance) {
					smallestDistance = distance;
					result = p;
				}
			}
		}
		return result;

	}
	
	
	/**
	 * creates a beam of rays(in a list of rays)
	 *
	 * @param n  Vector - normal vector where the rays start
	 * @param distance double - the distance between the  point and the circle we are creating to find the beam
	 * @param num int - the number of rays that will be in the beam
	 * @return list that includes all the rays that make up the beam
	 */
	public List<Ray> createBeamOfRays(Vector n, double distance, int num) {
	    List<Ray> beam = new LinkedList<Ray>();
	    beam.add(this);//the original ray that calls the function - there has to be at least one beam
	    if (num == 1)//if no additional rays were requested here  there is nothing else to do in this function
	        return beam;
	    Vector w = this.getDir().normalToVector();//finds a vector that is normal to the direction on the ray
	    Vector v = this.getDir().crossProduct(w).normalize();//the cross product between the normal and the direction

	    Point3D center = this.getPoint(distance);//the center of our circle is the distance requested from p0
	    Point3D randomP = Point3D.ZERO;
	    double xRandom, yRandom, random;
	    double nDotDirection = alignZero(n.dotProduct(this.getDir()));
	    double r = Math.abs(Math.tan(Math.acos(w.dotProduct(v))));
	    for (int i = 1; i < num; i++)//starts from 1 because there has to be at least one ray(the original)and we already dealt with it
	    {
	        xRandom = random(-1, 1);//random number [-1,1)
	        yRandom = Math.sqrt(1 - Math.pow(xRandom, 2));
	        random = random(-r, r);//random number[-r,r)
	        if (xRandom != 0)//vector cannot be scaled with zero
	            randomP = center.add(w.scale(random));
	        if (yRandom != 0)//vector cannot be scaled with zero
	            randomP = center.add(v.scale(random));
	        Vector t = randomP.subtract(this.getP0());//vector between the random point and the start of the original ray
	        double normalDotT = alignZero(n.dotProduct(t));
	        if (nDotDirection * normalDotT > 0)//if they are both positive or both negative then we need to create a ray with the original p0 and t
	            beam.add(new Ray(this.getP0(), t));
	    }
	    return beam;
	}


}
