package com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos;

import java.util.Iterator;
import java.util.NoSuchElementException;


// implementación de listas enlazadas
public class LinkedList<T> implements Iterable<T> {

    // Clase interna nodo
    class Node<T> {
        public T data;
        public Node<T> next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    // Atributos
    public Node<T> head, tail;

    // Constructor
    public LinkedList() {
        this.head = null;
        this.tail = null;
    }

    // Método para colocar un dato al frente de la lista enlazada
    public void pushFront(T data) {
        Node<T> node = new Node<>(data);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            node.next = head;
            head = node;
        }
    }

    // Método para obtener un iterador sobre los elementos de la lista
    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    // Clase interna que implementa Iterator
    private class LinkedListIterator implements Iterator<T> {
        private Node<T> current;

        public LinkedListIterator() {
            this.current = head;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T data = current.data;
            current = current.next;
            return data;
        }
    }

    // metodo para colocar un dato al frente de la lista enlazada


    // metodo para eliminar el dato que se encuentra al frente de la lista enlazada
    public void popFront(){
        if(head == null)
            throw new NoSuchElementException("List is empty");
        if(head == tail){
            head = null;
            tail = null;
        } else {
            head = head.next;
        }
    }

    // metodo para insertar un dato al final de la lista enlazada
    public void pushBack(T data){
        Node<T> node = new Node<>(data);
        if(tail == null){
            head = node;
            tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
    }

    // metodo para eliminar el dato que se encuentra al final de la lista enlazada
    public void popBack(){
        if(head == null)
            throw new NoSuchElementException("List is empty");
        if(head == tail){
            head = null;
            tail = null;
        } else {
            Node<T> node = head;
            while(node.next != tail){
                node = node.next;
            }
            tail = node;
            tail.next = null;
        }
    }

    // metodo para añadir un dato después de un nodo dado
    public void addAfter(Node<T> prevNode, T data){
        Node<T> node = new Node<>(data);
        node.next = prevNode.next;
        prevNode.next = node;
        if(node.next == null)
            tail = node;
    }

    // metodo para añadir un dato antes de un nodo dado
    public void addBefore(Node<T> nextNode, T data){
        Node<T> node = new Node<>(data);
        Node<T> prevNode = head;
        while(prevNode.next != nextNode){
            prevNode = prevNode.next;
        }
        prevNode.next = node;
        node.next = nextNode;
    }

    // metodo para obtener el tamaño de la lista enlazada
    public int size(){
        int size = 0;
        Node<T> node = head;
        while(node != null){
            size++;
            node = node.next;
        }
        return size;
    }

    // metodo para otener el dato que se encuentra al frente de la lista enlazada
    public T topFront(){
        if(head == null)
            throw new NoSuchElementException("List is empty");
        return head.data;
    }

    // metodo para obtener el dato que se encuentra al final de la lista enlazada
    public T topBack(){
        if(tail == null)
            throw new NoSuchElementException("List is empty");
        return tail.data;
    }

    // metodo para saber si un dato se encuentra en la lista enlazada
    public boolean find(T data){
        Node<T> node = head;
        while(node != null){
            if(node.data == data)
                return true;
            node = node.next;
        }
        return false;
    }

    // metodo para eliminar un dato especificado en la lista enlazada
    public void erase(T data){
        if(head == null)
            throw new NoSuchElementException("List is empty");
        if(head.data == data){
            head = head.next;
            if(head == null)
                tail = null;
            return;
        }
        Node<T> node = head;
        while(node.next != null){
            if(node.next.data == data){
                node.next = node.next.next;
                if(node.next == null)
                    tail = node;
                return;
            }
            node = node.next;
        }


    }

}
