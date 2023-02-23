package primitives;

/**
 * material class -  a substance or mixture of substances that constitutes an object
 *  * @author Elinoy Gidoni and Tamara Seban
 */
public class Material {

	/**
	 * fields for class material Attenuation diffuse, specular
	 */
	public double kD = 0; //diffuse
	public double kS = 0; //specular
	public int nShininess = 0;
	public double kT=0.0; //transparency
	public double kR=0.0; //reflection


	/**
	 * @param kD the kD to set
	 */
	public Material setkD(double kD) {
		this.kD = kD;
		return this;
	}

	/**
	 * @param kS the kS to set
	 */
	public Material setkS(double kS) {
		this.kS = kS;
		return this;
	}

	/**
	 * @param nShininess the nShininess to set
	 */
	public Material setnShininess(int nShininess) {
		this.nShininess = nShininess;
		return this;
	}
	
	/**
	 * @param kT the kT to set
	 */
	public Material setkT(double kT) {
		this.kT = kT;
		return this;
	}/**
	 * @param kR the kR to set
	 */
	public Material setkR(double kR) {
		this.kR = kR;
		return this;
	}
	
	
}
