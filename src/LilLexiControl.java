/*
 * Author: Ali Sartaz Khan
 * Description: Control Object for the Game
 */

public class LilLexiControl 
{
	private LilLexiDoc currentDoc;
	private MoveCursor cursorLeft;
	private MoveCursor cursorRight;
	private MoveCursor cursorUp;
	private MoveCursor cursorDown;
	private int cursor;
	private int cursorColumn;
	private Glyph currentGlyph;
	private Glyph prev; // contains current cursor glyph

	/**
	 * LilLexiControl
	 * @param moveCursorRight 
	 * @param moveCursorLeft 
	 * @param moveCursorDown 
	 * @param moveCursorUp 
	 */
	public LilLexiControl( LilLexiDoc doc, MoveCursor moveCursorLeft, MoveCursor moveCursorRight )
	{
		this.currentDoc = doc;
		this.cursorLeft = moveCursorLeft;
		this.cursorRight = moveCursorRight;
		this.cursorColumn = -14;
		prev = null;
		
	}
	
	/**
	 * Add glyph and move cursor
	 * 
	 * g: Glyph
	 */
	void add( Glyph g ) 
	{	
		currentGlyph = g;
		currentDoc.add(cursor,g);
		moveCursorRight();
		

		
	}	
	
	
	/*
	 * Update cursor glyph to new glyph and reset previous glyph
	 */
	void updateCursor(Glyph g) {
		if(prev == null) {prev = g;}
		else {
			prev.setCursor(false);
			prev = g;
		}
		g.setCursor(true);
	}
	
	/*
	 * Undos previous change
	 */
	void undo() {
		boolean added = currentDoc.undoLastCommand();
		if (added)
			moveCursorLeft();
	}
	
	
	/*
	 * Redos previous undo
	 */
	void redo() {
		boolean added = currentDoc.redoLastCommand();
		if (added)
			moveCursorRight();
	}

	/*
	 * Moves cursor to the right and updates cursor and UI
	 */
	protected void moveCursorRight() {
//		System.out.println("incrementing cursor");
		cursor = cursorRight.move(cursor, currentDoc.getGlyphSize());
		Glyph g = currentDoc.getGlyphCursor(cursor);
		if(g == null) {return;}
		updateCursor(g);
		currentDoc.ui.updateUI();
	}

	/*
	 * Moves cursor to the left and updates cursor and UI
	 */
	protected void moveCursorLeft() {
		cursor = cursorLeft.move(cursor, 0);
		Glyph g = currentDoc.getGlyphCursor(cursor);
		if(g == null) {return;}
		updateCursor(g);
		currentDoc.ui.updateUI();
	}


	/**
	 * quitEditor  user quits
	 */
	void  quitEditor() 
	{ 
		System.exit(0); 
	}

	/*
	 * Removes character at cursor and moves cursor to the left
	 */
	public void removeChar() {
		// TODO Auto-generated method stub
		currentDoc.remove(cursor);
		moveCursorLeft();
		
	}

	/*
	 * Increments Columns for drawing characters on canvas
	 * 
	 * column: int col number
	 * fontSize: fontSize of glyph
	 */
	public int incrementColumn(int column, int fontSize, int charCount, Glyph g) {
        // TODO Auto-generated method stub
//        if(!g.getID().equals("char")) {
        if (g.getID().equals("char"))
            column = column + fontSize;
        else if(g.getID().equals("image")) {
            column = 0;
        }
        else
            column = column + fontSize+ 20;

        return column;
    }

		
}