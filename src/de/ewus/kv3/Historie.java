package de.ewus.kv3; 

import java.util.Vector;
import java.io.*;
import javax.swing.table.AbstractTableModel;

/**
 * Write a description of class Historie here.
 * 
 * @author Erik Wegner
 * @version 3.0
 */
public class Historie extends javax.swing.table.AbstractTableModel {
    private Kleber kleber;
    private String[] columnNames = {
	"Strecke", 
	"Liter", 
	"Liter/100km", 
	"Strecke/Liter", 
	"Datum", 
	"Fahrzeug", 
	"Streckentyp", 
	"Preis"};
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

    private final String propPrefix = "Historie",
            COLNUM = propPrefix + "ColNum",
            COLINH = propPrefix + "ColInhalt";

    private Vector<Historieneintrag> historie;
    /**
     * Constructor for objects of class Historie
     */
    public Historie(Kleber kleber) {
	historie = new Vector<Historieneintrag>();
        this.kleber = kleber;
        //Hole Einstellungen Anzahl der Spalten
        if (kleber.getProperty(this.COLNUM) != null) {
            this.columnZahl = toInt(kleber.getProperty(this.COLNUM));
        }
	
        //Hole Einstellung Inhalte der Spalten
        for (int i = 0; i < maxColumnZahl; i++) {
            if (kleber.getProperty(this.COLINH + Integer.toString(i)) != null) {
                this.columnInhalt[i] = toInt(kleber.getProperty(this.COLINH + 
								Integer.toString(i)));
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
    
    public void liesHistorie(String datei) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(datei));
            String zeile;
            while ((zeile = in.readLine()) != null) fuegeHinzu(new Historieneintrag(zeile));            
            in.close();            
        } 
        catch (IOException e) { System.err.println(e.getMessage()); }
    }
    
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
    
    public int size() { return this.historie.size();}
    
    public Historieneintrag eintragNr(int nummer) {
        return historie.elementAt(nummer);
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
        Historieneintrag e = eintragNr(row);
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
