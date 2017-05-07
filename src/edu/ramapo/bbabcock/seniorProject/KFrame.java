/*
 * Class: KFrame
 * Purpose: Implements a window that handles intricacies of displaying content including framerate, validation, closing confirmation, etc
 */

package edu.ramapo.bbabcock.seniorProject;

import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class KFrame extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4697300103879772610L;

	/** Indicates whether or not the frame is currently active */
	private boolean m_enabled = false;

	/**
	 * Instantiates a new window
	 *
	 * @param a_title the title of the window
	 * @param a_size the size of the window
	 * @param a_layout the primary layout to use for the window
	 */
	public KFrame(String a_title, Dimension a_size, LayoutManager a_layout){
		// Create a JFrame with the indicated title
		super(a_title);

		// Set the window to confirm on the user attempting to close
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				int choice = JOptionPane.showOptionDialog(e.getWindow(), "Are you sure you want to close", "Closing", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Yes", "No"}, "No");
				if(choice == 0) e.getWindow().dispose();
			}
		});

		// Set the preferred size and layout of the frame
		setPreferredSize(a_size);
		setLayout(a_layout);
	}

	/**
	 * Display the window at the indicated framerate
	 *
	 * @param a_framerate the framerate to attempt to display at
	 */
	public void display(int a_framerate){

		// Finalize the frame and update at the indicated framerate
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		m_enabled = true;

		new Thread(new FrameLoop(a_framerate, this)).start();
	}

	/**
	 * The Class FrameLoop - Handles maintaining and updating the content contained within the window
	 */
	private class FrameLoop implements Runnable{

		/** The frame time. */
		public final int frameTime;

		/** The frame. */
		public final KFrame frame;

		/**
		 * Instantiates a frame loop with the given framerate
		 *
		 * @param a_framerate the framerate
		 * @param a_frame the frame to display
		 */
		public FrameLoop(int a_framerate, KFrame a_frame){

			// Calculate how long the time between frames should be
			frameTime = 1000 / a_framerate;

			// Store the frame
			this.frame = a_frame;
		}

		/* (non-Javadoc)
		 *
		 * Handles running the loop, repainting when necessary, and validating as necessary
		 *
		 * @see java.lang.Runnable#run()
		 */
		public void run(){

			// Set the next frame to appear one second from the current time
			long nextFrame = System.currentTimeMillis() + 1000;

			// As long as the frame is open
			while(frame.isVisible()){

				// Calculate the time until the next frame
				long sleepTime = nextFrame - System.currentTimeMillis();

				// Sleep until the next frame
				if(sleepTime > 0){
					try {
						Thread.sleep(sleepTime);
					} catch (InterruptedException e){}
				}

				// If any componenet of the frame is invalid, validate it
				if(frame.isValid() == false) frame.validate();

				// Repaint the frame if it is enabled
				if(m_enabled) frame.repaint();

				// Set when the next frame is to occur
				nextFrame += frameTime;
			}
		}
	}

	/**
	 * Toggle the visibility of the frame to the given value
	 *
	 * @param a_visible the a visible
	 */
	public void toggle(boolean a_visible){
		m_enabled = a_visible;
	}
}
