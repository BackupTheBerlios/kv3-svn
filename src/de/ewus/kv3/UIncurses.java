/*
 *  This file is part of Kraftstoffverbrauch3.
 *
 *  Kraftstoffverbrauch3 is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  Kraftstoffverbrauch3 is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Kraftstoffverbrauch3; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package de.ewus.kv3;


import java.awt.image.*;

/**
 * Diese Klasse bietet eine ncurses-basierte Programmoberfläche.
 *
 * @author     Erik Wegner
 * @version    0
 */
public class UIncurses extends UIManager
{
	// instance variables - replace the example below with your own
	private int x;

	/**
	 * Constructor for objects of class UIncurses
	 */
	public UIncurses()
	{
		// initialise instance variables
		x = 0;
	}

	/**
	 * An example of a method - replace this comment with your own
	 * 
	 * @param  y   a sample parameter for a method
	 * @return     the sum of x and y 
	 */
	public int sampleMethod(int y)
	{
		// put your code here
		return x + y;
	}
	
	public void startUI() {
	    super.neueWerte();
	}
    
    public void dispose() {}
    
    public HEDlgErgebnis bearbeiteHistorieneintrag(Historieneintrag e) {
        return HEDlgErgebnis.Abbrechen;
    }
    
    public void diagrammFertig(BufferedImage diagramm) {
        
    }
    
}
