package de.ewus.kv3;



public enum achsenListe
{
  Kraftstoffmenge("Kraftstoffmenge"), 
  Strecke("Strecke"), 
  ver100("Verbrauch l/100km"),
  strLtr("Strecke km/l"), 
  Datum("Datum"),
  Reihenfolge("Reihenfolge"),
  Fahrzeug("Fahrzeug");
  
  private String bezeichnung;
  
  achsenListe(String bezeichnung)
  {this.bezeichnung = bezeichnung;}
  
  public String bezeichnung() {
      return this.bezeichnung;
  }
} 

