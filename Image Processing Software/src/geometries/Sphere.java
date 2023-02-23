package geometries;

import java.util.*;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import primitives.Util;

/**
 * This class represents a sphere - a geometric body composed of points in space
 * whose distance from a fixed point is at most a certain fixed number, called a
 * radius.
 * 
 * @author Elinoy Gidoni and Tamara Seban
 */
public class Sphere extends Geometry {

	Point3D center;
	double radius;

	
	/**
	 * constructor
	 * 
	 * @param center - Point3D
	 * @param radius - The section connecting the center of a circle with a dot on
	 *               its circumference
	 */
	public Sphere(Point3D center, double radius) {
		this.center = center; 
		this.radius = radius;
		//sets the bounding box parameters for BVH
      if(this.setBoxes   ==true) {
          this.box.x1 = center.getX().get() - radius; // x min
          this.box.x2 = center.getX().get() + radius; //x max
          this.box.y1 = center.getY().get() - radius; // y min
          this.box.y2 = center.getY().get() + radius; // y max
          this.box.z1 = center.getZ().get() - radius; // z min
          this.box.z2 = center.getZ().get() + radius; // z max
      }
		
	}

	@Override
	public Vector getNormal(Point3D p) {
		Vector v = p.subtract(center);
		return v.normalize();
	}

	@Override
	public String toString() {
		return "Sphere [" + this.center.toString() + " radius=" + radius + "]";
	}

	/**
	 * finds intersections between a ray and a sphere
	 * 
	 * @param ray - to check if it intersects with the sphere
	 * @return the intersection GeoPoints
	 */
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray) {

		Point3D p0 = ray.getP0();// beginning point of ray
		Vector v = ray.getDir();// direction of ray
		Vector u;
		try// checking if the center of the sphere and p0 are the same point
		{
			u = center.subtract(p0);
		} catch (IllegalArgumentException e)// zero vector
		{

			return List.of(new GeoPoint(this, (ray.getPoint(this.radius))));// geopoint with Point3D that is target
																			// point of the radius
		}
		double tm = Util.alignZero(v.dotProduct(u));// direction of ray dot product vector u
		double dSquared;
		if (tm == 0)
			dSquared = u.lengthSquared();// the length of vector u squared
		else
			dSquared = u.lengthSquared() - tm * tm;// the length of vector u squared minus v dot product u squared
		double thSquared = Util.alignZero(this.radius * this.radius - dSquared);

		if (thSquared <= 0)// if thsquared is smaller than or equal to zero there are no intersection
							// points
			return null;

		double th = Util.alignZero(Math.sqrt(thSquared));// square root if thSquared
		if (th == 0)// if the root if thsquared equals zero there are no intersection points
			return null;

		double t1 = Util.alignZero(tm - th);
		double t2 = Util.alignZero(tm + th);
		if (t1 <= 0 && t2 <= 0)// if they are both smaller of equal to zero there are no intersection points
			return null;
		if (v.getHead() == Point3D.ZERO)
			return null;
		if (t1 > 0 && t2 > 0)// if they are both bigger than zero
		{
			return List.of(new GeoPoint(this, (ray.getPoint(t1)))// the target point with t1
					, new GeoPoint(this, (ray.getPoint(t2))));// the target point with t2
		}
		if (t1 > 0)// if only t1 is bigger than zero
			return List.of(new GeoPoint(this, (ray.getPoint(t1))));
		else if (t2 > 0)// if t2 is positive
			return List.of(new GeoPoint(this, (ray.getPoint(t2))));

		return null;// no intersection points
	}

}
