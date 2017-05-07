/*
 * Class: InstructionEndIf
 * Purpose: Implements an instruction to terminate a previous if statement. The instruction does not perform anything on its own.
 */

package edu.ramapo.bbabcock.seniorProject;

import java.util.Vector;

public class InstructionEndIf extends Instruction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.Instruction#getName()
	 */
	public String getName() {
		return "EndIf";
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
		// Simply return that the next instruction should be executed, since endif performs no function
		return a_instruction+1;
	}

}
