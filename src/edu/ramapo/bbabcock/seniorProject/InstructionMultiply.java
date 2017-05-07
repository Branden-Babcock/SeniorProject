/*
 * Class: InstructionMultiply
 * Purpose: Implements an instruction to multiply two numbers together and move the result into another location
 */

package edu.ramapo.bbabcock.seniorProject;

import java.util.Vector;

public class InstructionMultiply extends Instruction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.Instruction#getDefaultParams()
	 */
	public Parameter[] getDefaultParams() {

		// Create parameters for which two values to multiply and where to move the result
		return new Parameter[]{
				new ParameterRegister("", true, false, Integer.MIN_VALUE, Integer.MAX_VALUE),
				new ParameterRegister("*", true, false, Integer.MIN_VALUE, Integer.MAX_VALUE),
				new ParameterRegister("into", false, true)
		};
	}

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.Instruction#execute(edu.ramapo.bbabcock.seniorProject.Parameter[], int, java.util.Vector)
	 */
	public int execute(Parameter[] a_params, int a_instruction, Vector<Code> a_code){
		// Multiply the values of the first and second parameters, and place the result in the third
		Configuration.setParameterValue(a_params[2], Configuration.getParameterValue(a_params[0]) * Configuration.getParameterValue(a_params[1]));

		// Return that the next instruction should be run
		return a_instruction+1;
	}

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.Instruction#getName()
	 */
	public String getName(){ return "Multiply"; }

}
