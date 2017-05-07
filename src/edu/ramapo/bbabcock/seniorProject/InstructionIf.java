/*
 * Class: InstructionIf
 * Purpose: Implements an instruction to advance the code to the next statement only if a statement is true. It is a super class for specific implementations. If the statement is not true, code advances to the matching endIf statement
 */

package edu.ramapo.bbabcock.seniorProject;

import java.util.Vector;

import javax.swing.JOptionPane;

public abstract class InstructionIf extends Instruction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Find the matching end if statement to the code at the given instruction index
	 *
	 * @param a_code the a code
	 * @param a_instruction the a instruction
	 * @return the int
	 */
	protected static int findMatchingEndIf(Vector<Code> a_code, int a_instruction){

		// Keep a count of how many additional if statements have been found without matching end if statements
		int count = 0;

		// Loop through each additional instruction in the code base
		for(int i = a_instruction+1; i<a_code.size(); i++){

			// Increase the count if it is an if statement, and decrease the count if it is an end if statement
			Code c = a_code.get(i);
			if(c.getInstruction() instanceof InstructionIf) count++;
			else if(c.getInstruction() instanceof InstructionEndIf) count--;

			// Once the count is negative, we have found the matching end if statement, so return this index
			if(count < 0) return i;
		}

		// If no matching statement is found, display an error
		JOptionPane.showMessageDialog(Configuration.frame, "Could not find matching EndIf statement for instruction " + a_instruction, "Error", JOptionPane.ERROR_MESSAGE);

		return -1;
	}
}
