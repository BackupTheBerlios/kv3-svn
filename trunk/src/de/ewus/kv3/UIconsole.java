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
import java.awt.image.*;

/**
 * Diese Klasse bietet eine Programmschnittstelle auf CLI-Ebene.
 *
 * @author     Erik Wegner
 * @version    1.0
 */
public class UIconsole extends UIManager
{
    private boolean aktiv; //Bleib im Hauptmenü, solange aktiv=true
    private final String
	TextBlau   = "\033[0;34m",
	TextGruen  = "\033[0;32m",
	TextGelb   = "\033[1;33m",
	TextNormal = "\033[0m";

    private final String
	PrgSprRechnen = "r",	
	PrgSprHistorie = "h",
	PrgEnde = "b",
	RechWerteZuHist = "h",
	HEStrecke = "s",
	HEKraftstoff = "k",
	HEDatum = "d",
	HEPreis = "p", 
	HEFahrzeug = "f",
	HEStreckentyp = "t",
    HEOK = "o!",
    HEAbbrechen = "a!",
    HELoeschen = "l!",
    HBearbeiten = "a";

    private BufferedReader br;
    private boolean eingabeOK = false;
    /**
     * Constructor for objects of class UIconsole
     */
    public UIconsole()
    {
	eingabeVorbereiten();
    }
    
    public void startUI() {
	//super.neueWerte();
	System.out.println("Willkommen bei KV3");
	System.out.println("");
	aktiv = true;
	while (aktiv && eingabeOK) {
	    menu();
	}
	kleber.quitApp();
    }
    
    public void dispose() {
    }
    
    private void navUeberschrift(String u) {
	System.out.println("\n\n" + TextGruen + "> " + u + TextNormal);
    }

    private void menuEintrag(String taste, String text) {
	System.out.println(TextGelb + taste + TextNormal + " " + text);
    }

    private void eingabeVorbereiten() {
	try {
	    br = new BufferedReader(new InputStreamReader(System.in));
	    eingabeOK = true;
	} catch (NullPointerException e) {
	    System.err.println("Keine Standardeingabe vorhanden?");
	    System.err.println(e);
	}
    }

    private String eingabe() {
	String r = PrgEnde;
	try {
	    if (eingabeOK) r = br.readLine();
	} catch (IOException e) {System.err.println(e);}
	return r;
    }

    private String eingabe(String text) {
	System.out.print(TextBlau + text + TextNormal);
	return eingabe();
    }
    
    private String hauptmenu() {
	boolean antwortOK=false;
	String antwort = "";
	do {
	    navUeberschrift("Hauptmenue");
	    menuEintrag(PrgSprRechnen, "Berechnung durchfuehren");
	    menuEintrag(PrgSprHistorie, "gehe zur Historie");
	    menuEintrag(PrgEnde, "beenden");
	    antwort = eingabe();
	    if (antwort.equals(PrgEnde)) {antwortOK = true;}
	    if (antwort.equals(PrgSprHistorie)) {antwortOK = true;}
	    if (antwort.equals(PrgSprRechnen)) {antwortOK = true;}
	} while (!antwortOK);
	return antwort;
    }

    private void dumpHistorie() {
        Historie h = kleber.holeHistorie();
        int maxCol = h.getColumnCount();
        System.out.print(TextGruen);
        for (int col = 0 ; col < maxCol ; col++) {
            System.out.print(h.getColumnName(col) + "\t");
        }
        System.out.println(TextNormal);
        for (int row = 0 ; row < h.getRowCount() ; row++) {
            System.out.print(TextGruen + row + TextNormal + " ");
            for (int col = 0 ; col < maxCol ; col++) {		
            System.out.print(h.getValueAt(row, col));
            System.out.print("\t");
            }
            System.out.println();
        }
        System.out.println(TextGruen + "Durschnittswerte");
        System.out.print(TextNormal + "Kraftstoff je 100 km: ");
        System.out.println(h.durchschnittStr2(Historieneintrag.KRAFTSTOFF100));
        System.out.print("Strecke je Liter: ");
        System.out.println(h.durchschnittStr2(Historieneintrag.STRECKEJELITER));
        System.out.println("Preis: " + h.durchschnittStr3(Historieneintrag.PREIS));
        
        System.out.println("Gesamtstrecke: " + h.summeStr2(Historieneintrag.STRECKE));
        System.out.println("Gesamtkraftstoff: " + h.summeStr2(Historieneintrag.KRAFTSTOFF));
    }

    private void menuHistorie() {
        navUeberschrift("Historie");
        dumpHistorie();
        menuEintrag(HBearbeiten, "Eintrag zur Bearbeitung anzeigen");
        if (eingabe().equals(HBearbeiten)) {
            int eintragnr = string2Int(eingabe("Nummer des Eintrags = "));
            historieEintragBearbeiten(eintragnr);
        }
    }

    private void menuRechnen() {
	boolean weiterrechnen;
	do {
	    weiterrechnen = false;
	    navUeberschrift("Berechnung durchfuehren");
	
	    System.out.println(werteBereitstellen(
						  eingabe("Strecke = "),
						  eingabe("Kraftstoff = ")
						  ));
	    menuEintrag(PrgSprRechnen, "neue Berechnung durchfuehren");
	    menuEintrag(RechWerteZuHist, "zur Historie hinzufuegen");
	    menuEintrag("", " zurueck zum Hauptmenue mit beliebiger Eingabe");
	    String antwort = eingabe();
	    if (antwort.equals(PrgSprRechnen)) weiterrechnen = true;
	    if (antwort.equals(RechWerteZuHist)) historieNeueWerte();
	} while (weiterrechnen);
    }

    private void menu() {
	String auswahl = hauptmenu();
	if (auswahl.equals(PrgSprRechnen)) menuRechnen();
	if (auswahl.equals(PrgSprHistorie)) menuHistorie();
	if (auswahl.equals(PrgEnde)) aktiv = false;
    }

    public HEDlgErgebnis bearbeiteHistorieneintrag(Historieneintrag e) {
	//System.out.println("\nHistorieneintrag:" + e + "\n");
	//TODO: menuEintrag aus e.getFeldbezeichnung() generieren
        HEDlgErgebnis r = HEDlgErgebnis.Abbrechen;
	    String antwort; boolean weiterbearbeiten = true;
        do {
            navUeberschrift("Historieneintrag bearbeiten");
            menuHE1(HEStrecke, Historieneintrag.STRECKE, e);
            menuHE1(HEKraftstoff, Historieneintrag.KRAFTSTOFF, e);
            menuHE1(HEDatum, Historieneintrag.DATUM, e);
            menuHE1(HEPreis, Historieneintrag.PREIS, e);
            menuHE1(HEFahrzeug, Historieneintrag.FAHRZEUG, e);
            menuHE1(HEStreckentyp, Historieneintrag.STRECKENTYP, e);
            menuEintrag(HEOK, "Eingaben OK");
            menuEintrag(HEAbbrechen, "Eingabe abbrechen");
            menuEintrag(HELoeschen, "Eintrag aus der Historie loeschen");
            
             antwort = eingabe();
             if (antwort.equals(HEOK) || antwort.equals(HEAbbrechen) || antwort.equals(HELoeschen)) {
                 weiterbearbeiten = false;
                 if (antwort.equals(HEOK)) r = HEDlgErgebnis.OK;
                 if (antwort.equals(HEAbbrechen)) r = HEDlgErgebnis.Abbrechen;
                 if (antwort.equals(HELoeschen)) r = HEDlgErgebnis.Loeschen;
             }
             if (antwort.equals(HEStrecke)) bearbeiteFeld(e, HEStrecke);
             if (antwort.equals(HEKraftstoff)) bearbeiteFeld(e, HEKraftstoff);
             if (antwort.equals(HEDatum)) bearbeiteFeld(e, HEDatum);
             if (antwort.equals(HEPreis)) bearbeiteFeld(e, HEPreis);
             if (antwort.equals(HEFahrzeug)) bearbeiteFeld(e, HEFahrzeug);
             if (antwort.equals(HEStreckentyp)) bearbeiteFeld(e, HEStreckentyp);
        } while (weiterbearbeiten);
        return r;
    }

    private void bearbeiteFeld(Historieneintrag e, String feld) {
        String neuerWert = "";
        if (feld.equals(HEStreckentyp)) {
            // TODO: Ausgabe durch Historieneintrag generieren
            navUeberschrift("Streckentyp");
            menuEintrag("0", "Unbekannt");
            menuEintrag("1", "Stadt");
            menuEintrag("2", "Land");
        }
        if (feld.equals(HEFahrzeug)) {
            navUeberschrift("Fahrzeug auswaehlen");
            menuEintrag("0", "Fahrzeug 0");
            menuEintrag(" ", "...");
            menuEintrag("9", "Fahrzeug 9");
        }
        
        neuerWert = eingabe("Neuer Wert = ");
        if (feld.equals(HEStrecke)) e.setzeStrecke(string2Float(neuerWert));
        if (feld.equals(HEKraftstoff)) e.setzeKraftstoff(string2Float(neuerWert));
        if (feld.equals(HEDatum)) if (e.gueltigesDatum(neuerWert)) e.setzeDatum(neuerWert);        
        if (feld.equals(HEPreis)) e.setzePreis(string2Float(neuerWert));
        if (feld.equals(HEFahrzeug)) e.setzeFahrzeug(string2Int(neuerWert));
        if (feld.equals(HEStreckentyp)) e.setzeStreckentyp(string2Int(neuerWert));
    }
    
    private void menuHE1(String taste, int feld, Historieneintrag e) {
	menuEintrag(taste, "Wert fuer " + Historieneintrag.feldNamen[feld] +
		    " aendern (" + e.feld(feld) + ")");
    }
    
    public void diagrammFertig(BufferedImage diagramm) {
        
    }
    
}
