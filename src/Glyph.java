/*
 * Author:  KhojiAkbar
 * Description: Implements a Glyph class to store all the printables and key commands 
 * 			from the keyboard.
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;

/**
 * Composite Pattern
 * 
 * Glyph
 */
class Glyph 
{
	protected char c;
	protected String id;
	protected int color;
	protected boolean isCursor;
	protected Font font;
	protected int columnIncrement;
	protected int fontSize;
	protected boolean isMistake;
	protected String imagePath;
	
	/*
	 * Constructs a Glyph obj for a character
	 */
	public Glyph(char c)
	{
		this.c = c;
	}
	
	/*
	 * Constructs a Glyph obj for a character
	 * it stores the font, type and size
	 */
	public Glyph(char c2, int fontColor, Font font, int size) {
		// TODO Auto-generated constructor stub
		this.c = c2;
		this.color = fontColor;
		this.isCursor = false;
		this.font = font;
		this.fontSize = size;
		this.isMistake = false;
		
	}
	/*
	 * Constructs a Glyph obj for an image
	 */
	public Glyph (Display display, String path) {
		this.imagePath = path;
	}
	
	/*
	 * sets the cursor to this Glyph
	 */
	protected void setCursor(boolean isCursor) {
		this.isCursor = isCursor;
	}

	/*
	 * returns the ID of the Glyph
	 */
	protected String getID() {
		return "char";
	}
	
	/*
	 * sets color to this Glyph
	 */
	public void setColor(int color) {
		this.color = color;
	}
	/*
	 * returns the color of the glyph
	 */
	public int getColor() {
		return color;
	}
	
	/*
	 * marks this glyph as incorrectly spelled.
	 * Used for marking misspelled words
	 */
	public void setMistake(boolean m) {
		isMistake = m;
	}
	/*
	 * draws the glyph on canvas
	 * @param e - the paint event from the canvas listener
	 * @param display - the display of LilLexi
	 * @param column & row = the top left (x,y) coord to print this obj
	 * @param size - size of the glyph
	 */
	protected void draw(PaintEvent e, Display display,int column, int row, int size) {
		
		if (isMistake && isCursor) {
			e.gc.setBackground(display.getSystemColor(SWT.COLOR_DARK_RED));
		}
		else if (isMistake) {
			e.gc.setBackground(display.getSystemColor(SWT.COLOR_RED));
		}
		else if(isCursor) {
//			System.out.println("found cursor\n");
			e.gc.setBackground(display.getSystemColor(SWT.COLOR_GRAY)); //background color
		}else {
			e.gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE)); //background color
		}
		e.gc.setFont(font);
		e.gc.setForeground(display.getSystemColor(color)); // text color
		e.gc.drawString("" + c, column, row + 10);
		
	}
	/*
	 * set an ID for a glyph
	 */
	protected void setID(String id) {
		this.id = id;
	}

	/*
	 * returns the char stored by the glyph
	 */
	public char getChar() {return c;}
	/*
	 * sets a printable char for a glyph
	 */
	public void setChar(char c) {this.c = c;}
	/*
	 * returns the size of the glyph
	 */
	public int getFontSize() {
		return fontSize;
	}
}
