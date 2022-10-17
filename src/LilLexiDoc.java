/*
 * Author: Ali Sartaz Khan
 * Description: The Doc for the lilLexi Game
 */

import java.util.List;
import java.util.Stack;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * LilLexiDoc
 */
public class LilLexiDoc 
{
	protected LilLexiWindow ui;
	private List<Glyph> glyphs;
	private List<List<Glyph>> glyphWordList;
//	private int glyphInd;
	private Stack<Memory>undo;
	private Stack<Memory> redo;
	protected Container nameRepository;
	/**
	 * Constructor initiate all structures
	 */
	public LilLexiDoc() 
	{
		undo = new Stack<Memory>();
		redo = new Stack<Memory>();
		glyphs = new LinkedList<Glyph>();
		glyphWordList = new ArrayList<>();
		
	}
	
	/**
	 * Sets UI
	 */
	public void setUI(LilLexiWindow ui) {this.ui = ui;}

	
	/**
	 * add a char and update UI
	 * @param fontColor 
	 * @param shell 
	 */
	public void add( int cursor, Glyph g)
	{
		if (glyphs.size() == 0 || cursor == glyphs.size()-1) {
			glyphs.add(g);
			undo.push(new Memory(g,glyphs.indexOf(g),"add"));
			
//			letters.add(glyphs.get(glyphs.size()-1).getChar());
		}
		else if(cursor+1<glyphs.size()) {
			glyphs.add(cursor+1, g);
			undo.push(new Memory(g,glyphs.indexOf(g),"add"));
			
//			letters.add(cursor+1, glyphs.get(cursor+1).getChar());
		}else {
			System.out.println("!!!!!!!! char couldnt be added !!!!!!\n");}
	
		
		if (g.getChar() ==' ') 
			spellCheck();

		ui.updateUI();
	}
	
	/*
	 * Runs spellcheck on all the words in the canvas and highlights them red
	 */
	private void spellCheck() {
		int start = -1;
		int i=0;
		for (; i< glyphs.size();i++) { // seperate list based on words
			Glyph g = glyphs.get(i);
			if (start == -1 && g.getID() == "char"){
				char c = g.getChar();
				if (c >= 'A' && c<= 'z')
					start = i;
			}
			
			else if (start != -1 && (g.getID() != "char" || g.getChar() == ' ' || g.getChar() == ','
					|| g.getChar() == '.' || g.getChar() == '?' || g.getChar() == '!')) {
				words(start, i);
				start = -1;//might have to change to iterate till you reach char
			}
		}
		if (start != -1) {
			System.out.println("XX");
			int end = i;
			words(start, end);
		}
		
		nameRepository = new WordRepository(glyphWordList); //Uses iterator pattern 
		for(Iterator iter = nameRepository.getIterator(); iter.hasNext();){
	         List<Glyph> names = (List<Glyph>) iter.next();
	         String name = "";
	         for (Glyph g: names) {
	        	 name += g.getChar();
	         }
	         if (!Dictionary.dict.contains(name.toLowerCase())) {
	        	 for (Glyph g: names) {
		        	 g.setMistake(true);
		         }
	         }
	         else {
	        	 for (Glyph g: names) {
		        	 g.setMistake(false);
		         }
	         }
	         
	      } 
		
	}

	
	/*
	 * Separates the words into their own Lists and adds them to glyphwordList
	 * 
	 * start: starting index
	 * end: ending index
	 */
	private void words(int start, int end) {
		List<Glyph> lst = new ArrayList<>();
		
		for(int i = start; i<end;i++) {
//			System.out.println(start);
			Glyph g = glyphs.get(i);
			if (g.getID() == "char")
				lst.add(glyphs.get(i));
		}
		if (lst.size()!= 0)
			glyphWordList.add(lst);

	}
	
	/*
	 * Prints 2D list for bug checking 
	 */
	private void printlst() {
		for(int i=0;i<glyphWordList.size();i++)
		{
			List<Glyph> lst = glyphWordList.get(i);
			System.out.println("Inner list len: " +lst.size());
			System.out.print("[");
			for (int j=0;j<lst.size();j++) {
				System.out.print(lst.get(j).getChar());
			}
			System.out.println("]");
		}
	}
	
	
	/*
	 * Removes glyph at cursor and updates UI
	 * 
	 * cursor: cursor index value
	 */
	public void remove(int cursor) {
		if (cursor >= 0) {
//			undo.remove(cursor);
			Glyph g =glyphs.get(cursor);
			glyphs.remove(cursor);
			undo.push(new Memory(g, cursor, "remove"));
//			redo.push(new Memory(g,cursor));
//			printUndo();
			ui.updateUI();
		}
		
	}
	
	/**
	 * basically, undoes the last user command on canvas;
	 * @return
	 */
	public boolean undoLastCommand() {
		if(undo.isEmpty()) {return false;}
		
		Memory m = undo.pop();
		redo.push(m);
		//if user added smth last, this will delete it
		if(m.op.equals("add")) {
			glyphs.remove(m.actualIndex);
			ui.updateUI();
			return true;
		}else {
			//if user deleted smth last, this will add it back
			glyphs.add(m.actualIndex, m.g);
			ui.updateUI();
			return false;
			
		}
		
	
	}
	
	/**
	 * basically, it will redo the last undo command.
	 */
	public boolean redoLastCommand() {
		if(redo.isEmpty()) {return false;}
		Memory m = redo.pop();
		undo.push(m);
		//if user deleted smth last, this will delete 
		if(m.op.equals("remove")) {
			glyphs.remove(m.actualIndex);
			ui.updateUI();
			return false;
		}else {
			//if user added smth last, this will add it
			glyphs.add(m.actualIndex, m.g);
			ui.updateUI();
			return true;
			
		}
	
	}
	
	/**
	 * returns glyphs list
	 */
	public List<Glyph> getGlyphs(){return glyphs;}

	public int getGlyphSize() {
		// TODO Auto-generated method stub
		return glyphs.size();
	}
	
	
	/*
	 * returns the glyph in the associated index
	 * 
	 * cursor: cursor index point
	 */
	protected Glyph getGlyphCursor(int cursor) {
		
		int index =0;
		if(glyphs.size()==0) {
			return null;
		}

		while(glyphs.size()>index) {
			Glyph g = glyphs.get(index);
//			if(!g.getID().equals("char")) {index++;}
			if(index==cursor) {return g;}
			index++;
		}
		System.out.println("glyph index is screwed up\n");
		return null;
		
		
	}

}
/**
 * used to store user's typing history.
 * used to perform the undo/redo ops.
 * @author Khojiakbar Yokubjonov
 *
 */
class Memory{
	protected Glyph g;
	protected int actualIndex; 
	protected String op; //either the delete or add operation
	
	/**
	 * Constructs a memory class, used to perfor the undo/redo ops.
	 * @param g - glyph which was entered/deleted
	 * @param ind1 - //index of glyphg in the glyphs list
	 * @param op //either the delete or add operation
	 */
	protected Memory(Glyph g, int ind1, String op) {
		this.g = g;
		this.actualIndex = ind1;
		this.op = op;
		
	}
}
