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
 * Erzeugt grafische Auswertungen.
 * 
 * @author     Erik Wegner
 * @version    1.0
 */
public class Grafikfabrik implements Runnable
{
    private Kleber kleber;

    /**
     * Einstellungen zur Grafik
     */
    private GfxEinstellungen ge;

    /**
     * Der Historieneintrag wird geprüft, ob er in der Grafik 
     * auftaucht, je nach Filterkriterium des Benutzers
     */
    private boolean eintragsfilter(Historieneintrag he) {
	return true;
    }

    /**
     * Aus dem Historieneintrag wird ein GfxPunkt erzeugt
     *
     * @param he   Der Historieneintrag zur Darstellung im Diagramm
     * @return     Ein GfxPunkt zur Darstellung im Diagramm
     */
    private GfxPunkt punkterzeuger(Historieneintrag he) {
	GfxPunkt p;
	return p;
    }

    /**
     * Testet, ob laut GfxEinstellungen die Diagrammpunkte sortiert werden sollen
     *
     * @return    Wahrheitswert gibt an, ob sortiert werden soll
     */
    private boolean sortieren() {
	return false;
    }

    /**
     * Die Methode erzeugt das Diagramm.
     *
     * Die Methode wird durch Aufruf von run() aktiviert.
     * @see run()
     */
    private erzeugeBild() {
	//Vorbereitungen: Variablen, Instanzen, Initialisierungen
	Historieneintrag he;
	Historie historie = kleber.holeHistorie();
	Vector<GfxPunkt> punkte = new Vector<GfxPunkt>();
	GfxPunkt maxPunkt = new GfxPunkt();
	int breite = ge.breite,
	    hoehe = ge.hoehe;
	//Alle Punkte durchlaufen, ob sie in das Diagramm kommen
	int anzahlHE = historie.size();
	for (int c1 = 0; c1 < anzahlHE; c1++) {
	    he = historie.holeEintragNr(c1);
	    if (eintragfilter(he)) {
		GfxPunkt p = punkterzeuger(he);
		//Größten x- und y-Wert speichern
		if (p.x > maxPunkt.x) maxPunkt.x = p.x;
		if (p.y > maxPunkt.y) maxPunkt.y = p.y;
		punkte.add(p);
	    }
	}
	//Diagrammpunkte sortieren
	//if (sortierePunkte) punkte.sort;
	//Diagramm zeichen
	int anzahlPunke = punkte.size();
	GfxPunkt punkt, vorgaenger = null;
	for(int c2 = 0; c2 < anzahlPunkte; c2++) {
	    punkt = punkte.elementAt(c2);
	    // Zeichne Punkt
	    //...
	    if (c2 > 0 && ge.zeichneLinien) {
		//ZeichneLinie
		//...
	    }
	    vorgaenger = punkt;
	}
	//zeichneAchsen
	//...
	//Informiere Kleber, das Diagramm ist fertig
	kleber.diagrammFertig();
    }
    
    /**
     * Die Methode startet die Diagrammerzeugung.
     *
     * Die Methode kehrt unverzüglich zurück.
     */
    public void run() {
	erzeugeBild();
    }

    /**
     * Constructor for objects of class Grafikfabrik
     *
     * @param kleber Für Zugriff auf die Historie
     */
    public Grafikfabrik(Kleber kleber) {
    }

}
