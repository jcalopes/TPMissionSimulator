
package doublyLinkedList;

import exceptions.NullElementValueException;
import interfaces.OrderedListADT;

/**
 * This class store an ordered doubly linked list.
 */
public class OrderedDoublyLinkedList<T> extends DoublyLinkedListSentinela<T> implements OrderedListADT<T> {

    /**
     * Constructor for an ordered doubly linked list.
     */
    public OrderedDoublyLinkedList() {
        super();
    }

    /**
     * Adds a new element for a doubly linked list.
     *
     * @param element
     * @throws NullElementValueException
     * @throws ClassCastException
     */
    @Override
    public void add(T element) throws NullElementValueException, ClassCastException {
        if (element == null) {
            throw new NullElementValueException("The element is null!");
        }
        Comparable<T> newElem = null;

        try {
            newElem = (Comparable<T>) element;
        } catch (ClassCastException ex) {
            throw new ClassCastException("The element is not comparable!");
        }

        DoublyLinkedNode<T> node = new DoublyLinkedNode<>(element);

        DoublyLinkedNode<T> current = this.head.getNext();
        while (current.getElement() != null && 
                ((Comparable) node.getElement()).compareTo(current.getElement()) > 0) {
            current = current.getNext();
        }
        
        node.setPrevious(current.getPrevious());
        node.setNext(current);
        current.getPrevious().setNext(node);
        current.setPrevious(node);
        this.count++;
        this.modCount++;
    }
}
