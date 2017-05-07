/*
 * Class: InstructionPanel
 * Purpose: Constructs a panel to store instructions that have been added to the program
 */

package edu.ramapo.bbabcock.seniorProject;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class InstructionPanel extends KPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5227430404574206168L;

	/** The instructions contained within this panel */
	private Vector<Instruction> m_instructions;

	/** The panel which displays the code */
	private JPanel m_panel;

	/**
	 * Instantiates a new instruction panel.
	 */
	public InstructionPanel(){
		// Create a place to store the instructions
		m_instructions = new Vector<Instruction>();
		setBorder(BorderFactory.createEmptyBorder());

		// Create a panel to store the instructions
		m_panel = new JPanel();
		m_panel.setBackground(Configuration.BACKGROUND_COLOR);
		m_panel.setLayout(new GridLayout(0, 1));
		m_panel.setBorder(BorderFactory.createEmptyBorder());

		// Create a scroll pane so the instruction pane is scrollable
		JScrollPane sp = new JScrollPane(m_panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setBackground(Configuration.BACKGROUND_COLOR);
		sp.setBorder(Configuration.STD_BORDER);
		sp.getVerticalScrollBar().setUI(new CustomScrollBar(Configuration.BACKGROUND_COLOR, Configuration.FOREGROUND_COLOR, true));
		sp.setPreferredSize(new Dimension(400, 400));

		// Add the scroll panel to the layout
		add(sp);
	}

	/**
	 * Adds the instruction to the code panel
	 *
	 * @param a_instruction the instruction to add
	 * @return true, if successful
	 */
	public boolean addInstruction(Instruction a_instruction){
		// If the instruction is invalid, return
		if(a_instruction == null) return false;
		if(!m_instructions.add(a_instruction)) return false;

		// Add the instruction
		m_panel.add(a_instruction);

		return true;
	}

	/**
	 * Gets the instruction of the indicated name
	 *
	 * @param a_name the name of the instruction
	 * @return the instruction if it exists, otherwise null
	 */
	public Instruction getInstruction(String a_name){
		// Attempt to find the instruction with the given name and return it
		for(Instruction i : m_instructions){
			if(i.getName().equalsIgnoreCase(a_name)) return i;
		}

		// Return null if it couldn't be found
		return null;
	}

	/**
	 * Removes the instruction from the panel
	 *
	 * @param a_instruction the instruction to be removed
	 */
	public void removeInstruction(Instruction a_instruction){
		// Remove the indicated instruction from the panel and the set of instructions
		m_instructions.remove(a_instruction);
		m_panel.remove(a_instruction);
	}
}
