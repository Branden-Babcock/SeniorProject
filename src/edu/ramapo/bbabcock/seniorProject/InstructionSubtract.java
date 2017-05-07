/*
 * Class: InstructionSubtract
 * Purpose: Implements an instruction to subtracts one number from another and places the result in another location
 */

package edu.ramapo.bbabcock.seniorProject;

import java.util.Vector;

public class InstructionSubtract extends Instruction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.Instruction#getDefaultParams()
	 */
	public Parameter[] getDefaultParams() {
		// Create parameters for which value to subtract from, subtract, and move the result into
		return new Parameter[]{
				new ParameterRegister("", true, false, Integer.MIN_VALUE, Integer.MAX_VALUE),
				new ParameterRegister("-", true, false, Integer.MIN_VALUE, Integer.MAX_VALUE),
				new ParameterRegister("into", false, true)
		};
	}

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.Instruction#execute(edu.ramapo.bbabcock.seniorProject.Parameter[], int, java.util.Vector)
	 */
	public int execute(Parameter[] a_params, int a_instruction, Vector<Code> a_code){
		// Subtract the value of the second parameter from the value of the first and put the result in the third
		Configuration.setParameterValue(a_params[2], Configuration.getParameterValue(a_params[0]) - Configuration.getParameterValue(a_params[1]));

		// Return that the next instruction should be executed
		return a_instruction+1;
	}

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.Instruction#getName()
	 */
	public String getName(){ return "Subtract"; }
}
