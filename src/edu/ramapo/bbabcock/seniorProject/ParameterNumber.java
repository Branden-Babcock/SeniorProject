/*
 * Class: ParameterNumber
 * Purpose: Implements a parameter that represents a number, supports setting lower and upper limits, as well as a default value
 */

package edu.ramapo.bbabcock.seniorProject;

import java.awt.event.KeyEvent;

public class ParameterNumber extends Parameter {

	/** The current value of the parameter */
	protected int m_value;

	/** The name of the parameter */
	protected String m_name;

	/** The minimum value accepted by the parameter */
	protected int m_minimumValue;

	/** The maximum value acceptable by the parameter*/
	protected int m_maximumValue;

	/**
	 * Instantiates a new parameter that can be set to a numeric value
	 *
	 * @param a_name the name of the parameter
	 * @param a_minimumValue the minimum value of the parameter
	 * @param a_maximumValue the maximum value of the parameter
	 * @param a_defaultValue the default value of the parameter
	 */
	public ParameterNumber(String a_name, int a_minimumValue, int a_maximumValue, int a_defaultValue){
		// Store the passed in values
		this.m_name = a_name;
		this.m_minimumValue = a_minimumValue;
		this.m_maximumValue = a_maximumValue;
		setValue(a_defaultValue);
	}

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.Parameter#convertToString()
	 */
	public String convertToString(){
		// Append the value to the name to get the string representation
		return getName() + " " + getValue();
	}

	/* (non-Javadoc)
	 *
	 * Allows for keys 0-9 and delete to be pressed, and sets the value accordingly
	 *
	 * @see edu.ramapo.bbabcock.seniorProject.Parameter#keyPressed(java.awt.event.KeyEvent)
	 */
	public boolean keyPressed(KeyEvent a_event){

		// Invalidate the emulator since a parameter was changed
		Configuration.emulator.invalidate();

		// Get the value of the parameter currently
		int num = getValue();

		// Set the number to 0 if delete was pressed
		if(a_event.getKeyCode() == KeyEvent.VK_DELETE) num = 0;

		// Divide the number by 10 if backspace was pressed
		else if(a_event.getKeyCode() == KeyEvent.VK_BACK_SPACE) num /= 10;

		// Negate the number if - was pressed
		else if(a_event.getKeyCode() == KeyEvent.VK_MINUS) num = -getValue();

		// Attempt to add the key pressed to the end of the number
		else{
			if(a_event.getKeyChar() < '0' || a_event.getKeyChar() > '9') return false;

			int pressed = a_event.getKeyChar() - '0';
			if(num < 0) pressed *= -1;
			num *= 10;
			num += pressed;

			if(num < getValue()) return false;

			// If only single digit numbers are allowed, set the value to the pressed digit
			if(!isValid(num) && m_maximumValue < 10) num = pressed;
		}

		// Attempt to set the value of the parameter to the number, and return an error if it is invalid
		if(!isValid(num)){
			System.err.println(num + " is not a valid value for " + getName() + "(" + m_minimumValue + ":" + m_maximumValue + ")");
			return false;
		}

		// Set the value to the new number
		m_value = num;

		return true;
	}

	/**
	 * Gets the current value of the parameter
	 *
	 * @return the value of the parameter
	 */
	public int getValue(){ return m_value; }

	/**
	 * Gets the name of the parameter
	 *
	 * @return the name of the parameter
	 */
	public String getName(){ return m_name; }

	/**
	 * Checks if the number is a valid value for this parameter
	 *
	 * @param a_num the value to test
	 * @return true, if it is valid
	 */
	protected boolean isValid(int a_num){ return a_num >= m_minimumValue && a_num <= m_maximumValue; }

	/**
	 * Sets the minimum value of the parameter
	 *
	 * @param a_min the new minimum value
	 */
	protected void setMinimumValue(int a_min){ m_minimumValue = a_min; }

	/**
	 * Sets the maximum value of the parameter
	 *
	 * @param a_max the new maximum value
	 */
	protected void setMaximumValue(int a_max){ m_maximumValue = a_max; }

	/**
	 * Sets the value of the parameter, or the minimum value if the given value is invalid
	 *
	 * @param a_value the new value
	 */
	protected void setValue(int a_value){
		if(isValid(a_value)) this.m_value = a_value;
		else this.m_value = m_minimumValue;
	}

	/* (non-Javadoc)
	 *
	 * Attempts to parse the given number, but returns false if it cannot be done, or if the resulting number is invalid
	 *
	 * @see edu.ramapo.bbabcock.seniorProject.Parameter#setValue(java.lang.String)
	 */
	public boolean setValue(String a_value){

		// Attempt to parse the value into a number and set the value to that number
		try{
			int numValue = Integer.parseInt(a_value);
			if(!isValid(numValue)) return false;

			setValue(numValue);
		} catch(Exception ex){ return false; }

		return true;
	}

	/**
	 * Instantiates a new parameter that accepts numbers and sets to the minimum value automatically
	 *
	 * @param a_name the name of the parameter
	 * @param a_minimumValue the minimum value of the parameter
	 * @param a_maximumValue the maximum value of the parameter
	 */
	public ParameterNumber(String a_name, int a_minimumValue, int a_maximumValue){ this(a_name, a_minimumValue, a_maximumValue, a_minimumValue); }

	/**
	 * Instantiates a new parameter number that accepts any integer number
	 *
	 * @param a_name the name of the parameter
	 */
	public ParameterNumber(String a_name){ this(a_name, Integer.MIN_VALUE, Integer.MAX_VALUE, 0); }
}
