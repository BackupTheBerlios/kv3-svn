package de.ewus.kv3;
import java.io.*;

/**
 * Abstract class UIManager - write a description of the class here
 *
 * @author     Erik Wegner
 * @created    1. Oktober 2004
 * @version    3.0
 */
public abstract class UIManager {
    //Ergebnis des Dialogs zur Bearbeitung eines Historieneintrags
    public enum HEDlgErgebnis{ OK,  Abbrechen, Loeschen };
    protected float
        strecke = 0.0f,
        kraftstoff = 0.0f,
        ver100 = 0.0f,
        strLtr = 0.0f;
        
        
    /**
     *  Description of the Field
     */
    protected Kleber kleber;


    /**
     * Übernimmt die Masterklebeklasse
     *
     * @param  k  Description of the Parameter
     */
    public void setzeKleber(Kleber kleber) {
        this.kleber = kleber;
    }


    public void setzeVerbrauch100km(float ver100) {this.ver100 = ver100;}
    public void setzeStreckeJeLiter(float strLtr) {this.strLtr = strLtr;}
    /*public float holeStrecke() {return this.strecke;}
    public float holeKraftstoff() {return this.kraftstoff;}*/
    
    /**
     *  Teilt  Kleber mit, dass neue Werte in die Historie übernommen werden soll
     */
    protected void historieNeueWerte() {
        kleber.historieNeueWerte(strecke, kraftstoff);
    }
    
    /**
     *  Teilt Kleber mit, dass neue Werte zur Berechnung vorliegen.
     */
    protected void neueWerte() {
        kleber.neueWerte(strecke, kraftstoff);
    }


    /**
     *  Teilt Kleber mit, dass die Anwendung beendet werden soll.
     */
    protected void quitApp() {
        kleber.quitApp();
    }


    /**
     *  Wird von Kleber aufgerufen, um die UI-Ressourcen freizugeben,
     *  anschließend wird die Anwendung beendet.
     */
    abstract void dispose();


    /**
     * Diese Methode wird durch den Kleber aufgerufen,
     * wenn die Oberfläche dargestellt werden kann
     */
    abstract void startUI();
    

    protected String getProperty(String key) {
        return kleber.getProperty(key);
    }
    
    protected void setProperty(String key, String value) {
        kleber.setProperty(key, value);
    }

    
    /**
     *  Diese Methode wird von Kleber aufgerufen, wenn der 
     *  Historieneintrag e durch den Benutzer bearbeitet werden soll.
     *  Der Benutzer ändert die Werte des Historieneintrags,
     *  bestätigt die Änderungen (Rückgabewert OK), verwirrft sie
     *  (Abbrechen) oder wünscht den Eintrag zu löschen (Loeschen).     
     *
     *  @param e        Der zu bearbeitende Historieneintrag
     */
    abstract HEDlgErgebnis bearbeiteHistorieneintrag(Historieneintrag e);

    protected int string2Int(String fs) {
        int i = 0;
        try {
            i = Integer.parseInt(fs);
        } catch (NumberFormatException e) {}
        return i;
    }
    
    protected float string2Float(String fs) {
        float f = 0;
        try {
            f = Float.parseFloat(fs);
        } catch (NumberFormatException e) {}
        return f;
    }
    
    protected String werteBereitstellen(String streckeS, String kraftstoffS) {
        boolean werteIO = false;
	String r = "";
        try {
            this.strecke = Float.parseFloat(streckeS);
            this.kraftstoff = Float.parseFloat(kraftstoffS);
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
            r = sw.toString();
        }
	return r;
    }

}

