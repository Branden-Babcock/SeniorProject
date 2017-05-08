/*
 * Class: Drive
 * Purpose: The main class of the program. Creates all required data and frames for the execution. Prompts the user to either select which version of execution they would like to run
 */

package edu.ramapo.bbabcock.seniorProject;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class Driver {

	/**
	 * Continuously starts new programs when the last one ends
	 *
	 * @param a_args The command line arguments (unused)
	 * @throws Exception Any uncaught exception thrown by the program
	 */
	public static void main(String[] a_args) throws Exception{ while(true) new Driver().start(); }

	/**
	 * Starts a new instance of the program
	 *
	 * @throws Exception Any uncaught exception thrown by the program
	 */
	public void start() throws Exception{

		// Construct a frame for the project
		KFrame frame = new KFrame("Visual Coding Challenges - Senior Project", new Dimension(1200, 750), new BorderLayout());
		Configuration.frame = frame;

		// Compile a list of possible options
		Vector<String> choices = new Vector<String>();
		choices.add("Launch Challenge");
		if(FileReader.getAllFiles("challenges", null).length != 0) choices.add("Edit Challenge");
		if(FileReader.getAllFiles("instructions", null).length != 0) choices.add("Edit Instruction");

		// Ask the user what they would like to do
		String option = (String)JOptionPane.showInputDialog(frame, "What would you like to do", "Select Option", JOptionPane.QUESTION_MESSAGE, null, choices.toArray(new String[choices.size()]), "");

		// Exit if the user cancelled
		if(option == null) System.exit(0);

		// Allow the user to chose which file to open
		String file = "";
		String instruction = "";
		if(!option.equals("Edit Instruction")){
			file = (String)JOptionPane.showInputDialog(frame, "Which challenge would you like to open", "Select Challenge", JOptionPane.QUESTION_MESSAGE, null, FileReader.getAllFiles("challenges", option.equals("Edit Challenge") ? null : new String[]{""}), "");
			if(file == null) return;
		}else{
			instruction = (String)JOptionPane.showInputDialog(frame, "Which instruction would you like to edit", "Select Instruction", JOptionPane.QUESTION_MESSAGE, null, FileReader.getAllFiles("instructions", null), "");
			if(instruction == null) return;
		}

		// Create custom scrollbars for the display
		frame.add(Box.createVerticalGlue());
		UIManager.put("ScrollBarUI", "edu.ramapo.bbabcock.seniorProject.CustomScrollBar");

		// Create the instruction panel
		Configuration.instructionPanel  = new InstructionPanel();
		Configuration.instructionPanel.setLayout(new GridLayout(0, 1));

		// Create the emulator
		Configuration.emulator = new Emulator();

		// Add the instructions to the panel
		Configuration.instructionPanel.addInstruction(new InstructionMove());
		Configuration.instructionPanel.addInstruction(new InstructionAdd());
		Configuration.instructionPanel.addInstruction(new InstructionMultiply());
		Configuration.instructionPanel.addInstruction(new InstructionSubtract());
		Configuration.instructionPanel.addInstruction(new InstructionNegate());
		Configuration.instructionPanel.addInstruction(new InstructionIfLess());
		Configuration.instructionPanel.addInstruction(new InstructionIfEqual());
		Configuration.instructionPanel.addInstruction(new InstructionIfInputEmpty());
		Configuration.instructionPanel.addInstruction(new InstructionEndIf());
		Configuration.instructionPanel.addInstruction(new InstructionLabel());
		Configuration.instructionPanel.addInstruction(new InstructionGoto());

		// Create the input, output, and registers
		Configuration.input = new DataQueue("Input");
		Configuration.output = new DataQueue("Output");
		Configuration.registers = new Vector<Register>();
		Configuration.registersPanel = new DataQueue("Registers");

		// Create the control buttons
		Configuration.emulator.m_executeButton = new ButtonExecute();
		Configuration.emulator.m_stepButton = new ButtonStep();
		Configuration.emulator.m_submitButton = new ButtonSubmit();
		Configuration.emulator.m_resetButton = new ButtonReset();
		Configuration.emulator.m_saveInstructionButton = new ButtonSaveInstruction();
		Configuration.emulator.m_saveChallengeButton = new ButtonSaveChallenge();

		// Create the control panel and loud the selected challenge
		Configuration.codePanel = new CodePanel();
		Configuration.emulator.loadChallenge(file, !option.equals("Launch Challenge"));

		// Attempt to load each instruction from the files, exiting if it uses too many registers
		boolean instructionValid;
		for(String ins : FileReader.getAllFiles("instructions", null)){
			instructionValid = true;
			FileReader fr = new FileReader("instructions/" + ins + ".txt");

			InstructionCustom ic = new InstructionCustom(ins);
			while(fr.isValid()){
				Code c = new Code(fr.nextLine());
				if(c.isValid()){
					ic.addCode(c);
				}else{
					System.err.println("Could not add instruction, uses too many registers");
					System.err.println(c.toString());
					instructionValid = false;
					break;
				}
			}

			if(ins.equals(instruction)){
				for(Code c : ic.getCode()){
					Configuration.codePanel.addCode(c);
				}
			}else{
				if(instructionValid) Configuration.instructionPanel.addInstruction(ic);
			}
		}

		// Create the execution panel
		KPanel executionPanel = new KPanel();
		executionPanel.setLayout(new GridLayout(0, 1));

		// Add the control buttons
		executionPanel.add(Configuration.emulator.m_executeButton);
		executionPanel.add(Configuration.emulator.m_stepButton);
		executionPanel.add(Configuration.emulator.m_submitButton);
		executionPanel.add(Configuration.emulator.m_resetButton);
		executionPanel.add(Configuration.emulator.m_saveInstructionButton);
		executionPanel.add(Configuration.emulator.m_saveChallengeButton);
		executionPanel.add(new ButtonLoadInstruction());

		// Construct the layout
		JPanel leftContainer = new JPanel();
		leftContainer.setLayout(new GridLayout(1, 0));
		leftContainer.add(executionPanel);
		leftContainer.add(Configuration.instructionPanel);

		JPanel container = new JPanel();
		container.setLayout(new GridLayout(1, 0));
		container.add(leftContainer);
		container.add(Configuration.codePanel);

		JPanel ioPanel = new JPanel();
		ioPanel.setLayout(new GridLayout(3, 1));
		ioPanel.add(Configuration.input);
		ioPanel.add(Configuration.output);
		ioPanel.add(Configuration.registersPanel);

		JPanel label = new JPanel();
		JLabel l = new JLabel(Configuration.emulator.getCurrentChallenge());
		l.setForeground(Configuration.FOREGROUND_COLOR);
		label.add(l);
		label.setBackground(Configuration.BACKGROUND_COLOR);

		frame.add(container);
		frame.add(ioPanel, BorderLayout.SOUTH);
		frame.add(label, BorderLayout.NORTH);

		// Display the frame at a constant framerate
		frame.display(Configuration.FRAMERATE);

		// Reset the emulator
		Configuration.emulator.m_resetButton.doClick();

		// Loop until the frame is closed
		while(frame.isVisible()){
			Thread.sleep(1000);
		}
	}
}
