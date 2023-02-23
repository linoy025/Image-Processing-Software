package renderer;

import java.util.LinkedList;
import java.util.List;

import primitives.Util;
import elements.LightSource;
import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;
import static primitives.Util.alignZero;

/**
 * This class RayTracerBasic extends RayTracerBase
 * 
 * @author Elinoy Gidoni and Tamara Seban
 */
public class RayTracerBasic extends RayTracerBase {

	/**
	 * constants for the base case of recursion
	 */
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	private static final double MIN_CALC_COLOR_K = 0.001;
	private static final double INITIAL_K = 1.0;

	/**
	 * parameters for ray tracing- glossy surface and diffuse glass - they are in
	 * class RayTracerBasic because this class is responsible on ray tracing
	 */
	private int numOfRays;

	private double rayDistance;

	public RayTracerBasic setBvh(boolean sign) {
		this.scene.geometries.setBoxes = sign;
		return this;
	}

	/**
	 * gets the distance we want between the ray point and the circle
	 *
	 * @return double - distance
	 */
	public double getRayDistance() {
		return rayDistance;
	}

	/**
	 * sets the distance between the ray point and the circle
	 *
	 * @param rayDistance
	 * @return this
	 */
	public RayTracerBasic setRayDistance(double rayDistance) {
		if (rayDistance < 0)
			throw new IllegalArgumentException("distance cant be negative");
		this.rayDistance = rayDistance;
		return this;
	}

	/**
	 * get number of rays function
	 *
	 * @return number of rays that will be part of the beam
	 */
	public int getNumOfRays() {
		return numOfRays;
	}

	/**
	 * sets the number of rays that will be part of the beam
	 *
	 * @param numOfRays int - amount of rays that will be part of the beam
	 * @return this
	 */
	public RayTracerBasic setNumOfRays(int numOfRays) {
		if (numOfRays < 1)
			throw new IllegalArgumentException("there has to be at least one ray");
		this.numOfRays = numOfRays;
		return this;
	}

	public RayTracerBasic(Scene scene) {
		super(scene);
	}

	@Override
	public Color traceRay(Ray ray) {
		GeoPoint closestPoint = findClosestIntersection(ray);
		return closestPoint == null ? scene.background : calcColor(closestPoint, ray);

	}

	/**
	 * Calculate the color intensity in a point
	 * 
	 * @param intersection GeoPoint- the point for which the color is required
	 * @param ray          Ray
	 * @param level        - the recursion level
	 * @param k            - double - helps with recursion
	 * @return the color intensity
	 */

	private Color calcColor(GeoPoint intersection, Ray ray, int level, double k) {

		Color color = intersection.geometry.getEmission();
		color = color.add(calcLocalEffects(intersection, ray, k));// add calculated light contribution from all light
																	// sources)
		return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
	}

	/**
	 * Calculates the color at a given point according to local effects
	 * 
	 * @param intersection GeoPoint- the point for which the color is required
	 * @param ray          Ray
	 * @param k            - double - helps with recursion
	 * @return the color intensity
	 */

	private Color calcLocalEffects(GeoPoint intersection, Ray ray, double k) {
		Vector v = ray.getDir();
		Vector n = intersection.geometry.getNormal(intersection.point);
		double nv = Util.alignZero(n.dotProduct(v));
		if (Util.isZero(nv))
			return Color.BLACK;
		Material material = intersection.geometry.getMaterial();
		int nShininess = material.nShininess;
		double kd = material.kD;
		double ks = material.kS;
		Color color = Color.BLACK;
		double transparencyAmount = 0;
		for (LightSource lightSource : scene.lights) {
			Vector l = lightSource.getL(intersection.point);
			double nl = Util.alignZero(n.dotProduct(l));
			if (nl * nv > 0) {// sign(nl) == sing(nv)
				transparencyAmount = transparency(lightSource, l, intersection.geometry.getNormal(intersection.point),
						intersection);
				if (transparencyAmount * k > MIN_CALC_COLOR_K) {
//				if (unshaded(lightSource, l, n, intersection)){// if the geopoint isnt shaded by the light
					Color lightlntensity = lightSource.getIntensity(intersection.point).scale(transparencyAmount);
					color = color.add(calcDiffusive(kd, nl, lightlntensity),
							calcSpecular(ks, l, n, nl, v, nShininess, lightlntensity));
				}

			}
		}
		return color;

	}

	/**
	 * Calculates the color at a given point according to global effects
	 * 
	 * @param geopoint GeoPoint- the point for which the color is required
	 * @param ray      Ray * @param level- the recursion level
	 * @param k        double - helps with recursion
	 * @return the color intensity
	 */

	private Color calcGlobalEffects(GeoPoint geopoint, Ray ray, int level, double k) {
		Color color = Color.BLACK;
		Material material = geopoint.geometry.getMaterial();
		double kr = material.kR, kkr = k * kr;
		Vector normal = geopoint.geometry.getNormal(geopoint.point);
		List<Ray> beam = new LinkedList<>(); //

		if (kkr > MIN_CALC_COLOR_K) {
			Ray reflectedRay = constructReflectedRay(normal, geopoint.point, ray);
			if (this.numOfRays == 0 || this.rayDistance < 0) //
				beam.add(reflectedRay);
			else
				beam = reflectedRay.createBeamOfRays(geopoint.geometry.getNormal(geopoint.point), this.getRayDistance(),
						this.getNumOfRays());//
			primitives.Color tempColorReflection = primitives.Color.BLACK;
			for (Ray r : beam)//
			{
				GeoPoint reflectedPoint = findClosestIntersection(r);// find the closest point to the reflection ray's
																		// p0
				if (reflectedPoint != null)// if such a point exists
				{
					tempColorReflection = tempColorReflection
							.add(calcColor(reflectedPoint, r, level - 1, kr).scale(kr));// calls the recursion th find
																						// the rest of the color and
																						// then scales it with the
																						// reflection
				}
			}
			color = color.add(tempColorReflection.reduce(beam.size()));
		}

		double kt = material.kT, kkt = k * kt;
		if (kkt > MIN_CALC_COLOR_K) {
			Ray refractedRay = constructRefractedRay(normal, geopoint.point, ray);
			if (this.numOfRays == 0 || this.rayDistance < 0)//
				beam.add(refractedRay);
			else
				beam = refractedRay.createBeamOfRays(geopoint.geometry.getNormal(geopoint.point), this.getRayDistance(),
						this.getNumOfRays());
			primitives.Color tempColorRefraction = primitives.Color.BLACK;
			for (Ray r : beam)//
			{
				GeoPoint refraedPoint = findClosestIntersection(r);// find the closest point to the reflection ray's p0
				if (refraedPoint != null)// if such a point exists
				{
					tempColorRefraction = tempColorRefraction.add(calcColor(refraedPoint, r, level - 1, kr).scale(kr));// calls
																														// the
																														// recursion
																														// to
																														// find
																														// the
																														// rest
																														// of
																														// the
																														// color
																														// and
																														// then
																														// scales
																														// it
																														// with
																														// the
																														// refracted

				}
			}
			color = color.add(tempColorRefraction.reduce(beam.size()));
		}
		return color;

	}

	/**
	 * recursion helper function for calcColor- Calculate the color intensity in a
	 * point
	 * 
	 * @param gp  Geopoint for which the color is required
	 * @param ray Ray
	 * @return the color intensity of the requested point
	 */
	private Color calcColor(GeoPoint gp, Ray ray) {
		Color output = calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K);
		output = output.add(scene.ambientLight.getIntensity());
		return output;
	}

	/**
	 * Calculate Specular component of light reflection.
	 *
	 * @param ks         specular component
	 * @param l          direction from light
	 * @param n          normal to surface
	 * @param nl         dot product n*l
	 * @param v          direction from point of view
	 * @param nShininess shininess level
	 * @param ip         light intensity
	 * @return specular light color
	 */
	private Color calcSpecular(double ks, Vector l, Vector n, double nl, Vector v, int nShininess, Color ip) {

		Vector r = l.subtract(n.scale(nl * 2)).normalized();
		double factor = Math.max(0, (v.scale(-1)).dotProduct(r));
		return ip.scale(ks * Math.pow(factor, nShininess));

	}

	/**
	 * Calculate Diffusive component of light reflection.
	 *
	 * @param kd diffusive component
	 * @param nl dot-product n*l
	 * @param ip light intensity at the point
	 * @return diffusive component of light reflection
	 */
	private Color calcDiffusive(double kd, double nl, Color ip) {
		if (nl < 0) // if nl is negative we make it positive
			nl = Math.abs(nl);
		Color scaled = ip.scale(nl * kd);// scales nl with the diffuse component
		return scaled;
	}

	/**
	 * calculates transparency
	 * 
	 * @param light - light source
	 * @param l     Vector
	 * @param n     Vector
	 * @param gp    Geopoint
	 * @return double - the amount of transparency
	 */
	private double transparency(LightSource light, Vector l, Vector n, GeoPoint gp) {
		Vector direction = l.scale(-1);// direction from point to light source
		Ray ray = new Ray(gp.point, direction, n);// creates a new ray with the geopoint and normal received and the
													// light direction
		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);// finds intersections between the
																					// scene's geometries and the new
																					// ray
		if (intersections == null)// if there are no intersections
			return 1.0;
		double distance = light.getDistance(gp.point);// the distance between the light source and the geopoint
		double ktr = 1.0;
		for (GeoPoint g : intersections)// for each intersection point checks if the distance is negative and updates
										// the transparency accordingly
		{
			if (alignZero(g.point.distance(gp.point) - distance) <= 0) {
				ktr *= g.geometry.getMaterial().kT;/// multiplies with the geometry's transparency
				if (ktr < MIN_CALC_COLOR_K)// if the transparency is smaller than the min calc color
				{
					return 0.0;
				}
			}
		}
		return ktr;
	}

	/**
	 * constructing a reflected ray r=v-2*(v*n)*n
	 * 
	 * @param n     Vector Normal
	 * @param p     Point3D of a geopoint
	 * @param inRay Ray
	 * @return the constructed reflected ray
	 */
	private Ray constructReflectedRay(Vector n, Point3D point, Ray inRay) {
		Vector v = inRay.getDir();
		double nv = n.dotProduct(v);
		if (nv == 0)
			return null;
		return new Ray(point, v.subtract(n.scale(2 * nv)), n);
	}

	/**
	 * constructing a refracted ray
	 * 
	 * @param n     Vector Normal
	 * @param point Point3D of geopoint
	 * @param inRay Ray
	 * @return the constructed refracted ray
	 */
	private Ray constructRefractedRay(Vector n, Point3D point, Ray inRay) {
		return new Ray(point, inRay.getDir(), n);
	}

	/**
	 * finds intersectons between a ray geometries and return the geopoint closest
	 * to the ray's p0
	 * 
	 * @param ray Ray that we are checking for intersections with
	 * @return the geopoint closest to p0
	 */
	private GeoPoint findClosestIntersection(Ray ray) {
		if (ray == null || ray.getDir().getHead() == Point3D.ZERO)// so there are no problems with the zero vector
		{
			return null;
		}
		GeoPoint closest = null;// so far the closest point is unknown
		double distance = Double.MAX_VALUE;
		double check;

		List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
		if (intersections == null)// if there are no intersection points
			return null;
		for (GeoPoint gp : intersections)// for each intersection point we check if it is closer to p0 than the previous
											// pints
		{
			check = ray.getP0().distance(gp.point);// distance between p0 and the geopoint
			if (check < distance)// if the distance between the new point and p0 is smaller the previous smallest
									// distance
			{
				distance = check;// updates the distance
				closest = gp;// updates the geopoint
			}
		}
		return closest;
	}
}
