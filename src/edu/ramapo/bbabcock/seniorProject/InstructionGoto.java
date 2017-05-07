/*
 * Class: InstructionGoto
 * Purpose: Implements an instruction to advance execution to another point in the program, marked by a label. Can only access local code
 */

package edu.ramapo.bbabcock.seniorProject;

import java.util.Vector;

public class InstructionGoto extends Instruction{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.Instruction#getDefaultParams()
	 */
	public Parameter[] getDefaultParams() {
		// Create a single parameter for the name of the label the goto should go to
		return new Parameter[]{
				new ParameterString("")
		};
	}

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.Instruction#execute(edu.ramapo.bbabcock.seniorProject.Parameter[], int, java.util.Vector)
	 */
	public int execute(Parameter[] a_params, int a_instruction, Vector<Code> a_code){

		// Loop through the local code and find the label corresponding to the parameter and return that the following instruction should be run
		for(int i=0; i<a_code.size(); i++){
			Code c = a_code.get(i);
			if(c.getInstruction() instanceof InstructionLabel && ((ParameterString)c.getParameter(0)).getValue().equals(((ParameterString)a_params[0]).getValue())){
				return i+1;
			}
		}

		// If no label is found, simply run the next instruction
		return a_instruction+1;
	}

	/* (non-Javadoc)
	 * @see edu.ramapo.bbabcock.seniorProject.Instruction#getName()
	 */
	public String getName(){ return "Goto"; }

}
