/*
 * Class: CodePanel
 * Purpose: Stores the currently active Code to the user
 */

package edu.ramapo.bbabcock.seniorProject;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class CodePanel extends JPanel implements KeyListener{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5227430404574206168L;

	/** The code within the code panel */
	private Vector<Code> m_code = new Vector<Code>();;

	/** The panel used to display the code */
	private KPanel m_panel;

	/** The currently selected code */
	private Code m_selected;

	/** The panel to display if no code is selected */
	private KPanel m_unselectedPanel;

	/**
	 * Instantiates a new empty code panel.
	 */
	public CodePanel(){
		// Create an empty panel to display when no code is selected
		m_unselectedPanel = new KPanel();

		// Set the background of the panel
		setBackground(Configuration.BACKGROUND_COLOR);

		// Create a place to store the code within the panel
		setLayout(new GridLayout(1, 2));

		// Add a listener keyboard presses on this panel
		addKeyListener(this);

		// Create a panel to display for this code panel
		m_panel = new KPanel();
		m_panel.setLayout(new BoxLayout(m_panel, BoxLayout.Y_AXIS));
		m_panel.setBackground(Configuration.BACKGROUND_COLOR);
		m_panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		// Generate a scrollpane to store the code and add it to the panel
		JScrollPane sp = new JScrollPane(m_panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setBackground(Configuration.BACKGROUND_COLOR);
		sp.setBorder(Configuration.STD_BORDER);
		sp.getVerticalScrollBar().setUI(new CustomScrollBar(Configuration.BACKGROUND_COLOR, Configuration.FOREGROUND_COLOR, true));
		sp.setPreferredSize(new Dimension(400, 400));
		add(sp);

		// Add the unselected panel to the main panel
		add(m_unselectedPanel);
	}

	/**
	 * Adds the code to this code panel
	 *
	 * @param a_code the code to add
	 * @return true, if successful
	 */
	public boolean addCode(Code a_code){
		// If the code is not valid, return false
		if(a_code == null) return false;
		if(!m_code.add(a_code)) return false;

		// Add the code to the panel
		m_panel.add(a_code);
		a_code.updateText();

		// Invalidate the emulator since the code has changed
		Configuration.emulator.invalidate();

		// Invalidate this panel since the information to display has changed
		invalidate();

		return true;
	}

	/**
	 * Sets the selected code for this code panel
	 *
	 * @param a_code The code to select
	 */
	public void setSelected(Code a_code){
		// Request focus for keyboard input
		requestFocus();

		// Return if trying to select the same panel
		if(m_selected == a_code) return;

		// Remove the currently selected panel
		if(m_selected == null) remove(m_unselectedPanel);
		else m_selected.deselect(this);

		// Add the newly selected panel
		if(a_code == null) add(m_unselectedPanel);
		else a_code.select(this);

		// Set the selected code
		m_selected = a_code;
	}

	/**
	 * Gets the selected code for this code panel
	 *
	 * @return the selected code
	 */
	public Code getSelected(){ return m_selected; }

	/* (non-Javadoc)
	 *
	 * Handles a key being pressed while this code panel is focused, which can move code with arrow keys, or delete the currently selected code
	 *
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent a_event) {
		// If a key is pressed when nothing is selected, play an error sound
		if(m_selected == null){
			Configuration.playErrorSound();
			return;
		}

		// Stores the distance to move the selected code
		int mod = 0;

		// Find the index of the currently selected code
		int index = m_code.indexOf(m_selected);

		// If the key was up or down, adjust the mod accordingly, wrapping the move around
		if(a_event.getKeyCode() == KeyEvent.VK_UP) mod = index == 0 ? m_code.size()-1 : -1;
		else if(a_event.getKeyCode() == KeyEvent.VK_DOWN) mod = index == m_code.size()-1 ? -(m_code.size()-1) : 1;

		// If the delete key was pressed, remove the current code
		else if(a_event.getKeyCode() == KeyEvent.VK_DELETE){
			m_panel.remove(m_selected);
			m_code.remove(m_selected);
			setSelected(null);

			for(Code c : getCode()){
				c.updateText();
			}

			return;
		}

		// If this point is reached and the code should not be moved, play an error sound
		if(mod == 0){
			Configuration.playErrorSound();
			return;
		}

		// Move the selected piece of code to it's new location by shifting it by mod
		m_panel.remove(m_selected);
		m_panel.add(m_selected, index + mod);

		m_code.remove(index);
		m_code.add(index + mod, m_selected);

		// Update all code representations
		for(Code c : getCode()){
			c.updateText();
		}
	}

	/**
	 * Clears the code panel
	 */
	public void clear(){
		// Clear the panel and remove all buttons
		m_code.clear();
		setSelected(null);
		m_panel.removeAll();
	}

	/**
	 * Saves the code in the panel as a separate instruction
	 *
	 * @return the instruction that was created, or null on error
	 */
	public Instruction saveInstruction(){

		if(Configuration.codePanel.getCode().size() == 0){
			JOptionPane.showMessageDialog(Configuration.frame, "Write some code before you try to save it!", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}

		// Let the user enter the name of the instruction
		String fileName = JOptionPane.showInputDialog(Configuration.frame, "Enter Instruction Name", "Save Instruction", JOptionPane.QUESTION_MESSAGE);

		// Exit if the user cancelled
		if(fileName == null) return null;

		// Trim leading and trailing whitespace
		fileName = fileName.trim();

		// Remove all spaces from the entered filename and replace them with underscores
		fileName = fileName.replaceAll(" ", "_");

		// Return an error if no name was specified
		if(fileName.isEmpty()){
			JOptionPane.showMessageDialog(Configuration.frame, "You must specify a name for the instruction", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}

		// Replace all / and \ with _
		fileName = fileName.replaceAll("\\\\", "_");
		fileName = fileName.replaceAll("/", "_");

		// Try to find an existing instruction by the entered name
		Instruction i = Configuration.instructionPanel.getInstruction(fileName);

		// If an instruction is found
		if(i != null){

			// If it is a user-generated instruction, ask if it should be overwritten
			if(i instanceof InstructionCustom){
				if(JOptionPane.showConfirmDialog(Configuration.frame, "Instruction already exists... overwrite?", "Instruction Already Exists", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION)
					return null;

				Configuration.instructionPanel.remove((InstructionCustom)i);

			// If the instruction is a base instruction, tell the user they cannot name their instruction that
			}else{
				JOptionPane.showMessageDialog(Configuration.frame, "Cannot override standard instruction", "Error", JOptionPane.ERROR_MESSAGE);
				return null;
			}
		}

		// Attempt to save the instruction to the file and return it
		try{
			InstructionCustom ic = new InstructionCustom(fileName);
			BufferedWriter writer = new BufferedWriter(new FileWriter("instructions/" + fileName + ".txt"));

			for(Code c : m_code){
				writer.write(c + "\n");
				ic.addCode(c);
			}

			writer.close();
			return ic;
		}catch(Exception ex){
			Configuration.playErrorSound();
			JOptionPane.showMessageDialog(Configuration.frame, "Failed to save instruction to file", "Error", JOptionPane.ERROR_MESSAGE);
			System.err.println(ex.getMessage());

			// Return null if there was an error
			return null;
		}
	}

	/**
	 * Gets the code contained within this code panel
	 *
	 * @return the code
	 */
	public Vector<Code> getCode(){ return m_code; }

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent a_event){}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent a_event){}
}