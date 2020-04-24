package com.tbf;

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
