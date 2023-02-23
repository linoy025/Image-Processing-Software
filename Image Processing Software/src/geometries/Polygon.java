package geometries;

import java.util.List;

import primitives.*;
import static primitives.Util.*;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 * 
 * @author Dan
 */
public class Polygon extends Geometry {
	/**
	 * List of polygon's vertices
	 */
	protected List<Point3D> vertices;
	/**
	 * Associated plane in which the polygon lays
	 */
	protected Plane plane;

	/**
	 * Polygon constructor based on vertices list. The list must be ordered by edge
	 * path. The polygon must be convex.
	 * 
	 * @param vertices list of vertices according to their order by edge path
	 * @throws IllegalArgumentException in any case of illegal combination of
	 *                                  vertices:
	 *                                  <ul>
	 *                                  <li>Less than 3 vertices</li>
	 *                                  <li>Consequent vertices are in the same
	 *                                  point
	 *                                  <li>The vertices are not in the same
	 *                                  plane</li>
	 *                                  <li>The order of vertices is not according
	 *                                  to edge path</li>
	 *                                  <li>Three consequent vertices lay in the
	 *                                  same line (180&#176; angle between two
	 *                                  consequent edges)
	 *                                  <li>The polygon is concave (not convex)</li>
	 *                                  </ul>
	 */
	public Polygon(Point3D... vertices) {
		if (vertices.length < 3)
			throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
		this.vertices = List.of(vertices);
		// Generate the plane according to the first three vertices and associate the
		// polygon with this plane.
		// The plane holds the invariant normal (orthogonal unit) vector to the polygon
		plane = new Plane(vertices[0], vertices[1], vertices[2]);
		if (vertices.length == 3)
			return; // no need for more tests for a Triangle

		Vector n = plane.getNormal();

		// Subtracting any subsequent points will throw an IllegalArgumentException
		// because of Zero Vector if they are in the same point
		Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
		Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

		// Cross Product of any subsequent edges will throw an IllegalArgumentException
		// because of Zero Vector if they connect three vertices that lay in the same
		// line.
		// Generate the direction of the polygon according to the angle between last and
		// first edge being less than 180 deg. It is hold by the sign of its dot product
		// with
		// the normal. If all the rest consequent edges will generate the same sign -
		// the
		// polygon is convex ("kamur" in Hebrew).
		boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
		for (int i = 1; i < vertices.length; ++i) {
			// Test that the point is in the same plane as calculated originally
			if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
				throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
			// Test the consequent edges have
			edge1 = edge2;
			edge2 = vertices[i].subtract(vertices[i - 1]);
			if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
				throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
		}
		
		 //calculates the box parameters for BVH
        if(this.setBoxes==true) {
            //starting points
            box.x1 = this.vertices.get(0).getX().get();
            box.x2 = this.vertices.get(0).getX().get();
            box.y1 = this.vertices.get(0).getY().get();
            box.y2 = this.vertices.get(0).getY().get();
            box.z1 = this.vertices.get(0).getZ().get();
            box.z2 = this.vertices.get(0).getZ().get();

            for (int i = 1; i < this.vertices.size(); i++) {
                if (this.vertices.get(i).getX().get() < box.x1)//checks for min x
                    box.x1 = this.vertices.get(i).getX().get();
                if (this.vertices.get(i).getY().get() < box.y1)//checks for min y
                    box.y1 = this.vertices.get(i).getY().get();
                if (this.vertices.get(i).getZ().get() < box.z1)//checks for min z
                    box.z1 = this.vertices.get(i).getZ().get();
                if (this.vertices.get(i).getX().get() > box.x2)//checks for max x
                    box.x2 = this.vertices.get(i).getX().get();
                if (this.vertices.get(i).getY().get() > box.y2)//checks for max y
                    box.y2 = this.vertices.get(i).getY().get();
                if (this.vertices.get(i).getZ().get() > box.z2)//checks for max z
                    box.z2 = this.vertices.get(i).getZ().get();
            }
        }
	}

	@Override
	public Vector getNormal(Point3D point) {
		return plane.getNormal();
	}

	/**
	 * finds intersections between a ray and a polygon
	 * 
	 * @param ray the ray we are looking for intersection points with
	 * @return list of GeoPoints that intersect with the polygon
	 */
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray) {

		List<GeoPoint> planeIntersections = plane.findGeoIntersections(ray);
		if (planeIntersections == null)// if there are no intersections with the plane there wont be any with the
										// polygon
			return null;

		Point3D p0 = ray.getP0();
		Vector v = ray.getDir();

		Vector v1 = vertices.get(1).subtract(p0);// the starting point of the ray subtracted from the second point3D in
													// the list of vertices
		Vector v2 = vertices.get(0).subtract(p0);// the starting point of the ray subtracted from the first point3D in
													// the list of vertices
		Vector v3 = v1.crossProduct(v2);// cross product between the two vectors we just calculated
		double sign = v.dotProduct(v3);// dot product between the ray direction and v3 that we just calculated
		if (isZero(sign))// if the sign is zero there are no intersection points
			return null;

		boolean positive = sign > 0;// true if sign is bigger than zero, else false
		Vector v4;
		double resultt;
		for (int i = vertices.size() - 1; i > 0; --i) {
			v1 = v2;
			v2 = vertices.get(i).subtract(p0);// starting point of ray subtracted from the current point3d from list
			v4 = v1.crossProduct(v2);// current v2 cross product previous v2
			resultt = v.dotProduct(v4);// dot product between v4 and the ray direction
			sign = alignZero(resultt);
			if (isZero(sign))// if the dot product is zero there are no intersections
				return null;
			if (positive != (sign > 0))// if the sign isnt bigger than zero there are no intersection points
				return null;
		}

		for (GeoPoint geo : planeIntersections)// creates a new geopoint for each one in the plane intersections list
		{
			geo.geometry = this;
		}
		return planeIntersections;// list of geopoint intersections

	}

}
