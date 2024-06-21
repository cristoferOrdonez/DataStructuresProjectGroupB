package com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos;

import com.example.datastructureproject_groupb.entidades.evento.Evento;

public class MaxHeapCostoEventos {

    private Evento[] heap;
    private int size;

    public MaxHeapCostoEventos(int capacity) {
        heap = new Evento[capacity];
        size = 0;
    }

    public MaxHeapCostoEventos(Evento[] arr){
        heap = arr;
        size = arr.length;

        for(int i = (arr.length - 1) / 2; i > -1; i--)
            heapifyDown(i);

    }

    public Evento[] heapSort(){

        for(int i = size - 1; i > 0; i--){

            swap(0, i);
            size--;
            heapifyDown(0);

        }

        return heap;

    }


    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }

    public void insert(Evento evento) {
        if (size == heap.length) {
            throw new IllegalStateException("Heap is full");
        }

        heap[size] = evento;
        size++;
        heapifyUp(size - 1);
    }

    public Evento remove(Evento evento) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (heap[i] == evento) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            throw new IllegalArgumentException("Value not found in the heap");
        }

        Evento removedEvento = heap[index];
        heap[index] = heap[size - 1];
        size--;

        if (index < size) {
            heapifyDown(index);
            if (heap[index].getCostoEvento() < heap[(index - 1) / 2].getCostoEvento()) {
                heapifyUp(index);
            }
        }

        return removedEvento;
    }

    public Evento extractMax() {
        if (isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }

        Evento max = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapifyDown(0);

        return max;
    }

    private void heapifyUp(int index) {
        int parentIndex = (index - 1) / 2;
        while (index > 0 && heap[index].getCostoEvento() > heap[parentIndex].getCostoEvento()) {
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

            if (leftChildIndex < size && heap[leftChildIndex].getCostoEvento() > heap[largest].getCostoEvento()) {
                largest = leftChildIndex;
            }

            if (rightChildIndex < size && heap[rightChildIndex].getCostoEvento() > heap[largest].getCostoEvento()) {
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
    }

    public Evento[] getHeap() {
        return heap;
    }

}
