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
import javax.swing.table.AbstractTableModel;

/**
 * Die Klasse Historie verwaltet alle historisierten Eingaben.
 *
 * @author     Erik Wegner
 * @version    3.0
 */
public class Historie extends javax.swing.table.AbstractTableModel {
    private Kleber kleber;

    private int columnZahl = Historieneintrag.anzahlFelder;
    private int[] columnInhalt = {Historieneintrag.STRECKE, Historieneintrag.KRAFTSTOFF, 
				  Historieneintrag.KRAFTSTOFF100, Historieneintrag.STRECKEJELITER, 
				  Historieneintrag.DATUM, Historieneintrag.FAHRZEUG, 
				  Historieneintrag.STRECKENTYP, Historieneintrag.PREIS};

    private final String propPrefix = "Historie",
            COLNUM = propPrefix + "ColNum",
            COLINH = propPrefix + "ColInhalt";

    private VectorExtended<Historieneintrag> historie;
    
    private Zahlenformatierer zf = new Zahlenformatierer();
    
    /**
     * Erzeugt die Historien-Klasse und liest Voreinstellungen aus den Properties.
     * @param kleber Die Kleber-Klasse zur Verbindung aller Hilfsklassen.
     */
    public Historie(Kleber kleber) {
	historie = new VectorExtended<Historieneintrag>();
        this.kleber = kleber;
        //Hole Einstellungen Anzahl der Spalten
        if (kleber.getProperty(this.COLNUM) != null) {
            this.columnZahl = toInt(kleber.getProperty(this.COLNUM));
        }
	
        //Hole Einstellung Inhalte der Spalten
        for (int i = 0; i < Historieneintrag.anzahlFelder; i++) {
            if (kleber.getProperty(this.COLINH + Integer.toString(i)) != null) {
                this.columnInhalt[i] = toInt(kleber.getProperty(this.COLINH + 
								Integer.toString(i)));
            }
        }
	
    }

    /**
     * Die Methode berechnet die Summe eines Datenfeldes aller Historieneinträge.
     * 
     * Gibt das Datenfeld an, dessen Werte summiert werden sollen.
     * Gültige Werte sind:
     * <ul>
     * <li>Historieneintrag.STRECKE</li>
     * <li>Historieneintrag.KRAFTSTOFF</li>
     * <ul>
     * @see Historieneintrag
     * @param feld Das zu summierende Datenfeld
     * @return Die Summe
     */
    public float summe(int feld) {
        return historie.summe(feld);        
    }
    
    /**
     * Die Methode liefert das Ergebnis der Summen-Methode mit 2 Nachkommastellen.
     * 
     * Die Methode ruft die Methode summe() auf und 
     * liefert einen mit 2 Nachkommastellen formatierten 
     * String zurück
     * @see #summe(int)
     * @param feld Das zu summierende Datenfeld
     * @return Die Summe
     */
    public String summeStr2(int feld) {
        return zf.nf2nks.format(summe(feld));
    }
    
    /**
     * Die Methode liefert das Ergebnis der Summen-Methode mit 3 Nachkommastellen.
     * 
     * Die Methode ruft die Methode summe() auf und 
     * liefert einen mit 3 Nachkommastellen formatierten 
     * String zurück
     * @see #summe(int)
     * @param feld Das zu summierende Datenfeld
     * @return Die Summe
     */
    public String summeStr3(int feld) {
        return zf.nf3nks.format(summe(feld));
    }
    
    /**
     * Die Methode berechnet den Durchschnitt.
     *
     * Die Methode berechnet den Durchschnitt eines Datenfeldes aller Historieneinträge
     * Gültige Werte sind:
     * <ul>
     * <li>Historieneintrag.STRECKEJELITER</li>
     * <li>Historieneintrag.KRAFTSTOFF100</li>
     * <ul>
     * @see Historieneintrag
     *
     * @param feld Das Feld, dessen Durschnitt ermittelt werden soll
     * @return Der Durchschnitt
     */
    public float durchschnitt(int feld) {
        float r = 0.0f;
        if (feld == Historieneintrag.STRECKEJELITER) r = summe(Historieneintrag.STRECKE) / summe(Historieneintrag.KRAFTSTOFF);
        if (feld == Historieneintrag.KRAFTSTOFF100) r = 100 * summe(Historieneintrag.KRAFTSTOFF) / summe(Historieneintrag.STRECKE);
        if (feld == Historieneintrag.PREIS) r = summe(Historieneintrag.PREIS) / summe(Historieneintrag.KRAFTSTOFF);
        return r;
    }
    
   /**
     * Die Methode liefert das Ergebnis der Durchschnitt-Methode mit 2 Nachkommastellen.
     * 
     * Die Methode ruft die Methode durchschnitt() auf und 
     * liefert einen mit 2 Nachkommastellen formatierten 
     * String zurück
     * @see #durchschnitt(int)
     * @param feld Das Feld, dessen Durschnitt ermittelt werden soll
     * @return Der Durchschnitt
     */
    public String durchschnittStr2(int feld) {
        return zf.nf2nks.format(durchschnitt(feld));
    }
    
   /**
     * Die Methode liefert das Ergebnis der Durchschnitt-Methode mit 3 Nachkommastellen.
     * 
     * Die Methode ruft die Methode durchschnitt() auf und 
     * liefert einen mit 3 Nachkommastellen formatierten 
     * String zurück
     * @see #durchschnitt(int)
     * @param feld Das Feld, dessen Durschnitt ermittelt werden soll
     * @return Der Durchschnitt
     */
    public String durchschnittStr3(int feld) {
        return zf.nf3nks.format(durchschnitt(feld));
    }
    
    /**
     *  Wird beim Beenden der Anwendung aufgerufen, um die Einstellungen zu speichern.
     */
    public void dispose() {
        kleber.setProperty(this.COLNUM, Integer.toString(this.columnZahl));
        for (int i = 0; i < Historieneintrag.anzahlFelder; i++) {
            kleber.setProperty(this.COLINH + Integer.toString(i),
                    Integer.toString(columnInhalt[i]));
        }
    }

    /**
     * Die Methode fügt den Historieneintrag zur Liste hinzu.
     * 
     * @param  e   Ein Historieneintrag	 
     */
    public void fuegeHinzu(Historieneintrag e) {
        // DONE: fuegeHinzu(Historieneintrag)
        historie.add(e);
	this.neueWerte();
        //System.out.println(e);
    }
    
    /**
     * Die Historie wird mit Einträgen aus der Datei "datei" gefüllt.
     * @param datei Dateiname der Eingabedatei
     */
    public void liesHistorie(String datei) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(datei));
            String zeile;
            while ((zeile = in.readLine()) != null) fuegeHinzu(new Historieneintrag(zeile));            
            in.close();            
        } 
        catch (IOException e) { System.err.println(e.getMessage()); }
    }
    
    /**
     * Die Einträge der Historie werden in die Datei "datei" geschrieben.
     * @param datei Der Dateiname der Ausgabedatei
     */
    public void schreibHistorie(String datei) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(datei));
            for (Historieneintrag e : historie) {
                out.write(e.toString());
                out.newLine();
            }
            out.close();
        } catch (IOException e) { System.err.println(e.getMessage()); }
    }
    
    /**
     * Gibt an, wie viele Einträge in der Historie gespeichert sind.
     * @return Anzahl der Einträge
     */
    public int size() { return this.historie.size();}
    
    /**
     * Liest aus der Historie einen Eintrag aus.
     * @param nummer Die Nummer des gewünschten Eintrags
     * @return Der Eintrag der Historie
     */
    public Historieneintrag holeEintragNr(int nummer) {
        return historie.elementAt(nummer);
    }

    /**
     * Überschreibt in der Historie den Eintrag mit der Nummer "nummer" mit "e"
     * @param nummer Position des Eintrags in der Historie
     * @param e Der neue Eintrag, der in der Historie an Position "nummer" gespeichert werden soll
     */
    public void setzeEintragNr(int nummer, Historieneintrag e) {
        historie.setElementAt(e, nummer);
    }
    
    /**
     * Entfernt den Eintrag an Position "nummer" aus der Historie
     * @param nummer Die Position des Eintrags, der gelöscht werden soll.
     */
    public void loescheEintrag(int nummer) {
        historie.removeElementAt(nummer);
        neueWerte();
    }
    
    /**
     *  Wandelt eine Zeichenkette in Integer
     *
     * @param  s  Die Zeichenkette
     * @return    der Integerwert oder bei Fehler 0
     */
    public int toInt(String s) {
        int i = 0;
        try {
            i = Integer.parseInt(s);
        } catch (NumberFormatException e) {}
        return i;
    }


    /**
     *  Gets the rowCount attribute of the UIswingHistorieTableModel object
     *
     * @return    The rowCount value
     */
    public int getRowCount() {
        return size();
    }


    /**
     *  Gets the columnCount attribute of the UIswingHistorieTableModel object
     *
     * @return    The columnCount value
     */
    public int getColumnCount() {
        return columnZahl;
    }


    /**
     *  Gets the valueAt attribute of the UIswingHistorieTableModel object
     *
     * @param  row     Zeile des Eintrags
     * @param  column  Spalte des Eintrags
     * @return         The valueAt value
     */
    public Object getValueAt(int row, int column) {
        Historieneintrag e = holeEintragNr(row);
	return e.feld(columnInhalt[column]);
    }


    /**
     *  Gets the columnName attribute of the UIswingHistorieTableModel object
     *
     * @param  col  Description of the Parameter
     * @return      The columnName value
     */
    public String getColumnName(int col) {
        /*
         *  System.err.println("getColumnName(" + col + ")");
         *  System.err.println("columnInhal[" + col + "]=" + columnInhalt[col]);
         *  System.err.println("columnNames[" + columnInhalt[col] + "] = " + columnNames[columnInhalt[col]]);
         */
        return Historieneintrag.feldNamen[columnInhalt[col]];
    }
    
    /**
     * Informiert alle aktiven Listeners per fireData
     */
    public void neueWerte() {
        fireTableDataChanged();
    }
}
