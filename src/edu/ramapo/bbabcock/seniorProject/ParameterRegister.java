/*
 * Class: ParameterRegister
 * Purpose: Implements a paramter that indicates a register must be selected, but allows for numbers to be optionally selected
 */

package edu.ramapo.bbabcock.seniorProject;

import java.awt.event.KeyEvent;

public class ParameterRegister extends ParameterNumber {

	/** The lowest numeric value accepted by the parameter */
	private Integer m_lowValue;

	/** The largest numeric value accepted by the parameter */
	private Integer m_highValue;

	/** The current state of the parameter (register, number, input, or output) */
	private int m_state;

	/** Indicates whether or not the input setting is allowed */
	private boolean m_inputAllowed;

	/** Indicates whether or not the output setting is allowed */
	private boolean m_outputAllowed;

	/** The possible states for this parameter */
	private static final int
		STATE_REGISTER = 0,
		STATE_NUMBER = 1,
		STATE_INPUT = 2,
		STATE_OUTPUT = 3
	;

	/**
	 * Instantiates a new parameter that accepts registers with the given parameters
	 *
	 * @param name the name of the parameter
	 * @param a_inputAllowed whether or not input is allowed
	 * @param a_outputAllowed whether or not output is allowed
	 * @param a_low the lowest integer value that is allowed, or null if none are allowed
	 * @param a_high the largest integer value that is allowed, or null if none are allowed
	 */
	public ParameterRegister(String name, boolean a_inputAllowed, boolean a_outputAllowed, Integer a_low, Integer a_high){

		// Initialize a basic parameter with the passed in values
		super(name, 0, Configuration.registers.size()-1, 0);

		// Store the additional passed in values
		m_lowValue = a_low;
		m_highValue = a_high;
		this.m_inputAllowed = a_inputAllowed;
		this.m_outputAllowed = a_outputAllowed;

		// Set to store a register initially
		m_state = STATE_REGISTER;

		// Attempt to set the value to each of the possible types
		if(!(setValue("R0") || setValue("0") || setValue("INPUT") || setValue("OUTPUT"))){
			System.err.println("No valid values found for parameter");
		}
	}

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.ParameterNumber#convertToString()
	 */
	public String convertToString(){
		// Return a string representation of the current value of the parameter
		if(m_state == STATE_INPUT) return getName() + " INPUT";
		if(m_state == STATE_OUTPUT) return getName() + " OUTPUT";
		return getName() + " " + (m_state == 0 ? "R" : "") + getValue();
	}

	/* (non-Javadoc)
	 *
	 * Changes the state in accordance to the key pressed
	 *
	 * @see edu.ramapo.bbabcock.seniorProject.ParameterNumber#keyPressed(java.awt.event.KeyEvent)
	 */
	public boolean keyPressed(KeyEvent a_event){

		// Invalidate the emulator since a parameter value has changed
		Configuration.emulator.invalidate();

		// Attempt to switch to input if the i key is pressed
		if(a_event.getKeyCode() == KeyEvent.VK_I){
			return setValue("INPUT");
		}

		// Attempt to switch to output if the o key is pressed
		if(a_event.getKeyCode() == KeyEvent.VK_O){
			return setValue("OUTPUT");
		}

		// Attempt to switch to a register if the r key is pressed
		if(a_event.getKeyCode() == KeyEvent.VK_R){
			return setValue("R" + m_value);
		}

		// Attempt to switch to a number if the n key is pressed
		if(a_event.getKeyCode() == KeyEvent.VK_N){
			return setValue(m_value + "");
		}

		return super.keyPressed(a_event);
	}

	/**
	 * Instantiates a a new register that accepts registers, but not numbers
	 *
	 * @param a_name the name of the registers
	 * @param a_inputAllowed whether or not input is allowed for this register
	 * @param a_outputAllowed whether or not output is allowed for this register
	 */
	public ParameterRegister(String a_name, boolean a_inputAllowed, boolean a_outputAllowed){
		this(a_name, a_inputAllowed, a_outputAllowed, null, null);
	}

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.ParameterNumber#setValue(java.lang.String)
	 */
	public boolean setValue(String a_value){

		// Attempt to set to a register if the value starts with an r
		if(a_value.startsWith("R")){
			if(Configuration.registers.size() == 0) return false;
			setMinimumValue(0);
			setMaximumValue(Configuration.registers.size()-1);
			m_state = STATE_REGISTER;
			int numValue = Integer.parseInt(a_value.substring(1));
			if(!isValid(numValue)) return false;
			setValue(numValue);

		// Attempt to set to input if the value is input
		}else if(a_value.equalsIgnoreCase("INPUT")){
			if(!m_inputAllowed) return false;
			m_state = STATE_INPUT;

		// Attempt to set to output if the value is output
		}else if(a_value.equalsIgnoreCase("OUTPUT")){
			if(!m_outputAllowed) return false;
			m_state = STATE_OUTPUT;

		// Attempt to treat the value as a number
		}else{
			if(m_lowValue == null || m_highValue == null) return false;
			setMinimumValue(m_lowValue);
			setMaximumValue(m_highValue);
			m_state = STATE_NUMBER;
			if(!super.setValue(a_value)) return false;
		}

		return true;
	}

	/**
	 * Checks if this parameter is currently in the register state
	 *
	 * @return true, if the state is register
	 */
	public boolean isRegister(){ return m_state == STATE_REGISTER; }

	/**
	 * Checks if this parameter is currently in the number state
	 *
	 * @return true, if the state is number
	 */
	public boolean isNumber(){ return m_state == STATE_NUMBER; }

	/**
	 * Checks if this parameter is currently in the input state
	 *
	 * @return true, if the state is input
	 */
	public boolean isInput(){ return m_state == STATE_INPUT; }

	/**
	 * Checks if this parameter is currently in the output state
	 *
	 * @return true, if the state is output
	 */
	public boolean isOutput(){ return m_state == STATE_OUTPUT; }

}
