package geometries;

import java.util.*;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import primitives.Util;

/**
 * This class represents a triangle. Triangle is a polygon with three sides. The
 * triangle has three angles and three vertices.
 * 
 * @author Elinoy Gidoni and Tamara Seban
 */
public class Triangle extends Polygon {

	/**
	 * constructor
	 * 
	 * @param a - Point3D
	 * @param b - Point3D
	 * @param c - Point3D
	 */
	public Triangle(Point3D a, Point3D b, Point3D c) {
		super(a, b, c);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Triangle []";
	}

	/**
	 * finds intersections between a ray and a triangle
	 * 
	 * @param ray -the ray that we are checking to see if it intersects with the
	 *            triangle
	 * @return list of GeoPoints that intersect with the triangle
	 */
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray) {
		List<GeoPoint> planeIntersections = plane.findGeoIntersections(ray);// finds the intersections with the plane
		if (planeIntersections == null) // if there are no intersections with the plane then there is no intersection
										// with the triangle
			return null;

		Point3D p0 = ray.getP0();// beginning point of ray
		Vector v = ray.getDir();// direction of ray

		Vector v1 = vertices.get(0).subtract(p0);// p0 subtracted from first point in triangle
		Vector v2 = vertices.get(1).subtract(p0);// p0 subtracted from second point in triangle
		Vector v3 = vertices.get(2).subtract(p0);// p0 subtracted from third point in triangle
		Vector v4 = v1.crossProduct(v2);// v1 cross product v2
		double s1 = v.dotProduct(v4);// direction cross product v4
		if (Util.isZero(s1)) // if it equals zero there are no intersection points
			return null;
		Vector v5 = v2.crossProduct(v3);
		double s2 = v.dotProduct(v5);
		if (Util.isZero(s2))
			return null;
		Vector v6 = v3.crossProduct(v1);
		double s3 = v.dotProduct(v6);
		if (Util.isZero(s3))
			return null;

		if ((s1 > 0 && s2 > 0 && s3 > 0) || (s1 < 0 && s2 < 0 && s3 < 0))// if all the dot product results are positive
																			// or if all of them are negative
		{

			for (GeoPoint geo : planeIntersections)// adds all the plane intersection points to the list of results
			{
				geo.geometry = this;

			}
			return planeIntersections;
		}

		return null;// no intersection points
	}

}
