package de.ewus.kv3;

import java.util.*;
import java.text.*;
import java.text.NumberFormat;

public class Historieneintrag {
    public enum Streckentyp { Unbekannt, Stadt, Land };
    private float 
        strecke = 0.0f, 
        kraftstoff = 0.0f,
        preis = 0.0f;
    private Date datum = Calendar.getInstance().getTime(); //Datum ist heute
    private int fahrzeug = 0;
    private Streckentyp streckentyp = Streckentyp.Unbekannt;    
    private final String datumpattern = "dd.MM.yyyy";
    public final int maxFahrzeug = 10;
    public final String trenner = ",";

    public static final int anzahlFelder = 8;
    public static final String[] feldNamen = {
	"Strecke", 
	"Liter", 
	"Liter/100km", 
	"Strecke/Liter", 
	"Datum", 
	"Fahrzeug", 
	"Streckentyp", 
	"Preis"};
    public static final int
        STRECKE = 0,
        KRAFTSTOFF = 1,
        KRAFTSTOFF100 = 2,
        STRECKEJELITER = 3,
        DATUM = 4,
        FAHRZEUG = 5,
        STRECKENTYP = 6,
        PREIS = 7;

    private NumberFormat nf;

    private void initNF() {
	this.nf = NumberFormat.getInstance();
	this.nf.setMaximumFractionDigits(2);
    }

    public Historieneintrag clone() {
        return new Historieneintrag(this.toString());
    }
    
    public Historieneintrag(float strecke, float kraftstoff) {
        setzeStrecke(strecke);
        setzeKraftstoff(kraftstoff);
	initNF();
    }
    
    public Historieneintrag(String serial) {
        String[] teile = serial.split(",");
        int c1 = 0;
        try { setzeStrecke(Float.valueOf(teile[c1++])); } catch (NumberFormatException e) {}
        try { setzeKraftstoff(Float.valueOf(teile[c1++])); } catch (NumberFormatException e) {}
        try { setzePreis(Float.valueOf(teile[c1++])); } catch (NumberFormatException e) {}
        try { setzeFahrzeug(Integer.valueOf(teile[c1++])); } catch (NumberFormatException e) {}
        setzeStreckentyp(teile[c1++]);
        try { setzeDatum(teile[c1++]); } catch (NumberFormatException e) {}
	initNF();
    }
    
    public String toString() {
        StringBuffer sb = new StringBuffer("");
        sb.append(holeStrecke()); sb.append(trenner);
        sb.append(holeKraftstoff()); sb.append(trenner);
        sb.append(holePreis()); sb.append(trenner);
        sb.append(holeFahrzeug()); sb.append(trenner);
        sb.append(holeStreckentyp()); sb.append(trenner);
        sb.append(holeDatum());
        return sb.toString();
    }
    
    public String feld(int feldnummer) {
        String r = "";	
        switch (feldnummer) {
            case STRECKE        : r = nf.format(holeStrecke()); break;
            case KRAFTSTOFF     : r = nf.format(holeKraftstoff()); break;
            case KRAFTSTOFF100  : r = nf.format(holeVerbrauch100km()); break;
            case STRECKEJELITER : r = nf.format(holeStreckeJeLiter()); break;
            case DATUM          : r = holeDatum(); break;
            case FAHRZEUG       : r = Integer.toString(holeFahrzeug()); break;
            case STRECKENTYP    : switch (holeStreckentyp()) {
                case Unbekannt      : r = "U"; break;
                case Stadt          : r = "S"; break;
                case Land           : r = "L"; break;
            }
            break;
            case PREIS          : r = Float.toString(holePreis()); break;
        }
        return r;
    }

    public float        holeVerbrauch100km() {return kraftstoff*100/strecke;}
    public float        holeStreckeJeLiter() {return strecke/kraftstoff; }
    
    public float        holeStrecke()     { return this.strecke;     }
    public float        holeKraftstoff()  { return this.kraftstoff;  }
    public float        holePreis()       { return this.preis;       }
    public int          holeFahrzeug()    { return this.fahrzeug;    }
    public Streckentyp  holeStreckentyp() { return this.streckentyp; }
    public String       holeDatum()       {
        //DateFormat dateFormatter;
        //dateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT, new Locale("de","DE"));        
        //return dateFormatter.format(datum);
        SimpleDateFormat df = new SimpleDateFormat(datumpattern);
        return df.format(datum);
    }
    
	public void setzeStrecke(float strecke) {
		this.strecke = strecke;
	} // -- setzeStrecke(float strecke)()
    
    
	public void setzeKraftstoff(float kraftstoff) {
		this.kraftstoff = kraftstoff;
	} // -- setzeKraftstoff(float kraftstoff)()
    
    public void setzePreis(float preis) {
        this.preis = preis;
    }
    
    public void setzeDatum(Date datum) {
	this.datum = datum;        
    } // -- setzeDatum(Date datum)()
    
    
    public void setzeFahrzeug(int fahrzeug) {
        if (fahrzeug < maxFahrzeug) this.fahrzeug = fahrzeug;
    } // -- setzeFahrzeug(int Fahrzeug)()
    
    
    public void setzeStreckentyp(Streckentyp streckentyp) {
	this.streckentyp = streckentyp;
    } // -- setzeStreckentyp(Streckentyp streckentyp)()


    public void setzeStreckentyp(String st) {
        setzeStreckentyp(streckentyp.valueOf(streckentyp.getDeclaringClass(), st));
    }
    
    public void setzeStreckentyp(int i) {
        switch (i) {
            case 1 : setzeStreckentyp(Streckentyp.Stadt); break;
            case 2 : setzeStreckentyp(Streckentyp.Land); break;
            default : setzeStreckentyp(Streckentyp.Unbekannt);
        }
    }
    
    /**
     *  Die Methode prüft, ob die übergebene Zeichenkette ein Datum darstellt.
     *
     *  @param text     Die Zeichenkette, die geprüft werden soll
     *  @return         wahr, wenn beim Umwandeln kein Fehler auftrat
     */
    public boolean gueltigesDatum(String text) {
        boolean r=false;
        SimpleDateFormat df = new SimpleDateFormat(datumpattern);
        ParsePosition pos = new ParsePosition(0);
        try {
            Date d = df.parse(text, pos);
            r = pos.getIndex()>0;
        } catch (NullPointerException e) {}
        return r;
    }
    
    public void setzeDatum(String datum) {
        SimpleDateFormat df = new SimpleDateFormat(datumpattern);
        ParsePosition pos = new ParsePosition(0);
        try {
            Date d = df.parse(datum, pos);
            if(pos.getIndex()>0) this.datum = d;
        } catch (NullPointerException e) {}
    }
} // -- end class Historieneintrag

