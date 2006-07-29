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

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Eine Klasse zur Darstellung des Historieneintrag-bearbeiten-Dialogs im Java Swing-Stil.
 *
 * @author     Erik Wegner
 * @version    1.0
 */
public class UIswingHEDlg extends javax.swing.JDialog implements ActionListener
{
    private JTextField 
        tfStrecke,
        tfKraftstoff,
        tfDatum,
        tfPreis;
    private JComboBox cbFahrzeug, cbStreckentyp;
    private JButton btButton;
    private Historieneintrag e;
    private UIManager.HEDlgErgebnis ergebnis;
    private NumberFormat nf;
    
    /**
     * Die Methode liefert nach Ausführen des Dialogs das Dialogergebnis zurück.
     * @return Die Benutzerauswahl zum Schließen des Dialogs.
     */
    public UIManager.HEDlgErgebnis holeErgebnis() {
        return ergebnis;
    }
    
	/**
	 * Erzeugt den Dialog.
	 * @param owner Elternfenster des Dialogs
	 * @param modal Bestimmt, ob der Dialog modal angezeigt werden soll
	 * @param e Der Historieneintrag, der zur Bearbeitung anzeigt werden soll
	 */
	public UIswingHEDlg(Frame owner, boolean modal, Historieneintrag e) {
        super(owner, "Eintrag bearbeiten", modal);
        
        this.nf = NumberFormat.getInstance();
        
        this.e = e;
        this.ergebnis = UIManager.HEDlgErgebnis.Abbrechen;
        
		//Erzeuge ein Panel
        JPanel p1 = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        //Ereuge Labels und Textfelder     
        
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 0; c.gridy = 0;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.0;
        p1.add(new JLabel("Strecke"), c);
        
        c.gridx = 1; c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        tfStrecke = new JTextField(e.feld(e.STRECKE));
        p1.add(tfStrecke, c);
        //------------------------------------
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 0; c.gridy++;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.0;
        p1.add(new JLabel("Kraftstoff"), c);
        
        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        tfKraftstoff = new JTextField(e.feld(e.KRAFTSTOFF));
        p1.add(tfKraftstoff, c);
        //------------------------------------
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 0; c.gridy++;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.0;
        p1.add(new JLabel("Datum"), c);
        
        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        tfDatum = new JTextField(e.feld(e.DATUM));
        p1.add(tfDatum, c);
        //------------------------------------
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 0; c.gridy++;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.0;
        p1.add(new JLabel("Preis"), c);
        
        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        tfPreis = new JTextField(e.feld(e.PREIS));
        p1.add(tfPreis, c);
        //------------------------------------
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 0; c.gridy++;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.0;
        p1.add(new JLabel("Fahrzeug"), c);
        
        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        Vector<String> v = new Vector<String>();
        for (int c1 = 0 ; c1 < e.maxFahrzeug ; c1++) v.add(Integer.toString(c1));
        cbFahrzeug = new JComboBox(v.toArray());
        p1.add(cbFahrzeug, c);
        //------------------------------------
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 0; c.gridy++;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.0;
        p1.add(new JLabel("Streckentyp"), c);
        
        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        v.clear();
        // TODO: Liste muss durch den enum- Typ gefüllt werden        
        v.add("Unbekannt");
        v.add("Stadt");
        v.add("Land");
        cbStreckentyp = new JComboBox(v.toArray());
        v.clear();
        p1.add(cbStreckentyp, c);
        
        //Füge Panel als CENTER hinzu
        add(p1, BorderLayout.CENTER);
        //Erzeuge ein Panel
        JPanel p2 = new JPanel(new GridBagLayout());
        //Erzeuge drei Schaltflächen
        c.gridx = 0; c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        btButton = new JButton("OK"); btButton.addActionListener(this);            
        p2.add(btButton, c); c.gridx++;
        btButton = new JButton("Abbrechen"); btButton.addActionListener(this);
        p2.add(btButton, c); c.gridx++;
        btButton = new JButton("Löschen"); btButton.addActionListener(this);
        p2.add(btButton, c); c.gridx++;
        //Füge Panel als Page_End hinzu
        add(p2, BorderLayout.SOUTH);
        pack();
        // TODO: Dialog erscheint mittig zum Hauptfenster
        
        // TODO: Minimalgröße festlegen
        setMinimumSize(getSize());
        /*
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        addWindowListener(
            new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                    setVisible(false);
                }
            }
        );
        */
	} // -- Constructor

    /*private void holeDatenAusFeldern() {
        StringBuffer sb = new StringBuffer("");
        String trenner = e.trenner;
        sb.append(tfStrecke.getText()); sb.append(e.trenner);
        sb.append(tfKraftstoff.getText()); sb.append(e.trenner);
        sb.append(tfPreis.getText()); sb.append(e.trenner);
        sb.append(cbFahrzeug.getSelectedIndex()); sb.append(e.trenner);
        sb.append(cbStreckentyp.getSelectedItem()); sb.append(e.trenner);
        sb.append(tfDatum.getText());
        e = new Historieneintrag(sb.toString());
    }*/
    
    /**
     *  Die Methode prüft, ob die übergebene Zeichenkette eine float-Zahl darstellt.
     *
     *  @param text     Die Zeichenkette, die geprüft werden soll
     *  @return         wahr, wenn beim Umwandeln kein Fehler auftrat
     */
    private boolean kannUmwandeln(String text) {
        boolean r = false;               
        try {
            float f = Float.valueOf(text);
            r = true;
        } catch (NumberFormatException e) {}        
        try {
            Number n = nf.parse(text);
            r = true;
        } catch(ParseException e) {}
        return r;
    }
    
    private boolean validiereEingaben() {
        // TODO: Fehlermeldung erscheint grafisch
        boolean r = false;
        r = kannUmwandeln(tfStrecke.getText());
        if (!r) {System.err.println("Umwandeln mit Fehler: Strecke");}
        r = r && kannUmwandeln(tfKraftstoff.getText());
        if (!r) {System.err.println("Umwandeln mit Fehler: Kraftstoff");}
        r = r && kannUmwandeln(tfPreis.getText());
        if (!r) {System.err.println("Umwandeln mit Fehler: Preis");}
        r = r && e.gueltigesDatum(tfDatum.getText());
        if (!r) {System.err.println("Umwandeln mit Fehler: Datum");}
        return r;
    }
    
    private float umwandeln(String f) {        
        float r = 0.0f;
        try {
            r = Float.valueOf(f);
        } 
        catch (NumberFormatException nfe) {        
            try { 
                Number n = nf.parse(f);
                if (n instanceof Long) {
                    Long nlong = (Long)n;
                    r = (float)(nlong.longValue());
                }
                if (n instanceof Double) {
                    Double ndouble = (Double)n;
                    r = (float)(ndouble.doubleValue());
                }
            } catch (ParseException pe) { };
        }
        return r;
    }
    
    private void neueWerteFuerE() {
        e.setzeStrecke(umwandeln(tfStrecke.getText()));
        e.setzeKraftstoff(umwandeln(tfKraftstoff.getText()));
        e.setzePreis(umwandeln(tfPreis.getText()));
        e.setzeFahrzeug(cbFahrzeug.getSelectedIndex());
        e.setzeStreckentyp(cbStreckentyp.getSelectedIndex());
        e.setzeDatum(tfDatum.getText());
    }
    
    /**
     * Fängt Aktionsereignisse ab.
     * @param av Das Aktionsereignis
     */
    public void actionPerformed(ActionEvent av) {
        String cmd = av.getActionCommand();
        boolean visible = true;
        if (cmd.equals("OK")) {
            if (validiereEingaben()) {
                neueWerteFuerE();
                this.ergebnis = UIManager.HEDlgErgebnis.OK;
                visible = false;
            }
        }
        if (cmd.equals("Abbrechen")) {
            this.ergebnis = UIManager.HEDlgErgebnis.Abbrechen;
            visible = false;
        }
        if (cmd.equals("Löschen")) {
            this.ergebnis = UIManager.HEDlgErgebnis.Loeschen;
            visible = false;
        }
        if (!visible) setVisible(false);
    }

    /**
     * Diese Methode liefert den bearbeiteten Historieneintragsdatensatz zurück.
     * @return Der bearbeitete Eintrag
     */
    public Historieneintrag holeHistorieneintrag() { return e; }
} // -- end class UIswingHEDlg

