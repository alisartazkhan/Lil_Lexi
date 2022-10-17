/**
 * @author Khojiakbar Yokubjonov
 * @brief Implements the Circle, ZRectangle, and ZImage classes
 * 		all of which inherit from the Glyph class.
 * Circle class - 		the circle shape.
 * ZRectangle class -	the rectangle shape
 * ZImage class - 		represents an image that can be added to a LilLexi doc.
 */
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * 
 * Composite Pattern
 *
 */

public class Circle extends Glyph {
	/**
	 * Constructs a Cicle class for the LilLexi editor. 
	 * 	It allows a user to add a circle shape to their doc.
	 * @param c a char that repesents this obj. Since it's not a key from the keyboard,
	 * 			i just pass a space char for the sake of consistency with Glyph class
	 * @param font - the default font. Not important for this class.
	 * @param fontColor - the color of the shape
	 */
	public Circle(char c, Font font, int fontColor) {
		super(c, fontColor, font, fontColor);
		// TODO Auto-generated constructor stub
	}

	/**
	 * returns the ID of this obj.
	 * returns a string.
	 */
	protected String getID() {
		return "circle";
	}

	/**
	 * draws a circle on the LilLexi canvas.
	 * @param e - the paint event from the canvas listener
	 * @param display - the display of LilLexi
	 * @param column & row = the top left (x,y) coord to print this obj
	 * @param size - size of the shape
	 */
	protected void draw(PaintEvent e, Display display, int column, int row, int size) {
		e.gc.drawOval(column + size / 2 - 5, row + 10 + size / 3, (int) (size * 0.75), (int) (size * 0.75));

	}
}

class ZRectangle extends Glyph {
	/**
	 * Constructs a rectangle class for LilLexi
	 * It allows a user to add a rectangle shape to their doc.
	 * @param c- a char that repesents this obj. Since it's not a key from the keyboard,
	 * 			i just pass a space char for the sake of consistency with Glyph class
	 * @param font - the default font. Not important for this class.
	 * @param fontColor - the color of the shape
	 */
	public ZRectangle(char c, Font font, int fontColor) {
		super(c, fontColor, font, fontColor);
		// TODO Auto-generated constructor stub
	}
	/**
	 * returns the ID of this obj.
	 * returns a string.
	 */
	protected String getID() {
		return "rectangle";
	}

	/**
	 * draws a rectangle on the LilLexi canvas.
	 * @param e - the paint event from the canvas listener
	 * @param display - the display of LilLexi
	 * @param column & row = the top left (x,y) coord to print this obj
	 * @param size - size of the shape
	 */
	protected void draw(PaintEvent e, Display display, int column, int row, int size) {
		e.gc.drawRectangle(column + size / 2 - 5, row + 10 + size / 3, (int) (size * 0.75), (int) (size * 0.75));

	}
}

class ZImage extends Glyph {
	protected Image img;
	protected int height; //height of the image, used for a line width on canvas

	protected String path; // image path

	/*
	 * Constructs a image obj.
	 * It allows a user to add an image to their LilLexi doc
	 * @param display - the display of LilLexi
	 * @param path - the image path;
	 */
	public ZImage(Display display, String path) {
		super(display, path);
		img = new Image(display, path);
		this.height = img.getBounds().height;
		this.path = path;

	}

	/*
	 * returns  an int, the height of the image. 
	 * Used to determine the line width on canvas
	 */
	public int getHeight() {
		return img.getBounds().height;
	}

	/**
	 * returns the ID of this obj.
	 * returns a string.
	 */
	protected String getID() {
		return "image";
	}

	/**
	 * draws an image on the LilLexi canvas.
	 * @param e - the paint event from the canvas listener
	 * @param display - the display of LilLexi
	 * @param column & row = the top left (x,y) coord to print this image
	 * @param size - size of the image
	 */
	protected void draw(PaintEvent e, Display display, int column, int row, int size) {

		e.gc.drawImage(img, 100, row + 40);

	}

}
