/*
 *  This file is part of Kraftstoff3 - [What it does in brief]
 *  Copyright (c) 2004 Erik Wegner
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or any later version.
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *                                                                                                                                                                                                                                                                                 
 */
package de.ewus.kv3;

import javax.swing.table.AbstractTableModel;

/**
 *  Description of the Class
 *
 * @author     e-man
 * @created    24. Oktober 2004
 */
public class UIswingHistorieTableModel extends javax.swing.table.AbstractTableModel {
    private String[] columnNames = {"Strecke", "Liter", "Liter/100km", "Strecke/Liter", "Datum", "Fahrzeug", "Streckentyp", "Preis"};
    private final int maxColumnZahl = 8,
        STRECKE = 0,
        KRAFTSTOFF = 1,
        KRAFTSTOFF100 = 2,
        STRECKEJELITER = 3,
        DATUM = 4,
        FAHRZEUG = 5,
        STRECKENTYP = 6,
        PREIS = 7;
    private int columnZahl = maxColumnZahl;
    private int[] columnInhalt = {STRECKE, KRAFTSTOFF, KRAFTSTOFF100, 
        STRECKEJELITER, DATUM, FAHRZEUG, STRECKENTYP, PREIS};
    private Historie historie;
    private Kleber kleber;

    private final String propPrefix = "UISwingHistorie",
            COLNUM = propPrefix + "ColNum",
            COLINH = propPrefix + "ColInhalt";


    /**
     *Constructor for the UIswingHistorieTableModel object
     *
     * @param  historie  Die gespeicherten historischen Daten
     * @param  kleber    Die Kleber-Klasse f�r Zugriffe auf Properties
     */
    public UIswingHistorieTableModel(Historie historie, Kleber kleber) {
        super();
        this.historie = historie;
        this.kleber = kleber;
        //Hole Einstellungen Anzahl der Spalten
        if (kleber.getProperty(this.COLNUM) != null) {
            this.columnZahl = toInt(kleber.getProperty(this.COLNUM));
        }

        //Hole Einstellung Inhalte der Spalten
        for (int i = 0; i < maxColumnZahl; i++) {
            if (kleber.getProperty(this.COLINH + Integer.toString(i)) != null) {
                this.columnInhalt[i] = toInt(kleber.getProperty(this.COLINH + Integer.toString(i)));
            }
        }
    }


    /**
     *  Wird beim Beenden der Anwendung aufgerufen, um die Einstellungen zu speichern.
     */
    public void dispose() {
        kleber.setProperty(this.COLNUM, Integer.toString(this.columnZahl));
        for (int i = 0; i < maxColumnZahl; i++) {
            kleber.setProperty(this.COLINH + Integer.toString(i),
                    Integer.toString(columnInhalt[i]));
        }
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
        return historie.size();
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
        Historieneintrag e = historie.eintragNr(row);
        String r = "";
        switch (columnInhalt[column]) {
            case STRECKE        : r = Float.toString(e.holeStrecke()); break;
            case KRAFTSTOFF     : r = Float.toString(e.holeKraftstoff()); break;
            case KRAFTSTOFF100  : r = Float.toString(e.holeVerbrauch100km()); break;
            case STRECKEJELITER : r = Float.toString(e.holeStreckeJeLiter()); break;
            case DATUM          : r = e.holeDatum(); break;
            case FAHRZEUG       : r = Integer.toString(e.holeFahrzeug()); break;
            case STRECKENTYP    : switch (e.holeStreckentyp()) {
                case Unbekannt      : r = "U"; break;
                case Stadt          : r = "S"; break;
                case Land           : r = "L"; break;
            }
            break;
            case PREIS          : r = Float.toString(e.holePreis()); break;
        }
        return r;
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
        return columnNames[columnInhalt[col]];
    }
    
    public void neueWerte() {
        fireTableDataChanged();
    }
}
// -- end class UIswingHistorieTableModel

