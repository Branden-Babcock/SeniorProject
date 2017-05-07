/*
 * Class: InstructionMove
 * Purpose: Implements an instruction to move a value from one location to another, supports input, output, registers, and constants
 */

package edu.ramapo.bbabcock.seniorProject;

import java.util.Vector;

public class InstructionMove extends Instruction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.Instruction#getDefaultParams()
	 */
	public Parameter[] getDefaultParams() {

		// Create parameters for where to move from and to
		return new Parameter[]{
				new ParameterRegister("from", true, false, Integer.MIN_VALUE, Integer.MAX_VALUE),
				new ParameterRegister("to", false, true)
		};
	}

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.Instruction#execute(edu.ramapo.bbabcock.seniorProject.Parameter[], int, java.util.Vector)
	 */
	public int execute(Parameter[] a_params, int a_instruction, Vector<Code> a_code){
		// Set the value of the second parameter to the value of the first parameter
		Configuration.setParameterValue(a_params[1], Configuration.getParameterValue(a_params[0]));

		// Return that the next instruction should be run
		return a_instruction+1;
	}

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.Instruction#getName()
	 */
	public String getName(){
		return "Move";
	}
}
