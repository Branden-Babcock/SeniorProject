/*
 * Class: KPanel
 * Purpose: Implements a panel that matches the color scheme of the application
 */

package edu.ramapo.bbabcock.seniorProject;

import javax.swing.JPanel;

public class KPanel extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new panel with the standard background color and border
	 */
	public KPanel(){

		// Set the appearance of the panel
		setBackground(Configuration.BACKGROUND_COLOR);
		setBorder(Configuration.STD_BORDER);
	}
}
