package de.ewus.kv3; 

import java.util.Vector;
import java.io.*;

/**
 * Write a description of class Historie here.
 * 
 * @author Erik Wegner
 * @version 3.0
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
        // DONE: fuegeHinzu(Historieneintrag)
        historie.add(e);
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
}
