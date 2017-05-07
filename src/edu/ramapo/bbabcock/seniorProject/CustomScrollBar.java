/*
 * Class: CustomScrollBar
 * Purpose: Creates a custom scrollbar to match the colorscheme of the program
 */

package edu.ramapo.bbabcock.seniorProject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class CustomScrollBar extends BasicScrollBarUI{

	/** The background color of the scrollbar. */
	protected Color m_background;

	/** The thumb color of the scrollbar */
	protected Color m_foreground;

	/** Indicates whether the scrollbar is vertical (true) or horrizontal (false) */
	protected boolean m_vertical;

	/**
	 * Instantiates a new custom scroll bar.
	 *
	 * @param a_background the background color for the scrollbar
	 * @param a_foreground the foreground color for the scrollbar
	 * @param a_vertical the verticality setting for the scrollbar
	 */
	public CustomScrollBar(Color a_background, Color a_foreground, boolean a_vertical){
		// Store the passed in value
		this.m_background = a_background;
		this.m_foreground = a_foreground;
		this.m_vertical = a_vertical;
	}

	/* (non-Javadoc)
	 *
	 * Handles drawing the track to the screen
	 *
	 * @see javax.swing.plaf.basic.BasicScrollBarUI#paintTrack(java.awt.Graphics, javax.swing.JComponent, java.awt.Rectangle)
	 */
	protected void paintTrack(Graphics a_graphics, JComponent a_c, Rectangle a_trackBounds){
		// Draw the track
		a_graphics.setColor(m_background);
		a_graphics.fillRect(a_trackBounds.x, a_trackBounds.y, a_trackBounds.width, a_trackBounds.height);
	}

	/* (non-Javadoc)
	 *
	 * Handles the drawing of the thumb (slider) to the screen
	 *
	 * @see javax.swing.plaf.basic.BasicScrollBarUI#paintThumb(java.awt.Graphics, javax.swing.JComponent, java.awt.Rectangle)
	 */
	protected void paintThumb(Graphics a_graphics, JComponent a_c, Rectangle a_thumbBounds){
		// Draw the thumb
		a_graphics.setColor(m_foreground);
		a_graphics.fillRect(a_thumbBounds.x + (m_vertical ? a_thumbBounds.width/4 : 0), a_thumbBounds.y + ( m_vertical ? 0 : a_thumbBounds.height/4), a_thumbBounds.width / (m_vertical ? 2 : 1), a_thumbBounds.height / (m_vertical ? 1 : 2));
	}

	/**
	 * Creates an empty button to replace the slider moving buttons
	 *
	 * @return the empty button created
	 */
	protected JButton createZeroButton() {
		// Create an empty button
	    JButton button = new JButton();
	    Dimension dim = new Dimension(0,0);
	    button.setPreferredSize(dim);
	    button.setMinimumSize(dim);
	    button.setMaximumSize(dim);
	    return button;
	}

	/* (non-Javadoc)
	 *
	 * Replaces the decrease button with an empty button
	 *
	 * @see javax.swing.plaf.basic.BasicScrollBarUI#createDecreaseButton(int)
	 */
	protected JButton createDecreaseButton(int a_orientation) {
		// Set the decreasing button to the zero button
	    return createZeroButton();
	}

	/* (non-Javadoc)
	 *
	 * Replaces the increase button with an empty button
	 *
	 * @see javax.swing.plaf.basic.BasicScrollBarUI#createIncreaseButton(int)
	 */
	protected JButton createIncreaseButton(int a_orientation) {
		// Set the increasing button to the zero button
	    return createZeroButton();
	}
}