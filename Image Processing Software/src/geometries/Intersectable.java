package geometries;

import java.util.*;
import primitives.Point3D;
import primitives.Ray;

/**
 * This abstract class contains the function findGeoIntsersections which all
 * geometries implement it and internal class for creating boxes.
 * 
 * @author Elinoy Gidoni and Tamara Seban
 */
public abstract class Intersectable {

	/**
	 * parameter for using BVH feature
	 */
	public boolean setBoxes = false;

	/**
	 * inside helper class Bounding Box
	 */
	public class BoundingBox {
		// 1 is the min value of the coordinate and 2 is the max , we start them at + -
		// infinity
		public double x1 = Double.NEGATIVE_INFINITY;
		public double x2 = Double.POSITIVE_INFINITY;
		public double y1 = Double.NEGATIVE_INFINITY;
		public double y2 = Double.POSITIVE_INFINITY;
		public double z1 = Double.NEGATIVE_INFINITY;
		public double z2 = Double.POSITIVE_INFINITY;

	}

	// calls the default constructor to build a box for each intersectable shape
	protected BoundingBox box = new BoundingBox();

	/**
	 * checks if a ray intersects the box around a geometry
	 * 
	 * @param r the ray we are checking for an intersection with
	 * @return
	 */
	public boolean intersects(Ray r) {
		double xP = r.getP0().getX().get();
		double xD = r.getDir().getHead().getX().get();
		double yP = r.getP0().getY().get();
		double yD = r.getDir().getHead().getY().get();
		double zP = r.getP0().getZ().get();
		double zD = r.getDir().getHead().getZ().get();
		double txmin = (box.x1 - xP / xD); // starting min x
		double txmax = (box.x2 - xP / xD);// starting max x
		double temp;
		if (txmin > txmax) // if min is bigger we need to swap them so max will have the bigger value
		{
			temp = txmin;
			txmin = txmax;
			txmax = temp;
		}

		double tymin = (box.y1 - yP / yD);// min y
		double tymax = (box.y2 - yP / yD);// min x

		if (tymin > tymax) // if min is bigger we need to swap them so max will have the bigger value
		{
			temp = tymin;
			tymin = tymax;
			tymax = temp;
		}
		if ((txmin > tymax) || (tymin > txmax)) // if min x is bigger than max y or min y is bigger than max x there
												// cant be an intersection with the box
			return false;

		if (tymin > txmin) // if y min is bigger than x min then it takes its place
			txmin = tymin;

		if (tymax < txmax)// if y max is bigger than x max then it takes its place
			txmax = tymax;

		double tzmin = (box.z1 - zP / zD);// z min
		double tzmax = (box.z2 - zP / zD); // z max

		if (tzmin > tzmax) // if min is bigger we need to swap them so max will have the bigger value
		{
			temp = tzmin;
			tzmin = tzmax;
			tzmax = temp;
		}

		if ((txmin > tzmax) || (tzmin > txmax)) // if min is bigger than max z or min z is bigger than max there cant be
												// an intersection with the box
			return false;

		return true; // if the ray intersects the box

	}

	/**
	 * find intersection function
	 *
	 * @param ray Ray
	 * @return list of geoPoint intersections between a shape and a ray
	 */
	public List<GeoPoint> getfindIntersections(Ray ray) {
		if (!setBoxes || intersects(ray))
			return findGeoIntersections(ray);
		return null;
	}

	/**
	 * @param Ray - Represents group of points on a straight line that are on one
	 *            relative side To a given point
	 * @return List<GeoPoint> - List of intersection points with the ray obtained as
	 *         a parameter
	 */
	abstract List<GeoPoint> findGeoIntersections(Ray ray);

	/**
	 * static helper class geoPoint
	 */
	public static class GeoPoint {
		public Geometry geometry;
		public Point3D point;

		/**
		 * constructor for static helper class geoPoint
		 *
		 * @param geometry geometric shape
		 * @param point    Point3D point
		 */
		public GeoPoint(Geometry geometry, Point3D point) {
			this.geometry = geometry;
			this.point = point;
		}

		/**
		 * equals function for static helper class geoPoint
		 *
		 * @param o geoPoint
		 * @return if the geoPoints are equal
		 */
		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			GeoPoint geoPoint = (GeoPoint) o;
			return Objects.equals(geometry, geoPoint.geometry) && Objects.equals(point, geoPoint.point);
		}
	}
}
