package unittests;

import geometries.Triangle;
import org.junit.Test;

import elements.*;
import geometries.Plane;
import geometries.Sphere;
import primitives.*;
import renderer.*;
import scene.Scene;

public class Bvhtest {
	/**
	 * Testing for ray tracing acceleration
	 * 
	 * @author Elinoy Gidoni and Tamara Seban
	 */

	@Test
	public void rayTracingTestUsingBvh() {

		Scene scene = new Scene("Test scene");
		Camera camera = new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)) //
				.setViewPlaneSize(200, 200).setDistance(500);

		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.05));

		scene.geometries.add(
				
				 new Plane(new Point3D(0, 0, 210), new Vector(0, 0, -1)) .setEmission(Color.BLACK).setMaterial(new
				   Material().setkD(0.2).setkS(0.2).setnShininess(5).setkT(0).setkR(0.4)),
				

				new Sphere(new Point3D(145, 0, -75), 30) //
						.setEmission(new Color(0, 0, 255)) // dark blue
						.setMaterial(new Material().setkD(0.3).setkS(0.4).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(0, 75, -75), 30).setEmission(new Color(0, 0, 255)) // dark blue
						.setMaterial(new Material().setkD(0.3).setkS(0.4).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(75, 120, -75), 20) //
						.setEmission(new Color(0, 0, 255)) // dark blue
						.setMaterial(new Material().setkD(0.2).setkS(0.4).setnShininess(5).setkT(0.8).setkR(0)),

				new Sphere(new Point3D(143, -140, -75), 20) //
						.setEmission(new Color(170, 169, 173)) // silver
						.setMaterial(new Material().setkD(0.3).setkS(0.4).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(-100, 0, -75), 30).setEmission(new Color(170, 169, 173)) // silver
						.setMaterial(new Material().setkD(0.3).setkS(0.4).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(70, -175, -75), 40).setEmission(new Color(170, 169, 173)) // silver
						.setMaterial(new Material().setkD(0.3).setkS(0.4).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(-100, 120, -75), 20).setEmission(new Color(170, 169, 173)) // silver
						.setMaterial(new Material().setkD(0.3).setkS(0.4).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(-150, 145, -75), 20).setEmission(new Color(170, 169, 173)) // silver
						.setMaterial(new Material().setkD(0.3).setkS(0.4).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(0, 0, 0), 20).setEmission(new Color(255, 0, 0)) // orange
						.setMaterial(new Material().setkD(0.25).setkS(0.3).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(0, 0, 0), 30).setEmission(new Color(255, 164, 71)) // orange
						.setMaterial(new Material().setkD(0.1).setkS(0.3).setnShininess(5).setkT(0.8).setkR(0)),

				new Sphere(new Point3D(-146, 75, 75), 40).setEmission(new Color(255, 164, 71)) // orange
						.setMaterial(new Material().setkD(0.1).setkS(0.3).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(80, 180, 60), 25).setEmission(new Color(255, 164, 71)) // orange
						.setMaterial(new Material().setkD(0.25).setkS(0.3).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(178, 100, -60), 25).setEmission(new Color(255, 164, 71)) // orange
						.setMaterial(new Material().setkD(0.25).setkS(0.3).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(50, 180, -60), 25).setEmission(new Color(255, 164, 71)) // orange
						.setMaterial(new Material().setkD(0.25).setkS(0.3).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(99, 130, 90), 25).setEmission(new Color(204, 0, 0)) // red
						.setMaterial(new Material().setkD(0.25).setkS(0.3).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(-200, 200, 100), 15).setEmission(new Color(0, 0, 102)) // green
						.setMaterial(new Material().setkD(0.3).setkS(0.4).setnShininess(5).setkT(0.22).setkR(0)),

				new Sphere(new Point3D(75, 165, 100), 15).setEmission(new Color(0, 0, 102)) // green
						.setMaterial(new Material().setkD(0.3).setkS(0.4).setnShininess(5).setkT(0.22).setkR(0)),

				new Sphere(new Point3D(-75, -165, 100), 15).setEmission(new Color(0, 0, 102)) // green
						.setMaterial(new Material().setkD(0.3).setkS(0.4).setnShininess(5).setkT(0.22).setkR(0)),

				new Sphere(new Point3D(-80, 50, 200), 50).setEmission(new Color(204, 124, 244)) // big blue
						.setMaterial(new Material().setkD(0.3).setkS(0.2).setnShininess(5).setkT(0).setkR(0)),

				new Sphere(new Point3D(-140, -80, -20), 50).setEmission(new Color(255, 77, 77)) // pink peach
						.setMaterial(new Material().setkD(0.3).setkS(0.2).setnShininess(5).setkT(0).setkR(0)),

				new Sphere(new Point3D(-33, -30, 200), 50).setEmission(new Color(255, 77, 77)) // big blue
						.setMaterial(new Material().setkD(0.3).setkS(0.2).setnShininess(5).setkT(0).setkR(0)),

				new Sphere(new Point3D(75, -50, 200), 50).setEmission(new Color(0, 0, 153)) // big teal
						.setMaterial(new Material().setkD(0.3).setkS(0.2).setnShininess(5).setkT(0).setkR(0)),

				new Sphere(new Point3D(-54, 67, 200), 50).setEmission(new Color(0, 0, 153)) // big teal
						.setMaterial(new Material().setkD(0.3).setkS(0.2).setnShininess(5).setkT(0).setkR(0)),

				new Sphere(new Point3D(100, -36, 200), 50).setEmission(new Color(0, 0, 153)) // big teal
						.setMaterial(new Material().setkD(0.3).setkS(0.2).setnShininess(5).setkT(0).setkR(0)),

				new Triangle(new Point3D(1500, 1500, 1500), new Point3D(-1500, -1500, 1500),
						new Point3D(670, -670, -3000)).setEmission(new Color(20, 20, 20))
								.setMaterial(new Material().setkD(0).setkS(0).setnShininess(0).setkT(0).setkR(1)),

				new Triangle(new Point3D(1500, 1500, 1500), new Point3D(-1500, -1500, 1500),
						new Point3D(-1500, 1500, 200)).setEmission(new Color(20, 20, 20))
								.setMaterial(new Material().setkD(0).setkS(0).setnShininess(0).setkT(0).setkR(0.5)),

				new Sphere(new Point3D(76, 66, -65), 25).setEmission(new Color(255, 164, 71)) // orange
						.setMaterial(new Material().setkD(0.25).setkS(0.3).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(-145, 71, 35), 50).setEmission(new Color(0, 128, 85)) // big blue
						.setMaterial(new Material().setkD(0.3).setkS(0.2).setnShininess(5).setkT(0).setkR(0)),

				new Sphere(new Point3D(98, 56, 146), 50).setEmission(new Color(0, 0, 153)) // big teal
						.setMaterial(new Material().setkD(0.3).setkS(0.2).setnShininess(5).setkT(0).setkR(0)),

				new Sphere(new Point3D(-67, 34, -12), 20).setEmission(new Color(170, 169, 173)) // silver
						.setMaterial(new Material().setkD(0.3).setkS(0.4).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(-16, 56, 5), 15).setEmission(new Color(0, 0, 102)) // green
						.setMaterial(new Material().setkD(0.3).setkS(0.4).setnShininess(5).setkT(0.22).setkR(0)),

				new Triangle(new Point3D(-150, 150, 200), new Point3D(150, 150, 35), new Point3D(75, -75, 200))
						.setEmission(Color.BLACK)
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60).setkT(0).setkR(0)),

				new Triangle(new Point3D(-150, 150, 115), new Point3D(-70, -70, 140), new Point3D(75, -75, 115))
						.setEmission(Color.BLACK)
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60).setkT(0).setkR(0)),

				new Sphere(new Point3D(60, -50, 50), 30).setEmission(new Color(java.awt.Color.BLUE))// big blue
						.setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(30).setkT(0.6).setkR(0)),

				new Triangle(new Point3D(-150, 150, 115), new Point3D(150, 150, 135), new Point3D(75, -75, 150))
						.setEmission(Color.BLACK)
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60).setkT(0).setkR(0)),

				new Triangle(new Point3D(-150, 150, 115), new Point3D(150, 150, 135), new Point3D(75, -75, 150))
						.setEmission(new Color(255, 191, 191))
						.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(60).setkT(0).setkR(0)),

				new Sphere(new Point3D(-175, -160, -100), 50).setEmission(new Color(204, 0, 201))
						.setMaterial(new Material().setkD(0.25).setkS(0.25).setnShininess(20).setkT(0.9).setkR(0)),

				new Sphere(new Point3D(-95, 90, 100), 40).setEmission(new Color(102, 0, 204))// big blue
						.setMaterial(new Material().setkD(0.25).setkS(0.25).setnShininess(20).setkT(0).setkR(0.6)));

		scene.lights.add(new SpotLight(new Color(130, 100, 130), new Point3D(0, 30, -50), new Vector(0, -1, 0)).setkC(1)
				.setkL(4E-5).setkQ(2E-7));

		scene.lights.add(new PointLight(new Color(210, 210, 210), new Point3D(-160, 165, 100)));

		scene.lights.add(new DirectionalLight(new Color(210, 210, 210), new Vector(0, 1, 0)));
		scene.lights.add(new PointLight(new Color(210, 210, 210), new Point3D(160, 165, 100)));
		scene.lights.add(new SpotLight(new Color(400, 240, 0), new Point3D(-100, 100, -200), new Vector(1, -1, 3))
				.setkC(1).setkL(1E-5).setkQ(1.5E-7));

		ImageWriter imageWriter = new ImageWriter("bvhplusplane", 500, 500);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene).setNumOfRays(1).setBvh(true).setRayDistance(1))
				.setMultithreading(3).setDebugPrint();

		render.renderImage();
		render.writeToImage();
	}

}
