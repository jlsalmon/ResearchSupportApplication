/*
 * QueueD.java
 *
 * Created on 16 February 2010, 20:58
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package researchSupport;
/**
 *
 * @author David
 */
public class Queue 
{
    // a dynamic implementation of a Queue
    
    private QueueNode head;
    private QueueNode tail;
    
    /** Creates a new instance of Stack */
        
    public Queue() 
    {
        head = new QueueNode();
        // head always references a leading unused node
        tail = head;
        // when head and tail reference the same node the Queue is empty
    }
    
    protected void setHead(QueueNode aHead) 
    {
        head = aHead;
    }
    
    public QueueNode getHead()
    //protected QueueNode getHead()
    {
        return head;
    }
    
    protected void setTail(QueueNode aTail) 
    {
        tail= aTail;
    }
    
    protected QueueNode getTail() 
    {
        return tail;
    }
    
     
    public void joinBack(Object newEntry) 
    {
        if (! this.isFull() ) 
        {
            QueueNode temp = new QueueNode(newEntry);
            temp.setPrevious(this.getTail() );
            this.getTail().setNext(temp);
            this.setTail(temp);
        }
    }
    
    public Object leaveFront() 
    {
        Object result = null;
        if (! this.isEmpty() ) 
        {
            // head refrences the lead unused node 
            result = this.getHead().getNext().getItem();
            this.getHead().getNext().setPrevious(null);
            this.setHead( this.getHead().getNext() );
        }
        return result;
    }
    
    public Object peekFront() 
    {
        Object result = null;
        if (! this.isEmpty() ) 
        {
            result = this.getHead().getNext().getItem();
         }
        return result;
    }
    
    public Boolean isEmpty() 
    {
        return (this.getHead() == this.getTail() );
    }
    
    public Boolean isFull() 
    {
        // assumes no limit on capacity
        return false;
    }
    
    public void clear() 
    {
        this.setTail( (QueueNode) this.getHead() );
    }
    
    public String toString() 
    {
        String result = "Queue contains\n";
        QueueNode current = (QueueNode) this.getHead().getNext();
        while (current != null)
        {
            result = result + current.getItem() + " ";
            current = current.getNext();
        }
        result = result + "\n";
        return result;
    }
}

