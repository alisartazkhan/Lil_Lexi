/**
 * @author Khojiakbar Yokubjonov
 * @brief Implements a FuncKey class which inherits from Glyph.
 * 		it represents a function key (not a printable char) from the keyboard.
 * 		Used it to represent the ENTER key.
 *
 */

/**
 * 
 * Composite pattern
 *
 */
public class FuncKey extends Glyph{
	private String id; // id of the key
	private char c; // to be ignored in this class

	/*
	 * Constructs a funcKey obj
	 * @param c - char that represents this key.
	 */
	public FuncKey(char c) {
		super(c);
		this.c = c;
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructs a funcKey obj
	 * @param c a char that represents this key (ignored in this class)
	 * @param id - id of the funcKey
	 */
	public FuncKey(char c, String id) {
		super(c);
		this.c = c;
		this.id = id;
	}
	
	/**
	 * returns the ID of this key.
	 * returns a string.
	 */
	public String getID() {
		return id;
	}

}