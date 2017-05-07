/*
 * Class: Code
 * Purpose: Represents code which calls an instruction with set parameters, contains panels for setting these parameters that is displayed when the Code button is pressed
 */

package edu.ramapo.bbabcock.seniorProject;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Code extends JButton implements ActionListener{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The unselected border. */
	private static Border m_unselectedBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);

	/** The selected border. */
	private static Border m_selectedBorder = BorderFactory.createCompoundBorder(m_unselectedBorder, BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.PINK), BorderFactory.createEmptyBorder(5, 5, 5, 5)));

	/** The instruction. */
	private Instruction m_instruction;

	/** The params. */
	private Parameter[] m_params;

	/** The selected panel. */
	private KPanel m_selectedPanel;

	/** Indicates whether or not the code is currently valid */
	private boolean m_valid = true;

	/**
	 * Instantiates a new code object referencing the given instruction.
	 *
	 * @param a_instruction the instruction to be used in the new piece of code
	 */
	public Code(Instruction a_instruction){
		// Set up the visual aspects of the code button
		setForeground(Configuration.FOREGROUND_COLOR);
		setBackground(Configuration.BACKGROUND_COLOR);
		setFont(Configuration.BUTTON_FONT);
		setFocusPainted(false);
		setBorder(m_unselectedBorder);

		// Store the instruction that was passed in
		this.m_instruction = a_instruction;

		// Create a panel to show when this code is selected
		m_selectedPanel = new KPanel();

		// Store the default parameters of the passed in instruction
		Parameter[] defaultParams = a_instruction.getDefaultParams();

		// Create a layout for the parameters of this code
		m_selectedPanel.setLayout(new GridLayout(Configuration.MAX_PARAMS_ROWS, 0));

		// Create a place to store the parameters of the code
		m_params = new Parameter[defaultParams == null ? 0 : defaultParams.length];
		for(int i=0; i<m_params.length; i++){
			m_params[i] = defaultParams[i];
			ParamButton button = new ParamButton(m_params, i);
			m_selectedPanel.add(button);
		}


		// Center the code in the panel
		setAlignmentX(Component.CENTER_ALIGNMENT);

		// Add a listener to respond to click events
		this.addActionListener(this);
	}

	/**
	 * Instantiates a new code object, taking the instruction and parameter values from the provided string.
	 *
	 * @param a_code the a code
	 */
	public Code(String a_code){
		// Find the instruction indicated by the first word of the code
		this(Configuration.instructionPanel.getInstruction(a_code.substring(0, a_code.indexOf(' '))));

		// Loop through the values of the parameters and set their values
		String[] parts = a_code.split("\\s");
		for(int i=2; i<parts.length; i+=2){
			if(!m_params[i/2-1].setValue(parts[i])) m_valid = false;
		}
	}

	/**
	 * Updates the text currently displayed on the button to represent the current parameter settings
	 */
	public void updateText(){
		// Add an arrow to the beginning of the text if it is the next instruction to be executed
		String text = Configuration.emulator.getNextInstruction() == this ? "-> " : "";
		// Add the name of the instruction to the text
		text += m_instruction.getName();
		// Add the parameters to the text
		for(Parameter p : m_params){
			text += " " + p;
		}
		// Ensure the spaces are only a single space wide
		text = text.trim().replaceAll("  ", " ");
		// Set the text of the button to the found text
		setText(text);
	}

	/* (non-Javadoc)
	 *
	 * Handles the button press and selects this code as the currently selected code, allowing the parameters to be modified
	 *
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent a_e) {
		Configuration.codePanel.setSelected(this);
	}

	/**
	 * Selects this code, creating a border indicating such and allowing the parameters to be edited
	 *
	 * @param a_panel the a panel
	 */
	public void select(JPanel a_panel){
		// Set the border of the panel and add the selected panel to be displayed
		setBorder(m_selectedBorder);
		a_panel.add(m_selectedPanel);
	}

	/**
	 * Deselects this code, removing the border and the panel for editing the parameters
	 *
	 * @param a_panel the a panel
	 */
	public void deselect(JPanel a_panel){
		// Remove the border of the panel and add the selected panel to be displayed
		setBorder(m_unselectedBorder);
		a_panel.remove(m_selectedPanel);
	}

	/**
	 * Returns the parameters currently set by this code
	 *
	 * @return the parameters
	 */
	public Parameter[] getParams(){
		return m_params;
	}

	/**
	 * Executes this code with the current parameters
	 *
	 * @param a_index The current index of execution
	 * @param a_code The code to use as reference for goto and label statements
	 * @return The index of the next instruction
	 */
	public int execute(int a_index, Vector<Code> a_code){
		// Run the code with the current parameters
		return m_instruction.execute(getParams(), a_index, a_code);
	}

	/**
	 * Gets the parameter at the given index
	 *
	 * @param a_index the index
	 * @return the parameter at the given index, or null if the index was invalid
	 */
	public Parameter getParameter(int a_index){
		// Return the parameter at the indicated location, or null if it is invalid
		return (a_index >= m_params.length || a_index < 0) ? null : m_params[a_index];
	}

	/* (non-Javadoc)
	 *
	 * Handles the conversion of this object to a string, which lists the name of the instruction and all parameter values
	 *
	 * @see java.awt.Component#toString()
	 */
	public String toString(){
		// Construct and return the string representation of the code
		String ret = m_instruction.getName();

		for(Parameter p : m_params){
			ret += " " + p;
		}

		return ret;
	}

	/**
	 * Gets the number registers in use by this piece of code
	 *
	 * @return the number of registers
	 */
	public int getNumRegisters(){

		// If the instruction is custom, return the number of parameters used by that instruction
		if(m_instruction instanceof InstructionCustom){
			return ((InstructionCustom)m_instruction).getNumRegisters();
		}

		// Otherwise, search through the parameters and find the last parameter used, and return that value
		int ret = 0;
		for(Parameter p : m_params){
			if(p instanceof ParameterRegister && ((ParameterRegister)p).isRegister()){
				ret = Math.max(ret, ((ParameterRegister) p).getValue()+1);
			}
		}

		return ret;
	}

	/**
	 * Gets the instruction in use by this code.
	 *
	 * @return The instruction
	 */
	public Instruction getInstruction(){
		return m_instruction;
	}

	/* (non-Javadoc)
	 *
	 * Returns whether or not the current code is valid
	 *
	 * @see java.awt.Component#isValid()
	 */
	public boolean isValid(){ return m_valid; }
}
