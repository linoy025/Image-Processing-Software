package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;

import primitives.Vector;

/**
 * This abstract class contains the function getNormal which all geometries implement
 * it.
 * 
 * @author Elinoy Gidoni and Tamara Seban
 */
public abstract class Geometry extends Intersectable {

	/**
	 * fields for abstract class Geometry - the emission color and the material
	 */
	protected Color emission = Color.BLACK;

	private Material material = new Material();

	/**
	 * @return the material
	 */
	public Material getMaterial() {
		return material;
	}

	/**
	 * @param material
	 * @return the material
	 */
	public Geometry setMaterial(Material material) {
		this.material = material;
		return this;

	}

	/**
	 * returns the emission color
	 * 
	 * @return emission color
	 */
	public Color getEmission() {
		return emission;
	}

	/**
	 * @param emission the emission to set
	 * @return Geometry
	 */
	public Geometry setEmission(Color emission) {
		this.emission = emission;
		return this;
	}

	/**
	 * get one parameter of the point type [across the geometric body] and returns
	 * the normal vector (vertical) to the body at this point.
	 * 
	 * @param Point3D - Represents the coordinates of a three-dimensional (3D) data
	 *                point
	 * @return Vector - object in geometry with direction and size
	 */
	public abstract Vector getNormal(Point3D p);

}
