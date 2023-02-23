package unittests;

import renderer.ImageWriter;
import org.junit.Test;
import primitives.Color;

/**
 * ImageWriterTest - test module that will contain initial image construction -
 * 
 * @author Elinoy Gidoni and Tamara Seban
 */

public class ImageWriterTests {
	/**
	 * * a single color image with a second color grid of lines. In this test, a
	 * grid of 10x16 squares on the screen (ViewPlane) was built with a resolution
	 * of 500 by 800.
	 * 
	 * {@link renderer.ImageWriter#ImageWriter(string , int, int)}.
	 */
	@Test
	public void gridTest() {
		ImageWriter imageWriter = new ImageWriter("first test", 800, 500);
		int Nx = imageWriter.getNx();
		int Ny = imageWriter.getNy();
		for (int i = 0; i < Ny; i++) {
			for (int j = 0; j < Nx; j++) {
				if (i % 50 == 0 || j % 50 == 0) {
					imageWriter.writePixel(j, i, new Color(java.awt.Color.WHITE));
				} else {
					imageWriter.writePixel(j, i, new Color(java.awt.Color.PINK));
				}
			}
		}
		imageWriter.writeToImage();
	}

}
