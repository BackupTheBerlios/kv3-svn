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
    public AchsenListe
        xAchse=AchsenListe.Kraftstoffmenge, 
        yAchse=AchsenListe.Kraftstoffmenge;
    public Sortierung 
        sortierung = Sortierung.KEINE;
    public Drehung 
        drehung = Drehung.D0;
    
	public GfxEinstellungen() {
        setzeString2xAchse("Kraftstoffmenge");
        setzeString2yAchse("Strecke");
        setzeString2sortierung("XUP");
        setzeString2drehung("D0");
		/*this.xAchse = AchsenListe.Kraftstoffmenge;
        this.yAchse = AchsenListe.Strecke;
        this.sortierung = Sortierung.XUP;
        this.drehung = Drehung.D0;*/
	}
    
    public void setzeString2drehung(String v) {
        drehung = drehung.valueOf(drehung.getDeclaringClass(), v);
    }
    public void setzeString2sortierung(String v) {
        sortierung = sortierung.valueOf(sortierung.getDeclaringClass(), v);        
    }
    public void setzeString2xAchse(String v) {
        xAchse = xAchse.valueOf(xAchse.getDeclaringClass(), v);
    }
    public void setzeString2yAchse(String v) {
        yAchse = xAchse.valueOf(yAchse.getDeclaringClass(), v);
    }
    
} 

