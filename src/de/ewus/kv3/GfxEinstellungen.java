package de.ewus.kv3;



public class GfxEinstellungen extends java.lang.Object
{
    
    public enum Sortierung {
        KEINE("Keine"), 
        XUP( "X aufsteigend"), 
        YUP( "Y aufsteigend"),
        XYUP("X, dann Y auf."),
        YXUP("Y, dann X auf.");
  
        private String bezeichnung;
  
        Sortierung(String bezeichnung)
        {this.bezeichnung = bezeichnung;}
  
        public String bezeichnung() {
            return this.bezeichnung;
        }
    } 
    
    public enum AchsenListe {
        Kraftstoffmenge("Kraftstoffmenge"), 
        Strecke("Strecke"), 
        ver100("Verbrauch l/100km"),
        strLtr("Strecke km/l"), 
        Datum("Datum"),
        Reihenfolge("Reihenfolge"),
        Fahrzeug("Fahrzeug");
  
        private String bezeichnung;
  
        AchsenListe(String bezeichnung)
        {this.bezeichnung = bezeichnung;}
  
        public String bezeichnung() {
            return this.bezeichnung;
        }
    }
    
    public enum Drehung {
        D0(  "0 Grad", 0), 
        D1( "90 Grad", 1), 
        D2("180 Grad", 2),
        D3("270 Grad", 3);
    
        private String bezeichnung;
        private int drehschritte;
  
        Drehung(String bezeichnung, int drehschritte) {
            this.bezeichnung = bezeichnung;
            this.drehschritte = drehschritte;
        }
  
        public String bezeichnung() {
            return this.bezeichnung;
        }
  
  public int drehschritte() {
      return this.drehschritte;
  }
}
    
    public AchsenListe xAchse, yAchse;
    public Sortierung sortierung;
    public Drehung drehung;
    
	public GfxEinstellungen() {
		this.xAchse = AchsenListe.Kraftstoffmenge;
        this.yAchse = AchsenListe.Strecke;
        this.sortierung = Sortierung.XUP;
        this.drehung = Drehung.D0;
	}
    
    public void setzeString2Drehung(String v) {
        for (Drehung d : Drehung.values()) if (d.toString().equals(v)) this.drehung = d;
    }
    public void setzeString2Sortierung(String v) {
        for (Sortierung s : Sortierung.values()) if (s.toString().equals(v)) this.sortierung = s;
    }
    public void setzeString2xAchse(String v) {
        for (AchsenListe a : AchsenListe.values()) if (a.toString().equals(v)) this.xAchse = a;
    }
    public void setzeString2yAchse(String v) {
        for (AchsenListe a : AchsenListe.values()) if (a.toString().equals(v)) this.yAchse = a;
    }
    
} 

