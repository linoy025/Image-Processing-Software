package unittests;

import geometries.Plane;
import org.junit.Test;
import elements.*;
import geometries.Sphere;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for Implementation of a picture improvement algorithm Glossy surfaces /
 * Diffuse Glass
 * 
 * @author Elinoy Gidoni and Tamara Seban
 */

public class RayTracingTests {

	@Test
	public void rayTracingTest1() {

		Scene scene = new Scene("Test scene");
		Camera camera = new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)) //
				.setViewPlaneSize(200, 200).setDistance(600);

		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.05));

		scene.geometries.add(new Plane(new Point3D(0, 0, 210), new Vector(0, 0, -1)) //
				.setEmission(Color.BLACK) //
				.setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(75, 120, -75), 20) //
						.setEmission(new Color(255, 92, 51)) // orange
						.setMaterial(new Material().setkD(0.2).setkS(0.4).setnShininess(5).setkT(0.8).setkR(0)),

				new Sphere(new Point3D(160, 165, 100), 15).setEmission(new Color(255, 173, 153)) // pink
						.setMaterial(new Material().setkD(0.25).setkS(0.3).setnShininess(5).setkT(0.22).setkR(0)),

				new Sphere(new Point3D(0, 130, 90), 25) //
						.setEmission(new Color(255, 128, 128)) // light pink
						.setMaterial(new Material().setkD(0.25).setkS(0.3).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(0, 130, -60), 25) //
						.setEmission(new Color(255, 51, 133)) // dark pink
						.setMaterial(new Material().setkD(0.25).setkS(0.3).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(-53, -50, 200), 50).setEmission(new Color(255, 102, 163)) // Big light red
						.setMaterial(new Material().setkD(0.3).setkS(0.2).setnShininess(5).setkT(0).setkR(0)),

				new Sphere(new Point3D(53, -50, 200), 50).setEmission(new Color(255, 102, 102)) // big light pink
						.setMaterial(new Material().setkD(0.3).setkS(0.2).setnShininess(5).setkT(0).setkR(0)),

				new Sphere(new Point3D(-75, 120, -75), 20).setEmission(new Color(255, 77, 77)) // red
						.setMaterial(new Material().setkD(0.3).setkS(0.4).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(-160, 165, 100), 15).setEmission(new Color(255, 153, 194)) // light pink
						.setMaterial(new Material().setkD(0.3).setkS(0.4).setnShininess(5).setkT(0.22).setkR(0)));

		scene.lights.add(new SpotLight(new Color(130, 100, 130), new Point3D(0, 30, -50), new Vector(0, -1, 0)).setkC(1)
				.setkL(4E-5).setkQ(2E-7));

		scene.lights.add(new PointLight(new Color(210, 210, 210), new Point3D(-160, 165, 100)));

		scene.lights.add(new DirectionalLight(new Color(210, 210, 210), new Vector(0, 1, 0)));
		scene.lights.add(new PointLight(new Color(210, 210, 210), new Point3D(160, 165, 100)));

		ImageWriter imageWriter = new ImageWriter("ray tracing only one ray", 500, 500);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene).setNumOfRays(1).setBvh(true).setRayDistance(1)).setMultithreading(3).setDebugPrint();

	
		render.renderImage();
		render.writeToImage();
		
	}
	
	
	/*@Test
	public void rayTracingTest2() {

		Scene scene = new Scene("Test scene");
		Camera camera = new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)) //
				.setViewPlaneSize(200, 200).setDistance(600);

		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.05));

		scene.geometries.add(new Plane(new Point3D(0, 0, 210), new Vector(0, 0, -1)) //
				.setEmission(Color.BLACK) //
				.setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(75, 120, -75), 20) //
						.setEmission(new Color(255, 92, 51)) // orange
						.setMaterial(new Material().setkD(0.2).setkS(0.4).setnShininess(5).setkT(0.8).setkR(0)),

				new Sphere(new Point3D(160, 165, 100), 15).setEmission(new Color(255, 173, 153)) // pink
						.setMaterial(new Material().setkD(0.25).setkS(0.3).setnShininess(5).setkT(0.22).setkR(0)),

				new Sphere(new Point3D(0, 130, 90), 25) //
						.setEmission(new Color(255, 128, 128)) // light pink
						.setMaterial(new Material().setkD(0.25).setkS(0.3).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(0, 130, -60), 25) //
						.setEmission(new Color(255, 51, 133)) // dark pink
						.setMaterial(new Material().setkD(0.25).setkS(0.3).setnShininess(5).setkT(0).setkR(0.4)),
						
						new Sphere(new Point3D(-53, -50, 200), 50).setEmission(new Color(255, 102, 163)) // Big light red
						.setMaterial(new Material().setkD(0.3).setkS(0.2).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(53, -50, 200), 50).setEmission(new Color(255, 102, 102)) // big light pink
						.setMaterial(new Material().setkD(0.3).setkS(0.2).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(-75, 120, -75), 20).setEmission(new Color(255, 77, 77)) // red
						.setMaterial(new Material().setkD(0.3).setkS(0.4).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(-160, 165, 100), 15).setEmission(new Color(255, 153, 194)) // light pink
						.setMaterial(new Material().setkD(0.3).setkS(0.4).setnShininess(5).setkT(0.22).setkR(0)));

		scene.lights.add(new SpotLight(new Color(130, 100, 130), new Point3D(0, 30, -50), new Vector(0, -1, 0)).setkC(1)
				.setkL(4E-5).setkQ(2E-7));

		scene.lights.add(new PointLight(new Color(210, 210, 210), new Point3D(-160, 165, 100)));

		scene.lights.add(new DirectionalLight(new Color(210, 210, 210), new Vector(0, 1, 0)));
		scene.lights.add(new PointLight(new Color(210, 210, 210), new Point3D(160, 165, 100)));

		ImageWriter imageWriter = new ImageWriter("ray tracing only one ray-change kR big sphere", 500, 500);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene).setNumOfRays(1).setRayDistance(1));

		render.renderImage();
		render.writeToImage();
	}
	
	
	@Test
	public void rayTracing10RaysTest1() {

		Scene scene = new Scene("Test scene");
		Camera camera = new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)) //
				.setViewPlaneSize(200, 200).setDistance(600);

		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.05));

		scene.geometries.add(new Plane(new Point3D(0, 0, 210), new Vector(0, 0, -1)) //
				.setEmission(Color.BLACK) //
				.setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(75, 120, -75), 20) //
						.setEmission(new Color(255, 92, 51)) // orange
						.setMaterial(new Material().setkD(0.2).setkS(0.4).setnShininess(5).setkT(0.8).setkR(0)),

				new Sphere(new Point3D(160, 165, 100), 15).setEmission(new Color(255, 173, 153)) // pink
						.setMaterial(new Material().setkD(0.25).setkS(0.3).setnShininess(5).setkT(0.22).setkR(0)),

				new Sphere(new Point3D(0, 130, 90), 25) //
						.setEmission(new Color(255, 128, 128)) // light pink
						.setMaterial(new Material().setkD(0.25).setkS(0.3).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(0, 130, -60), 25) //
						.setEmission(new Color(255, 51, 133)) // dark pink
						.setMaterial(new Material().setkD(0.25).setkS(0.3).setnShininess(5).setkT(0).setkR(0.4)),
						
						new Sphere(new Point3D(-53, -50, 200), 50).setEmission(new Color(255, 102, 163)) // Big light red
						.setMaterial(new Material().setkD(0.3).setkS(0.2).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(53, -50, 200), 50).setEmission(new Color(255, 102, 102)) // big light pink
						.setMaterial(new Material().setkD(0.3).setkS(0.2).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(-75, 120, -75), 20).setEmission(new Color(255, 77, 77)) // red
						.setMaterial(new Material().setkD(0.3).setkS(0.4).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(-160, 165, 100), 15).setEmission(new Color(255, 153, 194)) // light pink
						.setMaterial(new Material().setkD(0.3).setkS(0.4).setnShininess(5).setkT(0.22).setkR(0)));

		scene.lights.add(new SpotLight(new Color(130, 100, 130), new Point3D(0, 30, -50), new Vector(0, -1, 0)).setkC(1)
				.setkL(4E-5).setkQ(2E-7));

		scene.lights.add(new PointLight(new Color(210, 210, 210), new Point3D(-160, 165, 100)));

		scene.lights.add(new DirectionalLight(new Color(210, 210, 210), new Vector(0, 1, 0)));
		scene.lights.add(new PointLight(new Color(210, 210, 210), new Point3D(160, 165, 100)));

		ImageWriter imageWriter = new ImageWriter("ray tracing 10 rays- change kR big sphere", 500, 500);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene).setNumOfRays(10).setRayDistance(1));

		render.renderImage();
		render.writeToImage();
	}


	@Test
	public void rayTracing10RaysTest2() {

		Scene scene = new Scene("Test scene");
		Camera camera = new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)) //
				.setViewPlaneSize(200, 200).setDistance(600);

		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.05));

		scene.geometries.add(new Plane(new Point3D(0, 0, 210), new Vector(0, 0, -1)) //
				.setEmission(Color.BLACK) //
				.setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(75, 120, -75), 20) //
						.setEmission(new Color(255, 92, 51)) // orange
						.setMaterial(new Material().setkD(0.2).setkS(0.4).setnShininess(5).setkT(0.8).setkR(0)),

				new Sphere(new Point3D(160, 165, 100), 15).setEmission(new Color(255, 173, 153)) // pink
						.setMaterial(new Material().setkD(0.25).setkS(0.3).setnShininess(5).setkT(0.22).setkR(0)),

				new Sphere(new Point3D(0, 130, 90), 25) //
						.setEmission(new Color(255, 128, 128)) // light pink
						.setMaterial(new Material().setkD(0.25).setkS(0.3).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(0, 130, -60), 25) //
						.setEmission(new Color(255, 51, 133)) // dark pink
						.setMaterial(new Material().setkD(0.25).setkS(0.3).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(-53, -50, 200), 50).setEmission(new Color(255, 102, 163)) // Big light red
						.setMaterial(new Material().setkD(0.3).setkS(0.2).setnShininess(5).setkT(0).setkR(0)),

				new Sphere(new Point3D(53, -50, 200), 50).setEmission(new Color(255, 102, 102)) // big light pink
						.setMaterial(new Material().setkD(0.3).setkS(0.2).setnShininess(5).setkT(0).setkR(0)),

				new Sphere(new Point3D(-75, 120, -75), 20).setEmission(new Color(255, 77, 77)) // red
						.setMaterial(new Material().setkD(0.3).setkS(0.4).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(-160, 165, 100), 15).setEmission(new Color(255, 153, 194)) // light pink
						.setMaterial(new Material().setkD(0.3).setkS(0.4).setnShininess(5).setkT(0.22).setkR(0)));

		scene.lights.add(new SpotLight(new Color(130, 100, 130), new Point3D(0, 30, -50), new Vector(0, -1, 0)).setkC(1)
				.setkL(4E-5).setkQ(2E-7));

		scene.lights.add(new PointLight(new Color(210, 210, 210), new Point3D(-160, 165, 100)));

		scene.lights.add(new DirectionalLight(new Color(210, 210, 210), new Vector(0, 1, 0)));
		scene.lights.add(new PointLight(new Color(210, 210, 210), new Point3D(160, 165, 100)));

		ImageWriter imageWriter = new ImageWriter("ray tracing 10 rays", 500, 500);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene).setNumOfRays(10).setRayDistance(1));

		render.renderImage();
		render.writeToImage();
	}

	@Test
	public void rayTracing30RaysTest() {

		Scene scene = new Scene("Test scene");
		Camera camera = new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)) //
				.setViewPlaneSize(200, 200).setDistance(600);

		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.05));

		scene.geometries.add(new Plane(new Point3D(0, 0, 210), new Vector(0, 0, -1)) //
				.setEmission(Color.BLACK) //
				.setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(75, 120, -75), 20) //
						.setEmission(new Color(255, 92, 51)) // orange
						.setMaterial(new Material().setkD(0.2).setkS(0.4).setnShininess(5).setkT(0.8).setkR(0)),

				new Sphere(new Point3D(160, 165, 100), 15).setEmission(new Color(255, 173, 153)) // pink
						.setMaterial(new Material().setkD(0.25).setkS(0.3).setnShininess(5).setkT(0.22).setkR(0)),

				new Sphere(new Point3D(0, 130, 90), 25) //
						.setEmission(new Color(255, 128, 128)) // light pink
						.setMaterial(new Material().setkD(0.25).setkS(0.3).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(0, 130, -60), 25) //
						.setEmission(new Color(255, 51, 133)) // dark pink
						.setMaterial(new Material().setkD(0.25).setkS(0.3).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(-53, -50, 200), 50).setEmission(new Color(255, 102, 163)) // Big light red
						.setMaterial(new Material().setkD(0.3).setkS(0.2).setnShininess(5).setkT(0).setkR(0)),

				new Sphere(new Point3D(53, -50, 200), 50).setEmission(new Color(255, 102, 102)) // big light pink
						.setMaterial(new Material().setkD(0.3).setkS(0.2).setnShininess(5).setkT(0).setkR(0)),

				new Sphere(new Point3D(-75, 120, -75), 20).setEmission(new Color(255, 77, 77)) // red
						.setMaterial(new Material().setkD(0.3).setkS(0.4).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(-160, 165, 100), 15).setEmission(new Color(255, 153, 194)) // light pink
						.setMaterial(new Material().setkD(0.3).setkS(0.4).setnShininess(5).setkT(0.22).setkR(0)));

		scene.lights.add(new SpotLight(new Color(130, 100, 130), new Point3D(0, 30, -50), new Vector(0, -1, 0)).setkC(1)
				.setkL(4E-5).setkQ(2E-7));

		scene.lights.add(new PointLight(new Color(210, 210, 210), new Point3D(-160, 165, 100)));

		scene.lights.add(new DirectionalLight(new Color(210, 210, 210), new Vector(0, 1, 0)));
		scene.lights.add(new PointLight(new Color(210, 210, 210), new Point3D(160, 165, 100)));

		ImageWriter imageWriter = new ImageWriter("ray tracing 30 rays", 500, 500);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene).setNumOfRays(30).setRayDistance(1));

		render.renderImage();
		render.writeToImage();
	}

	@Test
	public void rayTracing50RaysTest() {

		Scene scene = new Scene("Test scene");
		Camera camera = new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)) //
				.setViewPlaneSize(200, 200).setDistance(600);

		scene.setBackground(Color.BLACK);
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.05));

		scene.geometries.add(new Plane(new Point3D(0, 0, 210), new Vector(0, 0, -1)) //
				.setEmission(Color.BLACK) //
				.setMaterial(new Material().setkD(0.2).setkS(0.2).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(75, 120, -75), 20) //
						.setEmission(new Color(255, 92, 51)) // orange
						.setMaterial(new Material().setkD(0.2).setkS(0.4).setnShininess(5).setkT(0.8).setkR(0)),

				new Sphere(new Point3D(160, 165, 100), 15).setEmission(new Color(255, 173, 153)) // pink
						.setMaterial(new Material().setkD(0.25).setkS(0.3).setnShininess(5).setkT(0.22).setkR(0)),

				new Sphere(new Point3D(0, 130, 90), 25) //
						.setEmission(new Color(255, 128, 128)) // light pink
						.setMaterial(new Material().setkD(0.25).setkS(0.3).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(0, 130, -60), 25) //
						.setEmission(new Color(255, 51, 133)) // dark pink
						.setMaterial(new Material().setkD(0.25).setkS(0.3).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(-53, -50, 200), 50).setEmission(new Color(255, 102, 163)) // Big light red
						.setMaterial(new Material().setkD(0.3).setkS(0.2).setnShininess(5).setkT(0).setkR(0)),

				new Sphere(new Point3D(53, -50, 200), 50).setEmission(new Color(255, 102, 102)) // big light pink
						.setMaterial(new Material().setkD(0.3).setkS(0.2).setnShininess(5).setkT(0).setkR(0)),

				new Sphere(new Point3D(-75, 120, -75), 20).setEmission(new Color(255, 77, 77)) // red
						.setMaterial(new Material().setkD(0.3).setkS(0.4).setnShininess(5).setkT(0).setkR(0.4)),

				new Sphere(new Point3D(-160, 165, 100), 15).setEmission(new Color(255, 153, 194)) // light pink
						.setMaterial(new Material().setkD(0.3).setkS(0.4).setnShininess(5).setkT(0.22).setkR(0)));

		scene.lights.add(new SpotLight(new Color(130, 100, 130), new Point3D(0, 30, -50), new Vector(0, -1, 0)).setkC(1)
				.setkL(4E-5).setkQ(2E-7));

		scene.lights.add(new PointLight(new Color(210, 210, 210), new Point3D(-160, 165, 100)));

		scene.lights.add(new DirectionalLight(new Color(210, 210, 210), new Vector(0, 1, 0)));
		scene.lights.add(new PointLight(new Color(210, 210, 210), new Point3D(160, 165, 100)));

		ImageWriter imageWriter = new ImageWriter("ray tracing 50 rays", 500, 500);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene).setNumOfRays(50).setRayDistance(1));

		render.renderImage();
		render.writeToImage();
	}*/

}
