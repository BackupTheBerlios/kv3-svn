package de.ewus.kv3;



public enum drehung
{
  D0(  "0 Grad", 0), 
  D1( "90 Grad", 1), 
  D2("180 Grad", 2),
  D3("270 Grad", 3);
    
  private String bezeichnung;
  private int drehschritte;
  
  drehung(String bezeichnung, int drehschritte)
  {
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

