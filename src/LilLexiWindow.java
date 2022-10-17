/**
 * Abstract UI class for LilLexi
 * @author Khojiakbar Yokubjonov
 *
 */

abstract class LilLexiWindow {
	
	public abstract void start();
	public abstract void updateUI();
	public abstract void setCurrentDoc(LilLexiDoc cd);
	public abstract void setController(LilLexiControl lc);
	public abstract void drawVerticalScrollbar();
	

}
