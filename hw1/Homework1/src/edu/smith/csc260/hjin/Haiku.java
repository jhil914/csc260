package edu.smith.csc260.hjin;

import edu.smith.csc260.haiku.AbstractHaiku;
import edu.smith.csc260.haiku.Printable;
/**
 * 
 * @author Hee Jin
 * csc 260 
 * 16 sep 2013
 * class that returns a Haiku
 *
 */
public class Haiku extends AbstractHaiku implements Printable {
	
	@Override
	//overrides print method
	public void print() {
		
	}

	@Override
	//gets the first line of the Haiku
	public String getLine1() {
		
		return "The leaves fall from trees";
	}

	public String getLine2() {
	//gets the second line of the Haiku
		return "They cover the ground like snow";
	}

	@Override
	//gets the third line of the Haiku
	public String getLine3() {
		 
		return "with colors of fall";
	}
 public static void main(String[] args) {
		Haiku haiku = new Haiku(); //creates Haiku object
		haiku.printHaiku(); //prints out the Haiku
	 }

}
