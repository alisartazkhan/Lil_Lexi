/*
 * Author: Khojiakbar Yokubjonov
 * Description: Implements a LilLexi Window. it is a window without a scroll bar 
 * (basic version of a LilLexi window)
 */

/*
 * Decorator pattern
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;

import java.util.List;

/**
 * LilLexiWindow
 * 
 */
public class LilLexiSimpleWindow extends LilLexiWindow {
	private LilLexiDoc currentDoc;
	private LilLexiControl lexiControl;
	private Display display;
	private Shell shell;
	protected Canvas canvas;
	private int charCount; // the # of chars that can fit a line; calculated based off the canvas width
	protected int rowOffset;
	protected int lineWidth; // widths of a line; calculated based on the font size
	private int row;
	private Button undo;
	private Button redo;
	Composite lowerComp;
	Composite upperComp;
	protected Composite featureComp;
	protected Menu menuBar, fileMenu, insertMenu, helpMenu, fontColorMenu, fontSizeMenu, fontTypeMenu, shapeMenu;
	protected MenuItem fileMenuHeader, insertMenuHeader, helpMenuHeader, fileExitItem, fileSaveItem,
			helpGetHelpItem, fontColorHeader, fontSizeHeader, fontTypeHeader, insertImageItem, insertRectItem,
			shapeHeader;
	private Font font;
	private String fontName;
	private int fontSize;
	private int fontColor; // default font color
	private Menu ImageMenu;
	
	protected static int shellWidth = 835;
	protected static int shellHeight = 900;


	/**
	 * Constructs a Lillexi window
	 */
	public LilLexiSimpleWindow() {
		Display.setAppName("Lil Lexi");
		display = new Display();
		shell = new Shell(display);
		shell.setText("Lil Lexi");
		shell.setSize(shellWidth, shellHeight);
		shell.setLayout(new GridLayout());
		rowOffset = 0;
		fontColor = SWT.COLOR_BLACK;
		fontName = "Courier";
		font = new Font(display, fontName, fontSize, SWT.BOLD);
		fontSize = 11;
		featureComp = new Composite(shell, SWT.NO_FOCUS);	
		featureComp.setLayout(new RowLayout());	
		upperComp = new Composite(shell, SWT.NO_FOCUS);	
		
		
	}

	/**
	 * start the editor
	 */
	public void start() {
		// ---- create widgets for the interface
		
		setUpCanvas();
		setUpUndoRedoButtons();	
		drawVerticalScrollbar();	

		
		setVerticalScrollbar();
		createMenuBar();
		shell.setMenuBar(menuBar);

		// ---- event loop
		shell.open();
		while (!shell.isDisposed())
			if (!display.readAndDispatch()) {
			}
		display.dispose();
	}
	/*
	 * sets up a menu for all the shape objects available in LilLexi
	 */
	protected void setUpShapes() {	
		shapeHeader = new MenuItem(menuBar, SWT.CASCADE);	
		shapeHeader.setText("Shapes");	
		shapeMenu = new Menu(shell, SWT.DROP_DOWN);	
		shapeHeader.setMenu(shapeMenu);	
		setUpShapeMenu(shapeMenu);	
			
			
	}	
	/*
	 * sets up shape obj and adds listeners to them
	 */
	protected void setUpShapeMenu(Menu shapeMenu) {	
		MenuItem rectangle = new MenuItem(shapeMenu, SWT.PUSH);	
		rectangle.setText("Rectangle");	
		rectangle.addSelectionListener(new SelectionListener() {	
			@Override	
			public void widgetSelected(SelectionEvent e) {	
				// TODO Auto-generated method stub	
				lexiControl.add(new ZRectangle(' ', font, fontColor ));
			}	
			@Override	
			public void widgetDefaultSelected(SelectionEvent e) {	
				// TODO Auto-generated method stub	
					
			}	
				
		});	
			
			
		MenuItem circle = new MenuItem(shapeMenu, SWT.PUSH);	
		circle.setText("Circle");	
		circle.addSelectionListener(new SelectionListener() {	
			@Override	
			public void widgetSelected(SelectionEvent e) {	
				// TODO Auto-generated method stub	
				lexiControl.add(new Circle(' ', font, fontColor));	
			}	
			@Override	
			public void widgetDefaultSelected(SelectionEvent e) {	
				// TODO Auto-generated method stub	
					
			}	
				
		});	
			
			
	}
	
	/*
	 * sets up the canvas for LilLexi
	 */
	protected void setUpCanvas() {
		// ---- canvas for the document
		canvas = new Canvas(upperComp, SWT.FOCUSED);
		canvas.setSize(730, 750);
		Cursor cursor = Display.getCurrent().getSystemCursor(SWT.CURSOR_IBEAM);
		canvas.setCursor(cursor);
		canvas.setFocus();
		
		
		setCanvasPaintListener();
		setCanvasKeyListener();
		setCanvasMouseListener();
	}
	
	/*
	 * establishes the menu bar at the top of shell
	 * 
	 */
	protected void createMenuBar() {
		menuBar = new Menu(shell, SWT.BAR);
		createFileMenu(); //file ops
//		createInsertMenu(); //file insert op
		createHelpMenu(); // help menu
		setUpShapes(); //shapes
		setUpFont(); //font
		setUpImages(); //images
	}
	
	/*
	 * sets up the images in the menu bar
	 */
	protected void setUpImages() {
		MenuItem ImageHeader = new MenuItem(menuBar, SWT.CASCADE);	
		ImageHeader.setText("Images");	
		ImageMenu = new Menu(shell, SWT.DROP_DOWN);	
		ImageHeader.setMenu(ImageMenu);	
		setUpImageMenu();	
	}
	
	/*
	 * sets up the menu for images
	 */
	void setUpImageMenu() {
		MenuItem shape1 = new MenuItem(ImageMenu, SWT.PUSH);	
		shape1.setText("Happy Face");	
		shape1.addSelectionListener(new SelectionListener() {	
			@Override	
			public void widgetSelected(SelectionEvent e) {	
				// TODO Auto-generated method stub	
//				lexiControl.add();	
//				System.out.println("adding image\n");
				lexiControl.add(new ZImage(display,"work.png"));
			}	
			@Override	
			public void widgetDefaultSelected(SelectionEvent e) {	
				// TODO Auto-generated method stub	
					
			}	
				
		});	
			
			
		MenuItem shape2 = new MenuItem(ImageMenu, SWT.PUSH);	
		shape2.setText("Apple");	
		shape2.addSelectionListener(new SelectionListener() {	
			@Override	
			public void widgetSelected(SelectionEvent e) {	
				// TODO Auto-generated method stub	
//				System.out.println("adding image\n");
				lexiControl.add(new ZImage(display,"apple.jpg"));
//				lexiControl.add();	
			}	
			@Override	
			public void widgetDefaultSelected(SelectionEvent e) {	
				// TODO Auto-generated method stub	
					
			}	
				
		});	
	}
	/*
	 * sets up the font menu
	 */
	protected void setUpFont() {
		 fontColorHeader = new MenuItem(menuBar, SWT.CASCADE); // font Color
		    fontColorHeader.setText("Color");
			fontColorMenu = new Menu(shell, SWT.DROP_DOWN);
			fontColorHeader.setMenu(fontColorMenu);
			fontColorMenu(fontColorMenu);
			
			
			fontSizeHeader = new MenuItem(menuBar, SWT.CASCADE); // font Size 
			fontSizeHeader.setText("Size");
		    fontSizeMenu = new Menu(shell, SWT.DROP_DOWN);
		    fontSizeHeader.setMenu(fontSizeMenu);
			fontSizeMenu(fontSizeMenu);
			
			fontTypeHeader = new MenuItem(menuBar, SWT.CASCADE); // font Size 
			fontTypeHeader.setText("Font");
			fontTypeMenu = new Menu(shell, SWT.DROP_DOWN);
		    fontTypeHeader.setMenu(fontTypeMenu);
			fontTypeMenu(fontTypeMenu);
	}
	/**
	 * adds font size option in the font menu
	 * @param fontMenu
	 */
	private void fontSizeMenu(Menu fontMenu) {
		MenuItem font11 = new MenuItem(fontMenu, SWT.PUSH);
		font11.setText("11");
		
		MenuItem font12 = new MenuItem(fontMenu, SWT.PUSH);
		font12.setText("12");
		
		MenuItem font13 = new MenuItem(fontMenu, SWT.PUSH);
		font13.setText("13");
		
		MenuItem font14 = new MenuItem(fontMenu, SWT.PUSH);
		font14.setText("14");
		

		
		
		
		font11.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		setFontSize(11);	
	    		
	    	}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}});
		font12.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		setFontSize(12);		
	    	}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}});
		font13.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		setFontSize(13);
	    		
	    	}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}});
		
		font14.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		setFontSize(14);
	    		
	    	}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}});
	}
	
		

	/**
	 * add a font color menu item to the font menu
	 * @param fontMenu
	 */
	void fontColorMenu(Menu fontMenu) {
		
		MenuItem fontRed = new MenuItem(fontMenu, SWT.PUSH);
		fontRed.setText("Red");
		
		MenuItem fontBlue = new MenuItem(fontMenu, SWT.PUSH);
		fontBlue.setText("Blue");
		
		MenuItem fontGreen = new MenuItem(fontMenu, SWT.PUSH);
		fontGreen.setText("Green");
		
		MenuItem fontBlack = new MenuItem(fontMenu, SWT.PUSH);
		fontBlack.setText("Black");
		
//		fontSelected = fontBlack; // default color
		
		
		
		fontRed.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		setFontColor(SWT.COLOR_RED);	
	    		
	    	}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}});
		fontBlue.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
//	    		fontSelected = fontBlue;
	    		setFontColor(SWT.COLOR_BLUE);		
	    	}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}});
		fontGreen.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
//	    		fontSelected = fontGreen;
	    		setFontColor(SWT.COLOR_GREEN);
	    		
	    	}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}});
		
		fontBlack.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
//	    		fontSelected = fontBlack;
	    		setFontColor(SWT.COLOR_BLACK);
	    		
	    		
	    	}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}});
	}
	
	/*
	 * sets up the font types
	 */
	void fontTypeMenu(Menu fontMenu) {

		MenuItem fontTNR = new MenuItem(fontMenu, SWT.PUSH);
		fontTNR.setText("Bahnschrift");
		
		MenuItem fontCalibri = new MenuItem(fontMenu, SWT.PUSH);
		fontCalibri.setText("Calibri");
		
		MenuItem fontCourier = new MenuItem(fontMenu, SWT.PUSH);
		fontCourier.setText("Courier");
		
		MenuItem fontArial = new MenuItem(fontMenu, SWT.PUSH);
		fontArial.setText("Arial");

		
		
		
		fontTNR.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		setFontType("Bahnschrift Condensed");
	    	}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}});
		fontCalibri.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		setFontType("Calibri");	
	    	}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}});
		fontCourier.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		setFontType("Courier");
	    		
	    	}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}});
		
		fontArial.addSelectionListener(new SelectionListener() {
	    	public void widgetSelected(SelectionEvent event) {
	    		setFontType("Arial Narrow");
	    		
	    	}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {		
			}});
	}
	
	/*
	 * creates the file menu
	 */
	protected void createFileMenu() {
		fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		fileMenuHeader.setText("File");
		fileMenu = new Menu(shell, SWT.DROP_DOWN);
		fileMenuHeader.setMenu(fileMenu);
		createFileSaveItem();
		createFileExitItem();
		
		
	}
	
	/*
	 * file save menu. Not supprted
	 */
	protected void createFileSaveItem() {
		fileSaveItem = new MenuItem(fileMenu, SWT.PUSH);
		fileSaveItem.setText("Save");
		fileSaveItem.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
			}

			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
	}
	
	/*
	 * file exit option. when selected, exits the program
	 */
	protected void createFileExitItem() {
		fileExitItem = new MenuItem(fileMenu, SWT.PUSH);
		fileExitItem.setText("Exit");
		fileExitItem.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				shell.close();
				display.dispose();
			}

			public void widgetDefaultSelected(SelectionEvent event) {
				shell.close();
				display.dispose();
			}
		});
		
		
	}
	
	/*
	 * file insert option in the menu. NOT supported at this time
	 */
	protected void createInsertMenu() {
		insertMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		insertMenuHeader.setText("Insert");
		insertMenu = new Menu(shell, SWT.DROP_DOWN);
		insertMenuHeader.setMenu(insertMenu);

		insertImageItem = new MenuItem(insertMenu, SWT.PUSH);
		insertImageItem.setText("Image");
		insertRectItem = new MenuItem(insertMenu, SWT.PUSH);
		insertRectItem.setText("Rectangle");
	}
	
	/**
	 * help option in the menu. Not supported at this time.
	 */
	protected void createHelpMenu() {
		helpMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		helpMenuHeader.setText("Help");
		helpMenu = new Menu(shell, SWT.DROP_DOWN);
		helpMenuHeader.setMenu(helpMenu);

		helpGetHelpItem = new MenuItem(helpMenu, SWT.PUSH);
		helpGetHelpItem.setText("Get Help");
		helpGetHelpItem.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
			}

			public void widgetDefaultSelected(SelectionEvent event) {
			}
		});
	}
	
	/*
	 * adds a paint listener to the canvas.
	 */
	protected void setCanvasPaintListener() {
		canvas.addPaintListener(e -> {
			Rectangle rect = shell.getClientArea();
			e.gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
			e.gc.fillRectangle(rect.x, rect.y, rect.width, rect.height);
			e.gc.setForeground(display.getSystemColor(SWT.COLOR_BLUE));
			lineWidth = (int) (fontSize*1.6) ;
			charCount = (canvas.getSize().x - 50) / fontSize;
			List<Glyph> glyphs = currentDoc.getGlyphs();
			int column = 0; boolean freshStart = true;
			row = 0;
			row += rowOffset;
			
			//printing all entered chars
			for (Glyph g : glyphs) {
				int rowIncrement = (int)(g.getFontSize() * 2);
				if(g.getID().equals("ENTER")) {
					row += 17+(0.9*g.getFontSize());
					column=0;
					g.draw(e,  display, column, row, fontSize);
					}
				
				else {
					g.draw(e,  display, column, row, fontSize);
					
					column = lexiControl.incrementColumn(column, g.getFontSize(), charCount, g);
					
					if(g.getID().equals("image")) {//image requires the space equal to its height
						ZImage img = (ZImage)g;
						row+=(img.getHeight()+40);
					}else if (column > 710) {
						row += rowIncrement;
						column = 0;
					}
					System.out.println();
					
					}
				}
			}
			
		);}
	
	
	/*
	 * sets up a mouse listener for the canvas
	 */
	protected void setCanvasMouseListener() {
		canvas.setFocus();
		canvas.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent e) {
				System.out.println("mouseDown in canvas");
				canvas.setFocus();
				
			}

			public void mouseUp(MouseEvent e) {
				canvas.setFocus();
			}

			public void mouseDoubleClick(MouseEvent e) {
				canvas.setFocus();
			}
			
			
			
		});
		
	}
	
	/*
	 * sets a keylistener for the canvas
	 */
	protected void setCanvasKeyListener() {
		canvas.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if(e.keyCode == SWT.BS) {
					if (currentDoc.getGlyphSize() > 0)
						lexiControl.removeChar();
					
				}else if(e.keyCode==SWT.ARROW_LEFT) {
					lexiControl.moveCursorLeft();
					
				}else if(e.keyCode == SWT.ARROW_RIGHT) {
					lexiControl.moveCursorRight();
				}else if(e.keyCode == SWT.CR) {
					lexiControl.add(new FuncKey(e.character, "ENTER"));
					
				}else if(e.keyCode == SWT.ARROW_UP) {

				}else if(e.keyCode == SWT.ARROW_DOWN) {
				}else if(e.keyCode == SWT.SHIFT) {

				}
				else if(e.keyCode == SWT.CTRL) {}
				else if(e.keyCode == SWT.SHIFT) {}
				else if(e.keyCode == SWT.MouseDown) {
					canvas.forceFocus();
				}
				else {

				int num = e.keyCode;
				if ((num >= ' ' && num <= '~'))
					lexiControl.add(new Glyph(e.character, fontColor, font, fontSize));
				}
			}

			public void keyReleased(KeyEvent e) {
			}
		});
	}
	/*
	 * returns the current/default font size
	 */
	protected int getFontSize() {
		return fontSize;
	}
	
	/*
	 * returns the num of chars on canvas
	 */
	protected int getCharCount() {
		return charCount;
	}
	//set system font color for system to change colors when font color is changed
	protected void setFontColor(int f) {
			fontColor = f;
		}
	//set font size for system
	protected void setFontSize(int size) {
		fontSize = size;
		font = new Font(display, fontName, fontSize, SWT.BOLD);
	}
	//set font type for system
	protected void setFontType(String string) {
		fontName = string;
		font = new Font(display, fontName, fontSize, SWT.BOLD);
	}
	
	/*
	 * sets up buttons to do the undo & redo ops.
	 */
	protected void setUpUndoRedoButtons(){
		undo = new Button(featureComp, SWT.NO_FOCUS);
		undo.setSelection(false);
		undo.setText("Undo");
		canvas.setFocus();
		
		undo.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
				lexiControl.undo();
				canvas.setFocus();
				
			}
			
		});
	
		
		redo = new Button(featureComp, SWT.NO_FOCUS);
		redo.setSelection(false);
		redo.setText("Redo");
		canvas.setFocus();
		redo.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				lexiControl.redo();
				canvas.setFocus();
			}
			
		});
		
		
	}
	//not implemented in the simple window
	public void drawVerticalScrollbar() {}
	
	
	protected void setVerticalScrollbar() {}
	
	/**
	 * updateUI
	 */
	public void updateUI() {
//		System.out.println("updateUI");
		canvas.redraw();
	}

	/**
	 * setCurrentDoc
	 */
	public void setCurrentDoc(LilLexiDoc cd) {
		currentDoc = cd;
	}

	/**
	 * setController
	 */
	public void setController(LilLexiControl lc) {
		lexiControl = lc;
	}
}