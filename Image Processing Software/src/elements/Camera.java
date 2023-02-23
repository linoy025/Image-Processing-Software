package elements;

import primitives.Vector;
import primitives.Util;

import primitives.Point3D;
import primitives.Ray;

/**
 * Class Camera
 * 
 * @author Elinoy Gidoni and Tamara Seban
 */
public class Camera {

	/**
	 * fields for class camera - camera's location point, three vectors,width,height
	 * and distance
	 */
	private Point3D p0;
	private Vector vUp;
	private Vector vTo;
	private Vector vRight;
	private double width;
	private double height;
	private double distance;

	/**
	 * get p0 function
	 * 
	 * @return camera's location point
	 */
	public Point3D getP0() {
		return p0;
	}

	/**
	 *
	 * get vUp function
	 * 
	 * @return camera's up direction
	 */
	public Vector getVUp() {
		return vUp;
	}

	/**
	 * get vTo function
	 * 
	 * @return camera's towards direction
	 */
	public Vector getVTo() {
		return vTo;
	}

	/**
	 * f get vRight function
	 * 
	 * @return camera's right direction
	 */
	public Vector getVRight() {
		return vRight;
	}

	/**
	 * constructor for class camera
	 * 
	 * @param p0  - camera's location
	 * @param vUp - camera's up direction
	 * @param vTo - camera's towards direction
	 */
	public Camera(Point3D p0, Vector vTo, Vector vUp) {

		if (vUp.dotProduct(vTo) != 0)
			throw new IllegalArgumentException("vUp and vTo need to be orthogonal");
		// all vectors need to be normalized
		this.vUp = vUp.normalized();
		this.vTo = vTo.normalized();
		this.p0 = p0;
		vRight = this.vTo.crossProduct(this.vUp).normalize();

	}

	/**
	 * set view plane size
	 * 
	 * @param width  - real number
	 * @param height - real number
	 * @return camera
	 */

	public Camera setViewPlaneSize(double width, double height) {
		this.width = width;
		this.height = height;
		return this;

	}

	/**
	 * set distance
	 * 
	 * @param distance - real number
	 * @return camera
	 */
	public Camera setDistance(double distance) {
		this.distance = distance;
		return this;
	}

	/**
	 * construct ray through pixel
	 * 
	 * @param nX - amount of columns (row width), positive number
	 * @paramn nY - number of rows (column height),positive number
	 * @param j - pixels column, positive number
	 * @param i - Pixel row, positive number
	 * @return the ray through the pixel's center
	 */
	public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {

		if (Util.isZero(this.distance))// the distance between the camera and the view plane cannot be zero
		{
			throw new IllegalArgumentException("distance cannot be 0");
		}
		Vector v = vTo.scale(this.distance);// vTo scaled by distance
		Point3D Pc = p0.add(v);// adds the new vector to the starting point of the ray

		double Ry = height / nY;// height divided by view plane row pixels
		double Rx = width / nX;// width divided by view plane column pixels

		double yi = -(i - ((nY - 1) / 2d)) * Ry; // (row pixels minus half of the view plane column pixels)*Ry +Ry/2
		double xj = (j - ((nX - 1) / 2d)) * Rx; // (column pixels minus half of the view plane row pixels)*Rx +Rx/2

		Point3D Pij = Pc;// ray stating point + vTo scaled by distance

		if (!Util.isZero(xj))// vRight need to be scaled with xj so it cannot be zero
		{
			Vector w = vRight.scale(xj);
			Pij = Pij.add(w);
		}
		if (!Util.isZero(yi))// vUp need to be scaled with yi so it cannot be zero
		{
			Vector u = vUp.scale(yi);
			Pij = Pij.add(u);
		}

		Vector Vij = Pij.subtract(p0);

		return new Ray(p0, Vij);// returns a new ray with the same starting point and a different vector

	}

}
