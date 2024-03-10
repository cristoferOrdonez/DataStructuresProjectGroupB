package com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos;

// implementación de pilas con listas enlazadas
public class StackLinkedList<T>{

    // atributos
    private LinkedList<T> stack; // lista enlazada que contiene los datos de la pila

    // metodos

    // constructor
    public StackLinkedList(){
        stack = new LinkedList<>();
    }

    // metodo para apilar
    public void push(T data){
        stack.pushFront(data);
    }

    // metodo para desapilar
    public T pop(){
        T data = stack.head.data;
        stack.popFront();
        return data;
    }

    // metodo para obtener el elemento que esta en la cima de la pila
    public T peek(){
        return stack.head.data;
    }

    // metodo para saber si la pila está vacía
    public boolean isEmpty(){
        return stack.head == null;
    }

}
