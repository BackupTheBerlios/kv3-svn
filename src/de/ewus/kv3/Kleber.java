package de.ewus.kv3;

/**
 * Die Klasse Kleber verbindet alle Hilfsklassen zur lauffähigen Anwendung.
 *
 * @author     Erik Wegner
 * @created    1. Oktober 2004
 * @version    3
 */
public class Kleber {
    private UITypen targetui;
    private Konfiguration konfig;
    private UIManager ui;
    private Historie historie;
    private GfxEinstellungen gfxeinstellungen;


    /**
     * Constructor for objects of class Kleber
     *
     * @param  args  Die Übergabe der Kommandozeilenparameter
     */
    public Kleber(String[] args) {
        init(args);
        createUI();
        ui.startUI();        
    }


    /**
     *  Diese Methode wird von UIManager aufgerufen, wenn durch die
     *  Oberfläche neue Werte zur Berechnung bereitstehen.
     */
    public void neueWerte(float strecke, float kraftstoff) {
        //System.out.println("Neue Werte");
        ui.setzeVerbrauch100km(kraftstoff*100/strecke);
        ui.setzeStreckeJeLiter(strecke/kraftstoff);
    }

    
    public void historieNeueWerte(float strecke, float kraftstoff) {
        Historieneintrag e = new Historieneintrag(strecke, kraftstoff);
        UIManager.HEDlgErgebnis ergebnis = ui.bearbeiteHistorieneintrag(e);
        if (ergebnis.equals(UIManager.HEDlgErgebnis.OK)) historie.fuegeHinzu(e);
    }

    /**
     *  Diese Methode wird von UIManager aufgerufen, wenn die
     *  Anwendung beendet werden kann.
     */
    public void quitApp() {
        System.exit(0);
    }


    /**
     *  Hier wird die Oberfläche der Anwendung initialisiert,
     *  die Darstellung wird separat angestoßen.
     */
    private void createUI() {
        switch (this.targetui) {
            default:
                ui = new UIswing();
        }
        ui.setzeKleber(this);
    }


    /**
     *  Hier werden Konfigurationsoptionen ausgewertet und gesetzt.
     *
     * @param  args  Die Kommandozeilenparameter
     */
    private void init(String[] args) {
        historie = new Historie();
        konfig = new Konfiguration("kv3konfig.txt", true);
        //Zuerst alle Optionen auf Standard setzen
        setzeUI(UITypen.swing);
        gfxeinstellungen = new GfxEinstellungen();        
        //Suche nach Konfigurationsoptionen in der konfig-Datei

        //Suche nach Konfigurationsoptionen auf der Kommandozeile
        String[] argv;
        for (int c1 = 0; c1 < args.length; c1++) {
            argv = args[c1].split("=", 2);
            if (argv[0].equals("--ui") && argv.length == 2) {
                setzeUI(UITypen.console);
            }
        }
    }



    /**
     * Setzte die Oberfläche
     *
     * @param  targetui  Art der zu benutzenden Oberfläche
     */
    private void setzeUI(UITypen targetui) {
        this.targetui = UITypen.swing;
    }


    /**
     *  Ermöglicht Zugriff auf die GfxEinstellungen
     *
     * @return    die gegenwärtigen GfxEinstellungen
     */
    public GfxEinstellungen holeGfxEinstellungen() {
        return this.gfxeinstellungen;
    }


    /**
     * Der Einsprungpunkt für die Anwendung
     *
     * @param  args  The command line arguments
     */
    public static void main(String[] args) {
        new Kleber(args);
    }
}

