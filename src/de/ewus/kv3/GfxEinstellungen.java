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
 * Diese Klasse stellt einen Container bereit, der alle möglichen Grafikeinstellungen beinhaltet.
 *
 * @author     Erik Wegner
 * @version    1.0
 */
public class GfxEinstellungen extends java.lang.Object
{

    public int
	/**
	 * Die Breite der erzeugten Grafik
	 */
	breite = 640,
	/**
	 * Die Höhe der erzeugten Grafik
	 */
	hoehe = 480;

    /**
     * Gibt an, ob Linien zwischen den Punkten gezeichnet werden sollen
     */
    public boolean zeichneLinie;
    
    public enum Sortierung {
        KEINE("Keine"), 
        XUP( "X aufsteigend"), 
        YUP( "Y aufsteigend"),
        XYUP("X, dann Y auf."),
        YXUP("Y, dann X auf.");
  
        private String bezeichnung;
  
        Sortierung(String bezeichnung)
        {this.bezeichnung = bezeichnung;}
  
        public String bezeichnung() {
            return this.bezeichnung;
        }
    } 
    
    /*
    public enum AchsenListe {
        Kraftstoffmenge("Kraftstoffmenge"), 
        Strecke("Strecke"), 
        ver100("Verbrauch l/100km"),
        strLtr("Strecke km/l"), 
        Datum("Datum"),
        Reihenfolge("Reihenfolge"),
        Fahrzeug("Fahrzeug");
  
        private String bezeichnung;
  
        AchsenListe(String bezeichnung)
        {this.bezeichnung = bezeichnung;}
  
        public String bezeichnung() {
            return this.bezeichnung;
        }
    }
    */

    public enum Drehung {
        D0(  "0 Grad", 0), 
        D1( "90 Grad", 1), 
        D2("180 Grad", 2),
        D3("270 Grad", 3);
    
        private String bezeichnung;
        private int drehschritte;
  
        Drehung(String bezeichnung, int drehschritte) {
            this.bezeichnung = bezeichnung;
            this.drehschritte = drehschritte;
        }
  
        public String bezeichnung() {
            return this.bezeichnung;
        }
  
	public int drehschritte() {
	    return this.drehschritte;
	}
    }

    public int
	/** Einstellung für Wertequelle x-Achse */
        xAchse,
	/** Einstellung für Wertequelle y-Achse */
        yAchse;

    /** Einstellung zur Sortierung der Grafikpunkte */
    public Sortierung 
        sortierung = Sortierung.KEINE;

    /** Einstellung für die Drehung der Grafikausgabe */
    public Drehung 
        drehung = Drehung.D0;

    /**
     * Konstruktor für die GfxEinstellungen
     *
     * Folgende Voreinstellungen werden aktiviert:
     * <ul>
     *   <li>x-Achse = Kraftstoff</li>
     *   <li>y-Achse = Strecke</li>
     *   <li>Sortierung = x aufsteigend</li>
     *   <li>Drechung = 0 Grad</li>
     * </ul> 
     */
    public GfxEinstellungen() {
        //setzeString2xAchse("Kraftstoffmenge");
        //setzeString2yAchse("Strecke");
        setzeString2sortierung("XUP");
        setzeString2drehung("D0");
	xAchse = Historieneintrag.KRAFTSTOFF;
	yAchse = Historieneintrag.STRECKE;
	/*
	this.xAchse = AchsenListe.Kraftstoffmenge;
        this.yAchse = AchsenListe.Strecke;
        this.sortierung = Sortierung.XUP;
        this.drehung = Drehung.D0;
	*/
	}
    
    public void setzeString2drehung(String v) {
        drehung = drehung.valueOf(drehung.getDeclaringClass(), v);
    }
    public void setzeString2sortierung(String v) {
        sortierung = sortierung.valueOf(sortierung.getDeclaringClass(), v);        
    }
    /*
    public void setzeString2xAchse(String v) {
        xAchse = xAchse.valueOf(xAchse.getDeclaringClass(), v);
    }
    
    public void setzeString2yAchse(String v) {
        yAchse = xAchse.valueOf(yAchse.getDeclaringClass(), v);
    }
    */
    
} 

