package com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos;

public class DynamicUnsortedList<T> extends DynamicArray<T>{

    // Metodos

    // Constructor
    public DynamicUnsortedList(){
        super();
    }

    // Metodo para insertar un elemento a la lista
    public void insert(T item){
        pushBack(item);
    }

    // Metodo para eliminar un elemento de la lista
    public void remove(int index){
        if (index < 0 || index >= size)
            System.out.println("Index out of range");
        else
            array[index] = array[--size];
    }

}
