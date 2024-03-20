package main;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;

public class MyArrayList<E> {

    private Object[] elements;
    private int size;

    public MyArrayList() {
        this.elements = new Object[10];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void add(int index, E element) {
        rangeCheckForAdd(index);

        if (size == elements.length) increaseCapacity();

        System.arraycopy(elements, index, elements, index + 1, size - index);

        elements[index] = element;
        size++;
    }

    public void addAll(Collection<? extends E> col) {
        Object[] newArray = col.toArray();

        if (size > elements.length + newArray.length) increaseCapacity(newArray.length);

        System.arraycopy(newArray, 0, elements, size, newArray.length);

        size += newArray.length;
    }

    public E get(int index) {
        rangeCheckForRemoveAndGet(index);

        return (E) elements[index];
    }

    public void remove(int index) {
        rangeCheckForRemoveAndGet(index);

        removeFast(index);
    }

    public void remove(E element) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(element, elements[i])) removeFast(i);
        }
    }

    public void clear() {
        Arrays.fill(elements, null);
        size = 0;
    }

    public void sort(Comparator<? super E> comparator) {
        quicksort(0, size - 1, comparator);
    }

    private void increaseCapacity() {
        increaseCapacity(0);
    }

    private void increaseCapacity(int capacity) {
        int newCapacity = (elements.length + capacity) * 2;
        elements = Arrays.copyOf(elements, newCapacity);
    }

    private void rangeCheckForAdd(int index) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    private void rangeCheckForRemoveAndGet(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    private void removeFast(int index) {
        int elementsMoved = size - index - 1;
        if (elementsMoved > 0) System.arraycopy(elements, index + 1, elements, index, elementsMoved);

        elements[--size] = null;
    }

    private void quicksort(int low, int high, Comparator<? super E> comparator) {
        if (low < high) {
            int pivot = partition(low, high, comparator);

            quicksort(low, pivot - 1, comparator);
            quicksort(pivot + 1, high, comparator);
        }
    }

    private int partition(int low, int high, Comparator<? super E> comparator) {
        E pivot = (E) elements[high];

        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (comparator.compare((E) elements[j], pivot) <= 0) {
                i++;
                E temp = (E) elements[i];
                elements[i] = elements[j];
                elements[j] = temp;
            }
        }

        E temp = (E) elements[i + 1];
        elements[i + 1] = elements[high];
        elements[high] = temp;

        return i + 1;
    }
}
