package primitives;

/**
 * This class represents a vector - fundamental object in geometry with
 * direction and size, defined by the end point (when the starting point -the
 * axes)
 * 
 * @author Elinoy Gidoni and Tamara Seban
 */
public class Vector {

	Point3D head;

	/**
	 * constructor
	 * 
	 * @param x - Coordinate x
	 * @param y - Coordinate y
	 * @param z - Coordinate z
	 */
	public Vector(Coordinate x, Coordinate y, Coordinate z) {

		head = new Point3D(x, y, z);
		if (head.equals(Point3D.ZERO))
			throw new IllegalArgumentException("A vector cannot be a zero vector");

	}

	/**
	 * constructor
	 * 
	 * @param x - real number
	 * @param y - real number
	 * @param z - real number
	 */

	public Vector(double x, double y, double z) {
		head = new Point3D(x, y, z);
		if (head.equals(Point3D.ZERO))
			throw new IllegalArgumentException("A vector cannot be a zero vector");

	}

	/**
	 * constructor
	 * 
	 * @param p - Point3D
	 * @throws - IllegalArgumentException - if the point obtained as a parameter is
	 *           the zero vector
	 */

	public Vector(Point3D p) {
		if (p.equals(Point3D.ZERO))
			throw new IllegalArgumentException("A vector cannot be a zero vector");
		this.head = p;
	}

	/**
	 * Returns the end point of the vector
	 * 
	 * @return the head
	 */
	public Point3D getHead() {
		return head;
	}

	/**
	 * Vector addition (returns a new vector)
	 * 
	 * @param v- Vector
	 * @return Vector
	 */
	public Vector add(Vector v) {
		return new Vector(this.head.add(v));
	}

	/**
	 * Vector subtraction (returns a new vector)
	 * 
	 * @param v- Vector
	 * @return Vector
	 */
	public Vector subtract(Vector v) {
		return this.head.subtract(v.head);
	}

	/**
	 * Vector Multiplier - Scalar (Returns New Vector)
	 * 
	 * @param - real number
	 * @return - Vector
	 */
	public Vector scale(double d) {
		return new Vector(this.head.x.coord * d, this.head.y.coord * d, this.head.z.coord * d);
	}

	/**
	 * Vector multiplication - Returns a new vector that stands for the two existing
	 * vectors
	 * 
	 * @param v - Vector
	 * @return Vector
	 */
	public Vector crossProduct(Vector v) {
		return new Vector(((this.head.y.coord * v.head.z.coord) - (this.head.z.coord * v.head.y.coord)),
				((this.head.z.coord * v.head.x.coord) - (this.head.x.coord * v.head.z.coord)),
				((this.head.x.coord * v.head.y.coord) - (this.head.y.coord * v.head.x.coord)));
	}

	/**
	 * Scalar product
	 * 
	 * @param v- Vector
	 * @return real number
	 */
	public double dotProduct(Vector v) {
		// double d = 0;
		return (this.head.x.coord * v.head.x.coord + this.head.y.coord * v.head.y.coord
				+ this.head.z.coord * v.head.z.coord);
	}

	/**
	 * Calculate the length of the vector squared
	 * 
	 * @return real number
	 */
	public double lengthSquared() {
		return this.dotProduct(this);
	}

	/**
	 * Calculate the length of the vector
	 * 
	 * @return real number
	 */
	public double length() {
		return Math.sqrt(this.dotProduct(this));
	}

	/**
	 * The vector normalization action that will change the vector itself (the only
	 * action that changes the object to which it was summoned). The change is made
	 * by replacing the head point with a new point with updated coordinates. The
	 * operation will also return the vector itself for the purpose of chaining the
	 * operations if necessary
	 * 
	 * @return Vector
	 */
	public Vector normalize() {

		double length = this.length();
		head = new Point3D(this.head.x.coord / length, this.head.y.coord / length, this.head.z.coord / length);
		return this;

	}

	/**
	 * A normalization operation that returns a new vector is normalized in the same
	 * direction as the original vector
	 * 
	 * @return Vector
	 */
	public Vector normalized() {
		Vector v = new Vector(this.head);
		v.normalize();
		return v;

	}

	@Override
	public String toString() {
		return this.head.toString();
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Vector))
			return false;
		Vector other = (Vector) obj;
		return this.head.equals(other.head);

	}
	
	/**
	 * creates a vector  normal  to the vector that calls the function (the dot product of the new vector and the old vector equals zero)
	 * @return normal vector
	 */
	public Vector normalToVector()
	{
	    int min=0;
	    double coordinate;
	 //finding the smallest coordinate of the vector to replace it with 0
	    if(this.getHead().getX().get()>0)
	    {
	        coordinate = this.getHead().getX().get();
	    }
	    else
	        coordinate=-this.getHead().getX().get();
	    if(Math.abs(this.getHead().getY().get())<coordinate)
	    {
	        coordinate=1;
	        if(this.getHead().getY().get()>0)
	            coordinate=this.getHead().getY().get();
	        else
	            coordinate=-this.getHead().getY().get();
	    }
	    if(Math.abs(this.getHead().getZ().get())<coordinate)
	    {
	        coordinate=2;
	        //last coordinate that we are checking so no need to reassign coordinate
	    }
	    if(coordinate==0)//x is the smallest
	        return new Vector(0,-this.getHead().getZ().get(),this.getHead().getY().get()).normalize();
	    if(coordinate==1)//y is the smallest
	  	  return new Vector(-this.getHead().getZ().get(),0,this.getHead().getX().get()).normalize();
	    //z is the smallest
	     return new Vector(this.getHead().getY().get(),-this.getHead().getX().get(),0).normalize();
	}


}
