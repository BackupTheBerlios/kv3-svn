package de.ewus.kv3;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.io.*;

/**
 * Eine Implementation des UIManagers mit einer Java SWING-Oberfläche.
 *
 *  <p>This file is part of Kraftstoffverbrauch3.</p>
 *
 *  <p>Kraftstoffverbrauch3 is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.</p>

 *  <p>Kraftstoffverbrauch3 is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.</p>

 *  <p>You should have received a copy of the GNU General Public License
 *  along with Kraftstoffverbrauch3; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA</p>
 *
 * @author     Erik Wegner
 * @created    1. Oktober 2004
 * @version    1.0
 */
public class UIswing extends UIManager implements WindowListener, Runnable, ActionListener, KeyListener {
    private JFrame frame;  //Das Fenster
    private JLabel lStrecke, lKraftstoff, //Labels mit wechselnder Hintergrundfarbe
        lErgebnis;
    private JTextField tfStrecke, tfKraftstoff; //Textfelder für Eingabe
    
    private enum felder {Kraftstoff, Strecke};
    private felder aktivesFeld = felder.Kraftstoff;
    private boolean tfInhaltLoeschen = false;
    
    /**
     * Constructor for objects of class UIswing
     */
    public UIswing() { }

        /** Handle the key typed event from the text field. */
    public void keyTyped(KeyEvent e) {
	    System.out.println("KEY TYPED: " + e.getKeyChar());        
        // TODO: Auf Tastendrücke angemessen reagieren
        zahlentaste("");
    }

    /** Handle the key pressed event from the text field. */
    public void keyPressed(KeyEvent e) {
	    //System.out.println("KEY PRESSED: " + e);
    }

    /** Handle the key released event from the text field. */
    public void keyReleased(KeyEvent e) {
        //System.out.println("KEY RELEASED: " + e);
    }

    /**
     *  Description of the Method
     *
     * @param  e  Description of the Parameter
     */
    public void windowOpened(WindowEvent e) { }


    /**
     *  Description of the Method
     *
     * @param  e  Description of the Parameter
     */
    public void windowClosed(WindowEvent e) { }


    /**
     *  Description of the Method
     *
     * @param  e  Description of the Parameter
     */
    public void windowIconified(WindowEvent e) { }


    /**
     *  Description of the Method
     *
     * @param  e  Description of the Parameter
     */
    public void windowDeiconified(WindowEvent e) { }


    /**
     *  Description of the Method
     *
     * @param  e  Description of the Parameter
     */
    public void windowActivated(WindowEvent e) { }


    /**
     *  Description of the Method
     *
     * @param  e  Description of the Parameter
     */
    public void windowDeactivated(WindowEvent e) { }


    /**
     *  Description of the Method
     *
     * @param  e  Description of the Parameter
     */
    public void windowClosing(WindowEvent e) {
        quitApp();
    }


    public void dispose() {
        frame.dispose();
    }
    
    /**
     *  Aktiviert das angegebene Feld
     */
    public void setzeAktivesFeld(felder feld) {
        aktivesFeld = feld;
        lStrecke.setOpaque(feld == felder.Strecke);
        lKraftstoff.setOpaque(feld == felder.Kraftstoff);
        lStrecke.repaint();
        lKraftstoff.repaint();        
    }
    
    private void werteBereitstellen() {
        boolean werteIO = false;
        try {
            strecke = Float.parseFloat(tfStrecke.getText());
            kraftstoff = Float.parseFloat(tfKraftstoff.getText());
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
            lErgebnis.setText(sw.toString());
        }
    }
    
    private void naechstesFeldAktivieren() {
        if (aktivesFeld == felder.Strecke) {
            setzeAktivesFeld(felder.Kraftstoff);
        } else {
            setzeAktivesFeld(felder.Strecke);
            werteBereitstellen();
        }
    }
    
    /**
     *  Description of the Method
     */
    public void startUI() {
        javax.swing.SwingUtilities.invokeLater(this);        
    }


    /**
     *  Main processing method for the UIswing object
     */
    public void run() {
        /*
         *  create and show the GUI
         */
        frame = new JFrame("Kraftstoffverbrauch 3.0");
        java.net.URL imageURL = UIswing.class.getResource("ressourcen/img/kanister.png");
        if (imageURL != null) {
            // FIXME: Application Icon wird nicht geladen
            ImageIcon icon = new ImageIcon(imageURL);
            frame.setIconImage(icon.getImage());
        }
        addUIElements(frame);        
        frame.addWindowListener(this);
        setzeAktivesFeld(felder.Strecke);
        tfInhaltLoeschen = true;
        
        //frame.setSize(new Dimension(240, 320));
        // TODO: Fensterlaypout anpassen
        frame.pack();
        frame.setVisible(true);
    }


    private void zahlentaste(String taste) {
        JTextField tf = (aktivesFeld == felder.Kraftstoff ? tfKraftstoff : tfStrecke);
        StringBuffer sb = new StringBuffer(tfInhaltLoeschen ? "" : tf.getText());
        
        if (taste.equals(",")) {
            //Aus dem Dezimaltrennzeichen ";" wird das amerikanische
            //Trennzeichen "."
            taste = ".";
            
            //Enthält der Inhalt des Textfelds schon ein Komma?
            //Dann darf kein zweites angehängt werden
            if (sb.indexOf(".") != -1) taste = "";
        }
        sb.append(taste);
        String sbp = ""; int c1 = 0;
        boolean benutzesbp = false;
        do {
            try {
                if (sb.length() > 0)
                    sbp = (new Float(sb.substring(0, sb.length() - c1))).toString();                    
            } catch (NumberFormatException e) {
                benutzesbp = true;
                sbp = "";
            }
            c1++;
        }  while (sbp.length() < 1 && c1 < sb.length());
        tf.setText(benutzesbp ? sbp : sb.toString());
        tfInhaltLoeschen = false;
        
    }
    
    /**
     *  Die Methode verarbeitet alle ActionEvents
     *
     * @param  e  das aufgetretende ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        //System.out.println(command);
        if (command.equals("l/km")) naechstesFeldAktivieren();
        if (command.matches("[0-9,]")) zahlentaste(command);
        if (command.equals("E")) {tfInhaltLoeschen = true; naechstesFeldAktivieren();}
        if (command.equals("CE")) (aktivesFeld == felder.Kraftstoff ? tfKraftstoff : tfStrecke).setText("");
        if (command.equals("CA")) {tfKraftstoff.setText(""); tfStrecke.setText(""); }
        if (command.equals("+Hist")) {
            // TODO: Historie
            System.out.println("Historie");
            werteBereitstellen();
            historieNeueWerte();
        }
    }

    
    

    /**
     *  Adds a feature to the UIElements attribute of the UIswing object
     *
     * @param  frame  The feature to be added to the UIElements attribute
     */
    private void addUIElements(JFrame frame) {
        Font f, f2;
        //Seite Eingabe
        String[] tasten = {
                "l/km", "7", "8", "9",
                "+Hist", "4", "5", "6",
                "CE", "1", "2", "3",
                "CA", "0", ",", "E"};
        JPanel p1 = new JPanel(new BorderLayout());
        //Eingabefelder
        JPanel p11 = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.0;
        this.lStrecke = new JLabel("Strecke");
        lStrecke.setBackground(Color.orange);
        // TODO: rechtsbündige Ausrichtung für Textfeld
        p11.add(lStrecke, c);
        c.gridx = 1;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        tfStrecke = new JTextField();
        tfStrecke.addKeyListener(this);
        // FIXME: Textfeld darf keine Eingaben durch Benutzer anzeigen
        // IDEA: JFormattedTextField verwenden?!?
        p11.add(tfStrecke, c);
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.0;
        lKraftstoff = new JLabel("Kraftstoffmenge");
        lKraftstoff.setBackground(Color.orange);
        // TODO: rechtsbündige Ausrichtung für Textfeld
        p11.add(lKraftstoff, c);
        c.gridx = 1;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        tfKraftstoff = new JTextField();
        tfKraftstoff.addKeyListener(this);
        // FIXME: Textfeld darf keine Eingaben durch Benutzer anzeigen
        p11.add(tfKraftstoff, c);
        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 2;
        lErgebnis = new JLabel("Ergebnis der Berechnung");
        f = lErgebnis.getFont();
        f2 = new Font(f.getName(), Font.PLAIN, f.getSize() - 1);
        lErgebnis.setFont(f2);
        p11.add(lErgebnis, c);
        p1.add(p11, BorderLayout.NORTH);
        //Tastenfelder
        JPanel p12 = new JPanel(new GridLayout(4, 4));
        JButton b;
        f = p12.getFont();
        f2 = new Font(f.getName(), Font.BOLD, f.getSize() * 2);
        for (int c1 = 0; c1 < tasten.length; c1++) {
            // TODO: Tab-Taste deaktivieren
            b = new JButton(tasten[c1]);
            b.addActionListener(this);
            switch (c1) {
                case 0:
                    b.setBackground(Color.yellow);
                    break;
                case 4:
                    b.setBackground(Color.green);
                    break;
                case 8:
                    b.setForeground(Color.red);
                    break;
                case 12:
                    b.setForeground(Color.red);
                    break;
                case 15:
                    b.setBackground(Color.green);
                    break;
            }
            if (c1 != 0 && c1 != 4) {
                b.setFont(f2);
            }
            p12.add(b);
        }
        p1.add(p12, BorderLayout.CENTER);

        //Seite Historie
        JPanel p2 = new JPanel(new BorderLayout());
        JTable table = new JTable(k.holeHistorie());
        //JList liste = new JList();
        JScrollPane lsp = new JScrollPane(table);
        p2.add(lsp, BorderLayout.CENTER);
        p2.add(new JLabel("Gesamtdurchschnitt"), BorderLayout.SOUTH);
        //Seite Grafik
        JTabbedPane gfxPane = new JTabbedPane(JTabbedPane.BOTTOM);
        f = gfxPane.getFont();
        f2 = new Font(f.getName(), Font.PLAIN, 10);
        gfxPane.setFont(f2);
        JPanel p31 = new JPanel(new GridBagLayout()); gfxPane.addTab("Achsen", p31);
        JPanel p32 = new JPanel(new GridBagLayout()); gfxPane.addTab("Punkte", p32);
        JPanel p33 = new JPanel(new BorderLayout()); gfxPane.addTab("Filter", p33);
        JPanel p34 = new JPanel(new BorderLayout()); gfxPane.addTab("Grafik", p34);
        //Seite Grafik, Achsen
        JComboBox xliste = new JComboBox(); JComboBox yliste = new JComboBox();
        GfxEinstellungen gfxe = this.k.holeGfxEinstellungen();
        for (GfxEinstellungen.AchsenListe a : GfxEinstellungen.AchsenListe.values()) {
            xliste.addItem(a.bezeichnung());
            yliste.addItem(a.bezeichnung());
            if (a.equals(gfxe.xAchse)) xliste.setSelectedIndex(xliste.getItemCount()-1);
            if (a.equals(gfxe.yAchse)) yliste.setSelectedIndex(yliste.getItemCount()-1);
        }
        JComboBox sortliste = new JComboBox();
        for (GfxEinstellungen.Sortierung s :GfxEinstellungen.Sortierung.values()) {
            sortliste.addItem(s.bezeichnung());
            if (s.equals(gfxe.sortierung)) sortliste.setSelectedIndex(sortliste.getItemCount()-1);
        }
        JComboBox drehliste = new JComboBox();
        for (GfxEinstellungen.Drehung d : GfxEinstellungen.Drehung.values()) {
            drehliste.addItem(d.bezeichnung());
            if (d.equals(gfxe.drehung)) drehliste.setSelectedIndex(drehliste.getItemCount()-1);
        }
        GridBagConstraints cl = new GridBagConstraints();
        GridBagConstraints cr = new GridBagConstraints();
        //GridBagConstraints linke Spalte
        cl.anchor = GridBagConstraints.LINE_START;
        cl.fill = GridBagConstraints.NONE;
        cl.gridx = 0; cl.gridy = 0; cl.weightx = 0.0;
        //GridBagConstraints rechte Spalte
        cr.gridx = 1; cr.gridy = 0; 
        cr.fill = GridBagConstraints.BOTH;
        cr.weightx = 1.0; cr.weighty = 1.0;
        //Die Elemente der Tabelle
        p31.add(new JLabel("x-Achse"), cl); cl.gridy++;
        p31.add(xliste, cr); cr.gridy++;
        p31.add(new JLabel("y-Achse"), cl); cl.gridy++;
        p31.add(yliste, cr); cr.gridy++;
        p31.add(new JLabel("Sortierung"), cl); cl.gridy++;
        p31.add(sortliste, cr); cr.gridy++;
        p31.add(new JLabel("Ausgabe drehen"), cl); cl.gridy++;
        p31.add(drehliste, cr); cr.gridy++;
        p31.add(new JLabel("Profile"), cl); cl.gridy++;
        p31.add(new JLabel("--- FIXME ---"), cr); cr.gridy++;
        //Seite Grafik, Punkte
        cl.gridy = cr.gridy = 0;
        p32.add(new JLabel("Farbe Achsen"), cl); cl.gridy++;
        p32.add(new JLabel("--- FIXME ---"), cr); cr.gridy++;
        p32.add(new JLabel("Farbe Werte"), cl); cl.gridy++;
        p32.add(new JLabel("--- FIXME ---"), cr); cr.gridy++;
        p32.add(new JLabel("Modus"), cl); cl.gridy++;
        p32.add(new JLabel("--- FIXME ---"), cr); cr.gridy++;
        p32.add(new JLabel("Stärke"), cl); cl.gridy++;
        p32.add(new JLabel("--- FIXME ---"), cr); cr.gridy++;
        p32.add(new JLabel("Form"), cl); cl.gridy++;
        p32.add(new JLabel("--- FIXME ---"), cr); cr.gridy++;
        p32.add(new JLabel("Vorschau"), cl); cl.gridy++;
        p32.add(new JLabel("--- FIXME ---"), cr); cr.gridy++;        
        //Seite Info
        JEditorPane infoarea = new JEditorPane();
        JScrollPane spia = new JScrollPane(infoarea,
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        infoarea.setEditable(false);
        infoarea.setContentType("text/html");
        infoarea.setPreferredSize(new Dimension(200,300));
        // FIXME: Textfeld nach oben scrollen
        infoarea.setText("<html>"+
            "<p style=\"color:blue;\">Kraftstoffverbrauch 3.0</p>" +
            "<p style=\"color:red;\">Erik Wegner</p>"+            
            "<p>This program is free software; you can redistribute it and/or modify " + 
            "it under the terms of the GNU General Public License as published by " +
            "the Free Software Foundation; either version 2 of the License, or " +
            "(at your option) any later version.</p>"+

            "<p>This program is distributed in the hope that it will be useful, "+
            "but WITHOUT ANY WARRANTY; without even the implied warranty of "+
            "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the "+
            "GNU General Public License for more details.</p>"+

            "<p>You should have received a copy of the GNU General Public License " +
            "along with this program; if not, write to the Free Software "+
            "Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA</p>"+
            "</html>");
        //Eingabe, Historie, Grafik, Info
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
        tabbedPane.setFont(f2);
        tabbedPane.addTab("Eingabe", p1);
        tabbedPane.addTab("Historie", p2);
        tabbedPane.addTab("Graph", gfxPane);
        tabbedPane.addTab("Info", spia);
        frame.getContentPane().add(tabbedPane);
    }
    
    public HEDlgErgebnis bearbeiteHistorieneintrag(Historieneintrag e) {
        UIswingHEDlg dlg = new UIswingHEDlg(frame, true, e);
        dlg.setVisible(true);
        //System.out.println("Dialog fertig, Ergebnis:" + dlg.holeErgebnis());
        return dlg.holeErgebnis();
    }
}

