package de.ewus.kv3;

/**
 * Die Klasse Kleber verbindet alle Hilfsklassen zur lauff‰higen Anwendung.
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
     * @param  args  Die ‹bergabe der Kommandozeilenparameter
     */
    public Kleber(String[] args) {
        init(args);
        createUI();
        ui.startUI();
    }


    /**
     *  Diese Methode wird von UIManager aufgerufen, wenn durch die
     *  Oberfl‰che neue Werte zur Berechnung bereitstehen.
     */
    public void neueWerte(float strecke, float kraftstoff) {
        //System.out.println("Neue Werte");
        ui.setzeVerbrauch100km(kraftstoff*100/strecke);
        ui.setzeStreckeJeLiter(strecke/kraftstoff);
    }


    /**
     *  Diese Methode wird von UIManager aufgerufen, wenn die
     *  Anwendung beendet werden kann.
     */
    public void quitApp() {
        System.exit(0);
    }


    /**
     *  Hier wird die Oberfl‰che der Anwendung initialisiert,
     *  die Darstellung wird separat angestoﬂen.
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
     * Setzte die Oberfl‰che
     *
     * @param  targetui  Art der zu benutzenden Oberfl‰che
     */
    private void setzeUI(UITypen targetui) {
        this.targetui = UITypen.swing;
    }


    /**
     *  Ermˆglicht Zugriff auf die GfxEinstellungen
     *
     * @return    die gegenw‰rtigen GfxEinstellungen
     */
    public GfxEinstellungen holeGfxEinstellungen() {
        return this.gfxeinstellungen;
    }


    /**
     * Der Einsprungpunkt f¸r die Anwendung
     *
     * @param  args  The command line arguments
     */
    public static void main(String[] args) {
        new Kleber(args);
    }
}

