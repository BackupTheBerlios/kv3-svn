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
import java.util.Properties;
import ml.options.*;
import java.io.*;
import java.awt.image.*;

/**
 * Die Klasse Kleber verbindet alle Hilfsklassen zur lauffähigen Anwendung.
 *
 * @author     Erik Wegner
 * @version    3.0
 */
public class Kleber {
    private UITypen targetui;
    //private Konfiguration konfig;
    private Properties properties; // NOTE: Apache.org bietet unter http://jakarta.apache.org/commons/cli/index.html ebenfalls eine Bibliothek
    private UIManager ui;
    private Historie historie;
    private GfxEinstellungen gfxeinstellungen;
    private Grafikfabrik gfxfabrik;
    private ml.options.Options opt;
    private boolean hasCLIOptions = false;
    
    private final String
        CFGDATEI = "cfgDatei",
        CFGFORMAT = "cfgFormat",
        DEFAULTUI = "defaultUI",
        HISTORIEDATEI = "HistorieDatei";

    /**
     * Constructor for objects of class Kleber
     *
     * @param  args  Die Übergabe der Kommandozeilenparameter
     */
    public Kleber(String[] args) {         
        opt = new Options(args, 0, 0);
        hasCLIOptions = args.length > 0;
        init();
        createUI();
        ui.startUI();
    }


    /**
     * Diese Methode wird von UIManager aufgerufen, wenn durch die
     * Oberfläche neue Werte zur Berechnung bereitstehen.
     * @param strecke Wegstrecke
     * @param kraftstoff Verbrauchter Kraftstoff
     */
    public void neueWerte(float strecke, float kraftstoff) {
        //System.out.println("Neue Werte");
        ui.setzeVerbrauch100km(kraftstoff*100/strecke);
        ui.setzeStreckeJeLiter(strecke/kraftstoff);
    }

    /*
     * Die Methode startet die Diagrammerzeugung.
     *
     * Diese Methode wird durch die Oberfläche aufgerufen, wenn ein
     * Diagramm erzeugt werden soll. Die Einstellungen dazu finden sich
     * in den GfxEinstellungen
     *
     * @see GfxEinstellungen
     * @see gfxeinstellungen
     */
    public void starteDiagrammerzeugung() {
        gfxfabrik.start();
    }

    /**
     * Diese Methode wird von der Grafikfabrik aufgerufen, wenn das Diagramm vollendet ist.
     */
    public void diagrammFertig(BufferedImage i){
        ui.diagrammFertig(i);
    }
        
    /**
     * Wird vom UIManager aufgerufen, wenn der Anwender einen Eintrag der Historie bearbeiten möchte. Die Methode prüft selbstständig, dass die Eintragsnummer gültig ist.
     * @param eintragnr Nummer des Historieneintrags, der bearbeitet werden soll.
     */
    public void historieEintragBearbeiten(int eintragnr) {
        if (eintragnr >= 0 && eintragnr < holeHistorie().size()) {
            Historieneintrag e = historie.holeEintragNr(eintragnr);
            Historieneintrag e2 = e.clone();
            UIManager.HEDlgErgebnis ergebnis = ui.bearbeiteHistorieneintrag(e2);
            switch (ergebnis) {
                case OK : historie.setzeEintragNr(eintragnr, e2); break;
                case Abbrechen : break;
                case Loeschen : historie.loescheEintrag(eintragnr); break;
            }
        }
    }
    
    /**
     * Wird vom UIManager aufgerufen, wenn der Anwender die durchgeführte Berechnung der Historie hinzufügen möchte. 
     * Die Methode sorgt dafür, dass anschließend der Eintrag-Bearbeiten-Dialog aufgerufen wird und bei Auswahl "OK" wird der Eintrag in die Historie eingefügt.
     * @param strecke Die Wegstrecke
     * @param kraftstoff Der benötigte Kraftstoff
     */
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
        historie.schreibHistorie(getProperty(HISTORIEDATEI));
	historie.dispose();
        schreibProperties();
        //System.exit(0);
    }


    /**
     *  Hier wird die Oberfläche der Anwendung initialisiert,
     *  die Darstellung wird separat angestoßen.
     */
    private void createUI() {
        switch (this.targetui) {
	case console : 
	    ui = new UIconsole();
	    break;
	default:
	    ui = new UIswing();
        }
        ui.setzeKleber(this);
    }


    private void hinweiseKommandozeile(String msg) {
        System.out.println("Folgende Kommandozeilenparameter werden verarbeitet:");
        System.out.println(" -ui=[swing|console|ncurses]");
        System.out.println(" -cfgFormat=[XML|TXT]");
        System.out.println(" -cfgDatei=<datei.cfg>");
        if (!msg.equals("")) {
            System.out.println("\n" + msg);
        }
    }

    /**
     * Die Klasse opt wird befüllt.
     */
    private void setzeCLIOptionen() {
        // TODO: Verlinken von http://www.javaworld.com/javaworld/jw-08-2004/jw-0816-command.html und Lizenz abbilden        
        opt.getSet().addOption("ui", 
            ml.options.Options.Separator.EQUALS, 
            ml.options.Options.Multiplicity.ZERO_OR_ONE);
        opt.getSet().addOption("cfgFormat",
            ml.options.Options.Separator.EQUALS, 
            ml.options.Options.Multiplicity.ZERO_OR_ONE);
        opt.getSet().addOption("cfgDatei",
            ml.options.Options.Separator.EQUALS, 
            ml.options.Options.Multiplicity.ZERO_OR_ONE);
    }
    
    /**
     *  Hier werden Konfigurationsoptionen ausgewertet und gesetzt.
     */
    private void init() {
        setzeCLIOptionen();
        if (hasCLIOptions && !opt.check(false,false)) {
            // Print usage hints
            hinweiseKommandozeile(opt.getCheckErrors());
            System.exit(1);
        } else {        
            //konfig = new Konfiguration("kv3konfig.txt", true);
            // DONE: properties aus Datei laden
            properties = new Properties();            
            liesProperties();
            historie = new Historie(this);
            
            //Zuerst alle Optionen auf Standard setzen
            setzeUI("swing");
            String histDatei = getProperty(HISTORIEDATEI);
            if (histDatei == null) histDatei = new String("");
            if (histDatei.equals("")) histDatei = "historie.kv3";
            setProperty(HISTORIEDATEI, histDatei);
            historie.liesHistorie(histDatei);
            gfxeinstellungen = new GfxEinstellungen();
            
            
            //Suche nach Konfigurationsoptionen in der konfig-Datei
            // TODO: Suche nach Konfigurationsoptionen in der konfig-Datei
            //if !properties.getValue("defaultui").equals("") setzeUI;
            String propDefUI = getProperty(DEFAULTUI);
            if (propDefUI != null) if (!propDefUI.equals("")) setzeUI(propDefUI);
            //System.out.println(DEFAULTUI + " = " + propDefUI);
            
            //Setze Optionen aus der Kommandozeile
            // TODO: Setze Optionen aus der Kommandozeile
            if (opt.getSet().isSet("ui")) setzeUI(opt.getSet().getOption("ui").getResultValue(0));
    
        }
        gfxfabrik = new Grafikfabrik(this);
    }

    
    private void liesProperties() {
        //In welchem Format legen wir die Konfiguration ab?
        boolean useXMLcfg = true; //Standard ist XML
        // QUESTION: Kann vielleicht nach einem fehlgeschlagenen Laden von XML-Parametern ein Laden als TXT versucht werden?
        String cfgDatei = "", cfgFormat = "";
        if (opt.getSet().isSet(CFGDATEI)) cfgDatei = opt.getSet().getOption(CFGDATEI).getResultValue(0);
        
        if (opt.getSet().isSet(CFGFORMAT)) {
            cfgFormat = opt.getSet().getOption(CFGFORMAT).getResultValue(0);
            if ( cfgFormat.equalsIgnoreCase("TXT") || cfgFormat.equalsIgnoreCase("XML"))
                useXMLcfg = cfgFormat.equalsIgnoreCase("XML");
            else
                hinweiseKommandozeile("Parameter -" + CFGFORMAT + " hat einen ungültigen Wert (" + cfgFormat + ").");
        }
        //Schonmal abspeichern, welches Datenformat wir haben
        setProperty(CFGFORMAT, (useXMLcfg ? "XML" : "TXT"));
        
        //Und wie heißt die Konfigurationsdatei?
        if (cfgDatei.equals("")) cfgDatei = "kv3.cfg";
        File cfgFile = new File(cfgDatei);
        try {
            if (cfgFile.exists()) {
                if (!cfgFile.canRead()) fehler("Datei \"" + cfgDatei + "\" ist nicht lesbar.");
                else {
                    FileInputStream in = new FileInputStream(cfgFile);
                    if (useXMLcfg) properties.loadFromXML(in);
                    else properties.load(in);
                    in.close();
                    //fehler("Datei geladen!");
                }
            }
            // QUESTION: Sollte in properties der Wert für cfgDatei wieder auf den Variableninalt von cfgDatei gesetzt werden? Oder soll der Wert aus der Datei bleiben?
        } 
        catch (SecurityException e) { fehler(e.getMessage()); }
        catch (IOException e) { 
            cfgDatei = cfgDatei + ".fix";
            fehler(e.getMessage());
            fehler("Die Speicherung erfolgt in die Datei " + cfgDatei);
        }
        catch (IllegalArgumentException e) { 
            cfgDatei = cfgDatei + ".fix";
            fehler(e.getMessage());
            fehler("Die Speicherung erfolgt in die Datei " + cfgDatei);
        }
        // QUESTION: Warum wird die Exception nicht geworfen?
        /*catch (InvalidPropertiesFormatException e) { 
            cfgDatei = cfgDatei + ".fix";
            fehler(e.getMessage());
            fehler("Die Speicherung erfolgt in die Datei " + cfgDatei);
        }*/
        catch (NullPointerException e) { fehler(e.getMessage()); }
        //Und wir merken uns unsere Konfigurationsdatei
        setProperty(CFGDATEI, cfgDatei);
    }

    private void schreibProperties() {
        boolean useXMLcfg = true; //Standard ist XML
        useXMLcfg = !getProperty(CFGFORMAT).equalsIgnoreCase("TXT");
        
        String cfgDatei = "", cfgFormat = "";
        cfgDatei = getProperty(CFGDATEI);
        cfgFormat = getProperty(CFGFORMAT);
        File cfgFile = new File(cfgDatei);
        try {
            if (cfgFile.exists() && !cfgFile.canWrite()) fehler("Datei \"" + cfgDatei + "\" ist nicht schreibbar.");
            if (!cfgFile.exists() && !cfgFile.createNewFile()) fehler("Datei \"" + cfgDatei + "\" kann nicht erstellt werden.");
            FileOutputStream out = new FileOutputStream(cfgFile);
            String comments = "KV3 - Konfigurationseinstellungen";
            if (useXMLcfg) properties.storeToXML(out, comments);
            else properties.store(out, comments);
            out.close();
            //fehler("Datei gespeichert!");
        }
        catch (SecurityException e) { fehler(e.getMessage()); }
        catch (IOException e) { fehler(e.getMessage()); }
        catch (ClassCastException e) { fehler(e.getMessage()); }
        catch (NullPointerException e) { fehler(e.getMessage()); }
    }

    private void fehler(String msg) {
        System.err.println(msg);
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
     * Die Methode erlaubt Zugriff auf den Properties-Speicher, der Benutzer- und Programmeinstellungen sichert.
     * @param key Die Bezeichnung des Schlüssels in der Properties-Datei.
     * @return Der dem Schlüssel zugeordnete Wert.
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    /**
     * Die Methode erlaubt Zugriff auf den Properties-Speicher, der Benutzer- und Programmeinstellungen sichert.
     * @param key Die Bezeichnung des Schlüsselwortes.
     * @param value Der Wert, der dem Schlüsselwort zugeordnet werden soll.
     */
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    /**
     * Die Methode ermöglicht den Hilfsklassen direkten Zugriff auf die Historien-Klasse.
     * @return Die Historie der Anwendung.
     */
    public Historie holeHistorie() {return this.historie;}

    /**
     * Der Einsprungpunkt für die Anwendung
     *
     * @param  args  The command line arguments
     */
    public static void main(String[] args) {
        new Kleber(args);
    }
}

