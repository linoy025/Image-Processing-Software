package geometries;

import java.util.*;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * This class represents a cylinder. In geometry, a cylinder is the geometric
 * place of all the points in space, which are at a fixed distance, the radius
 * of the cylinder, some plane, the axis of the cylinder.
 * 
 * @author Elinoy Gidoni and Tamara Seban
 */
public class Cylinder extends Tube {

	double height;

	/**
	 * constructor
	 * 
	 * @param axisRay - ray
	 * @param radius  - The section connecting the center of a circle with a dot on
	 *                its circumference
	 * @param height  - The vertical that connects the bases.
	 */
	public Cylinder(Ray axisRay, double radius, double height) {
		super(axisRay, radius);
		this.height = height;
	}

	@Override
	public Vector getNormal(Point3D p) {
		// subtracts the beginning of the axis ray from point3D p
		Vector u = p.subtract(this.axisRay.getP0());
		// dot product between the axis ray direction and the distance between the
		// beginning of the axis ray from point3D p
		double t = this.axisRay.getDir().dotProduct(u);
		// scales the direction vector with v and adds it to the begging point of the
		// axis ray
		Point3D point = this.axisRay.getP0().add(this.axisRay.getDir().scale(t));
		// subtracts the new point we created from the point received in the function
		// and creates a new vector for it
		Vector n = p.subtract(point);
		// returns the new vector normalized
		return n.normalize();
	}

	@Override
	public String toString() {
		return super.toString() + "height= " + height;
	}

	/**
	 * finds intersections of a cylinder and a ray
	 * 
	 * @param ray
	 * @return list of the intersected points
	 */
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray) {
		return super.findGeoIntersections(ray);// calls the tube find intersections
	}

}
