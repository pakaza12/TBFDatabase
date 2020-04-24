package com.tbf;

public class Node<T>  {
	
	private T item;
	private Node<T> next;
	private Node<T> prev;
	
	public Node(T item) {
		this.item = item;
		this.next = null;
	}
	
	public T getItem() {
		return item;
	}
	
	public void setItem(T item) {
		this.item = item;
	}
	
	public void setNext(Node<T> after){
		this.next = after;
	}
	
	public Node<T> getNext(){
		return next;
	}
	
	public void setPrev(Node<T> prev) {
		this.prev = prev;
	}
	public Node<T> getPrev(){
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
