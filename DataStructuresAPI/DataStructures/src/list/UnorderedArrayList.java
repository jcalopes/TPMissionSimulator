
package list;

import exceptions.ElementNotFoundException;
import exceptions.NullElementValueException;
import interfaces.UnorderedListADT;

/**
 * This class store an unordered list implemented by an array.
 */
public class UnorderedArrayList<T> extends List<T> implements UnorderedListADT<T>{

    /**
     * Constructor for a array list.
     */
    public UnorderedArrayList(){ 
        super();
    }
    
    /**
     * Constructor for a array list.
     * @param capacity 
     */
    public UnorderedArrayList(int capacity){
        super(capacity);
    }
    
    /**
     * Add new element to front the array list.
     * @param element
     * @throws NullElementValueException 
     */
    @Override
    public void addToFront(T element) throws NullElementValueException {
        if(element==null)throw new NullElementValueException("The element is null!");
        this.expandCapacity();
        for(int i=this.size();i>0;i--){
            this.list[i]=this.list[i-1];
        }
        this.list[0]=element;
        modCount++;
        this.rear++;
    }

    /**
     * Add new element to rear the array list.
     * @param element
     * @throws NullElementValueException 
     */
    @Override
    public void addToRear(T element) throws NullElementValueException {
        if(element==null)throw new NullElementValueException("The element is null!");
        this.expandCapacity();
        this.list[this.rear++]=element;
        modCount++;
    }

    /**
     * Add new element after a specific element in the array list.
     * @param element
     * @param target
     * @throws ElementNotFoundException
     * @throws NullElementValueException 
     */
    @Override
    public void addAfter(T element, T target)throws ElementNotFoundException, NullElementValueException {
        if(element==null)throw new NullElementValueException("The element is null!");
        int pos=this.posElement(target);
        pos++;
        
        for(int i=this.size();i>pos;i--){
            this.list[i]=this.list[i-1];
        }      
        this.list[pos]=element;
        this.rear++;
        modCount++;
    } 
}
