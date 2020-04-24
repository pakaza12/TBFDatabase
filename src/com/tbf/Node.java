package com.tbf;

import java.util.Iterator;

public class Node<Portfolio>  {
	
	private Portfolio item;
	private Node<Portfolio> next;
	private Node<Portfolio> prev;
	
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
	
	public void setNext(Node<Portfolio> after){
		this.next = after;
	}
	
	public Node<Portfolio> getNext(){
		return next;
	}
	
	public void setPrev(Node<Portfolio> prev) {
		this.prev = prev;
	}
	public Node<Portfolio> getPrev(){
		return prev;
	}
	
	// Checks to see if there is there is something next in the list
	public boolean validNext() {
		boolean valid = false;
		if(this.next != null) {
			valid = true;
		}
		return valid;
	}
	
	
}
