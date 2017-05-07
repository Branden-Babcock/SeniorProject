/*
 * Class: InstructionCustom
 * Purpose: Implements an instruction to run code that was saved as a custom instruction
 */

package edu.ramapo.bbabcock.seniorProject;

import java.util.Vector;

public class InstructionCustom extends Instruction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The name of the custom instruction */
	private String m_name;

	/** The code that should run when the custom instruction runs */
	private Vector<Code> m_code;

	/**
	 * Instantiates a new instruction with the given name
	 *
	 * @param a_name the name of the instruction
	 */
	public InstructionCustom(String a_name){

		// Set up the instruction
		setName(a_name);
		m_code = new Vector<Code>();
		setText(a_name);
	}

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.Instruction#getName()
	 */
	public String getName() {
		return m_name;
	}

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.Instruction#getDefaultParams()
	 */
	public Parameter[] getDefaultParams() {
		// Return an empty parameter list
		return new Parameter[0];
	}

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.Instruction#execute(edu.ramapo.bbabcock.seniorProject.Parameter[], int, java.util.Vector)
	 */
	public int execute(Parameter[] a_params, int a_instruction, Vector<Code> a_code) {
		// Loop through each piece of code and execute it
		int i=0;
		while(i < m_code.size()){
			i = m_code.elementAt(i).execute(i, m_code);
		}

		// Return that the next instruction should be run, since custom instructions never change this
		return a_instruction+1;
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#setName(java.lang.String)
	 */
	public void setName(String a_name){ this.m_name = a_name; }

	/**
	 * Adds the code.
	 *
	 * @param a_c the a c
	 * @return true, if successful
	 */
	public boolean addCode(Code a_c){ return m_code.add(a_c); }

	/**
	 * Gets the number of registers used by the instruction
	 *
	 * @return the number of registers
	 */
	public int getNumRegisters(){

		// Loop through the contained code and determine the maximum number of registers used
		int ret = 0;
		for(Code c : m_code){
			ret = Math.max(ret, c.getNumRegisters());
		}
		return ret;
	}

	/**
	 * Adds all code for this instruction to the code panel
	 */
	public void edit(){

		// Add the code to the code panel if we need to edit the instruction
		for(Code c : m_code){
			Configuration.codePanel.addCode(c);
		}
	}

	/**
	 * Returns the code that this instruction uses to run
	 *
	 * @return the code this instruction runs
	 */
	public Vector<Code> getCode(){
		return m_code;
	}

}
