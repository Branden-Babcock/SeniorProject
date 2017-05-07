/*
 * Class: InstructionLabel
 * Purpose: Implements an instruction to set a label at a given location. used for goto statements. Instruction does not perform any function on its own, and only the top-most label is used for a given name
 */

package edu.ramapo.bbabcock.seniorProject;

import java.util.Vector;

public class InstructionLabel extends Instruction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.Instruction#getName()
	 */
	public String getName() {
		return "Label";
	}

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.Instruction#getDefaultParams()
	 */
	public Parameter[] getDefaultParams() {
		// Create a single parameter for the name of the label
		return new Parameter[]{ new ParameterString("") };
	}

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.Instruction#execute(edu.ramapo.bbabcock.seniorProject.Parameter[], int, java.util.Vector)
	 */
	public int execute(Parameter[] a_params, int a_instruction, Vector<Code> a_code) {
		// Return that the next instruction should be run, since labels do not perform a function
		return a_instruction + 1;
	}

}
