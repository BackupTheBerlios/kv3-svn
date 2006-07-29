/*
 * VectorExtended.java
 *
 * Created on 14. Januar 2005, 19:38
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

import java.util.Vector;

/**
 * Die Klasse erweitert die Vector-Klasse um die Funktion zur Berechnung der Summe.
 * @see java.util.Vector
 * @author e-man
 * @version    1.0
*/
public class VectorExtended<E extends Historieneintrag> extends Vector<E>{
    
    private float 
        summe_Strecke = 0.0f,
        summe_Kraftstoff = 0.0f,
        summe_Preis = 0.0f;
    
    /**
     * Gibt die Summe eines Datenfelds zurück.
     * @see Historie#summe(int)
     * @param feld 
     * @return Die Summe
     */
    public float summe(int feld) {
        float r = 0.0f;
        if (feld == Historieneintrag.STRECKE ) r = summe_Strecke;
        if (feld == Historieneintrag.KRAFTSTOFF) r = summe_Kraftstoff;
        if (feld == Historieneintrag.PREIS) r = summe_Preis;
        return r;
    }
    
    public boolean add(E o) {
        this.addiereHE(o);
        return super.add(o);
    }
    
    public void removeElementAt(int index) {        
        this.subtrahiereHE(elementAt(index));
        super.removeElementAt(index);
    }
    
    public void setElementAt(E obj, int index) {
        //Zuerst das alte Element von den Summenfeldern abziehen
        this.subtrahiereHE(elementAt(index));
        //dafür das neue Element hinzuaddieren
        this.addiereHE(obj);
        super.setElementAt(obj, index);
    }
    
    
    /**
     * Nimmt einen Historieneintrag zur Summen- und Durchschnittsberechnung hinzu.
     * @param o Der Historieneintrag
     */
    private void addiereHE(Historieneintrag o) {
        this.summe_Strecke += o.holeStrecke();
        this.summe_Kraftstoff += o.holeKraftstoff();
        this.summe_Preis += o.holePreis() * o.holeKraftstoff();        
    }
    
    
    /**
     * Entfernt einen Historieneintrag aus der Summen- und Durchschnittsberechnung.
     * @param he Der Historieneintrag
     */
    private void subtrahiereHE(Historieneintrag he) {
        this.summe_Strecke -= he.holeStrecke();
        this.summe_Kraftstoff -= he.holeKraftstoff();
        this.summe_Preis -= he.holePreis() * he.holeKraftstoff();        
    }
    
}
