package de.ewus.kv3; 
import java.util.HashMap;
import java.util.Set;
import java.io.*;

/**
 * Die Klasse Konfiguration speichert Einstellungen der Anwendung.
 * 
 * <p>Einstellungen werden in einer baumartigen Struktur gespeichert,
 * sodass sich jeder Wert über eine Kategorie und ein Schlüsselwort definiert.
 * Eine Kategorie fasst mehrere Schlüsselworte zusammen.</p>
 * 
 * <p>Die Kategorie "GLOBAL" ist immer existent und enthält alle Schlüsselworte,
 * die keiner konkreten Kategorie zugeordnet wurden.</p>
 * 
 * @author Erik Wegner
 * @version 1.0
 */
public class Konfiguration
{
    private String datei;
    private HashMap<String,HashMap<String,String>> kategorien;
    private HashMap<String,String> schluessel;
    private final String GLOBAL = "GLOBAL";
    private final String TRENNER = "=";
    
	/**
     * Konstruktor mit Übergabe einer Ladedatei
     *
     * @param  datei   Dateiname mit zuvor gespeicherten Werten
     * @param  laden   Bestimmt, ob die Datei geladen werden soll
     */
    public Konfiguration(String datei, boolean laden)
    {
        this.datei = datei;
        this.init();
        if (laden) this.laden();
    }
    
    /**
     * Die Methode übernimmt die Grundeinrichtung der Hashmap
     */
    public void init()
    {
        kategorien = new HashMap<String,HashMap<String,String>>();
        this.setzeKategorie(this.GLOBAL);
    }
    
    /**
     * Die Methode setzt die für alle nachfolgenden Operationen gültige 
     * Kategorie.
     *
     * @param  kategorie   Die Kategorie
     */
    public void setzeKategorie(String kategorie)
    {
        if (kategorie.equals("")) kategorie = this.GLOBAL;
        if ( this.kategorien.containsKey(kategorie) ) {
            schluessel = this.kategorien.get(kategorie);
        } else {
            schluessel = new HashMap<String,String>();
            this.kategorien.put(kategorie, schluessel);
        }
    }
    
    /**
     * Diese Methode setzt in der aktuellen Kategorie den Wert für
     * das Schlüsselwort.
     *
     * @param   schluesselwort  Das Schlüsselwort
     * @param   wert            Der Wert des Schlüsselworts
     */
    public void setzeWert(String schluesselwort, String wert)
    {
        schluessel.put(schluesselwort, wert);
    }
    
    /**
     * Diese Methode liest den Wert für das Schlüsselwort aus
     *
     * @param  schluesselwort   Das Schlüsselwort
     * @return                  Den zum Schlüsselwort gespeicherten Wert
     */
    public String holeWert(String schluesselwort)
    {
        String r = "";
        if (schluessel.containsKey(schluesselwort)) r = schluessel.get(schluesselwort);
        return r;
    }    
    
    /**
     * Liest die Datei aus und stellt die dort gespeicherten Werte
     * zur Verfügung
     * 
     * @return  Erfolg des Ladevorgangs
     */
    public boolean laden()
    {
        boolean erfolg = false;
        try {
            String zeile; String[] zeilenteile;
            BufferedReader br = new BufferedReader(new FileReader(this.datei));
            int z1 = this.str2int(br.readLine());
            while (z1 > 0) {
                this.setzeKategorie(br.readLine());
                int z2 = this.str2int(br.readLine());
                while (z2 > 0) {
                    zeile = br.readLine();
                    zeilenteile = zeile.split(this.TRENNER,2);
                    if (zeilenteile.length > 1) 
                        this.setzeWert(zeilenteile[0], zeilenteile[1]);
                    z2--;
                }
                z1--;
            }
        }
        catch (IOException e) {
            System.err.println(e);
        }
        return erfolg;
    }
    
    private int str2int(String zahl) {
        int i = 0;   
        try {
            int ti = Integer.parseInt(zahl);
            i = ti;
        } catch (NumberFormatException e) {
            System.err.println(e);
        }
        return i;
    }
    
    /**
     * Speichert die Werte in der Datei
     * 
     * @return  Erfolg des Speicherns
     */
    public boolean speichern()
    {
        boolean erfolg = false;
        try {
            FileOutputStream out = new FileOutputStream(this.datei);
            PrintWriter pw = new PrintWriter(out);
            //Anzahl Kategorien
            pw.println(kategorien.size());
            Set<String> kset = kategorien.keySet();
            HashMap<String,String> speicherhash;
            for (String kategorie : kset) {
                //Kategoriename
                pw.println(kategorie);
                speicherhash = kategorien.get(kategorie);
                //Anzahl Schlüsselwörter
                pw.println(speicherhash.size());
                Set<String> shset = speicherhash.keySet();
                for (String schluesselwort : shset) {
                    //Schlüsselwort = Wert
                    pw.println(schluesselwort + this.TRENNER + 
                        speicherhash.get(schluesselwort));
                }
            }
            pw.close();
            erfolg = true;
        } catch (IOException e){
            System.err.println(e);
        }
        return erfolg;
    }
       
    
}
