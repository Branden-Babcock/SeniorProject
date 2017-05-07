/*
 * Class: ButtonExecute
 * Purpose: Represents a button which, when pressed, causes the emulator to run through the duration of the program
 */

package edu.ramapo.bbabcock.seniorProject;

import java.awt.event.ActionEvent;

public class ButtonExecute extends KButton {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new button execute.
	 */
	public ButtonExecute(){ super("Execute"); }

	/* (non-Javadoc)
	 *
	 * Handles the clicking of the button and causes the emulator to execute the current code set
	 *
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent a_event) {
		// Tell the emulator to execute the current code, and make it visual
		Configuration.emulator.execute(true);
		// Invalidate the emulator since running is complete
		Configuration.emulator.invalidate();
	}

}
