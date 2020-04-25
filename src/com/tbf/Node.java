package com.tbf;


/**
 * This is a basic Node class that allows us to make custom, or general
 * lists, stacks, queues, etc.
 * 
 * @authors Jayden Carlon and Parker Zach
 *
 */
public class Node {

	private Portfolio item;
	private Node next;

	public Node(Portfolio item) {
		this.item = item;
		this.next = null;
	}

	public Portfolio getItem() {
		return item;
	}

	public void setItem(Portfolio item) {
		this.item = item;
	}

	public void setNext(Node after) {
		this.next = after;
	}

	public Node getNext() {
		return next;
	}

	// Checks to see if there is there is something next in the list
	public boolean validNext() {
		boolean valid = false;
		if (this.next != null) {
			valid = true;
		}
		return valid;
	}

}
