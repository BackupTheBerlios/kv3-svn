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

    private int columnZahl = Historieneintrag.anzahlFelder;
    private int[] columnInhalt = {Historieneintrag.STRECKE, Historieneintrag.KRAFTSTOFF, 
				  Historieneintrag.KRAFTSTOFF100, Historieneintrag.STRECKEJELITER, 
				  Historieneintrag.DATUM, Historieneintrag.FAHRZEUG, 
				  Historieneintrag.STRECKENTYP, Historieneintrag.PREIS};

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
        for (int i = 0; i < Historieneintrag.anzahlFelder; i++) {
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
    
    public Historieneintrag holeEintragNr(int nummer) {
        return historie.elementAt(nummer);
    }

    public void setzeEintragNr(int nummer, Historieneintrag e) {
        historie.setElementAt(e, nummer);
    }
    
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
    
    public void neueWerte() {
        fireTableDataChanged();
    }
}
