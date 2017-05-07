/*
 * Class: InstructionIfEqual
 * Purpose: Implements an if statement that checks if two values are equal, handles registers and constants
 */

package edu.ramapo.bbabcock.seniorProject;

import java.util.Vector;

public class InstructionIfEqual extends InstructionIf {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.Instruction#getDefaultParams()
	 */
	public Parameter[] getDefaultParams() {

		// Add parameters for the two values which should be compared
		return new Parameter[]{
				new ParameterRegister("", false, false, Integer.MIN_VALUE, Integer.MAX_VALUE),
				new ParameterRegister("and", false, false, Integer.MIN_VALUE, Integer.MAX_VALUE)
		};
	}

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.Instruction#execute(edu.ramapo.bbabcock.seniorProject.Parameter[], int, java.util.Vector)
	 */
	public int execute(Parameter[] a_params, int a_instruction, Vector<Code> a_code) {
		// Test if the two parameter values are equal and return that the next statement should be run if they are
		if(Configuration.getParameterValue(a_params[0]) == Configuration.getParameterValue(a_params[1])) return a_instruction+1;

		// Otherwise find the matching end if statement and indicate that the statement following it should be run
		return findMatchingEndIf(a_code, a_instruction)+1;
	}

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.Instruction#getName()
	 */
	public String getName() { return "IfEqual"; }

}
