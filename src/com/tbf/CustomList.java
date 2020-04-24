package com.tbf;

import java.util.Iterator;

public class CustomList<T> implements Iterable<T> {

	private Node<T> head = null;
	private Node<T> tail = null;
	
	public Node<T> getHead() {
		return this.head;
	}
	
	public void setHead(T head) {
		this.head = head;
	}
	
	public Node<T> getTail() {
		return this.tail;
	}

	public void setTail(T insert) {
		if(insert == null) {
			
		}
		if(this.tail == null)
		this.tail = tail;
	}



	@Override
	public Iterator<T> iterator() {
		
		return null;
	}

}
