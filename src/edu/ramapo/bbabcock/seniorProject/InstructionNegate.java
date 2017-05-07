/*
 * Class: InstructionNegate
 * Purpose: Implements an instruction to set a value equal to its inverse, and place the result in another location
 */

package edu.ramapo.bbabcock.seniorProject;

import java.util.Vector;

public class InstructionNegate extends Instruction{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.Instruction#getDefaultParams()
	 */
	public Parameter[] getDefaultParams() {
		// Create parameters for which value to negate and where to place the result
		return new Parameter[]{
				new ParameterRegister("", true, false, Integer.MIN_VALUE, Integer.MAX_VALUE),
				new ParameterRegister("into", false, true)
		};
	}

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.Instruction#execute(edu.ramapo.bbabcock.seniorProject.Parameter[], int, java.util.Vector)
	 */
	public int execute(Parameter[] a_params, int a_instruction, Vector<Code> a_code){
		// Negate the value of the first parameter, and place the result in the second parameter
		Configuration.setParameterValue(a_params[1], -1*Configuration.getParameterValue(a_params[0]));

		return a_instruction+1;
	}

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.Instruction#getName()
	 */
	public String getName(){ return "Negate"; }
}
