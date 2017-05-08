package edu.ramapo.bbabcock.seniorProject;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

public class ButtonLoadInstruction extends KButton {

	private static final long serialVersionUID = 1L;

	public ButtonLoadInstruction() {
		super("Load Instruction");
	}

	@Override
	public void actionPerformed(ActionEvent a_action) {
		String[] files = FileReader.getAllFiles("instructions", null);
		if(files.length == 0){
			JOptionPane.showMessageDialog(Configuration.frame, "Found no instructions to load", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		String instruction = (String)JOptionPane.showInputDialog(Configuration.frame, "Which instruction would you like to edit", "Select Instruction", JOptionPane.QUESTION_MESSAGE, null, FileReader.getAllFiles("instructions", null), "");
		if(instruction == null) return;

		FileReader fr = new FileReader("instructions/" + instruction + ".txt");

		while(fr.isValid()){
			Code c = new Code(fr.nextLine());
			if(c.isValid()) Configuration.codePanel.addCode(c);
		}
	}

}
