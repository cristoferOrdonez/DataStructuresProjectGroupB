package com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos;

// implementación de pilas con listas enlazadas
public class StackLinkedList<T> extends LinkedList<T>{

    // metodos

    // constructor
    public StackLinkedList(){
        super();
    }

    // metodo para apilar
    public void push(T data){
        pushFront(data);
    }

    // metodo para desapilar
    public T pop(){
        T data = topFront();
        popFront();
        return data;
    }

    // metodo para obtener el elemento que esta en la cima de la pila
    public T peek(){
        return topFront();
    }

    // metodo para saber si la pila está vacía
    public boolean isEmpty(){
        return head == null;
    }

}
