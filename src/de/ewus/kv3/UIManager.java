package de.ewus.kv3;
import java.lang.reflect.Method;

/**
 * Abstract class UIManager - write a description of the class here
 *
 * @author     Erik Wegner
 * @created    1. Oktober 2004
 * @version    3.0
 */
public abstract class UIManager {
    protected float
        strecke = 0.0f,
        kraftstoff = 0.0f,
        ver100 = 0.0f,
        strLtr = 0.0f;
        
        
    /**
     *  Description of the Field
     */
    protected Kleber k;


    /**
     * ‹bernimmt die Masterklebeklasse
     *
     * @param  k  Description of the Parameter
     */
    public void setzeKleber(Kleber k) {
        this.k = k;
    }


    public void setzeVerbrauch100km(float ver100) {this.ver100 = ver100;}
    public void setzeStreckeJeLiter(float strLtr) {this.strLtr = strLtr;}
    /*public float holeStrecke() {return this.strecke;}
    public float holeKraftstoff() {return this.kraftstoff;}*/
    
    /**
     *  Teilt Kleber mit, dass neue Werte zur Berechnung vorliegen.
     */
    protected void neueWerte() {
        k.neueWerte(strecke, kraftstoff);
    }


    /**
     *  Teilt Kleber mit, dass die Anwendung beendet werden soll.
     */
    protected void quitApp() {
        k.quitApp();
    }


    /**
     *  Wird von Kleber aufgerufen, um die UI-Ressourcen freizugeben,
     *  anschlieﬂend wird die Anwendung beendet.
     */
    abstract void dispose();


    /**
     * Diese Methode wird durch den Kleber aufgerufen,
     * wenn die Oberfl‰che dargestellt werden kann
     */
    abstract void startUI();
}

