package de.ewus.kv3;

import java.io.*;

/**
 * Write a description of class UIconsole here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
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
	HEStreckentyp = "t";

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
    }

    private void menuHistorie() {
	navUeberschrift("Historie");
	dumpHistorie();
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
	navUeberschrift("Historieneintrag bearbeiten");
	menuHE1(HEStrecke, Historieneintrag.STRECKE, e);
	menuHE1(HEKraftstoff, Historieneintrag.KRAFTSTOFF, e);
	menuHE1(HEDatum, Historieneintrag.DATUM, e);
	menuHE1(HEPreis, Historieneintrag.PREIS, e);
	menuHE1(HEFahrzeug, Historieneintrag.FAHRZEUG, e);
	menuHE1(HEStreckentyp, Historieneintrag.STRECKENTYP, e);

        return HEDlgErgebnis.Abbrechen;
    }

    public void menuHE1(String taste, int feld, Historieneintrag e) {
	menuEintrag(taste, "Wert fuer " + Historieneintrag.feldNamen[feld] +
		    " aendern (" + e.feld(feld) + ")");
    }
}
