package com.tbf;

import java.util.Iterator;


/**
 * This is a custom list ADT that allows us to store Portfolios
 * 
 * @authors Jayden Carlon and Parker Zach
 *
 */
public class CustomList implements Iterable<Portfolio> {

	private Node head = null;
	public int size = 0;

	public Node getHead() {
		return this.head;
	}

	public void setHead(Portfolio insert) {
		if (insert == null) {
			throw new RuntimeException("You cannot insert a null value");
		}
		Node a = new Node(insert);
		if (this.head == null) {
			this.head = a;
			this.head.setNext(null);
			this.size++;
		} else {
			a.setNext(this.head);
			this.head = a;
			this.size++;
		}
	}

	public int getSize() {
		return this.size;
	}

	//Inserts a portfolio into the list based on the LastName FirstName of the owner
	public void insertByName(Portfolio portfolio) {
		if (this.size == 0) {
			setHead(portfolio);
		} else {
			int place = 0;
			for (Portfolio port : this) {
				if (portfolio.ownerToString().compareTo(port.ownerToString()) < 0) {
					insertAtPoint(portfolio, place);
					return;
				} else if(portfolio.ownerToString().compareTo(port.ownerToString()) == 0) {
					insertAtPoint(portfolio, place+1);
					return;
				}
				if(place == size-1) {
					insertAtPoint(portfolio, place+1);
					return;
				}
				place++;
			}
		}
	}
	
	//Inserts a portfolio into the customList by its totalValue
	public void insertByValue(Portfolio portfolio) {
		if (this.size == 0) {
			setHead(portfolio);
		} else {
			int place = 0;
			for (Portfolio port : this) {
				double value = Portfolio.getTotalValue(portfolio.getAssetList()) - Portfolio.getTotalValue(port.getAssetList());
				if (value > 0) {
					insertAtPoint(portfolio, place);
					return;
				}
				if(place == size-1) {
					insertAtPoint(portfolio, place+1);
					return;
				}
				place++;
			}
		}
	}
	
	//Inserts a portfolio into the list based on the LastName FirstName of the owner
	public void insertByManager(Portfolio portfolio) {
		if (this.size == 0) {
			setHead(portfolio);
		} else {
			int place = 0;
			for (Portfolio port : this) {
				if (portfolio.compByTypeManager(port) < 0) {
					insertAtPoint(portfolio, place);
					return;
				} else if (portfolio.compByTypeManager(port) == 0) {
					insertAtPoint(portfolio, place);
					return;
				}
				if(place == size-1) {
					insertAtPoint(portfolio, place+1);
					return;
				}
				place++;
			}
		}
	}

	// The index starts at 0
	public void insertAtPoint(Portfolio insert, int point) {
		if (insert == null) {
			throw new RuntimeException("You cannot insert a null value");
		}
		if (point > this.size || point < 0) {
			throw new RuntimeException("You cannot insert a value outside of the bounds");
		}

		//
		if (point == 0) {
			setHead(insert);
			// This would add an element onto the end of the list
		} else if (point == size) {
			Node a = getNodeAtPoint(point - 1);
			Node b = new Node(insert);
			a.setNext(b);
			size++;
		} else {
			Node a = getNodeAtPoint(point);
			Node b = getNodeAtPoint(point - 1);
			Node c = new Node(insert);

			c.setNext(a);
			b.setNext(c);
			size++;
		}

	}

	public Node getNodeAtPoint(int point) {
		if (point > this.size || point < 0) {
			throw new RuntimeException("You cannot insert a value outside of the bounds");
		}
		if (point == 0) {
			return this.head;
		}
		Node current = this.head;
		for (int i = 0; i < point; i++) {
			current = current.getNext();
		}
		return current;
	}

	public void removeAtPoint(int point) {
		if (size == 0) {
			throw new RuntimeException("You cannot remove from an empty list");
		}
		if (point >= size || point < 0) {
			throw new RuntimeException("You cannot remove a point from out of bounds");
		}
		if (point == 0) {
			this.head = this.head.getNext();
			size--;
		} else {
			Node a = getNodeAtPoint(point - 1);
			Node b = getNodeAtPoint(point);
			a.setNext(b.getNext());
			size--;
		}

	}

	@Override
	public Iterator iterator() {
		return new Iterator() {
			Node curr = head;

			@Override
			public boolean hasNext() {
				if (curr == null)
					return false;
				else
					return true;
			}

			@Override
			public Portfolio next() {
				Portfolio item = curr.getItem();
				curr = curr.getNext();
				return item;
			}
		};
	}

}
