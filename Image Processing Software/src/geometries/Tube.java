package geometries;

import java.util.List;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * This class represents a tube. A tube is the geometric place of all the points
 * in space, which are at a fixed distance, the radius of the cylinder, some
 * plane, the axis of the cylinder. It is a smooth infinite surface, with the
 * curvature at each point on its surface fixed.
 * 
 * @author Elinoy Gidoni and Tamara Seban
 */
public class Tube extends Geometry {

	Ray axisRay;
	double radius;

	/**
	 * constructor
	 * 
	 * @param axisRay - Ray
	 * @param radius  - The section connecting the center of a circle with a dot on
	 *                its circumference
	 */
	public Tube(Ray axisRay, double radius) {
		this.axisRay = axisRay;
		this.radius = radius;
	}

	@Override
	public Vector getNormal(Point3D p) {
		// vector from point of the cylinder to the given point
		Vector v1 = p.subtract(axisRay.getP0());
		// projection multiply by direction unit vector
		double t = v1.dotProduct(axisRay.getDir());
		// scales the direction vector of the axis ray with the projection
		Point3D o = axisRay.getP0().add(axisRay.getDir().scale(t));
		Vector v = p.subtract(o);
		// returns the new vector normalized
		return v.normalize();
	}

	@Override
	public String toString() {
		return this.axisRay.toString() + "radius=" + radius;
	}

	/**
	 * finds the intersection points between a ray and a tube
	 * 
	 * @param ray
	 * @return list of intersection points
	 */
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray) {
		return null;
	}
}
