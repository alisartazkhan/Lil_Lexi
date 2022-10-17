/*
 * Author: Ali Sartaz Khan
 * Description: Creates interface for moving the cursor and creates classes that 
 * implement that
 */
public interface MoveCursor {
	
	/*
	 * Moves that cursor but incrementing or decrementing the value with 
	 * a cursor algorithm
	 * 
	 * cursor: value of cursor
	 * size: size of glyphs list
	 */
	public int move(int cursor, int size);
}

/*
 * Called to move cursor left
 */
class MoveCursorLeft implements MoveCursor{

	/*
	 * Moves that cursor but incrementing or decrementing the value with 
	 * a cursor algorithm
	 * 
	 * cursor: value of cursor
	 * size: size of glyphs list
	 */
	@Override
	public int move(int cursor, int size) {
		// TODO Auto-generated method stub
		if (cursor-1>=0) // checking if cursor can moving left does change index <0
			cursor--;
		return cursor;
	}
	
}


/*
 * Called to move cursor right
 */
class MoveCursorRight implements MoveCursor{

	/*
	 * Moves that cursor but incrementing or decrementing the value with 
	 * a cursor algorithm
	 * 
	 * cursor: value of cursor
	 * size: size of glyphs list
	 */
	@Override
	public int move(int cursor, int size) {
		// TODO Auto-generated method stub
		if (cursor+1<size) // checking if increasing cursor size doesnt take it out of the array
			cursor++;
		return cursor;
		
	}
}


/*
 * Called to move cursor up
 */
class MoveCursorUp implements MoveCursor{

	/*
	 * Moves that cursor but incrementing or decrementing the value with 
	 * a cursor algorithm
	 * 
	 * cursor: value of cursor
	 * size: size of glyphs list
	 */
	@Override
	public int move(int cursor, int lineWidth) {
		// TODO Auto-generated method stub
		if (cursor-lineWidth>=0) {
			cursor-=lineWidth;}
		return cursor;
	
}}



/*
 * Called to move cursor down
 */
class MoveCursorDown implements MoveCursor{

	/*
	 * Moves that cursor but incrementing or decrementing the value with 
	 * a cursor algorithm
	 * 
	 * cursor: value of cursor
	 * size: size of glyphs list
	 */
	@Override
	public int move(int cursor, int lineWidth) {
		// TODO Auto-generated method stub
		if (cursor+lineWidth<16) 
			cursor+=lineWidth;
		return cursor;
		
	
}
}