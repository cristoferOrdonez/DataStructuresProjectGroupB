package com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos;

public class MinHeapWithArrays {
    private int[] heap;
    private int size;
    private int capacity;

    public MinHeapWithArrays(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.heap = new int[capacity];
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
        int temp = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = temp;
    }

    private void heapifyUp() {
        int index = size - 1;
        while (hasParent(index) && heap[index] < heap[getParentIndex(index)]) {
            swap(index, getParentIndex(index));
            index = getParentIndex(index);
        }
    }

    private void heapifyDown() {
        int index = 0;
        while (hasLeftChild(index)) {
            int smallerChildIndex = getLeftChildIndex(index);
            if (hasRightChild(index) && heap[getRightChildIndex(index)] < heap[smallerChildIndex]) {
                smallerChildIndex = getRightChildIndex(index);
            }

            if (heap[index] < heap[smallerChildIndex]) {
                break;
            } else {
                swap(index, smallerChildIndex);
            }

            index = smallerChildIndex;
        }
    }

    public void insert(int value) {
        if (size == capacity) {
            throw new IllegalStateException("Heap is full");
        }

        heap[size] = value;
        size++;
        heapifyUp();
    }

    public void remove(int value) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (heap[i] == value) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            throw new IllegalArgumentException("Value not found in heap");
        }

        heap[index] = heap[size - 1];
        size--;
        heapifyUp();
        heapifyDown();
    }

    public int extractMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }

        int min = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapifyDown();

        return min;
    }

    public int peekMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }

        return heap[0];
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
