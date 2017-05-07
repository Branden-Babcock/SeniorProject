/*
 * Class: ParamButton
 * Purpose: Creates a button that allows a parameter to be modified for a given piece of code
 */

package edu.ramapo.bbabcock.seniorProject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class ParamButton extends JButton {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The parameters for the button to control editing of */
	private Parameter[] m_params;

	/** The index of the controlled parameter */
	private int m_index;

	/**
	 * Instantiates a new parameter button.
	 *
	 * @param a_params The parameters to control
	 * @param a_index the index of the specific parameter this button edits
	 */
	public ParamButton(Parameter[] a_params, int a_index){
		this.m_params = a_params;
		this.m_index = a_index;

		// Set the appearance of the button
		setBackground(Configuration.BACKGROUND_COLOR);
		setForeground(Configuration.FOREGROUND_COLOR);
		setFont(Configuration.BUTTON_FONT);
		setBorder(BorderFactory.createEmptyBorder());
		setFocusable(true);

		// Update the text of the button
		updateText();

		// Create click and keyboard listener for this button
		ButtonListener buttonListener = new ButtonListener();
		addKeyListener(buttonListener);
		addActionListener(buttonListener);
	}

	/**
	 * The listener interface for receiving button events.
	 * The class that is interested in processing a button
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addButtonListener<code> method. When
	 * the button event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see ButtonEvent
	 */
	private class ButtonListener  extends KeyAdapter implements ActionListener{

		/* (non-Javadoc)
		 *
		 * Causes the pressed button to request focus from the keyboard
		 *
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent a_event){
			// Request keyboard focus when this button is clicked
			((JButton)a_event.getSource()).requestFocus();
		}

		/* (non-Javadoc)
		 *
		 * If this button can accept the keyboard input, update it, otherwise play an error sound
		 *
		 * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
		 */
		public void keyPressed(KeyEvent a_event){
			// Get the button this event belongs to and attempt to perform the keypress on it
			ParamButton outer = ParamButton.this;
			if(outer.m_params[outer.m_index].keyPressed(a_event)) updateText();
			else Configuration.playErrorSound();
		}
	}

	/**
	 * Update the text on the button to represent the current value of the parameter
	 */
	public void updateText(){
		// Set the text on the button to the string representation of the parameter
		setText(m_params[m_index].toString());
		// Update the text of the selected code
		Code s = Configuration.codePanel.getSelected();
		if(s != null) s.updateText();
	}
}
