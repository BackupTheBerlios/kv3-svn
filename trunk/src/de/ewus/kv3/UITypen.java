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

/**
 * Enumeration class UITypen - Liste verf�gbarer Oberfl�chen
 *
 * @author     Erik Wegner
 * @version    1.0
 */
public enum UITypen
{
	/**
	 * Einstellung f�r Java Swing Oberfl�che
	 */
	swing, 
        /**
         * Einstellung f�r ncurses-basierte Oberfl�che
         */
        ncurses, 
        /**
         * Einstellung f�r Kommandozeileninterface
         */
        console
}
