/*
 * Class: InstructionIfLess
 * Purpose: Implements an if statement that checks if one value is less than another, supports constants and registers
 */

package edu.ramapo.bbabcock.seniorProject;

import java.util.Vector;

public class InstructionIfLess extends InstructionIf {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.Instruction#getDefaultParams()
	 */
	public Parameter[] getDefaultParams() {

		// Add parameters for the two values that should be compared
		return new Parameter[]{
				new ParameterRegister("", true, false, Integer.MIN_VALUE, Integer.MAX_VALUE),
				new ParameterRegister("lessThan", true, false, Integer.MIN_VALUE, Integer.MAX_VALUE)
		};
	}

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.Instruction#execute(edu.ramapo.bbabcock.seniorProject.Parameter[], int, java.util.Vector)
	 */
	public int execute(Parameter[] a_params, int a_instruction, Vector<Code> a_code) {
		// Compare the values indicated by the parameters, and return that the next instruction should be run if the first is less than the second
		if(Configuration.getParameterValue(a_params[0]) < Configuration.getParameterValue(a_params[1])) return a_instruction+1;

		// Otherwise, find the matching end if and return that the following statement should be run
		return findMatchingEndIf(a_code, a_instruction)+1;
	}

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.Instruction#getName()
	 */
	public String getName() { return "IfLess"; }

}
