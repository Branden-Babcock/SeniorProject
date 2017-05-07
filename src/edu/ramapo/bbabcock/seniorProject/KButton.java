/*
 * Class: KButton
 * Purpose: Generates buttons which match the color scheme of the application
 */

package edu.ramapo.bbabcock.seniorProject;

import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public abstract class KButton extends JButton implements ActionListener{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new button with the given text
	 *
	 * @param a_text the a text
	 */
	public KButton(String a_text){
		// Create a button with the indicated text
		super(a_text);

		// Set the appearance of the button
		setForeground(Configuration.FOREGROUND_COLOR);
		setBackground(Configuration.BACKGROUND_COLOR);
		setBorder(BorderFactory.createEmptyBorder());
		setFont(Configuration.BUTTON_FONT);
		setFocusPainted(false);

		// Add this class as an action listener
		addActionListener(this);
	}
}
