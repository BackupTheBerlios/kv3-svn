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

import java.util.*;
import java.text.*;

/**
 * Diese Klasse dient zur Speicherung historischer Daten.
 *
 * @author     Erik Wegner
 * @version    3.0
 */
public class Historieneintrag extends Zahlenformatierer {
    /**
     * Definiert die Zustände für Streckentyp
     */
    public enum Streckentyp { 
        /**
         * Streckentyp ist nicht bekannt oder eindeutig zuzuordnen
         */
        Unbekannt, 
        /**
         * Strecke ist überwiegend innerstädtisch
         */
        Stadt, 
        /**
         * Strecke ist überwiegend außerstädtisch
         */
        Land };
    private float 
        strecke = 0.0f, 
        kraftstoff = 0.0f,
        preis = 0.0f;
    private Date datum = Calendar.getInstance().getTime(); //Datum ist heute
    private int fahrzeug = 0;
    private Streckentyp streckentyp = Streckentyp.Unbekannt;    
    private final String datumpattern = "dd.MM.yyyy";
    /**
     * Die Anzahl verwalteter Fahrzeuge.
     */
    public final int maxFahrzeug = 10;
    /**
     * Das Zeichen zur Trennung der Datenfelder bei der Umwandlung in Zeichenketten.
     */
    public final String trenner = ",";

    /**
     * Die Anzahl der Datenfelder, die die Klasse speichert.
     */
    public static final int anzahlFelder = 8;
    /**
     * Die Bezeichnung der Datenfelder
     */
    public static final String[] feldNamen = {
	"Strecke", 
	"Liter", 
	"Liter/100km", 
	"Strecke/Liter", 
	"Datum", 
	"Fahrzeug", 
	"Streckentyp", 
	"Preis"};
    public static final int
        /**
         * Konstante für Zugriff auf Datenfeld STRECKE
         */
        STRECKE = 0,
        /**
         * Konstante für Zugriff auf Datenfeld KRAFTSTOFF
         */
        KRAFTSTOFF = 1,
        /**
         * Konstante für Zugriff auf Datenfeld VERBRAUCHJE100KILOMETER
         */
        KRAFTSTOFF100 = 2,
        /**
         * Konstante für Zugriff auf Datenfeld STRECKEJELITER
         */
        STRECKEJELITER = 3,
        /**
         * Konstante für Zugriff auf Datenfeld DATUM
         */
        DATUM = 4,
        /**
         * Konstante für Zugriff auf Datenfeld FAHRZEUG
         */
        FAHRZEUG = 5,
        /**
         * Konstante für Zugriff auf Datenfeld STRECKENTYP
         */
        STRECKENTYP = 6,
        /**
         * Konstante für Zugriff auf Datenfeld PREIS
         */
        PREIS = 7;

    /**
     * Die Methode erzeugt eine identische Kopie dieses Historieneintrags.
     * @return Eine Kopie des Historieneintrags.
     */
    public Historieneintrag clone() {
        return new Historieneintrag(this.toString());
    }
    
    /**
     * Dieser Konstruktor erstellt einen neuen Historieneintrag mit der Vorbelegung der Datenfelder Strecke und Kraftstoff. Eine übergeordnete Klasse muss sicherstellen, dass die übrigen Datenfelder korrekt befüllt werden.
     * @param strecke Die Wegstrecke
     * @param kraftstoff Der verbrauchte Kraftstoff
     */
    public Historieneintrag(float strecke, float kraftstoff) {
        super();
        setzeStrecke(strecke);
        setzeKraftstoff(kraftstoff);        
    }
    
    /**
     * Dieser Konstruktor erstellt einen neuen Historieneintrag anhand der übergebenen Zeichenkette, die zuvor mit der Methode toString() erstellt wurde.
     * @param serial Die Darstellung des Historieneintrags als Zeichenkette
     */
    public Historieneintrag(String serial) {
        super();
        String[] teile = serial.split(",");
        int c1 = 0;
        try { setzeStrecke(Float.valueOf(teile[c1++])); } catch (NumberFormatException e) {}
        try { setzeKraftstoff(Float.valueOf(teile[c1++])); } catch (NumberFormatException e) {}
        try { setzePreis(Float.valueOf(teile[c1++])); } catch (NumberFormatException e) {}
        try { setzeFahrzeug(Integer.valueOf(teile[c1++])); } catch (NumberFormatException e) {}
        setzeStreckentyp(teile[c1++]);
        try { setzeDatum(teile[c1++]); } catch (NumberFormatException e) {}
    }
    
    /**
     * Die Methode wandelt den Historieneintrag in eine Zeichenkette um.
     * 
     * Die Zeichenkette enthält die Datenfelder, getrennt durch das Trennzeichen.
     * Diese Zeichenkette kann genutzt werden, um den Historieneintrag später zu rekonstruieren.
     * @see #trenner
     * @return Die gefüllte Zeichenkette
     */
    public String toString() {
        StringBuffer sb = new StringBuffer("");
        sb.append(holeStrecke()); sb.append(trenner);
        sb.append(holeKraftstoff()); sb.append(trenner);
        sb.append(holePreis()); sb.append(trenner);
        sb.append(holeFahrzeug()); sb.append(trenner);
        sb.append(holeStreckentyp()); sb.append(trenner);
        sb.append(holeDatum());
        return sb.toString();
    }

    /**
     * Die Methode erwartet eine der Feld-Konstanten und gibt den 
     * entsprechenden Wert zurück.
     * @param feldnummer Eine Konstante, die angibt, welches Datenfeld geliefert werden soll.
     * @return Eine formatierte Zeichenkette, die das Datenfeld darstellt.
     */
    public String feld(int feldnummer) {
        String r = "";	
        switch (feldnummer) {
            case STRECKE        : r = nf2nks.format(holeStrecke()); break;
            case KRAFTSTOFF     : r = nf2nks.format(holeKraftstoff()); break;
            case KRAFTSTOFF100  : r = nf2nks.format(holeVerbrauch100km()); break;
            case STRECKEJELITER : r = nf2nks.format(holeStreckeJeLiter()); break;
            case DATUM          : r = holeDatum(); break;
            case FAHRZEUG       : r = Integer.toString(holeFahrzeug()); break;
            case STRECKENTYP    : switch (holeStreckentyp()) {
                case Unbekannt      : r = "U"; break;
                case Stadt          : r = "S"; break;
                case Land           : r = "L"; break;
            }
            break;
            case PREIS          : r = nf3nks.format(holePreis()); break;
        }
        return r;
    }

    /**
     * Die Methode liefert den berechneten Verbrauch von Kraftstoff je 100 Kilometern.
     * @return Kraftstoffverbrauch je 100 Kilometer
     */
    public float        holeVerbrauch100km() {return kraftstoff*100/strecke;}
    /**
     * Die Methode berechnet, welche Entfernung mit einem Liter Kraftstoff zurückgelegt werden kann.
     * @return Die Wegstrecke je Liter Kraftstoff
     */
    public float        holeStreckeJeLiter() {return strecke/kraftstoff; }
    
    /**
     * Liefert den gespeicherten Wert für zurückgelegte Strecke.
     * @return Die Wegstrecke
     */
    public float        holeStrecke()     { return this.strecke;     }
    /**
     * Liefert den gespeicherten Wert für verbrauchten Kraftstoff.
     * @return Der verbrauchte Kraftstoff
     */
    public float        holeKraftstoff()  { return this.kraftstoff;  }
    /**
     * Liefert den gespeicherten Wert für Preis.
     * @return Der Preis
     */
    public float        holePreis()       { return this.preis;       }
    /**
     * Liefert den gespeicherten Wert für Fahrzeug.
     * @return Das Fahrzeug
     */
    public int          holeFahrzeug()    { return this.fahrzeug;    }
    /**
     * Liefert den gespeicherten Wert für Streckentyp.
     * @return Der Streckentyp
     */
    public Streckentyp  holeStreckentyp() { return this.streckentyp; }
    /**
     * Liefert den gespeicherten Wert für Datum.
     * @return Das Datum
     */
    public String       holeDatum()       {
        //DateFormat dateFormatter;
        //dateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT, new Locale("de","DE"));        
        //return dateFormatter.format(datum);
        SimpleDateFormat df = new SimpleDateFormat(datumpattern);
        return df.format(datum);
    }
    
	/**
	 * Setzt den Wert für zurückgelegte Strecke.
	 * @param strecke Die Wegstrecke
	 */
	public void setzeStrecke(float strecke) {
		this.strecke = strecke;
	} // -- setzeStrecke(float strecke)()
    
    
	/**
	 * Setzt den Wert für verbrauchter Kraftstoff.
	 * @param kraftstoff Der verbrauchte Kraftstoff
	 */
	public void setzeKraftstoff(float kraftstoff) {
		this.kraftstoff = kraftstoff;
	} // -- setzeKraftstoff(float kraftstoff)()
    
    /**
     * Setzt den Wert für Preis.
     * @param preis Der Preis
     */
    public void setzePreis(float preis) {
        this.preis = preis;
    }
    
    /**
     * Setzt den Wert für Datum.
     * @param datum Das Datum
     */
    public void setzeDatum(Date datum) {
	this.datum = datum;        
    } // -- setzeDatum(Date datum)()
    
    
    /**
     * Setzt den Wert für Fahrzeug. Die Methode prüft, dass der Wert kleiner als maxFahrzeug ist.
     * @param fahrzeug Die Fahrzeugnummer
     */
    public void setzeFahrzeug(int fahrzeug) {
        if (fahrzeug < maxFahrzeug) this.fahrzeug = fahrzeug;
    } // -- setzeFahrzeug(int Fahrzeug)()
    
    
    /**
     * Setzt den Wert für Streckentyp.
     * @param streckentyp Der Streckentyp
     */
    public void setzeStreckentyp(Streckentyp streckentyp) {
	this.streckentyp = streckentyp;
    } // -- setzeStreckentyp(Streckentyp streckentyp)()


    /**
     * Setzt den Wert Streckentyp anhand einer Zeichenkette
     * @param st Der Streckentyp
     */
    public void setzeStreckentyp(String st) {
        setzeStreckentyp(streckentyp.valueOf(streckentyp.getDeclaringClass(), st));
    }
    
    /**
     * Setzt den Wert für Streckentyp anhand einer Zahl.
     * @param i Der Streckentyp
     */
    public void setzeStreckentyp(int i) {
        switch (i) {
            case 1 : setzeStreckentyp(Streckentyp.Stadt); break;
            case 2 : setzeStreckentyp(Streckentyp.Land); break;
            default : setzeStreckentyp(Streckentyp.Unbekannt);
        }
    }
    
    /**
     *  Die Methode prüft, ob die übergebene Zeichenkette ein Datum darstellt.
     *
     *  @param text     Die Zeichenkette, die geprüft werden soll
     *  @return         wahr, wenn beim Umwandeln kein Fehler auftrat
     */
    public boolean gueltigesDatum(String text) {
        boolean r=false;
        SimpleDateFormat df = new SimpleDateFormat(datumpattern);
        ParsePosition pos = new ParsePosition(0);
        try {
            Date d = df.parse(text, pos);
            r = pos.getIndex()>0;
        } catch (NullPointerException e) {}
        return r;
    }
    
    /**
     * Setzt den Wert für Datum, wenn bei der Umwandlung in die Date-Klasse kein Fehler auftritt.
     * @param datum Die Zeichenkette, die das Datum darstellt.
     */
    public void setzeDatum(String datum) {
        SimpleDateFormat df = new SimpleDateFormat(datumpattern);
        ParsePosition pos = new ParsePosition(0);
        try {
            Date d = df.parse(datum, pos);
            if(pos.getIndex()>0) this.datum = d;
        } catch (NullPointerException e) {}
    }
} // -- end class Historieneintrag

