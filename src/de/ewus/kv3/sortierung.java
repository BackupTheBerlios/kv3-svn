package de.ewus.kv3;



public enum sortierung
{
  KEINE("Keine"), 
  XUP("X aufsteigend"), 
  YUP("Y aufsteigend"),
  XYUP("X, dann Y auf."),
  YXUP("Y, dann X auf.");
  
  private String bezeichnung;
  
  sortierung(String bezeichnung)
  {this.bezeichnung = bezeichnung;}
  
  public String bezeichnung() {
      return this.bezeichnung;
  }
} 

