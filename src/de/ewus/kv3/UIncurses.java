package de.ewus.kv3;


/**
 * Write a description of class UIncurses here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class UIncurses extends UIManager
{
	// instance variables - replace the example below with your own
	private int x;

	/**
	 * Constructor for objects of class UIncurses
	 */
	public UIncurses()
	{
		// initialise instance variables
		x = 0;
	}

	/**
	 * An example of a method - replace this comment with your own
	 * 
	 * @param  y   a sample parameter for a method
	 * @return     the sum of x and y 
	 */
	public int sampleMethod(int y)
	{
		// put your code here
		return x + y;
	}
	
	public void startUI() {
	    super.neueWerte();
	}
    
    public void dispose() {}
    
    public HEDlgErgebnis bearbeiteHistorieneintrag(Historieneintrag e) {
        return HEDlgErgebnis.Abbrechen;
    }
}
