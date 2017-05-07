/*
 * Class: Parameter
 * Purpose: Abstract class that contains the base functionality for a parameter that all sub-classes must implement
 */

package edu.ramapo.bbabcock.seniorProject;

import java.awt.event.KeyEvent;

public abstract class Parameter {

	/**
	 * Should react to the given keyboard press, and return whether or not it was handled
	 *
	 * @param a_event the keyboard event that occurred
	 * @return true, if successfully handled, oterwise false
	 */
	public boolean keyPressed(KeyEvent a_event){
		return false;
	}

	/**
	 * Convert the parameter to a string representation, displayed on buttons for the parameter
	 *
	 * @return the string representation
	 */
	public abstract String convertToString();

	/* (non-Javadoc)
	 *
	 * Converts the given item to a string, kept separate from convertToString to allow for separate internal and external representations fo data
	 *
	 * @see java.lang.Object#toString()
	 */
	public String toString(){ return convertToString(); }

	/**
	 * Sets the value of the parameter to the given value
	 *
	 * @param a_value the value to set to
	 * @return true, if successful
	 */
	public abstract boolean setValue(String a_value);
}
