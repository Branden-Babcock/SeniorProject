/*
 * Class: Emulator
 * Purpose: Runs the current code on input and output that is currently set. Gives the ability to run one step at a time, or run all.
 */

package edu.ramapo.bbabcock.seniorProject;

import java.awt.Color;
import java.util.Vector;

import javax.swing.JOptionPane;

public class Emulator {

	/** The button that is used to reset the emulator */
	public ButtonReset m_resetButton;

	/** The button used to advance a step in the emulator */
	public ButtonStep m_stepButton;

	/** The button used to execute the remaining portion of the program */
	public ButtonExecute m_executeButton;

	/** The button used to submit the current code as a possible answer to the current challenge */
	public ButtonSubmit m_submitButton;

	/** The button used to save the current code as an instruction */
	public ButtonSaveInstruction m_saveInstructionButton;

	/** The button used to save the current code as a challenge */
	public ButtonSaveChallenge m_saveChallengeButton;

	/** The index of the next instruction to be executed */
	private int m_nextInstruction = 0;

	/** The name of the current challenge */
	private String m_currentChallenge;

	/** The data queues representing the input to use for each trial */
	private Vector<DataQueue> m_inputs = new Vector<DataQueue>();

	/** The expected outputs for each trial */
	private Vector<DataQueue> m_outputs = new Vector<DataQueue>();

	/** An indication as to whether or not the emulator is able to be started currently (Modifies buttons) */
	private boolean m_valid;

	/** An indication as to whether or not the emulator is currently running */
	private boolean m_running;

	/**
	 * Runs code until the end of the program is reached
	 *
	 * @param a_visible whether or not the results of each piece of code should be shown on the screen
	 */
	public void execute(boolean a_visible){
		// Loop until a step fails
		while(step(a_visible));
		invalidate();
	}

	/**
	 * Runs a single step of the code
	 *
	 * @param a_visible whether or not the updates should be displayed on the screen
	 * @return true, if the program stepped successfully, otherwise false
	 */
	public boolean step(boolean a_visible){
		// Return immediately if execution is complete
		if(complete()) return false;

		// Set the emulator to running
		m_running = true;

		// Store the next instruction for changing displays later
		int previousInstruction = m_nextInstruction;

		// Run the instruction
		m_nextInstruction = getNextInstruction().execute(m_nextInstruction, Configuration.codePanel.getCode());

		// All the remaining code in the following section is visual, so return if we are not rendering
		if(!a_visible) return true;

		// Loop through the output and color according to whether or not it is correct
		for(int i=0; i<Configuration.output.getInput().size(); i++){
			if(i >= m_outputs.elementAt(0).getInput().size()){
				Configuration.output.getInput().get(i).setBackground(Configuration.BACKGROUND_COLOR);
			}else if(Configuration.output.getInput().get(i).get() == m_outputs.elementAt(0).getInput().get(i).get()){
				Configuration.output.getInput().get(i).setBackground(Color.GREEN);
			}else{
				Configuration.output.getInput().get(i).setBackground(Color.RED);
			}
		}

		// Update the text of the previous and this instruction
		Configuration.codePanel.getCode().elementAt(previousInstruction).updateText();

		// If execution is complete, return
		if(complete()) return false;

		// Update the text of the next instruction
		getNextInstruction().updateText();

		return true;
	}

	/**
	 * Resets the emulator to the beginning of the current challenge, including registers, input, output, etc.
	 *
	 * @param a_index The index of the trial that should be loaded
	 */
	public void reset(int a_index){

		// Reset and reload on the indicated index
		m_nextInstruction = 0;

		reload(a_index);
		m_running = false;

		validate();
	}

	/**
	 * Runs each code trial against the code produces by the running the code in the code panel with each input, comparing the results to the expected output
	 */
	public void submit(){

		// Turn the display off
		Configuration.frame.toggle(false);

		// Loop through each input - output pair
		for(int i=0; i<m_inputs.size(); i++){

			// Load up the given input and run it with the current code
			reset(i);
			execute(false);

			// End instantly if the sizes of the outputs are not equal
			if(Configuration.output.getInput().size() != m_outputs.elementAt(i).getInput().size()){
				reset(0);
				error();
				return;
			}

			// Loop through the items within the input - output pair
			for(int x=0; x<Configuration.output.getInput().size(); x++){

				// If the corresponding elements are not equal, return an error
				if(Configuration.output.getInput().get(x).get() != m_outputs.elementAt(i).getInput().get(x).get()){
					reset(0);
					error();
					return;
				}
			}
		}

		// Reset the currently displayed problem
		reset(0);

		// If this point is reached, all input-output pairs are the same, display success
		success();
	}

	/**
	 * Returns true if the emulator is currently complete
	 *
	 * @return true if the program is complete, otherwise false
	 */
	public boolean complete(){
		// Return true if the next instruction location is valid
		if(m_nextInstruction >= Configuration.codePanel.getCode().size() || m_nextInstruction < 0) return true;

		return false;
	}

	/**
	 * Loads the indicated challenge in edit or challenge mode
	 *
	 * @param a_name The name of the file to load the challenge from
	 * @param a_edit True if the challenge should be able to be edited, or false if it is a challenge to solve
	 * @return true, if successful
	 */
	public boolean loadChallenge(String a_name, boolean a_edit){

		// Store the current challenge
		m_currentChallenge = a_name;

		// Determine the name of the file containing the challenge
		a_name = "challenges/" + a_name;
		if(a_name != null && !a_name.endsWith(".txt")) a_name += ".txt";

		// Clear the inputs and the outputs
		m_inputs.clear();
		m_outputs.clear();

		// Read in the data from the file
		FileReader file = new FileReader(a_name);

		// If the file is not valid, generate a base challenge
		if(!file.isValid()){
			resetRegisters(10);
			DataQueue dq = new DataQueue("Input");
			for(int i=0; i<100; i++){
				dq.push(new Data(i));
			}
			m_inputs.add(dq);
			m_outputs.add(new DataQueue("Output"));
		}

		// Turn the display off
		Configuration.frame.toggle(false);

		// Load the information from the file
		while(file.isValid()){
			String line = file.nextLine();

			// For each input, store the input and an empty output
			if(line.startsWith("input: ")){
				String[] input = line.substring(line.indexOf(' ')+1).split("\\s*,\\s*");
				m_inputs.add(new DataQueue("Input"));
				m_outputs.add(new DataQueue("Output"));
				for(String i : input) m_inputs.get(m_inputs.size()-1).push(new Data(Integer.parseInt(i)));

			// Reset to the indicated number of registers
			}else if(line.startsWith("registers: ")){
				resetRegisters(Integer.parseInt(line.substring(line.indexOf(' ')+1)));

			// Set the description of the current challenge
			}else if(line.startsWith("description: ")){
				m_currentChallenge = m_currentChallenge + ": " + line.substring(line.indexOf(' ')+1);

			// If the type of data could not be determined, try it as an instruction
			}else{
				String[] parts = line.split(" ");
				Configuration.codePanel.addCode(new Code(Configuration.instructionPanel.getInstruction(parts[0])));
				for(Code c : Configuration.codePanel.getCode()) System.out.println(c.toString());
				for(int i=2; i<parts.length; i+=2){
					Configuration.codePanel.getCode().lastElement().getParameter(i/2-1).setValue(parts[i]);
				}
			}
		}

		// Loop through each of the input and run it through the emulator to get the output
		for(int i=0; i<m_inputs.size(); i++){
			Configuration.output.clear();

			DataQueue temp = Configuration.input;
			Configuration.input = new DataQueue(m_inputs.elementAt(i));

			if(a_edit) return true;

			m_nextInstruction = 0;
			execute(false);
			Data d;

			while((d = Configuration.output.pop()) != null){
				m_outputs.elementAt(i).push(d);
			}

			Configuration.input = temp;
		}

		// Clear the input and the output of the display
		Configuration.input.clear();
		Configuration.output.clear();

		// Reset all of the registers
		resetRegisters(Configuration.registers.size());

		// Set the current input to be the first one
		for(Data d : m_inputs.elementAt(0).getInput()){
			Configuration.input.push(d);
		}

		// Clear the solution code from the code panel
		Configuration.codePanel.clear();

		// Turn the display back on
		Configuration.frame.toggle(true);

		return true;
	}

	/**
	 * Loads the indicated index for sets of input and output
	 *
	 * @param a_index the index of the trial to load
	 */
	public void reload(int a_index){

		// Clear the input and the output
		Configuration.input.clear();
		Configuration.output.clear();

		// Reset the registers
		resetRegisters(Configuration.registers.size());

		// Add all of the input from the indicated index
		for(Data d : m_inputs.elementAt(a_index).getInput()){
			Configuration.input.push(d);
		}

		// Set the first instruction to be the next to run
		m_nextInstruction = 0;

		// Update the text of all the code
		for(Code c : Configuration.codePanel.getCode()){
			c.updateText();
		}
	}

	/**
	 * Resets to have the number of registers indicated, all empty
	 *
	 * @param a_numRegisters the number of registers
	 */
	private void resetRegisters(int a_numRegisters){

		// Clear the registers
		Configuration.registers.clear();
		Configuration.registersPanel.clear();

		// Add the registers back
		for(int i=0; i<a_numRegisters; i++) addRegister();

		// Invalidate the registers so they are redrawn
		Configuration.registersPanel.invalidate();
	}

	/**
	 * Adds a register
	 */
	private void addRegister(){

		// Create a new register and add it to the panel
		Register r = new Register(Configuration.registers.size());
		Data d = new Data(r);
		r.setData(d);
		Configuration.registersPanel.push(d);
		Configuration.registers.add(r);
	}

	/**
	 * Displays an error message, for use with a submit producing incorrect output
	 */
	private void error(){
		// Turn the frame back on and display an error message
		Configuration.frame.toggle(true);
		JOptionPane.showMessageDialog(Configuration.frame, "Sorry, one or more test cases were incorrect...", "", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Displays a success message, for use with a submit producing correct output
	 */
	private void success(){
		// Turn the frame back on and display a success message
		Configuration.frame.toggle(true);
		JOptionPane.showMessageDialog(Configuration.frame, "Good Job!", "", JOptionPane.PLAIN_MESSAGE);
	}

	/**
	 * Sets the emulator to set buttons to the state corresponding to an emulator in a running state
	 */
	public void invalidate(){
		// Set the emulator to invalid and enable the buttons accordingly
		m_valid = false;
		if(!m_running) return;
		setButtonsEnabled();
	}

	/**
	 * Sets the emulator to set buttons to the state corresponding to an emulator in a beginning state
	 */
	public void validate(){
		// Set the emulator to valid and enable the buttons accordingly
		m_valid = true;
		setButtonsEnabled();
	}

	/**
	 * Sets buttons to be enabled in accordance with m_valid's value
	 */
	private void setButtonsEnabled(){

		// Set the reset button to always be enabled
		m_resetButton.setEnabled(true);

		// Enable the step and execute button only if the emulator is valid
		m_stepButton.setEnabled(m_valid);
		m_executeButton.setEnabled(m_valid);
	}

	/**
	 * Returns the next instruction to be run by the emulator
	 *
	 * @return The instruction that will run next, or null if the next index is invalid
	 */
	public Code getNextInstruction(){
		return complete() ? null : Configuration.codePanel.getCode().elementAt(m_nextInstruction);
	}

	/**
	 * Returns the name of the current challenge
	 *
	 * @return the name of the current challenge
	 */
	public String getCurrentChallenge(){
		return m_currentChallenge;
	}
}
