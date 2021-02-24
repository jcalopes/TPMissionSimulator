
package doublyLinkedList;

/**
 * 
 * Represents a class that stores a doublylinked node.
 */
public class DoublyLinkedNode<T> {
    private T element;
    private DoublyLinkedNode<T> previous;
    private DoublyLinkedNode<T> next;
    
    /**
     * Constructor for a doubly linked node.
     */
    public DoublyLinkedNode(){
        this.element=null;
        this.previous=null;
        this.next=null;
    }
    
    /**
     * Constructor for a doubly linked node. 
     * @param element 
     */
    public DoublyLinkedNode(T element){
        this.element=element;
        this.previous=null;
        this.next=null;
    }
    
    /**
     * Getter for the element stored by the doubly linked node.
     * @return 
     */
    public T getElement(){
        return this.element;
    }
    
    /**
     * Setter for the element.
     * @param element 
     */
    public void setElement(T element){
        this.element=element;
    }
    
    /**
     * Getter for the previous node.
     * @return 
     */
    public DoublyLinkedNode<T> getPrevious(){
        return previous;
    }
    
    /**
     * Getter for the next node.
     * @return 
     */
    public DoublyLinkedNode<T> getNext(){
        return next;
    }
    
    /**
     * Setter for the previous node.
     * @param prev 
     */
    public void setPrevious(DoublyLinkedNode<T> prev){
        this.previous=prev;
    }
    
    /**
     * Setter for the next node.
     * @param next 
     */
    public void setNext(DoublyLinkedNode<T> next){
        this.next=next;
    }
    
    /**
     * Return a string representation for the element stored.
     * @return the information 
     */
    @Override
    public String toString(){
        String s="\n Elemento: ";
        s+=this.element.toString();
        return s;
    }
    
    /**
     * Equals for the class doubly linked node.
     * @param obj
     * @return bolean value
     */
    @Override
    public boolean equals(Object obj){
        if(obj!=null){
            if(this.getClass()==obj.getClass()){
                DoublyLinkedNode<T> object=(DoublyLinkedNode<T>)obj;
                if(this.element.equals(object.getElement())){
                    return true;
                }
            }
        }
        
        return false;
    }
}
