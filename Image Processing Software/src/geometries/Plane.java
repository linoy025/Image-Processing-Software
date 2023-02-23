package geometries;

import java.util.*;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import primitives.Util;

/**
 * This class represents a Plane - In geometry, a plane is a fundamental
 * concept, reflecting the two-dimensional object of the base
 * 
 * @author Elinoy Gidoni and Tamara Seban
 */
public class Plane extends Geometry {

	Point3D q0;
	Vector normal;

	/**
	 * constructor
	 * 
	 * @param q0     - Point3D
	 * @param normal - Vector
	 */
	public Plane(Point3D q0, Vector normal) {
		this.q0 = q0;
		this.normal = normal;
	}

	/**
	 * constructor
	 * 
	 * @param a - Point3D
	 * @param b - Point3D
	 * @param c - Point3D
	 */
	public Plane(Point3D a, Point3D b, Point3D c) {

		Vector v1 = (a.subtract(b));
		Vector v2 = (a.subtract(c));
		Vector v3 = (v1.crossProduct(v2));

		this.q0 = a;
		this.normal = v3.normalize();

	}

	/**
	 * returns normal - vector
	 * 
	 * @return normal
	 */
	public Vector getNormal() {
		// TODO Auto-generated method stub
		return normal;
	}

	@Override
	public Vector getNormal(Point3D p) {
		return normal;
	}

	@Override
	public String toString() {
		return "Plane [q0=" + q0 + ", normal=" + normal + "]";
	}

	/**
	 * finds intersections between a ray and a plane
	 * 
	 * @param ray the ray we are looking for intersections with
	 * @return list of GeoPoints that intersect with the plane
	 */
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray) {
		Vector v;
		try {
			v = q0.subtract(ray.getP0());
		} catch (IllegalArgumentException e) {
			return null; // ray starts from point Q - no intersections
		}

		double nv = normal.dotProduct(ray.getDir());
		if (Util.isZero(nv)) // ray is parallel to the plane - no intersections
			return null;

		double t = Util.alignZero(normal.dotProduct(v) / nv);
		if (t <= 0)// there are no intersections
			return null;

		GeoPoint point = new Intersectable.GeoPoint(this, ray.getPoint(t));
		return List.of(point);// returns list of points intersections
	}

}
