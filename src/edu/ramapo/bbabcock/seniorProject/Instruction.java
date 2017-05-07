/*
 * Class: Instruction
 * Purpose:  A super class to handle a general instruction. Provides on click functionality and parameter functionality
 */

package edu.ramapo.bbabcock.seniorProject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public abstract class Instruction extends JButton{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new instruction
	 */
	public Instruction(){

		// Set the button to perform the on click function when clicked
		addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a_event) {
				onClick();
			}
		});

		// Set the appearance of this button
		setBackground(Configuration.BACKGROUND_COLOR);
		setForeground(Configuration.FOREGROUND_COLOR);
		setFont(Configuration.BUTTON_FONT);
		setBorder(BorderFactory.createEmptyBorder());
		setFocusPainted(false);
		setText(getName());
	}

	/**
	 * On a button press, a code indicated by this instruction is added to the code panel
	 */
	protected final void onClick(){

		// On click, add the code to the code panel
		if(Configuration.codePanel != null){
			Code c = new Code(this);
			Configuration.codePanel.addCode(c);
			c.updateText();
		}
	}

	/* (non-Javadoc)
	 *
	 * Returns the name of the instruction
	 *
	 * @see java.awt.Component#getName()
	 */
	public abstract String getName();

	/**
	 * Gets the default parameters for the implementing instruction
	 *
	 * @return the default params
	 */
	public abstract Parameter[] getDefaultParams();

	/**
	 * Executes the instruction with the given parameters, at the given index, in the given code base
	 *
	 * @param a_params The parameters for the instruction
	 * @param a_instruction the index of the instruction in the given code base
	 * @param a_code the code base to reference from within the instruction
	 * @return the next instruction in the code base to execute
	 */
	public abstract int execute(Parameter[] a_params, int a_instruction, Vector<Code> a_code);
}
