/*
 *  This file is part of Kraftstoffverbrauch3.
 *
 *  Kraftstoffverbrauch3 is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  Kraftstoffverbrauch3 is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Kraftstoffverbrauch3; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package de.ewus.kv3;

import javax.swing.JPanel;
import java.awt.image.*;
import java.awt.Graphics;
import java.awt.Color;

/**
 * Eine Swing-Komponente zur Anzeige des Diagrams.
 *
 * @author  e-man
 */
public class UIswingDiagramm extends JPanel {    
    private BufferedImage img;
    
    /**
     * Setzt das Bild, das gezeigt werden soll.
     * @param newImage Das neue Bild.
     */
    public void setImage(BufferedImage newImage) {
        this.img = newImage;
        repaint(0, 0, 0, getSize().width - 1, getSize().height - 1);	
    }
    
    /**
     * Der Konstruktor.
     * 
     * Das Standardbild ist 1x1 Pixel, die Komponente ist nicht transparent.
     */
    public UIswingDiagramm() {
        img = new BufferedImage(1,1, BufferedImage.TYPE_INT_RGB);
        setBackground(Color.WHITE);
        setOpaque(true);
    }
    
    /**
     * Wird aufgerufen, wenn die Komponente neu gezeichnet werden soll
     *
     * @param g 
     */
    protected void paintComponent(Graphics g) {    
        g.drawImage(img, 0, 0, this);
	System.out.println("paintComponent aufgerufen");
    }
}
