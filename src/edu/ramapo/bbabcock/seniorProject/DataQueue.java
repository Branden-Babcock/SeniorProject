/*
 * Class: DataQueue
 * Purpose: Contains a set of data that can be added to and removed from, updates the view of the data during each change
 */

package edu.ramapo.bbabcock.seniorProject;

import java.awt.FlowLayout;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class DataQueue extends KPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2060901521969058172L;

	/** The data currently in the queue */
	private LinkedList<Data> m_input;

	/** The panel used to display the data in the queue */
	private JPanel m_inputPanel;

	/** The text that represents that title */
	private String m_text;

	/**
	 * Instantiates a new data queue that is a copy of the one passed in
	 *
	 * @param a_copy the queue to copy from
	 */
	public DataQueue(DataQueue a_copy){
		// Initialize this with the other queue's name
		this(a_copy.m_text);

		// Loop through all the inputs of the other data queue and insert them into this one
		for(Data d : a_copy.m_input){
			m_input.addLast(d);
		}
	}

	/**
	 * Instantiates a new data queue with the given title
	 *
	 * @param a_text the title
	 */
	public DataQueue(String a_text){

		// Set the appearance of the queue
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBackground(Configuration.BACKGROUND_COLOR);
		m_input = new LinkedList<Data>();

		// Design the panel holding the input
		m_inputPanel = new JPanel();
		m_inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		m_inputPanel.setBackground(Configuration.BACKGROUND_COLOR);
		m_inputPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		// Design the label holding the name
		JLabel label = new JLabel();
		label.setForeground(Configuration.FOREGROUND_COLOR);
		label.setFont(Configuration.LABEL_FONT);
		label.setText(a_text);
		label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		this.m_text = a_text;
		add(label);

		// Add a scrollpane containing the input pane
		JScrollPane sp = new JScrollPane(m_inputPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		sp.setBorder(BorderFactory.createEmptyBorder());
		sp.getHorizontalScrollBar().setUI(new CustomScrollBar(Configuration.BACKGROUND_COLOR, Configuration.FOREGROUND_COLOR, false));
		add(sp);
	}

	/**
	 * Adds a new piece of data to the queue
	 *
	 * @param a_data the data being added
	 * @return true, if successful
	 */
	public boolean push(Data a_data){
		// If the data is null, return
		if(a_data == null) return false;

		// Add the data
		m_inputPanel.add(a_data);
		invalidate();
		return m_input.add(a_data);
	}

	/**
	 * Removes and returns the top element of the queue
	 *
	 * @return the data removed
	 */
	public Data pop(){
		// Obtain the data from the front of the queue
		Data d = m_input.poll();
		if(d == null) return null;

		// Remove the data
		if(m_inputPanel != null) m_inputPanel.remove(d);
		invalidate();

		// Return the data
		return d;
	}

	/**
	 * Returns the top element of the queue without removing it
	 *
	 * @return the element
	 */
	public Data peek(){
		return m_input.peek();
	}

	/**
	 * Removes all data from the queue and the panel representing the queue
	 */
	public void clear() {
		// Remove everything from the panel
		m_input.clear();
		m_inputPanel.removeAll();
		invalidate();
	}

	/**
	 * Returns the full set of data in the queue
	 *
	 * @return the data
	 */
	public LinkedList<Data> getInput(){
		return m_input;
	}
}
