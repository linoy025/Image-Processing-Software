package primitives;

/**
 * This class represents a point in three-dimensional space
 * 
 * @author Elinoy Gidoni and Tamara Seban
 */

public class Point3D {

	final Coordinate x;
	final Coordinate y;
	final Coordinate z;

	public static final Point3D ZERO = new Point3D(0, 0, 0);

	/**
	 * constructor
	 * 
	 * @param x - Coordinate x
	 * @param y - Coordinate y
	 * @param z - Coordinate z
	 */
	public Point3D(Coordinate x, Coordinate y, Coordinate z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * constructor
	 * 
	 * @param x - real number
	 * @param y - real number
	 * @param z - real number
	 */

	public Point3D(double x, double y, double z) {
		this.x = new Coordinate(x);
		this.y = new Coordinate(y);
		this.z = new Coordinate(z);

	}

	/**
	 * Get functions
	 * 
	 * @return new Coordinate based on _x , _y, or _z
	 */
	public Coordinate getX() {
		return x;
	}

	public Coordinate getY() {
		return y;
	}

	public Coordinate getZ() {
		return z;
	}

	/**
	 * Adding a vector to a point - Returns a new point
	 * 
	 * @param v - Vector
	 * @return Point3D
	 */
	public Point3D add(Vector v) {
		return new Point3D(this.x.coord + v.head.x.coord, this.y.coord + v.head.y.coord, this.z.coord + v.head.z.coord);
	}

	/**
	 * Vector subtraction - receives a second point in the parameter, returns a
	 * vector from the second point to the point on which the operation is performed
	 * 
	 * @param p - Point3D
	 * @return Vector
	 */
	public Vector subtract(Point3D p) {
		return new Vector(this.x.coord - p.x.coord, this.y.coord - p.y.coord, this.z.coord - p.z.coord);
	}

	/**
	 * The distance between two points squared
	 * 
	 * @param p- Point3D
	 * @return real number
	 */
	public double distanceSquared(Point3D p) {
		double x, y, z;
		x = (this.x.coord - p.x.coord) * (this.x.coord - p.x.coord);
		y = (this.y.coord - p.y.coord) * (this.y.coord - p.y.coord);
		z = (this.z.coord - p.z.coord) * (this.z.coord - p.z.coord);

		return x + y + z;

	}

	/**
	 * Distance between 2 points
	 * 
	 * @param p -Point3D
	 * @return real number
	 */
	public double distance(Point3D p) {

		return Math.sqrt(distanceSquared(p));
	}

	@Override
	public String toString() {
		return " [x=" + x.toString() + ", y=" + y.toString() + ", z=" + z.toString() + "]";
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Point3D))
			return false;
		Point3D other = (Point3D) obj;
		return this.x.equals(other.x) && this.y.equals(other.y) && this.z.equals(other.z);

	}

}
