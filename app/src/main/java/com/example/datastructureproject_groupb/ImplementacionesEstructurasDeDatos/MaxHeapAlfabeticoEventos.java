package com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos;


import com.example.datastructureproject_groupb.entidades.evento.Evento;

import java.util.HashMap;
import java.util.Map;

public class MaxHeapAlfabeticoEventos {
    private Evento[] heap;
    private int size;
    private Map<Evento, Integer> indexMap;

    public MaxHeapAlfabeticoEventos(int capacity) {
        heap = new Evento[capacity];
        size = 0;
        indexMap = new HashMap<>();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }

    public void insert(Evento value) {
        if (size == heap.length) {
            throw new IllegalStateException("Heap is full");
        }

        heap[size] = value;
        indexMap.put(value, size);
        size++;
        heapifyUp(size - 1);
    }

    public Evento remove(Evento value) {
        Integer index = indexMap.get(value);
        if (index == null) {
            throw new IllegalArgumentException("Value not found in the heap");
        }

        Evento removedValue = heap[index];
        heap[index] = heap[size - 1];
        indexMap.put(heap[size - 1], index);
        indexMap.remove(value);
        size--;

        if (index < size) {
            heapifyDown(index);
            if (index > 0 && heap[index].getNombreEvento().compareTo(heap[(index - 1) / 2].getNombreEvento()) > 0) {
                heapifyUp(index);
            }
        }

        return removedValue;
    }

    public Evento extractMax() {
        if (isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }

        Evento max = heap[0];
        heap[0] = heap[size - 1];
        indexMap.put(heap[size - 1], 0);
        indexMap.remove(max);
        size--;
        heapifyDown(0);

        return max;
    }

    private void heapifyUp(int index) {
        int parentIndex = (index - 1) / 2;
        while (index > 0 && heap[index].getNombreEvento().compareTo(heap[parentIndex].getNombreEvento()) > 0) {
            swap(index, parentIndex);
            index = parentIndex;
            parentIndex = (index - 1) / 2;
        }
    }

    private void heapifyDown(int index) {
        int largest = index;
        while (true) {
            int leftChildIndex = 2 * largest + 1;
            int rightChildIndex = 2 * largest + 2;

            if (leftChildIndex < size && heap[leftChildIndex].getNombreEvento().compareTo(heap[largest].getNombreEvento()) > 0) {
                largest = leftChildIndex;
            }

            if (rightChildIndex < size && heap[rightChildIndex].getNombreEvento().compareTo(heap[largest].getNombreEvento()) > 0) {
                largest = rightChildIndex;
            }

            if (largest != index) {
                swap(index, largest);
                index = largest;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        Evento temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
        indexMap.put(heap[i], i);
        indexMap.put(heap[j], j);
    }

    public Evento[] getHeap() {
        return heap.clone();
    }
}

