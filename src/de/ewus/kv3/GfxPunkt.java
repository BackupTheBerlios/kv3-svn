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

/**
 * Stellt einen Punkt in der grafischen Auswertung dar
 * 
 * @author     Erik Wegner
 * @version    1.0
 */
public class GfxPunkt {
    public int 
	/** 
	 * x-Wert 
	 */
	x, 
	/** 
	 * y-Wert 
	 */
	y, 
	/** 
	 * z-Wert (z.B. Farbe)
	 */
	z;
    
    /**
     * Constructor f�r GfxPunkt
     *
     * Die Werte f�r x, y, und z werden auf 0 gesetzt.
     */
    public GfxPunkt() {
	x = y = z = 0;
    }
}
