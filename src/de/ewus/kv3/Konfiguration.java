package de.ewus.kv3; 
import java.util.HashMap;
import java.util.Set;
import java.io.*;

/**
 * Die Klasse Konfiguration speichert Einstellungen der Anwendung.
 * 
 * <p>Einstellungen werden in einer flachen Struktur gespeichert,
 * jeder Wert ist �ber ein Schl�sselwort definiert.</p>
 * 
 * @author Erik Wegner
 * @version 1.0
 */
public class Konfiguration
{
    private String datei;
    private HashMap<String,String> schluessel;
    private final String GLOBAL = "GLOBAL";
    private final String TRENNER = "=";
    
	/**
     * Konstruktor mit �bergabe einer Ladedatei
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
     * Die Methode �bernimmt die Grundeinrichtung der Hashmap
     */
    public void init()
    {
        this.schluessel = new HashMap<String,String>();        
    }
    
    
    /**
     * Diese Methode setzt in der aktuellen Kategorie den Wert f�r
     * das Schl�sselwort.
     *
     * @param   schluesselwort  Das Schl�sselwort
     * @param   wert            Der Wert des Schl�sselworts
     */
    public void setzeWert(String schluesselwort, String wert)
    {
        this.schluessel.put(schluesselwort, wert);
    }
    
    /**
     * Diese Methode liest den Wert f�r das Schl�sselwort aus
     *
     * @param  schluesselwort   Das Schl�sselwort
     * @return                  Den zum Schl�sselwort gespeicherten Wert
     */
    public String holeWert(String schluesselwort)
    {
        String r = "";
        if (schluessel.containsKey(schluesselwort)) r = schluessel.get(schluesselwort);
        return r;
    }    
    
    /**
     * Liest die Datei aus und stellt die dort gespeicherten Werte
     * zur Verf�gung
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
                zeile = br.readLine();
                zeilenteile = zeile.split(this.TRENNER,2);
                if (zeilenteile.length > 1) 
                    this.setzeWert(zeilenteile[0], zeilenteile[1]);                    
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
            //Anzahl Schl�sselworte
            pw.println(schluessel.size());
            Set<String> kschluessel = schluessel.keySet();
            for (String schluesselwort : kschluessel) {
                //Schl�sselwort = Wert
                pw.println(schluesselwort + this.TRENNER + 
                    holeWert(schluesselwort));
            }
            pw.close();
            erfolg = true;
        } catch (IOException e){
            System.err.println(e);
        }
        return erfolg;
    }
       
    
}
