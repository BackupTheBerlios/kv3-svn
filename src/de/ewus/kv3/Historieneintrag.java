package de.ewus.kv3;

import java.util.*;
import java.text.*;

public class Historieneintrag {
    public enum Streckentyp { Unbekannt, Stadt, Land };
    private float 
        strecke = 0.0f, 
        kraftstoff = 0.0f,
        preis = 0.0f;
    private Date datum = Calendar.getInstance().getTime(); //Datum ist heute
    private int fahrzeug = 0;
    private Streckentyp streckentyp = Streckentyp.Unbekannt;    
    
    public final int maxFahrzeug = 10;
    
    public Historieneintrag(float strecke, float kraftstoff) {
        setzeStrecke(strecke);
        setzeKraftstoff(kraftstoff);
    }
    
	public float        holeStrecke()     { return this.strecke;     }
    public float        holeKraftstoff()  { return this.kraftstoff;  }
    public float        holePreis()       { return this.preis;       }
    public int          holeFahrzeug()    { return this.fahrzeug;    }
    public Streckentyp  holeStreckentyp() { return this.streckentyp; }
    public String       holeDatum()       {
        DateFormat dateFormatter;
        dateFormatter = DateFormat.getDateInstance(DateFormat.DEFAULT, new Locale("de","DE"));        
        return dateFormatter.format(datum);
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
    
} // -- end class Historieneintrag
