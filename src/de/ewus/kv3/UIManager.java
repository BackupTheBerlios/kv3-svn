package de.ewus.kv3;
import java.lang.reflect.Method;

/**
 * Abstract class UIManager - write a description of the class here
 *
 *
 * @author     Erik Wegner
 * @created    1. Oktober 2004
 * @version    3.0
 */
public abstract class UIManager {
    protected Kleber k;


    /**
     * Übernimmt die Masterklebeklasse
     *
     * @param  k  Description of the Parameter
     */
    public void setzeKleber(Kleber k) {
        this.k = k;
    }


    /**
     *  Teilt Kleber mit, dass neue Werte zur Berechnung vorliegen.
     */
    protected void neueWerte() {
        k.neueWerte();
    }


    /**
     *  Teilt Kleber mit, dass die Anwendung beendet werden soll.
     */
    protected void quitApp() {
        k.quitApp();
    }


    /**
     * Diese Methode wird durch den Kleber aufgerufen,
     * wenn die Oberfläche dargestellt werden kann
     */
    abstract void startUI();
}

