/*
 * Author: Khojiakbar Yokubjonov
 * Description: 
 */
/**
 * Lil Lexi Document Editor
 * 
 * LilLexiSimpleWindow()					- a window without a scroll bar
 * LilLexiWindowWithVerticalScrollbar()		- a window with a scroll bard (decorator pattern)
 * 
 */

public class LilLexi
{	
	static private LilLexiDoc currentDoc = null;

	public static void main(String args[])
	{
		if (currentDoc == null)
			currentDoc = new LilLexiDoc();
		LilLexiWindow lexiUI = new LilLexiWindowWithVerticalScrollbar();
		lexiUI.setCurrentDoc( currentDoc );
		MoveCursor moveCursorLeft = new MoveCursorLeft();
		MoveCursor moveCursorRight = new MoveCursorRight();
		currentDoc.setUI(lexiUI);
		
		LilLexiControl lexiControl = new LilLexiControl(currentDoc, moveCursorLeft, moveCursorRight);
		lexiUI.setController( lexiControl );
		
		lexiUI.start();
	} 
}