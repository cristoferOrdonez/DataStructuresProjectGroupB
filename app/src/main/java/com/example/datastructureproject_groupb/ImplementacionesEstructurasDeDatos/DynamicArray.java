package com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos;

import java.util.Comparator;
import java.lang.reflect.Array;

// Generic class for a dynamic array
public class DynamicArray<T> {
    T[] arr; // Array to hold elements of type T
    int capacity; // Maximum capacity of the array
    int size = 0; // Current size of the array

    // Constructor to initialize the dynamic array with a given array
    public DynamicArray(T[] ar) {
        arr = ar;
        capacity = arr.length;
    }

    // Method to set an element at a specific index
    public void set(int i, T item) {
        if (i < 0 || i >= size) {
            System.out.println("Index out of range");
        } else {
            arr[i] = item;
        }
    }

    // Method to get an element at a specific index
    public T get(int i) {
        if (i < 0 || i >= size) {
            System.out.println("Index out of range");
            throw new RuntimeException("Index out of array bounds");
        } else {
            return arr[i];
        }
    }

    // Method to remove an element at a specific index
    public void remove(int i) {
        if (i < 0 || i >= size) {
            System.out.println("Index out of range");
            throw new RuntimeException("Index out of array bounds");
        } else {
            for (int j = i; j < size - 1; j++) {
                arr[j] = arr[j + 1];
            }
            size--;
        }
    }

    // Method to add an element at the end of the array
    public void pushBack(T item) {
        if (size == capacity) {
            T[] newArr = (T[]) Array.newInstance(arr.getClass().getComponentType(), capacity * 2);
            for (int i = 0; i < size - 1; i++) {
                newArr[i] = arr[i];
            }
            arr = newArr;
            capacity = capacity * 2;
        }
        arr[size] = item;
        size++;
    }

    // Method to get the current size of the array
    public int size() {
        return size;
    }
}
