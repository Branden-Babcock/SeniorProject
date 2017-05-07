/*
 * Class: InstructionAdd
 * Purpose: Implements an instruction to add two numbers or registers together, and put the results into another register
 */

package edu.ramapo.bbabcock.seniorProject;

import java.util.Vector;

public class InstructionAdd extends Instruction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.Instruction#getDefaultParams()
	 */
	public Parameter[] getDefaultParams() {

		// Generate parameters for the two components of the sum and the result location
		return new Parameter[]{
				new ParameterRegister("", true, false, Integer.MIN_VALUE, Integer.MAX_VALUE),
				new ParameterRegister("+", true, false, Integer.MIN_VALUE, Integer.MAX_VALUE),
				new ParameterRegister("into", false, true)
		};
	}

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.Instruction#execute(edu.ramapo.bbabcock.seniorProject.Parameter[], int, java.util.Vector)
	 */
	public int execute(Parameter[] a_params, int a_instruction, Vector<Code> a_code){

		// Add the value of the first two parameters together and store them in the location indicated by the third parameter
		Configuration.setParameterValue(a_params[2], Configuration.getParameterValue(a_params[0]) + Configuration.getParameterValue(a_params[1]));

		// Return the the next instruction should be run
		return a_instruction+1;
	}

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.Instruction#getName()
	 */
	public String getName(){ return "Add"; }

}
