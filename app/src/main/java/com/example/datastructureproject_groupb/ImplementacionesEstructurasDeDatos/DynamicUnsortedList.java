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

}
