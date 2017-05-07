/*
 * Class: FileReader
 * Purpose: Reads a file into a buffer to make data use easier
 */

package edu.ramapo.bbabcock.seniorProject;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;

public class FileReader {

	/** The data in the read in file. */
	private ArrayList<String> m_fileData = new ArrayList<String>();

	/** The next line to be read from the file */
	private int m_nextLine = 0;

	/**
	 * Instantiates a new file reader to read the given file
	 *
	 * @param a_filename the path to the file to read from
	 */
	public FileReader(String a_filename){

		// Create a reader for the file
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new java.io.FileReader(a_filename));

			// Add each line to the reader
			String line;
			while((line = reader.readLine()) != null){
				m_fileData.add(line);
			}

		} catch (Exception e){
			System.err.println("Error reading from \"" + a_filename + "\"\n" + e.getMessage());
		}

		try{
			if(reader != null) reader.close();
		}catch(Exception e){}
	}

	/**
	 * Gets the next line from the file
	 *
	 * @return the next line, or null if out of lines
	 */
	public String nextLine(){
		if(m_nextLine > m_fileData.size()) return null;

		return m_fileData.get(m_nextLine++);
	}

	/**
	 * Determines if another line can be read from the file
	 *
	 * @return true, if is valid
	 */
	public boolean isValid(){ return m_fileData.size() > m_nextLine; }

	/**
	 * Gets the all files in a given directory, and adds other options to the beginning of it
	 *
	 * @param a_dir the directory to locate files in
	 * @param a_initial the initial strings to add to the beginning of the file list
	 * @return The list of all the files in the directory, with a_initial at the beginning of the list
	 */
	public static String[] getAllFiles(String a_dir, String[] a_initial){

		// Get a folder representing the indicated directory
		File folder = new File(a_dir);

		// Generate a list of all files in the indicated directory
		File[] listOfFiles = folder.listFiles();

		// If there were no files, return just the initial set
		if(listOfFiles == null) return a_initial;

		// Create an array that can store the found files and the initial set
		String[] ret = new String[listOfFiles.length + (a_initial == null ? 0 : a_initial.length)];

		// Add all elements of the initial set to the array
		for(int i=0; i< (a_initial == null ? 0 : a_initial.length); i++){
			ret[i] = a_initial[i];
		}

		// Add all files to the array
		for(int i=0; i<listOfFiles.length; i++){
			ret[i+(a_initial == null ? 0 : a_initial.length)] = listOfFiles[i].getName().substring(0, listOfFiles[i].getName().lastIndexOf("."));
		}

		return ret;
	}
}
