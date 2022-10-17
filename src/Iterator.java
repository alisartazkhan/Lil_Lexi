/*
 * Author: Ali Sartaz Khan
 * Description: Iterator interface for Iterator Pattern.
 */

import java.util.List;

public interface Iterator {
	/*
     * Checks if the list has a next value and returns true or false
     */
   public boolean hasNext();
   
   /*
    * Returns the next object in the list
    */
   public List<Glyph> next();
}