/*
 * Class: ButtonSaveInstruction
 * Purpose: Represents a button which, when pressed, causes the emulator to run save the current code area as a stand-alone instruction
 */

package edu.ramapo.bbabcock.seniorProject;

import java.awt.event.ActionEvent;

public class ButtonSaveInstruction extends KButton {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new button save instruction.
	 */
	public ButtonSaveInstruction(){ super("Save Instruction"); }

	/* (non-Javadoc)
	 *
	 * Handles the clicking of the button and causes the current code set to be saved as an external instruction
	 *
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent a_event) {
		// Save the current code as an instruction and add that instruction to the instruction panel
		Configuration.instructionPanel.addInstruction(Configuration.codePanel.saveInstruction());
	}

}
