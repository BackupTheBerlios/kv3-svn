package de.ewus.kv3;


/**
 * Write a description of class UIconsole here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class UIconsole extends UIManager
{
	/**
	 * Constructor for objects of class UIconsole
	 */
	public UIconsole()
	{
	}

	public void startUI() {
	    //super.neueWerte();
	}
    
    public void dispose() {}
    
    public  HEDlgErgebnis bearbeiteHistorieneintrag(Historieneintrag e) {
        return HEDlgErgebnis.Abbrechen;
    }
}
