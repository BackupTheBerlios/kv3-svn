/*
 * Zahlenformatierer.java
 *
 * Created on 14. Januar 2005, 21:22
 *
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

import java.text.NumberFormat;

/**
 * Die Klasse bietet Möglichkeiten zur Formatierung von Zahlen mit einer 
 * bestimmten Anzahl Nachkommastellen
 *
 * @author e-man
 * @version    1.0
*/
public class Zahlenformatierer {
    public NumberFormat 
        /** Formatierung auf 2 Stellen */ 
        nf2nks, //_N_ach_K_omma_S_tellen
        /** Formatierung auf 3 Stelle */
        nf3nks;

    /**
     * Initialisiert die Klasse
     */
    public Zahlenformatierer() {
        this.nf2nks = NumberFormat.getInstance();
        this.nf2nks.setMaximumFractionDigits(2);
        this.nf2nks.setMinimumFractionDigits(2);
        this.nf3nks = NumberFormat.getInstance();
        this.nf3nks.setMaximumFractionDigits(3);
        this.nf3nks.setMinimumFractionDigits(3);
    }

}
