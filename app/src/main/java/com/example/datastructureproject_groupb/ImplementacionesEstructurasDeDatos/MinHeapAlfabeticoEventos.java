package com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos;

import com.example.datastructureproject_groupb.entidades.evento.Evento;

import java.util.HashMap;
import java.util.Map;

public class MinHeapAlfabeticoEventos{
    private Evento[] heap;
    private int size;
    private int capacity;
    private Map<Evento, Integer> indexMap;

    public MinHeapAlfabeticoEventos(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.heap = new Evento[capacity];
        this.indexMap = new HashMap<>();
    }

    private int getParentIndex(int index) {
        return (index - 1) / 2;
    }

    private int getLeftChildIndex(int index) {
        return (2 * index) + 1;
    }

    private int getRightChildIndex(int index) {
        return (2 * index) + 2;
    }

    private boolean hasParent(int index) {
        return getParentIndex(index) >= 0;
    }

    private boolean hasLeftChild(int index) {
        return getLeftChildIndex(index) < size;
    }

    private boolean hasRightChild(int index) {
        return getRightChildIndex(index) < size;
    }

    private void swap(int index1, int index2) {
        Evento temp = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = temp;
        indexMap.put(heap[index1], index1);
        indexMap.put(heap[index2], index2);
    }

    private void heapifyUp() {
        int index = size - 1;
        while (hasParent(index) && heap[index].getNombreEvento().compareTo(heap[getParentIndex(index)].getNombreEvento()) < 0) {
            swap(index, getParentIndex(index));
            index = getParentIndex(index);
        }
    }

    private void heapifyDown() {
        int index = 0;
        while (hasLeftChild(index)) {
            int smallerChildIndex = getLeftChildIndex(index);
            if (hasRightChild(index) && heap[getRightChildIndex(index)].getNombreEvento().compareTo(heap[smallerChildIndex].getNombreEvento()) < 0) {
                smallerChildIndex = getRightChildIndex(index);
            }

            if (heap[index].getNombreEvento().compareTo(heap[smallerChildIndex].getNombreEvento()) < 0) {
                break;
            } else {
                swap(index, smallerChildIndex);
            }

            index = smallerChildIndex;
        }
    }

    public void insert(Evento value) {
        if (size == capacity) {
            throw new IllegalStateException("Heap is full");
        }

        heap[size] = value;
        indexMap.put(value, size);
        size++;
        heapifyUp();
    }

    public void remove(Evento value) {
        Integer index = indexMap.get(value);
        if (index == null) {
            throw new IllegalArgumentException("Value not found in heap");
        }

        heap[index] = heap[size - 1];
        indexMap.put(heap[size - 1], index);
        indexMap.remove(value);
        size--;
        heapifyDown();
    }

    public Evento extractMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }

        Evento min = heap[0];
        heap[0] = heap[size - 1];
        indexMap.put(heap[size - 1], 0);
        indexMap.remove(min);
        size--;
        heapifyDown();

        return min;
    }

    public Evento peekMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }

        return heap[0];
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
