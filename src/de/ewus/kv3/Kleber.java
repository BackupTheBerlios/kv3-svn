package de.ewus.kv3;
import java.util.Properties;
import ml.options.*;

/**
 * Die Klasse Kleber verbindet alle Hilfsklassen zur lauffähigen Anwendung.
 *
 * @author     Erik Wegner
 * @created    1. Oktober 2004
 * @version    3
 */
public class Kleber {
    private UITypen targetui;
    //private Konfiguration konfig;
    private Properties properties;
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
        // QUESTION: System.exit oder ui.dispose();
        ui.dispose();
        //System.exit(0);
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


    private void hinweiseKommandozeile(String msg) {
        System.out.println("Folgende Kommandozeilenparameter werden verarbeitet:");
        System.out.println(" -ui=[swing|console|ncurses]");
        if (!msg.equals("")) {
            System.out.println("\n" + msg);
        }
    }
    
    /**
     *  Hier werden Konfigurationsoptionen ausgewertet und gesetzt.
     *
     * @param  args  Die Kommandozeilenparameter
     */
    private void init(String[] args) {
        ml.options.Options opt = new Options(args, 0, 1);
        opt.getSet().addOption("ui", 
            ml.options.Options.Separator.EQUALS, 
            ml.options.Options.Multiplicity.ZERO_OR_ONE);
        if (args.length > 0 && !opt.check(false,false)) {
            // Print usage hints
            hinweiseKommandozeile(opt.getCheckErrors());
            System.exit(1);
        } else {        
            historie = new Historie();
            //konfig = new Konfiguration("kv3konfig.txt", true);
            // TODO: properties aus Datei laden
            properties = new Properties();
            
            
            //Zuerst alle Optionen auf Standard setzen
            setzeUI("swing");
            gfxeinstellungen = new GfxEinstellungen();
            
            
            //Suche nach Konfigurationsoptionen in der konfig-Datei
            // TODO: Suche nach Konfigurationsoptionen in der konfig-Datei
            //if !properties.getValue("defaultui").equals("") setzeUI;
            
            
            //Setze Optionen aus der Kommandozeile
            // TODO: Setze Optionen aus der Kommandozeile
            setzeUI(opt.getSet().getOption("ui").getResultValue(0));
    
        }
    }



    /**
     * Setzte die Oberfläche
     *
     * @param  targetui  Art der zu benutzenden Oberfläche
     */
    private void setzeUI(String targetui) {
        if (targetui.equals("swing")) this.targetui = UITypen.swing;
        else if (targetui.equals("console")) this.targetui = UITypen.console;
            else if (targetui.equals("ncurses")) this.targetui = UITypen.ncurses;
                else hinweiseKommandozeile("Parameter -ui hat einen ungültigen Wert (" + targetui + ").");        
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

