/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package linkedListSentinela;

/**
 * 
 *  Nome:João Carlos Abreu Lopes 
    Número:8190221
    Turma:T1
 */
public class LinkedNode<T> {
    private T element;
    private LinkedNode<T> next;
    
    public LinkedNode(){
        this.element=null;
        this.next=null;
    }
    
    public LinkedNode(T element){
        this.element=element;
        this.next=null;
    }
    
    public T getElement(){
        return element; 
    }
    
    public LinkedNode<T> getNext(){
        return next;
    }
    
    public void setElement(T element){
        this.element=element;
    }
    
    public void setNext(LinkedNode<T> next){
        this.next=next;
    }
  
    @Override
    public boolean equals(Object obj){
        if(obj!=null){
            if(this.getClass()==obj.getClass()){
                LinkedNode<T> object=(LinkedNode<T>)obj;
                if(this.element.equals(object.getElement())){
                    return true;
                }
            }
        }
        return false;  
    }
    
    @Override
    public String toString(){
        String s="";
        s+=element.toString();
        
        return s;
    }
}
