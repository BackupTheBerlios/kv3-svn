package de.ewus.kv3;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
public class UIswing extends UIManager implements WindowListener, Runnable, ActionListener {
    private JFrame frame;  //Das Fenster
    
    /**
     * Constructor for objects of class UIswing
     */
    public UIswing() { }


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
     *  Description of the Method
     */
    public void startUI() {
        javax.swing.SwingUtilities.invokeLater(this);
        neueWerte();
    }


    /**
     *  Main processing method for the UIswing object
     */
    public void run() {
        /*
         *  create and show the GUI
         */
        frame = new JFrame("Kraftstoffverbrauch 3.0");
        ImageIcon icon = new ImageIcon("kanister.png", "Kanister");
        frame.setIconImage(icon.getImage());
        addUIElements(frame);
        frame.addWindowListener(this);
        frame.setSize(new Dimension(240, 320));
        frame.setVisible(true);
    }


    /**
     *  Description of the Method
     *
     * @param  e  Description of the Parameter
     */
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
    }


    /**
     *  Adds a feature to the UIElements attribute of the UIswing object
     *
     * @param  frame  The feature to be added to the UIElements attribute
     */
    private void addUIElements(JFrame frame) {
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
        p11.add(new JLabel("Strecke"), c);
        c.gridx = 1;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        p11.add(new JTextField(), c);
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.0;
        p11.add(new JLabel("Kraftstoffmenge"), c);
        c.gridx = 1;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        p11.add(new JTextField(), c);
        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 2;
        p11.add(new JLabel("Ergebnis der Berechnung"), c);
        p1.add(p11, BorderLayout.NORTH);
        //Tastenfelder
        JPanel p12 = new JPanel(new GridLayout(4, 4));
        JButton b;
        Font f = p12.getFont();
        Font f2 = new Font(f.getName(), Font.BOLD, f.getSize() * 2);
        for (int c1 = 0; c1 < tasten.length; c1++) {
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
        JList liste = new JList();
        JScrollPane lsp = new JScrollPane(liste);
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
        for (drehung d : drehung.values()) {
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
        
        //Eingabe, Historie, Grafik, Info
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
        tabbedPane.setFont(f2);
        tabbedPane.addTab("Eingabe", p1);
        tabbedPane.addTab("Historie", p2);
        tabbedPane.addTab("Graph", gfxPane);
        tabbedPane.addTab("Info", new JButton("Knopf"));
        frame.getContentPane().add(tabbedPane);
    }
}

