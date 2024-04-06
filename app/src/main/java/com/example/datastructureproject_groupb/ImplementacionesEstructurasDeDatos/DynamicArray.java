package com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos;

public class DynamicArray<T>{

    // Atributos
    protected T[] array;
    protected int size;
    private int capacity;

    // Metodos

    // Constructor
    public DynamicArray(){
        array = (T[]) new Object[10];
        size = 0;
        capacity = 10;
    }

    // Metodo para duplicar la capacidad del arreglo estatico
    public void duplicateSize(){
        T[] array = (T[]) new Object[capacity*2];
        for (int i = 0; i < size; i++)
            array[i] = this.array[i];
        this.array = array;
        capacity *= 2;
    }

    // Metodo para obtener el tamaño del arreglo dinamico
    public int size(){
        return size;
    }

    // Metodo para añadir un elemento al final del arreglo dinamico
    public void pushBack(T item){
        if(size == capacity)
            duplicateSize();

        array[size++] = item;
    }

    // Metodo para colocar un elemento en una posición especifica del arreglo dinamico
    public void set(int index, T item){
        if (index < 0 || index >= size)
            System.out.println("Index out of range");
        else
            array[index]=item;
    }

    // Metodo para remover un elemento de una posición especifica del arreglo dinamico

    public void remove(int index){
        if (index < 0 || index >= size)
            System.out.println("Index out of range");
        else{
            for (int j = index; j < size - 1; j++)
                array[j]=array[j+1];
            size--;
        }
    }

    public T get(int index){
        if (index < 0 || index >= size)
            throw new RuntimeException("Indice fuera del arreglo");
        else
            return array[index];
    }

}
