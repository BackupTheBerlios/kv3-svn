package de.ewus.kv3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

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

    public UIManager.HEDlgErgebnis ergebnis() {
        return ergebnis;
    }
    
	public UIswingHEDlg(Frame owner, boolean modal, Historieneintrag e) {
        super(owner, "Eintrag bearbeiten", modal);
        
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
        tfStrecke = new JTextField(Float.toString(e.holeStrecke()));
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
        tfKraftstoff = new JTextField(Float.toString(e.holeKraftstoff()));
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
        tfDatum = new JTextField(e.holeDatum());
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
        tfPreis = new JTextField();
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
        Vector v = new Vector<String>();
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
        // TODO: Liste muss durch den enum-Typ gefüllt werden        
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

    public void actionPerformed(ActionEvent av) {
        String cmd = av.getActionCommand();
        boolean visible = true;
        if (cmd.equals("OK")) {
            this.ergebnis = UIManager.HEDlgErgebnis.OK;
            visible = false;
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

    
} // -- end class UIswingHEDlg

