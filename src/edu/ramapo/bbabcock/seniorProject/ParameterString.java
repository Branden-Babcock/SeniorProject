/*
 * Class: ParameterString
 * Purpose: Implements a parameter that accepts a string value and can be edited
 */

package edu.ramapo.bbabcock.seniorProject;

import java.awt.event.KeyEvent;

public class ParameterString extends Parameter {

	/** The current value of the parameter */
	private String m_value = "";

	/** The name of the parameter */
	private String m_name = "";

	/**
	 * Instantiates a new parameter with the given name
	 *
	 * @param a_name the name of the parameter
	 */
	public ParameterString(String a_name){
		this.m_name = a_name;
	}

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.Parameter#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public boolean keyPressed(KeyEvent a_event){

		// Invalidate the emulator since the value of a parameter changed
		Configuration.emulator.invalidate();

		// Remove the last character if the back-space key is pressed
		if(a_event.getKeyCode() == KeyEvent.VK_BACK_SPACE) m_value = m_value.substring(0, m_value.length()-1);

		// Attempt to add the character to the end of the value if possible
		else if(a_event.getKeyChar() >= 'a' && a_event.getKeyChar() <= 'z') m_value += a_event.getKeyChar();

		// If none of these were possible, return false
		else return false;

		return true;
	}

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.Parameter#setValue(java.lang.String)
	 */
	@Override
	public boolean setValue(String a_value) {

		// Remove the quotes around the value if they exist
		if(a_value.startsWith("\"") && a_value.endsWith("\"")) a_value = a_value.substring(1, a_value.length()-1);

		// Store the value and return
		this.m_value = a_value;
		return true;
	}

	/**
	 * Gets the current value of the parameter
	 *
	 * @return the value of the parameter
	 */
	public String getValue(){ return " " + m_value; }

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.Parameter#convertToString()
	 */
	public String convertToString(){ return "\"" + m_value + "\""; }

	/**
	 * Gets the name of the parameter
	 *
	 * @return the name of the parameter
	 */
	public String getName(){ return m_name; }
}
