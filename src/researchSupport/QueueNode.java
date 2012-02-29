/*
 * QueueNode.java
 *
 * Created on 31 October 2009, 07:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package researchSupport;

/**
 * 
 * @author David
 */
public class QueueNode {

	private QueueNode next; // link to the next item in the queue
	private QueueNode previous; // link to previous item (needed only for deque)
	private Object item; // item held on the stack

	/** Creates a new instance of QueueNode */
	public QueueNode() {
		this.next = null;
		this.previous = null;
		this.item = null;
	}

	public QueueNode(Object anItem) {
		this.next = null;
		this.previous = null;
		this.item = anItem;
	}

	public void setNext(QueueNode aNext) {
		this.next = aNext;
	}

	public QueueNode getNext() {
		return this.next;
	}

	public void setPrevious(QueueNode aPrevious) {
		this.previous = aPrevious;
	}

	public QueueNode getPrevious() {
		return this.previous;
	}

	public void setItem(Object anItem) {
		this.item = anItem;
	}

	public Object getItem() {
		return this.item;
	}
}
