/*
 * Class: ButtonSaveChallenge
 * Purpose: Represents a button which, when pressed, asks for all information required to save the challenge to a file, and does so
 */

package edu.ramapo.bbabcock.seniorProject;

import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Vector;

import javax.swing.JOptionPane;

public class ButtonSaveChallenge extends KButton {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new button save challenge.
	 */
	public ButtonSaveChallenge(){ super("Save Challenge"); }

	/* (non-Javadoc)
	 *
	 * Handles the clicking of the button and causes the current code set to be saved as a challenge
	 *
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent a_event) {
		// Create a new vector to store all of the inputs
		Vector<String> inputs = new Vector<String>();
		// Initialize a place to count the number of registers for the challenge
		int numRegisters = -1;
		// Initalize a place to store the minimum number of registers required by the current challenge
		int minRegisters = 0;
		// Set the minimum number of registers to the highest register value in use
		for(Code c : Configuration.codePanel.getCode()){
			minRegisters = Math.max(c.getNumRegisters(), minRegisters);
		}

		// Let the user enter the number of registers to use, ensuring that value is at least the minimum value
		while(numRegisters < 0){
			try{
				numRegisters = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of registers allowed"));
				if(numRegisters < minRegisters){
					JOptionPane.showMessageDialog(Configuration.frame, "Your solution uses " + minRegisters + " registers! Invalid!");
					numRegisters = -1;
				}
			}catch(Exception ex){
				JOptionPane.showMessageDialog(Configuration.frame, "Invalid Number!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}

		// Allow the user to enter the input they wish to use for the challenge
		String input = null;
		while(true){
			input = JOptionPane.showInputDialog("Enter 1 or more comma-separated inputs, close this window when done");
			if(input == null){
				if(inputs.size() == 0) continue;
				else break;
			}

			try{
				for(String i : input.split(",")){
					i = i.trim();
					Integer.parseInt(i);
				}
			}catch(Exception ex){
				JOptionPane.showMessageDialog(Configuration.frame, "Invalid value found in input", "Error", JOptionPane.ERROR_MESSAGE);
				continue;
			}

			inputs.add(input);
		}

		// Allow the user to write a description for their challenge
		input = JOptionPane.showInputDialog("Enter a description for your challenge");

		// Allow the user to select a name for their challenge
		String file = null;
		while(file == null){
			file = JOptionPane.showInputDialog("Enter Challenge Name");
			if(file ==null || file.isEmpty()){
				JOptionPane.showMessageDialog(Configuration.frame, "You must specify a challenge name", "Error", JOptionPane.ERROR_MESSAGE);
				file = null;
			}
		}

		// Attempt to save the challenge to the indicated file
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));

			writer.write("registers: " + numRegisters);
			for(String i : inputs) writer.write("\ninput: " + i);
			for(Code c : Configuration.codePanel.getCode()) writer.write("\n" + c.toString());
			writer.write("\ndescription: " + input);

			writer.close();
		}catch(Exception ex){
			System.err.println("Error saving challenge to file");
			System.err.println(ex.getMessage());
		}
	}

}
