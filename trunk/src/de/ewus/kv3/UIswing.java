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
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.net.URL;

/**
 * Eine Implementation des UIManagers mit einer Java SWING-Oberfl�che.
 *
 * @author     Erik Wegner
 * @version    1.0
 */
public class UIswing extends UIManager implements WindowListener, Runnable, ActionListener, KeyListener, MouseListener, ComponentListener, ItemListener {
    private JFrame frame;  //Das Fenster
    private JLabel lStrecke, lKraftstoff, //Labels mit wechselnder Hintergrundfarbe
        lErgebnis;
    private JTextArea aSummen = new JTextArea();
    private JTextField tfStrecke, tfKraftstoff; //Textfelder f�r Eingabe
    private JTable histTable; //Tabelle der Historie
    private JComboBox 
        xliste, //Auswahl Datenquelle X-Achse
        yliste, //Auswahl Datenquelle Y-Achse
        cb_modus, //Auswahl Punkt- oder Linienzeichenmodus
        sortliste; //Auswahl Sortierung der Datenpunkte
    private UIswingDiagramm diagrammPanel; // Panel zur Darstellung des Diagramms
    
    private enum felder {Kraftstoff, Strecke};
    private felder aktivesFeld = felder.Kraftstoff;
    private boolean tfInhaltLoeschen = false;
    private final String diagrammPanelName = "diagrammPanel";
    private JTabbedPane tabbedPane;
    
    /**
     * Constructor for objects of class UIswing
     */
    public UIswing() {         
    }
        
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource().equals(cb_modus)) lies_UI_Einstellung_Modus();
        if (e.getSource().equals(xliste)) lies_UI_Einstellungen_xListe();
        if (e.getSource().equals(yliste)) lies_UI_Einstellungen_yListe();
        if (e.getSource().equals(sortliste)) lies_UI_Einstellungen_sortierliste();
    }

    
    public void mousePressed(MouseEvent e) {       
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getSource().equals(histTable)) {
            historieEintragBearbeiten(histTable.getSelectedRow());
            this.zeige_UI_Gesamtdurchschnitt();
        }
    }
    
    private void lies_UI_Einstellungen_sortierliste() {
        switch (sortliste.getSelectedIndex()) {
            case 0 : ge.sortierung = GfxEinstellungen.Sortierung.KEINE; break;
            case 1 : ge.sortierung = GfxEinstellungen.Sortierung.XUP; break;
            case 2 : ge.sortierung = GfxEinstellungen.Sortierung.YUP; break;
            case 3 : ge.sortierung = GfxEinstellungen.Sortierung.XYUP; break;
            case 4 : ge.sortierung = GfxEinstellungen.Sortierung.YXUP; break;
        }
    }
    
    private void lies_UI_Einstellungen_xListe() {
        ge.xAchse = xliste.getSelectedIndex();
    }
    
    private void lies_UI_Einstellungen_yListe() {
        ge.yAchse = yliste.getSelectedIndex();
    }
    
    private void lies_UI_Einstellung_Modus() {
        ge.zeichneLinie = cb_modus.getSelectedIndex() != 0;
        ge.zeichnePunkte = cb_modus.getSelectedIndex() != 1;
    }

    private void zeige_UI_Gesamtdurchschnitt() {
        StringBuffer s = new StringBuffer("");
        Historie h = kleber.holeHistorie();
        s.append("Gesamtstrecke ");
        s.append(h.summeStr2(Historieneintrag.STRECKE));
        s.append("km, Gesamtkraftstoff ");
        s.append(h.summeStr2(Historieneintrag.KRAFTSTOFF));
        s.append("l, durchschn. Verbrauch ");
        s.append(h.durchschnittStr2(Historieneintrag.KRAFTSTOFF100));
        s.append("l/100km, durchschn. ");
        s.append(h.durchschnittStr2(Historieneintrag.STRECKEJELITER));
        s.append("km/l, durchschn. Preis ");
        s.append(h.durchschnittStr3(Historieneintrag.PREIS));
        aSummen.setText(s.toString());
    }
    
    public void componentHidden(ComponentEvent e) {
        //Nichts zu tun
        //System.out.println("EHidden " + e.getComponent().getName());
    }
          
    public void componentMoved(ComponentEvent e) {
        //Nichts zu tun
        //System.out.println("EMoved " + e.getComponent().getName());
    }
          
    public void componentResized(ComponentEvent e) {
        Component c = e.getComponent();
        if (c.equals(diagrammPanel)) {
            System.out.println("EResized " + c.getName());
            ge.breite = c.getSize().width;
            ge.hoehe = c.getSize().height;
            if (diagrammPanel.isVisible()) {
                System.out.println("Panel ist sichtbar, Gr��e " + ge.breite + "x" + ge.hoehe);
                kleber.starteDiagrammerzeugung();
            } else System.out.println("Panel ist unsichtbar");
        }
    }

    public void componentShown(ComponentEvent e) {
        //System.out.println("EShow " + e.getComponent().getName());
        kleber.starteDiagrammerzeugung();
    }
 
   /**
     * Handle the key typed event from the text field.
     * @param e Ereignis
     */
    public void keyTyped(KeyEvent e) {
	    System.out.println("KEY TYPED: " + e.getKeyChar());        
        // TODO: Auf Tastendr�cke angemessen reagieren
        zahlentaste("");
    }

    /**
     * Handle the key pressed event from the text field.
     * @param e Ereignis
     */
    public void keyPressed(KeyEvent e) {
	    //System.out.println("KEY PRESSED: " + e);
    }

    /**
     * Handle the key released event from the text field.
     * @param e Ereignis
     */
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

    /*
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    */
    
    /**
     * 
     * @param e 
     */
    public void valueChanged(ListSelectionEvent e) {
        historieEintragBearbeiten(histTable.getSelectedRow());
        this.zeige_UI_Gesamtdurchschnitt();
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
	lErgebnis.setText(werteBereitstellen(tfStrecke.getText(), tfKraftstoff.getText()));
    }
    
    private void naechstesFeldAktivieren() {
        if (aktivesFeld == felder.Strecke) {
            setzeAktivesFeld(felder.Kraftstoff);
        } else {
            setzeAktivesFeld(felder.Strecke);
            werteBereitstellen();
        }
    }
    
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
        
        java.net.URL imageURL = UIswing.class.getResource("/kanister.gif");
        //System.out.println("imageURL=" + imageURL);
        if (imageURL != null) {            
            ImageIcon icon = new ImageIcon(imageURL);
            frame.setIconImage(icon.getImage());
        } else System.out.println("" + imageURL + " funktioniert nicht.");
        
        //frame.setIconImage( Toolkit.getDefaultToolkit().getImage("/kanister.png") );
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
            
            //Enth�lt der Inhalt des Textfelds schon ein Komma?
            //Dann darf kein zweites angeh�ngt werden
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
            // DONE: Historie
            //System.out.println("Historie");
            long anzahl = historieAnzahlEintraege();
            zeigeSeiteHistorie();            
            werteBereitstellen();
            historieNeueWerte();
            if (historieAnzahlEintraege() == anzahl) zeigeSeiteEingabe();            
        }
    }

    
    

    /**
     *  Adds a feature to the UIElements attribute of the UIswing object
     *
     * @param  frame  The feature to be added to the UIElements attribute
     */
    private void addUIElements(JFrame frame) {
        Font f, f2;
        //*****************************************
        //
        //Seite Eingabe
        //
        //*****************************************
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
        // TODO: rechtsb�ndige Ausrichtung f�r Textfeld
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
        // TODO: rechtsb�ndige Ausrichtung f�r Textfeld
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

        //*****************************************
        //
        //Seite Historie
        //
        //*****************************************
        JPanel p2 = new JPanel(new BorderLayout());
        histTable = new JTable(kleber.holeHistorie());
        //histTable.getSelectionModel().addListSelectionListener( this );
        histTable.addMouseListener(this);
        //JList liste = new JList();
        JScrollPane lsp = new JScrollPane(histTable);
        p2.add(lsp, BorderLayout.CENTER);
        p2.add(aSummen, BorderLayout.SOUTH);
        aSummen.setWrapStyleWord(true);
        aSummen.setLineWrap(true);
        zeige_UI_Gesamtdurchschnitt();
        //Seite Grafik
        JTabbedPane gfxPane = new JTabbedPane(JTabbedPane.BOTTOM);
        f = gfxPane.getFont();
        f2 = new Font(f.getName(), Font.PLAIN, 10);
        gfxPane.setFont(f2);
        JPanel p31 = new JPanel(new GridBagLayout()); gfxPane.addTab("Achsen", p31);
        JPanel p32 = new JPanel(new GridBagLayout()); gfxPane.addTab("Punkte", p32);
        JPanel p33 = new JPanel(new BorderLayout()); gfxPane.addTab("Filter", p33);
        diagrammPanel = new UIswingDiagramm(); 
        diagrammPanel.setName(diagrammPanelName);
        gfxPane.addTab("Grafik", diagrammPanel);
        diagrammPanel.addComponentListener(this);
        //Seite Grafik, Achsen
        xliste = new JComboBox(); 
        yliste = new JComboBox();
        GfxEinstellungen gfxe = this.kleber.holeGfxEinstellungen();
        for (int c1 = 0; c1<Historieneintrag.anzahlFelder; c1++) {
            xliste.addItem(Historieneintrag.feldNamen[c1]);
            yliste.addItem(Historieneintrag.feldNamen[c1]);
        };
        xliste.setSelectedIndex(gfxe.xAchse); xliste.addItemListener(this);
        yliste.setSelectedIndex(gfxe.yAchse); yliste.addItemListener(this);
	/*
        for (GfxEinstellungen.AchsenListe a : GfxEinstellungen.AchsenListe.values()) {
            xliste.addItem(a.bezeichnung());
            yliste.addItem(a.bezeichnung());
            if (a.equals(gfxe.xAchse)) xliste.setSelectedIndex(xliste.getItemCount()-1);
            if (a.equals(gfxe.yAchse)) yliste.setSelectedIndex(yliste.getItemCount()-1);
        }
	*/
        sortliste = new JComboBox();
        for (GfxEinstellungen.Sortierung s :GfxEinstellungen.Sortierung.values()) {
            sortliste.addItem(s.bezeichnung());
            if (s.equals(gfxe.sortierung)) sortliste.setSelectedIndex(sortliste.getItemCount()-1);
        }
        sortliste.addItemListener(this);
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
        //*****************************************
        //
        //Seite Grafik, Punkte
        //
        //*****************************************
        cl.gridy = cr.gridy = 0;
        p32.add(new JLabel("Farbe Achsen"), cl); cl.gridy++;
        p32.add(new JLabel("--- FIXME ---"), cr); cr.gridy++;
        p32.add(new JLabel("Farbe Werte"), cl); cl.gridy++;
        p32.add(new JLabel("--- FIXME ---"), cr); cr.gridy++;
        p32.add(new JLabel("Modus"), cl); cl.gridy++;
        cb_modus = new JComboBox();
        cb_modus.addItem("Punkte");
        cb_modus.addItem("Linien");
        cb_modus.addItem("Punkte und Linien");
        cb_modus.addItemListener(this);
        p32.add(cb_modus, cr);
        cr.gridy++;
        p32.add(new JLabel("St�rke"), cl); cl.gridy++;
        p32.add(new JLabel("--- FIXME ---"), cr); cr.gridy++;
        p32.add(new JLabel("Form"), cl); cl.gridy++;
        p32.add(new JLabel("--- FIXME ---"), cr); cr.gridy++;
        p32.add(new JLabel("Vorschau"), cl); cl.gridy++;
        p32.add(new JLabel("--- FIXME ---"), cr); cr.gridy++;        
        //*****************************************
        //
        //Seite Info
        //
        //*****************************************
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
        tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
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
    
    public void diagrammFertig(BufferedImage diagramm) {
        diagrammPanel.setImage(diagramm);
        System.out.println("Grafik gezeichnet");
    }
    
    private void zeigeSeiteHistorie() {
        tabbedPane.setSelectedIndex(1);
    }
    
    private void zeigeSeiteEingabe() {
        tabbedPane.setSelectedIndex(0);
    }
    
}
