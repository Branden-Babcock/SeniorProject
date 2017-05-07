/*
 * Class: ButtonSubmit
 * Purpose: Represents a button which, when pressed, causes the emulator to compare code generated outputs to correct outputs
 */

package edu.ramapo.bbabcock.seniorProject;

import java.awt.event.ActionEvent;

public class ButtonSubmit extends KButton {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

/**
 * Instantiates a new button submit.
 */
public ButtonSubmit(){ super("Submit"); }

	/* (non-Javadoc)
	 *
	 * Handles the button press and compares the current code's output to the expected output provided by the current challenge
	 *
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent a_event) {
		// Submit the current code as a possible solution to the current challenge
		Configuration.emulator.submit();
		// Validate the emulator since it will be reset once the submission is completed
		Configuration.emulator.validate();
	}

}
