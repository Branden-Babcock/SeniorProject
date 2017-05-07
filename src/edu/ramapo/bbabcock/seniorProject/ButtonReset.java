/*
 * Class: ButtonReset
 * Purpose: Represents a button which, when pressed, causes the emulator to reset all aspects of the current execution
 */

package edu.ramapo.bbabcock.seniorProject;

import java.awt.event.ActionEvent;

public class ButtonReset extends KButton {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new button reset.
	 */
	public ButtonReset(){ super("Reset"); }

	/* (non-Javadoc)
	 *
	 * Handles the button press and resets the current code execution
	 *
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent a_event) {
		// Reset the emulator using the first set of input
		Configuration.emulator.reset(0);
		// Set the emulator to be valid, since it has been reset
		Configuration.emulator.validate();
	}

}
