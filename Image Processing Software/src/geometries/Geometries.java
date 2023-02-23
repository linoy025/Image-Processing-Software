package geometries;

import java.util.*;
import primitives.Ray;

/**
 * geometries class that extends the intersectable abstract class.
 * 
 * @author Elinoy Gidoni and Tamara Seban
 */
public class Geometries extends Intersectable {
	/**
	 * field for class Geometries - list of intersectable geometries
	 */

	private List<Intersectable> geometriesList;

	private List<Geometries> geoList = new LinkedList<>();

	/**
	 * default constructor for class geometries
	 */
	public Geometries() {
		// for BVH
		if (this.setBoxes == true) {
			// makes them the opposite of what they should be so we can build boxes by
			// checking if there is a bigger max or smaller min
			this.box.x2 = Double.NEGATIVE_INFINITY;
			this.box.x1 = Double.POSITIVE_INFINITY;
			this.box.y2 = Double.NEGATIVE_INFINITY;
			this.box.y1 = Double.POSITIVE_INFINITY;
			this.box.z2 = Double.NEGATIVE_INFINITY;
			this.box.z1 = Double.POSITIVE_INFINITY;
		}
		geometriesList = new ArrayList<>();// creates a new list for intersectable geometries
	}

	/**
	 *
	 * constructor for class Geometries
	 * 
	 * @param geometries - list of intersectable geometries to add to the list of
	 *                   intersectables
	 */
	public Geometries(Geometries... geometriesIntersections) {
		this();
		add(geometriesIntersections);
	}

	/**
	 * adds new shapes to the geometry list and sets the bounding boxes which are
	 * required
	 * 
	 * @param geometries - list of intersectable geometries
	 */
	public void add(Intersectable... geometries) {

		for (Intersectable geo : geometries)// adds each of the geometries the function received to to the list of
											// intersectable geometries
		{
			geometriesList.add(geo);
			// sets the bounding boxes between shapes - if there is a bigger max value of
			// smaller min value for one of the coordinates than what we already have we
			// will switch it
			if (this.setBoxes == true) {
				if (geo.box.x2 > this.box.x2)
					this.box.x2 = geo.box.x2;
				if (geo.box.x1 < this.box.x1)
					this.box.x1 = geo.box.x1;
				if (geo.box.y2 > this.box.y2)
					this.box.y2 = geo.box.y2;
				if (geo.box.y1 < this.box.y1)
					this.box.y1 = geo.box.y1;
				if (geo.box.z2 > this.box.z2)
					this.box.z2 = geo.box.z2;
				if (geo.box.z1 < this.box.z1)
					this.box.z1 = geo.box.z1;

			}
		}

	}

	/**
	 * finds intersections between a list of geometries and a ray
	 * 
	 * @param ray - to find intersections with
	 * @return list of intersection points from a list of geometries and a ray
	 */
	@Override
	public List<GeoPoint> findGeoIntersections(Ray ray) {
		List<GeoPoint> intersections = new LinkedList<GeoPoint>();

		for (Intersectable geo : geometriesList) // finds the intersection points for each geometry and adds
													// them to the list of geopoints to be returned
		{
			if ((this.setBoxes == true && geo.intersects(ray)) || this.setBoxes == false) {
				List<GeoPoint> tempIntersections = geo.findGeoIntersections(ray);
				if (tempIntersections != null) // if intersection points were found we need to add them to the list
				{
					intersections.addAll(tempIntersections);
				}
			}
		}
		if (intersections.size() == 0)// if none of the shapes have intersection points with the ray null is returned
			return null;

		return intersections;

	}

	/**
	 * recursive function to check for intersections with the bvh tree
	 * 
	 * @param ray- the ray we are checking for an intersection with
	 * @return list of intersection points
	 */
	public List<GeoPoint> bvhTree(Ray ray) {
		if (this.geoList.size() == 0)// if there are no boxes left to check
		{
			return this.findGeoIntersections(ray);// we need to check for an intersection with the geometry
		} else {
			if (this.intersects(ray))// if there is an intersection with a ray
			{
				for (Geometries geo : this.geoList)// for each geometry in the box that we found an intersection with
				{
					geo.bvhTree(ray);// recursive call
				}
			}
		}
		return null;
	}

}
