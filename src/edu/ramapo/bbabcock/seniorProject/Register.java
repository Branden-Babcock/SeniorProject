/*
 * Class: Register
 * Purpose: Implements a data storage location for program execution
 */

package edu.ramapo.bbabcock.seniorProject;

public class Register{

	/** The value currently in the register */
	private int m_value;

	/** The index of the register */
	private int m_index;

	/** The data object representing the value in the register */
	private Data m_data;

	/** The string representation of the register */
	private String m_stringRep;

	/**
	 * Instantiates a new register at the given index, and the value of 0
	 *
	 * @param a_index the index which the register resides at
	 */
	public Register(int a_index){

		// Store the passed in value and set the value to 0
		this.m_index = a_index;
		set(0);
	}

	/**
	 * Sets the value of the register
	 *
	 * @param a_value the value of the register
	 */
	public void set(int a_value){

		// Set the value and recalculate the representations
		this.m_value = a_value;
		if(m_data != null) m_data.invalidate();
		m_stringRep = "R" + m_index + ": " + a_value;
	}

	/**
	 * Sets the data object of the register
	 *
	 * @param a_data the new data
	 */
	public void setData(Data a_data){
		// Store the passed-in data
		this.m_data = a_data;
	}

	/**
	 * Gets the value currently contained within the register
	 *
	 * @return the value contained within the register
	 */
	public int get(){
		return m_value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return m_stringRep;
	}
}
