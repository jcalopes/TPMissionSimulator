
package list;

import exceptions.NullElementValueException;
import interfaces.OrderedListADT;

/**
 * This class store an ordered list implemented by an array.
 */
public class OrderedArrayList<T> extends List<T> implements OrderedListADT<T>
{
    /**
     * Constructor for an ordered list.
     */
    public OrderedArrayList(){
        super();
    }
    
    /**
     * Constructor for an ordered list.
     * @param capacity
     */
    public OrderedArrayList(int capacity){
        super(capacity);
    }
    
    /**
     * Add a new element and reorder the list.
     * @param element
     * @throws ClassCastException
     * @throws NullElementValueException 
     */
    @Override
    public void add(T element)throws ClassCastException, NullElementValueException {
        if(element==null)throw new NullElementValueException("The element is null!");
        Comparable<T> newElem=null;
        try{
            newElem=(Comparable)element;
        }catch(ClassCastException ex){
            throw new ClassCastException("The element is not comparable!");
        }
        
        this.expandCapacity();
        int pos=0;
        
        if(!this.isEmpty()){
            while(pos<this.size() && newElem.compareTo(this.list[pos])>0){
            pos++;}
         
            for(int i=this.size();i>pos;i--){
                this.list[i]=this.list[i-1];} 
        }
        
        this.list[pos]=element;
        modCount++;
        this.rear++;      
    }
    
}
