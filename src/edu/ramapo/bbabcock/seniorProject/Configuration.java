/*
 * Class: Configuration
 * Purpose: Contains constants as well as program-wide functions for use throughout the program
 */

package edu.ramapo.bbabcock.seniorProject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

public class Configuration {

	/** The size of the buttons containing data */
	public static final int DATA_BUTTON_SIZE = 50;

	/** The framerate the application should run at */
	public static final int FRAMERATE = 30;

	/** The width of the screen */
	public static final int SCREEN_WIDTH = 1600;

	/** The height of the screen */
	public static final int SCREEN_HEIGHT = 900;

	/** The dimension representing the size of the screen */
	public static final Dimension SCREEN_SIZE = new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT);

	/** The font to be used by all buttons */
	public static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 24);

	/** The font to be used by all labels */
	public static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 24);

	/** The background color to use for the application */
	public static final Color BACKGROUND_COLOR = Color.DARK_GRAY;

	/** The color to use for the foreground (text) of the application */
	public static final Color FOREGROUND_COLOR = Color.LIGHT_GRAY;

	/** The spacing to add between various elements of the layout */
	public static final int ELEMENT_SPACING = 5;

	/** The margin added around buttons */
	public static final int BUTTON_MARGIN = 5;

	/** The maximum number of parameter rows to add before adding an additional column */
	public static final int MAX_PARAMS_ROWS = 4;

	/** The Border to add around various elements of the layout */
	public static final Border STD_BORDER = BorderFactory.createCompoundBorder(new EmptyBorder(Configuration.ELEMENT_SPACING, Configuration.ELEMENT_SPACING, Configuration.ELEMENT_SPACING, Configuration.ELEMENT_SPACING), new EtchedBorder());

	/** The code panel for the application */
	public static CodePanel codePanel;

	/** The frame used by the application */
	public static KFrame frame;

	/** The registers used by the emulator for the application */
	public static Vector<Register> registers;

	/** The data queue representing the input for the application */
	public static DataQueue input;

	/** The data queue representing the output for the application */
	public static DataQueue output;

	/** The emulator for the application */
	public static Emulator emulator;

	/** The registers panel for the application */
	public static DataQueue registersPanel;

	/** The instruction panel for the application */
	public static InstructionPanel instructionPanel;

	/**
	 * Play the error sound on Windows machines
	 */
	public static void playErrorSound(){
		java.awt.Toolkit.getDefaultToolkit().beep();
	}

	/**
	 * Gets the parameter value for a given parameter
	 *
	 * @param a_parameter the a parameter to get the value of
	 * @return the parameter value, or -1 on error
	 */
	public static int getParameterValue(Parameter a_parameter){
		if(!(a_parameter instanceof ParameterRegister)) return ((ParameterNumber)a_parameter).getValue();

		ParameterRegister pr = (ParameterRegister)a_parameter;
		if(pr.isNumber()) return pr.getValue();
		if(pr.isRegister()) return Configuration.registers.get(pr.getValue()).get();
		if(pr.isOutput()) return (int)Configuration.output.pop().get();
		if(pr.isInput()) return (int)Configuration.input.pop().get();

		return -1;
	}

	/**
	 * Sets the parameter value
	 *
	 * @param a_parameter the parameter to set the value of
	 * @param a_value the value to set the paremter to
	 */
	public static void setParameterValue(Parameter a_parameter, int a_value){
		// If the parameter is not a register, we cannot set the value of it
		if(!(a_parameter instanceof ParameterRegister)) return;

		// Get the parameter indicated
		ParameterRegister pr = (ParameterRegister)a_parameter;

		// If it is in register mode, set the value of the register
		if(pr.isRegister()) Configuration.registers.get(pr.getValue()).set(a_value);

		// If it is input or output, add the value accordingly
		if(pr.isOutput()) Configuration.output.push(new Data(a_value));
		if(pr.isInput()) Configuration.input.push(new Data(a_value));
	}
}