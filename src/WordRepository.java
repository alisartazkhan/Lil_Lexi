/*
 * Author: Ali Sartaz Khan
 * Description: Uses iterator pattern to create classe that
 * hold the list of glyphs and iterates through it. Uses Iterator
 * Pattern.
 */

import java.util.List;

public class WordRepository implements Container {
	
   protected List<List<Glyph>> words;
   
   /*
    * Constructor stores list 
    * 
    * wordGlyphs: list of glyphs
    */
   public WordRepository(List<List<Glyph>> wordGlyphs) {
	   this.words = wordGlyphs;
	   
   }
   
   
   /*
    * Returns the iterator object
    */
   @Override
   public Iterator getIterator() {
      return new WordIterator();
   }

   private class WordIterator implements Iterator {

     int index;

     
     /*
      * Checks if the list has a next value and returns true or false
      */
      @Override
	public boolean hasNext() {
      
         if(index < words.size()){
            return true;
         }
         return false;
      }

      
      /*
       * Returns the next object in the list
       */
      @Override
	public List<Glyph> next() {
      
         if(this.hasNext()){
            return words.get(index++);
         }
         return null;
      }		
   }
}