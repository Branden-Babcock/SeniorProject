/*
 * Class: InstructionIfInputEmpty
 * Purpose: Implements an if statement that checks if there is nothing left in input
 */

package edu.ramapo.bbabcock.seniorProject;

import java.util.Vector;

public class InstructionIfInputEmpty extends InstructionIf {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.Instruction#getName()
	 */
	public String getName() {
		return "IfInputEmpty";
	}

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.Instruction#getDefaultParams()
	 */
	public Parameter[] getDefaultParams() {
		// Return an empty parameter list, null works the same way
		return null;
	}

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.Instruction#execute(edu.ramapo.bbabcock.seniorProject.Parameter[], int, java.util.Vector)
	 */
	public int execute(Parameter[] a_params, int a_instruction, Vector<Code> a_code) {

		// Try to peek the next value, and return that the next instruction should be run if it is empty
		if(Configuration.input.peek() == null) return a_instruction+1;

		// Otherwise, find the matching end if statement and return that the statement following that one should be run
		return findMatchingEndIf(a_code, a_instruction)+1;
	}

}
