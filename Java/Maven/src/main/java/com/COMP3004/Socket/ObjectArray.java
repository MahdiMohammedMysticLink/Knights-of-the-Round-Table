package com.COMP3004.Socket;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.COMP3004.Maven.Card;

public class ObjectArray implements Serializable {

	private static final long serialVersionUID = 1L;
	List<Object> objectList;
	
	/**
	 * Constructor
	 */
	public ObjectArray() {
		// TODO Auto-generated constructor stub

		objectList = new ArrayList<Object>();
	}

	// Getters
	public int size() {
		return objectList.size();
	}

	public Object get(int index) {
		return objectList.get(index);
	}


	public void add(Object o) {
		objectList.add(o);
	}

	public void remove(int index) {
		objectList.remove(index);
	}


	// Methods

	
	
	

}
