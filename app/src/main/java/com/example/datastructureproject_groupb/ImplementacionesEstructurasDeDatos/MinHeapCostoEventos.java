package com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos;

import com.example.datastructureproject_groupb.entidades.evento.Evento;

public class MinHeapCostoEventos {

    private Evento[] heap;
    private int size;
    private int capacity;

    public MinHeapCostoEventos(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.heap = new Evento[capacity];
    }

    public MinHeapCostoEventos(Evento[] arr){
        heap = arr;
        size = arr.length;

        for(int i = (arr.length - 1) / 2; i > -1; i--)
            heapifyDown(i);

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
    }

    private void heapifyUp(int index) {
        while (hasParent(index) && heap[index].getCostoEvento() < heap[getParentIndex(index)].getCostoEvento()) {
            swap(index, getParentIndex(index));
            index = getParentIndex(index);
        }
    }

    private void heapifyDown(int index) {
        while (hasLeftChild(index)) {
            int smallerChildIndex = getLeftChildIndex(index);
            if (hasRightChild(index) && heap[getRightChildIndex(index)].getCostoEvento() < heap[smallerChildIndex].getCostoEvento()) {
                smallerChildIndex = getRightChildIndex(index);
            }

            if (heap[index].getCostoEvento() < heap[smallerChildIndex].getCostoEvento()) {
                break;
            } else {
                swap(index, smallerChildIndex);
            }

            index = smallerChildIndex;
        }
    }

    public void insert(Evento evento) {
        if (size == capacity) {
            throw new IllegalStateException("Heap is full");
        }

        heap[size] = evento;
        size++;
        heapifyUp(size - 1);
    }

    public void remove(Evento evento) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (heap[i] == evento) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            throw new IllegalArgumentException("Value not found in heap");
        }

        heap[index] = heap[size - 1];
        size--;
        heapifyDown(index);
    }

    public Evento extractMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }

        Evento min = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapifyDown(0);

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

    public Evento[] heapSort(){

        for(int i = size - 1; i > 0; i--){

            swap(0, i);
            size--;
            heapifyDown(0);

        }

        return heap;

    }


}
