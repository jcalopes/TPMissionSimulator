package linkedListSentinela;

import exceptions.NullElementValueException;
import interfaces.OrderedListADT;

/**
 * This class store an ordered linked list.
 */
public class OrderedLinkedList<T> extends LinkedListSentinela<T> implements OrderedListADT<T> {

    public OrderedLinkedList(){
        super();
    }
    
    /**
     * Add and reorder the linked list.
     * @param element
     * @throws NullElementValueException 
     */
    @Override
    public void add(T element) throws NullElementValueException {
        if(element==null)throw new NullElementValueException("The element is null!");
        
        Comparable<T> novoElement=null;
        try{
            novoElement=(Comparable<T>)element;
        }
        catch(ClassCastException ex){
            throw new ClassCastException("The element must be Comparable!");
        }
        
        LinkedNode<T> current=this.head.getNext();
        LinkedNode<T> previous=this.head;
        while(current!=this.tail && novoElement.compareTo(current.getElement())>0){
            previous=current;
            current=current.getNext();
        }
        
        LinkedNode novoNode=new LinkedNode(novoElement);
               
        previous.setNext(novoNode);
        novoNode.setNext(current);
        count++;
        modCount++;
    }
}
