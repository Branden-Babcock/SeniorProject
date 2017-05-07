/*
 * Class: ButtonStep
 * Purpose: Represents a button which, when pressed, causes the emulator to advance one step in program execution
 */

package edu.ramapo.bbabcock.seniorProject;

import java.awt.event.ActionEvent;

public class ButtonStep extends KButton {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new button step.
	 */
	public ButtonStep(){ super("Step"); }

	/* (non-Javadoc)
	 *
	 * Handles the button press and causes the emulator to advance to the next instruction
	 *
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent a_event) {
		// Advance the emulator one step and render it
		if(!Configuration.emulator.step(true)){
			// Invalidate the emulator since it is in progress
			Configuration.emulator.invalidate();
		}
	}

}
