package de.ewus.kv3; 

import java.util.Vector;
import java.io.*;

/**
 * Write a description of class Historie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Historie
{
	private Vector<Historieneintrag> historie;
	/**
	 * Constructor for objects of class Historie
	 */
	public Historie()
	{
        historie = new Vector<Historieneintrag>();
	}

	/**
	 * Die Methode fügt den Historieneintrag zur Liste hinzu.
	 * 
	 * @param  e   Ein Historieneintrag	 
	 */
	public void fuegeHinzu(Historieneintrag e) {
        // TODO: fuegeHinzu(Historieneintrag)
        historie.add(e);
        System.out.println(e);
    }
    
    public void liesHistorie(String datei) {
        try {
            //Öffne Datei
            BufferedReader in = new BufferedReader(new FileReader(datei));
            String zeile;
            while ((zeile = in.readLine()) != null) fuegeHinzu(new Historieneintrag(zeile));            
            in.close();
            //lies Zeile
            //teile Zeile in Einheiten
        } 
        catch (IOException e) { System.err.println(e.getMessage()); }
        //catch (PatternSyntaxException e) { System.err.println(e.getMessage()); }
    }
}
