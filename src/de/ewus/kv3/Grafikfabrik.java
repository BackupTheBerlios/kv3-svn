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

import java.util.Collections;
import java.util.Vector;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Erzeugt grafische Auswertungen.
 * 
 * @author     Erik Wegner
 * @version    1.0
 */
public class Grafikfabrik implements Runnable
{
    private Kleber kleber;
    private BufferedImage diagramm;
    private Thread zeichenThread = null;
    
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
	GfxPunkt p = new GfxPunkt();
	System.out.println("Eintrag " + he.toString() + " wird verarbeitet.");
	//Auswahl der Wertequelle für x-Achse
	switch (ge.xAchse) {
	case Historieneintrag.KRAFTSTOFF : p.x = he.holeKraftstoff(); break;
	case Historieneintrag.STRECKE : p.x = he.holeStrecke(); break;
	default :
	    System.err.println("Auswahl für Wertequelle x-Achse nicht gültig");
	}
	
	//Auswahl der Wertequelle für y-Achse
	switch (ge.yAchse) {
	case Historieneintrag.KRAFTSTOFF : p.y = he.holeKraftstoff(); break;
	case Historieneintrag.STRECKE : p.y = he.holeStrecke(); break;
	default :
	    System.err.println("Auswahl für Wertequelle x-Achse nicht gültig");
	}
	
	//Rückgabe des Ergebnis
	System.out.println("GfxPunkt: " + p.x + "," + p.y);
	return p;
    }

    /**
     * Testet, ob laut GfxEinstellungen die Diagrammpunkte sortiert 
     * werden sollen
     *
     * @return    Wahrheitswert gibt an, ob sortiert werden soll
     */
    private boolean sortieren() {
	return (ge.sortierung != GfxEinstellungen.Sortierung.KEINE);
    }

    /**
     * Die Methode erzeugt das Diagramm.
     *
     * Die Methode wird durch Aufruf von run() aktiviert.
     * @see run()
     */
    private void erzeugeBild() {
	//Vorbereitungen: Variablen, Instanzen, Initialisierungen
        Graphics2D g2;
	Historieneintrag he;
        Historie historie = kleber.holeHistorie();
	Vector<GfxPunkt> punkte = new Vector<GfxPunkt>();
	GfxPunkt maxPunkt = new GfxPunkt();        
	int breite = ge.breite,
	    hoehe = ge.hoehe;
	System.out.println("Bildgröße " + breite + "x" + hoehe);
	diagramm = new BufferedImage(breite, hoehe, BufferedImage.TYPE_INT_RGB);
	g2 = diagramm.createGraphics();
        //Alle Punkte durchlaufen, ob sie in das Diagramm kommen
	int anzahlHE = historie.size();
        for (int c1 = 0; c1 < anzahlHE; c1++) {
	    he = historie.holeEintragNr(c1);
	    if (eintragsfilter(he)) {
		GfxPunkt p = punkterzeuger(he);
		//Größten x- und y-Wert speichern
		if (p.x > maxPunkt.x) maxPunkt.x = p.x;
		if (p.y > maxPunkt.y) maxPunkt.y = p.y;
		punkte.add(p);
	    }
	}
	//Diagrammpunkte sortieren
	//punkte = Collections.sort(punkte);

	//if (sortierePunkte) punkte.sort;
	//Diagramm zeichen
	//Fläche löschen
        g2.setBackground(Color.WHITE);
        g2.clearRect(0,0, breite-1, hoehe-1);
	//Grafikoperationen ausführen
        g2.setColor(Color.BLACK);
        g2.drawRect(10,10, breite - 21, hoehe - 21);
	g2.setColor(Color.RED);
	g2.drawRect(0,0, 2, 2);

	int anzahlPunkte = punkte.size();
	GfxPunkt punkt, vorgaenger = null;
        for(int c2 = 0; c2 < anzahlPunkte; c2++) {
	    punkt = punkte.elementAt(c2);
	    // Zeichne Punkt
	    //...
	    if (c2 > 0 && ge.zeichneLinie) {
		//ZeichneLinie
		//...
	    }
	    vorgaenger = punkt;
	}
	//zeichneAchsen
	//...
	//Informiere Kleber, das Diagramm ist fertig
        kleber.diagrammFertig(diagramm);
	zeichenThread = null;
    }

    /**
     * Die Methode startet die Diagrammerzeugung.
     *
     * Die Methode kehrt unverzüglich zurück.
     */
    public void start() {
        if (zeichenThread == null) {
            zeichenThread = new Thread(this, "DiagrammZeichner");
            zeichenThread.start();
        }
    }
    
    /**
     * Die Methode erzeugt das Diagramm.
     *
     * Die Methode sollte nicht direkt aufgerufen werden,
     * dazu kann der Aufruf von start() genutzt werden.
     *
     * @see #start()
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
        this.kleber = kleber;
        this.ge = kleber.holeGfxEinstellungen();
    }

}
