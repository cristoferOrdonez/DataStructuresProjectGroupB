package com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos;

// implementación de listas desordenadas con arreglos estaticos
public class StaticUnsortedList<T>{

    // atributos
    private T[] list; // arreglo estatico de tipo generico que contiene los datos de la lista desordenada
    private int size; // variable de tipo entero que almacena el tamaño de la lista desordenda

    // metodos

    // metodo constructor, recibe como parametro la capacidad de la lista desordenada
    public StaticUnsortedList(int capacity){
        list = (T[]) new Object[capacity];
        size = 0;
    }

    // metodo de insersión, retorna un booleano para saber si fue posible insertar o no
    public boolean insert(T element){
        if(isFull()){
            System.out.println("List is full");
            return false;
        }
        list[size++] = element;
        return true;
    }

    // metodo de eliminación por indice, retorna un booleano para saber si fue posible elimnar o no
    public boolean delete(int index){
        if(isEmpty()){
            System.out.println("List is empty");
            return false;
        }
        list[index] = list[--size];
        return true;
    }

    // metodo de acceso por indice, retorna el dato que se encuentra en la posición indicada
    public T get(int index){
        if(index >= size){
            System.out.println("Index out of range");
            return null;
        }

        return list[index];

    }

    // metodo para saber si la lista esta vacia, retorna un booleano
    public boolean isEmpty(){
        return size == 0;
    }

    // metodo para saber si la lista esta llena, retorna un booleano
    public boolean isFull(){
        return size == list.length;
    }

    // metodo para obtener el tamaño de la lista
    public int size(){
        return size;
    }

}
