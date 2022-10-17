/**
 * @author Khojiakbar Yokubjonov
 * @brief Description: Implements a LilLexi Window. it is a window with a scroll bar 
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Slider;

public class LilLexiWindowWithVerticalScrollbar extends LilLexiSimpleWindow{
	private int sliderLastVal; //last position of the slider
	
	public LilLexiWindowWithVerticalScrollbar() {
		super();
		
		sliderLastVal = 0;
		
	}
	/**
	 * implements the slider in the LilLexi window
	 */
	@Override
	public void drawVerticalScrollbar() {
		setVerticalScrollbar();
	}
	/*
	 * helper method to implement the slider obj
	 */
	protected void setVerticalScrollbar() {
		Slider slider = new Slider(upperComp, SWT.VERTICAL);

//		slider.setBackground(display.getSystemColor(SWT.));
		Rectangle clientArea = canvas.getClientArea();
		slider.setMinimum(0);
		slider.setMaximum(100);
		slider.setBounds(clientArea.width, clientArea.y, 32, clientArea.height);
		slider.addListener(SWT.Selection, event -> {
			String string = "SWT.NONE";
			switch (event.detail) {
			case SWT.DRAG:
				string = "SWT.DRAG";

				// every pixel change in slider's Y coord reflect a line width;
				rowOffset -= getSliderChange(slider.getSelection()) * lineWidth;
//				System.out.println("aifnadfian");
				setYCoord(rowOffset);
				canvas.redraw();
				break;
			case SWT.HOME:
				string = "SWT.HOME";
				break;
			case SWT.END:
				string = "SWT.END";
				break;
			case SWT.ARROW_DOWN:
				string = "SWT.ARROW_DOWN"; break;

			case SWT.ARROW_UP:
				string = "SWT.ARROW_UP";
				break;
			case SWT.PAGE_DOWN:
				string = "SWT.PAGE_DOWN";
				break;
			case SWT.PAGE_UP:
				string = "SWT.PAGE_UP";
				break;
			}
		});
		canvas.setFocus();
	}

	/**
	 * computes and returns the drag value the slider positive val indicates a
	 * downward direction negative val indicates an upward direction
	 * 
	 * @param currentVal
	 * @return
	 */
	int getSliderChange(int currentVal) {
//		System.out.println("XXXX");
		int diff = currentVal - sliderLastVal;
		sliderLastVal = currentVal;
		return diff;
	}

	/**
	 * keeps track of the previous location of the slider obj
	 * 
	 * @param val
	 */
	void setYCoord(int val) {
		rowOffset = val;
	}

}