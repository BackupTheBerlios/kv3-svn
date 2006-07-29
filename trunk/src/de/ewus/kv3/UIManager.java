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
import java.io.*;
import java.awt.image.*;

/**
 * Abstract class UIManager - stellt die Methoden bereit, die
 * jede abgeleitete Oberfl�che beherrschen muss.
 *
 * @author     Erik Wegner
 * @version    3.0
 */
public abstract class UIManager {
    //Ergebnis des Dialogs zur Bearbeitung eines Historieneintrags
    /**
     * Definiert die Antwort-M�glichkeiten ds HistorienEintragBearbeitenDialogs
     */
    public enum HEDlgErgebnis{ 
        /**
         * Auswahl von OK
         */
        OK,  
        /**
         * Auswahl Abbrechen
         */
        Abbrechen, 
        /**
         * Auswahl L�schen
         */
        Loeschen };
    protected float
        /** Die Variable speichert die letzte Eingabe der Wegstrecke. */
        strecke = 0.0f,
        /** Die Variable speichert die letzte Eingabe des Kraftstoffs. */
        kraftstoff = 0.0f,        
        /** Die Variable speichert das letzte Berechnungsergebnis f�r Verbrauch je 100 Kilometern. */
        ver100 = 0.0f,
        /** Die Variable speichert das letzte Berechnungsergebnis f�r Strecke je 100 Kilometern. */
        strLtr = 0.0f;
        
    /** Die Grafikeinstellungen speichern alle Einstellungen zur Diagrammerzeugung */
    protected GfxEinstellungen ge;
        
    /**
     *  Description of the Field
     */
    protected Kleber kleber;


    /**
     * �bernimmt die Masterklebeklasse
     * @param kleber Die �bergeordnete Kleber-Instanz
     */
    public void setzeKleber(Kleber kleber) {
        this.kleber = kleber;
        ge = kleber.holeGfxEinstellungen();
    }


    /**
     * Die Methode wird von Kleber aufgerufen, nachdem die Berechnung durchgef�hrt wurde.
     * @param ver100 Der berechnete Wert f�r Verbrauch je 100 Kilometern
     */
    public void setzeVerbrauch100km(float ver100) {this.ver100 = ver100;}
    /**
     * Die Methode wird von Kleber aufgerufen, nachdem die Berechnung durchgef�hrt wurde.
     * @param strLtr Der berechnete Wert f�r Strecke je Kilometer
     */
    public void setzeStreckeJeLiter(float strLtr) {this.strLtr = strLtr;}
    
    /**
     *  Teilt  Kleber mit, dass neue Werte in die Historie �bernommen werden soll
     */
    protected void historieNeueWerte() {
        kleber.historieNeueWerte(strecke, kraftstoff);
    }
    
    /**
     * Ermittelt die Anzahl der in der Historie gespeicherten Eintr�ge.
     *
     * @return Anzahl der Eintr�ge
     */
    protected long historieAnzahlEintraege() {
        return kleber.holeHistorie().size();
    }
    
    /**
     * Von UIManager abgeleitete Klassen rufen diese Methode auf, wenn die Anwendung das Dialogfeld zur Bearbeitung eines Historieneintrags darstellen soll. Der Aufruf des Dialogfeldes wird durch die Kleberklasse durchgef�hrt.
     * @param eintragnr Die Nummer des Eintrags, der bearbeitet werden soll.
     */
    protected void historieEintragBearbeiten(int eintragnr) {
        kleber.historieEintragBearbeiten(eintragnr);
    }
    
    /**
     *  Teilt Kleber mit, dass neue Werte zur Berechnung vorliegen.
     */
    protected void neueWerte() {
        kleber.neueWerte(strecke, kraftstoff);
    }


    /**
     *  Teilt Kleber mit, dass die Anwendung beendet werden soll.
     */
    protected void quitApp() {
        kleber.quitApp();
    }


    /**
     *  Wird von Kleber aufgerufen, um die UI-Ressourcen freizugeben,
     *  anschlie�end wird die Anwendung beendet.
     */
    abstract void dispose();


    /**
     * Diese Methode wird durch den Kleber aufgerufen,
     * wenn die Oberfl�che dargestellt werden kann
     */
    abstract void startUI();
    

    /**
     * Die Methode erlaubt Zugriff auf den Properties-Speicher, der Benutzer- und Programmeinstellungen sichert.
     * @param key Die Bezeichnung des Schl�ssels in der Properties-Datei.
     * @return Der dem Schl�ssel zugeordnete Wert.
     */
    protected String getProperty(String key) {
        return kleber.getProperty(key);
    }
    
    /**
     * Die Methode erlaubt Zugriff auf den Properties-Speicher, der Benutzer- und Programmeinstellungen sichert.
     * @param key Die Bezeichnung des Schl�sselwortes.
     * @param value Der Wert, der dem Schl�sselwort zugeordnet werden soll.
     */
    protected void setProperty(String key, String value) {
        kleber.setProperty(key, value);
    }

    
    /**
     *  Diese Methode wird von Kleber aufgerufen, wenn der 
     *  Historieneintrag e durch den Benutzer bearbeitet werden soll.
     *  Der Benutzer �ndert die Werte des Historieneintrags,
     *  best�tigt die �nderungen (R�ckgabewert OK), verwirrft sie
     *  (Abbrechen) oder w�nscht den Eintrag zu l�schen (Loeschen).     
     *
     *  @param e        Der zu bearbeitende Historieneintrag
     */
    abstract HEDlgErgebnis bearbeiteHistorieneintrag(Historieneintrag e);

    /**
     * Das Diagramm wurde erzeugt.
     *
     * Die Methode wird von Kleber aufgerufen, wenn die Bearbeitung
     * des Diagramms abgeschlossen ist und durch die Oberfl�che dargestellt 
     * werden kann.
     * 
     * @param diagramm  Das Bild, das das Diagramm enth�lt.
     */
    abstract void diagrammFertig(BufferedImage diagramm);
    
    /**
     * Bietet abeleiteten Klassen die M�glichkeit, eine einfache Umwandlung einer Zeichenkette in Integer vorzunehmen. Fehler werden durch die Klasse abgefangen, der R�ckgabewert ist dann 0.
     * @param fs Eine Zeichenkette mit einer Integerdarstellung
     * @return Der umgewandelte Integerwert
     */
    protected int string2Int(String fs) {
        int i = 0;
        try {
            i = Integer.parseInt(fs);
        } catch (NumberFormatException e) {}
        return i;
    }
    
    /**
     * Bietet abeleiteten Klassen die M�glichkeit, eine einfache Umwandlung einer Zeichenkette in Float vorzunehmen. Fehler werden durch die Klasse abgefangen, der R�ckgabewert ist dann 0.
     * @param fs Eine Zeichenkette mit einer Floatdarstellung
     * @return Der umgewandelte Floatwert
     */
    protected float string2Float(String fs) {
        float f = 0;
        try {
            f = Float.parseFloat(fs);
        } catch (NumberFormatException e) {}
        return f;
    }
    
    /**
     * Die Methode wird aufgerufen, wenn neue Werte zur Berechnung eingegeben wurden.
     * 
     * Die Methode ruft in der Kleber-Klasse die Methode neueDaten() auf, wenn die �bergebenen Zeichenketten ohne Probleme in Zahlen umgewandelt werden konnten. Anschlie�end wird eine Ausgabe konstruiert, die die Berechungsergebnisse enth�lt.
     * @param streckeS Die eingegebene Zeichenkette f�r Wegstrecke
     * @param kraftstoffS Die eingegebene Zeichenkette f�r verbrauchter Kraftstoff
     * @return Sind die Zeichenketten i.O., wird ein Text konstruiert, der die Berechnungergebnisse enth�lt, ansonsten wird eine leere Zeichenkette zur�ckgeliefert.
     */
    protected String werteBereitstellen(String streckeS, String kraftstoffS) {
        boolean werteIO = false;
	String r = "";
        try {
            this.strecke = Float.parseFloat(streckeS);
            this.kraftstoff = Float.parseFloat(kraftstoffS);
            werteIO = true;
        } catch (NumberFormatException e) {
            System.err.println(e);
        }
        //System.out.println("Strecke=" + strecke + ", Kraftstoff=" + kraftstoff);
        if (werteIO) {
            neueWerte();
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            pw.printf("Verbrauch %1.2f l/100km,\n Strecke je Liter %1.2f", ver100, strLtr);            
            r = sw.toString();
        }
	return r;
    }

}

