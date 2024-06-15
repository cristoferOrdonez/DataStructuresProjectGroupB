package com.example.datastructureproject_groupb.ImplementacionesEstructurasDeDatos;

public class MaxHeapWithArrays{
private int[] heap;
private int size;

public MaxHeapWithArrays(int capacity) {
    heap = new int[capacity];
    size = 0;
}

public boolean isEmpty() {
    return size == 0;
}

public int getSize() {
    return size;
}

public void insert(int value) {
    if (size == heap.length) {
        throw new IllegalStateException("Heap is full");
    }

    heap[size] = value;
    size++;
    heapifyUp(size - 1);
}

public int remove(int value) {
    int index = -1;
    for (int i = 0; i < size; i++) {
        if (heap[i] == value) {
            index = i;
            break;
        }
    }

    if (index == -1) {
        throw new IllegalArgumentException("Value not found in the heap");
    }

    int removedValue = heap[index];
    heap[index] = heap[size - 1];
    size--;

    if (index < size) {
        heapifyDown(index);
        if (heap[index] < heap[(index - 1) / 2]) {
            heapifyUp(index);
        }
    }

    return removedValue;
}

public int extractMax() {
    if (isEmpty()) {
        throw new IllegalStateException("Heap is empty");
    }

    int max = heap[0];
    heap[0] = heap[size - 1];
    size--;
    heapifyDown(0);

    return max;
}

private void heapifyUp(int index) {
    int parentIndex = (index - 1) / 2;
    while (index > 0 && heap[index] > heap[parentIndex]) {
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

        if (leftChildIndex < size && heap[leftChildIndex] > heap[largest]) {
            largest = leftChildIndex;
        }

        if (rightChildIndex < size && heap[rightChildIndex] > heap[largest]) {
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
    int temp = heap[i];
    heap[i] = heap[j];
    heap[j] = temp;
}

public int[] getHeap() {
    return heap;
}
}
