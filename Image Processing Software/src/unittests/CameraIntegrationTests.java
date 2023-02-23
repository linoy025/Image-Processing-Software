package unittests;

import elements.Camera;

import geometries.*;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import static org.junit.Assert.*;
import org.junit.Test;
import java.util.List;

/**
 * camera integration tests with a sphere, plane, and triangle.
 * 
 * @author Elinoy Gidoni and Tamara Seban
 */
public class CameraIntegrationTests {
	/**
	 * integration tests for constructing a ray through a pixel with a sphere
	 * {@link elements.Camera#constructRayThroughPixel(int, int, int, int)}.
	 */
	@Test
	public void constructRayThroughPixelWithSphere() {

		// TC01: 2 intersection points
		Camera cam1 = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
		Camera cam2 = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));
		Sphere sph = new Sphere(new Point3D(0, 0, 3), 1);
		List<GeoPoint> results;
		int count = 0;
		int Nx = 3;
		int Ny = 3;
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				results = sph.findGeoIntersections(
						cam1.setDistance(1).setViewPlaneSize(3, 3).constructRayThroughPixel(Nx, Ny, j, i));
				if (results != null)
					count += results.size();
			}
		}
		assertEquals("wrong amount of intersections,2 intersection points are required", 2, count);

		// TC02: 18 intersection points
		Sphere sph2 = new Sphere(new Point3D(0, 0, 2.5), 2.5);
		count = 0;
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				results = sph2.findGeoIntersections(
						cam2.setDistance(1).setViewPlaneSize(3, 3).constructRayThroughPixel(Nx, Ny, j, i));
				if (results != null)
					count += results.size();
			}
		}
		assertEquals("wrong amount of intersections,18 intersection points are required", 18, count);

		// TC03: 10 intersection points
		Sphere sph3 = new Sphere(new Point3D(0, 0, 2), 2);
		count = 0;
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				results = sph3.findGeoIntersections(
						cam2.setDistance(1).setViewPlaneSize(3, 3).constructRayThroughPixel(Nx, Ny, j, i));
				if (results != null)
					count += results.size();
			}
		}
		assertEquals("wrong amount of intersections,10 intersection points are required", 10, count);

		// TC04: 9 intersection points
		Sphere sph4 = new Sphere(new Point3D(0, 0, 1), 4);
		count = 0;
		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 3; ++j) {
				results = sph4.findGeoIntersections(
						cam2.setDistance(1).setViewPlaneSize(3, 3).constructRayThroughPixel(Nx, Ny, j, i));
				if (results != null)
					count += results.size();
			}
		assertEquals("wrong amount of intersections,9 intersection points are required", 9, count);

		// TC05: 0 intersection points
		Sphere sph5 = new Sphere(new Point3D(0, 0, -1), 0.5);
		count = 0;
		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 3; ++j) {
				results = sph5.findGeoIntersections(
						cam1.setDistance(1).setViewPlaneSize(3, 3).constructRayThroughPixel(Nx, Ny, j, i));
				if (results != null)
					count += results.size();
			}
		assertEquals("wrong amount of intersections,0 intersection points are required", 0, count);

	}

	/**
	 * integration tests for constructing a ray through a pixel with a plane
	 * {@link elements.Camera#constructRayThroughPixel(int, int, int, int)}.
	 */
	@Test
	public void constructRayThroughPixelWithPlane() {
		Camera cam = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
		List<GeoPoint> results;
		int count;
		// Tc01: 9 intersection points- plane against camera
		Plane plane1 = new Plane(new Point3D(0, 0, 5), new Vector(0, 0, 1));
		count = 0;
		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 3; ++j) {
				results = plane1.findGeoIntersections(
						cam.setDistance(1).setViewPlaneSize(3, 3).constructRayThroughPixel(3, 3, j, i));
				if (results != null)
					count += results.size();
			}
		assertEquals("wrong amount of intersections", 9, count);

		// TC02: 9 intersection points- plane with small angle
		Plane plane2 = new Plane(new Point3D(0, 0, 5), new Vector(0, -1, 2));
		count = 0;
		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 3; ++j) {
				results = plane2.findGeoIntersections(
						cam.setDistance(1).setViewPlaneSize(3, 3).constructRayThroughPixel(3, 3, j, i));
				if (results != null)
					count += results.size();
			}
		assertEquals("wrong amount of intersections", 9, count);

		// TC03: 6 intersection points- plane parallel to lower rays
		Plane plane3 = new Plane(new Point3D(0, 0, 5), new Vector(0, -1, 1));
		count = 0;
		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 3; ++j) {
				results = plane3.findGeoIntersections(
						cam.setDistance(1).setViewPlaneSize(3, 3).constructRayThroughPixel(3, 3, j, i));
				if (results != null)
					count += results.size();
			}
		assertEquals("wrong amount of intersections", 6, count);

		// TC04: 0 intersection points - beyond plane
		Plane plane4 = new Plane(new Point3D(0, 0, -5), new Vector(0, 0, 1));
		count = 0;
		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 3; ++j) {
				results = plane4.findGeoIntersections(
						cam.setDistance(1).setViewPlaneSize(3, 3).constructRayThroughPixel(3, 3, j, i));
				if (results != null)
					count += results.size();
			}
		assertEquals("wrong amount of intersections", 0, count);
	}

	/**
	 * integration tests for constructing a ray through a pixel with a triangle
	 * {@link elements.Camera#constructRayThroughPixel(int, int, int, int)}.
	 */
	@Test
	public void constructRayThroughPixelWithTriangle() {
		Camera cam = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
		List<GeoPoint> results;
		int count = 0;

		// TC01: 1 intersection point- small triangle
		Triangle tr1 = new Triangle(new Point3D(1, 1, 2), new Point3D(-1, 1, 2), new Point3D(0, -1, 2));
		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 3; ++j) {
				results = tr1.findGeoIntersections(
						cam.setDistance(1).setViewPlaneSize(3, 3).constructRayThroughPixel(3, 3, j, i));
				if (results != null)
					count += results.size();
			}
		assertEquals("wrong amount of intersections", 1, count);

		// TC02: 2 intersection points - medium triangle
		Triangle tr2 = new Triangle(new Point3D(1, 1, 2), new Point3D(-1, 1, 2), new Point3D(0, -20, 2));
		count = 0;
		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 3; ++j) {
				results = tr2.findGeoIntersections(
						cam.setDistance(1).setViewPlaneSize(3, 3).constructRayThroughPixel(3, 3, j, i));
				if (results != null)
					count += results.size();
			}
		assertEquals("wrong amount of intersections", 2, count);

	}
}
