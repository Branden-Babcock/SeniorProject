/*
 * Class: Data
 * Purpose: Stores an piece of data displayed as an inactive button
 */

package edu.ramapo.bbabcock.seniorProject;

import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

public class Data extends JButton{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4116784690904946171L;

	/** The object representing the data for the button. */
	private Object m_data;

	/**
	 * Instantiates a new data object with the given data
	 *
	 * @param a_value the value of the data
	 */
	public Data(Object a_value){
		// If we cannot set using the passed in value, throw an exception
		if(!set(a_value))
			throw new IllegalArgumentException("Invalid value " + a_value);

		// Set the appearance of the button
		setEnabled(false);
		setBackground(Configuration.BACKGROUND_COLOR);
		setForeground(Configuration.FOREGROUND_COLOR);
		setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(0,0,0,0), new EtchedBorder()));
		setFont(Configuration.BUTTON_FONT);
	}

	/**
	 * Gets the current data
	 *
	 * @return the data stored
	 */
	public Object get(){
		return m_data;
	}

	/**
	 * Sets the current data
	 *
	 * @param a_value the data to set to
	 * @return true, if successful (Always)
	 */
	public boolean set(Object a_value){
		m_data = a_value;

		return true;
	}

	/* (non-Javadoc)
	 *
	 * Handles drawing the data to the screen, primarily implemented to update the displayed data each time
	 *
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	public void paint(Graphics a_graphics){

		// Update the text
		setText(m_data + "");
		super.paint(a_graphics);
	}
}
