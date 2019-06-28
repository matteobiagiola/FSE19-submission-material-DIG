package org.evosuite.ga.metaheuristics.art.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

// In parts copied from CircularFifoQueue implementation in Apache commons https://commons.apache.org/proper/commons-collections/jacoco/org.apache.commons.collections4.queue/CircularFifoQueue.java.html

public class CircularFifoQueue<T> implements List<T> {

    /** Serialization version. */
    private static final long serialVersionUID = -8423413834657610406L;

    /** Underlying storage array. */
    private transient T[] elements;

    /** Array index of first (oldest) queue element. */
    private transient int start = 0;

    /**
     * Index mod maxElements of the array position following the last queue
     * element.  Queue elements start at elements[start] and "wrap around"
     * elements[maxElements-1], ending at elements[decrement(end)].
     * For example, elements = {c,a,b}, start=1, end=1 corresponds to
     * the queue [a,b,c].
     */
    private transient int end = 0;

    /** Flag to indicate if the queue is currently full. */
    private transient boolean full = false;

    /** Capacity of the queue. */
    private final int maxElements;

    /**
     * Constructor that creates a queue with the default size of 32.
     */
    public CircularFifoQueue() {
        this(32);
    }

    /**
     * Constructor that creates a queue with the specified size.
     *
     * @param size  the size of the queue (cannot be changed)
     * @throws IllegalArgumentException  if the size is &lt; 1
     */
    @SuppressWarnings("unchecked")
    public CircularFifoQueue(final int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("The size must be greater than 0");
        }
        elements = (T[]) new Object[size];
        maxElements = elements.length;
    }


    //-----------------------------------------------------------------------
    /**
     * Returns the number of elements stored in the queue.
     *
     * @return this queue's size
     */
    @Override
    public int size() {
        int size = 0;

        if (end < start) {
            size = maxElements - start + end;
        } else if (end == start) {
            size = full ? maxElements : 0;
        } else {
            size = end - start;
        }

        return size;
    }

    /**
     * Returns true if this queue is empty; false otherwise.
     *
     * @return true if this queue is empty
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException("contains is not implemented yet in CircularFifoQueue");
    }

    /**
     * {@inheritDoc}
     * <p>
     * A {@code CircularFifoQueue} can never be full, thus this returns always
     * {@code false}.
     *
     * @return always returns {@code false}
     */
    public boolean isFull() {
        return false;
    }

    /**
     * Returns {@code true} if the capacity limit of this queue has been reached,
     * i.e. the number of elements stored in the queue equals its maximum size.
     *
     * @return {@code true} if the capacity limit has been reached, {@code false} otherwise
     * @since 4.1
     */
    public boolean isAtFullCapacity() {
        return size() == maxElements;
    }

    /**
     * Gets the maximum size of the collection (the bound).
     *
     * @return the maximum number of elements the collection can hold
     */
    public int maxSize() {
        return maxElements;
    }

    /**
     * Clears this queue.
     */
    @Override
    public void clear() {
        full = false;
        start = 0;
        end = 0;
        Arrays.fill(elements, null);
    }

    /**
     * Adds the given element to this queue. If the queue is full, the least recently added
     * element is discarded so that a new element can be inserted.
     *
     * @param element  the element to add
     * @return true, always
     * @throws NullPointerException  if the given element is null
     */
    @Override
    public boolean add(final T element) {
        if (null == element) {
            throw new NullPointerException("Attempted to add null object to queue");
        }

        if (isAtFullCapacity()) {
            remove();
        }

        elements[end++] = element;

        if (end >= maxElements) {
            end = 0;
        }

        if (end == start) {
            full = true;
        }

        return true;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("remove is not implemented yet in CircularFifoQueue");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("containsAll is not implemented yet in CircularFifoQueue");
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException("addAll is not implemented yet in CircularFifoQueue");
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException("addAll is not implemented yet in CircularFifoQueue");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("removeAll is not implemented yet in CircularFifoQueue");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("retainAll is not implemented yet in CircularFifoQueue");
    }

    /**
     * Returns the element at the specified position in this queue.
     *
     * @param index the position of the element in the queue
     * @return the element at position {@code index}
     * @throws NoSuchElementException if the requested position is outside the range [0, size)
     */
    @Override
    public T get(final int index) {
        final int sz = size();
        if (index < 0 || index >= sz) {
            throw new NoSuchElementException(
                    String.format("The specified index (%1$d) is outside the available range [0, %2$d)",
                            Integer.valueOf(index), Integer.valueOf(sz)));
        }

        final int idx = (start + index) % maxElements;
        return elements[idx];
    }

    @Override
    public T set(int index, T element) {
        throw new UnsupportedOperationException("set is not implemented yet in CircularFifoQueue");
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException("add is not implemented yet in CircularFifoQueue");
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException("remove is not implemented yet in CircularFifoQueue");
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException("indexOf is not implemented yet in CircularFifoQueue");
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("lastIndexOf is not implemented yet in CircularFifoQueue");
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException("listIterator is not implemented yet in CircularFifoQueue");
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException("listIterator is not implemented yet in CircularFifoQueue");
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("subList is not implemented yet in CircularFifoQueue");
    }

    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("queue is empty");
        }

        final T element = elements[start];
        if (null != element) {
            elements[start++] = null;

            if (start >= maxElements) {
                start = 0;
            }
            full = false;
        }
        return element;
    }

    /**
     * Increments the internal index.
     *
     * @param index  the index to increment
     * @return the updated index
     */
    private int increment(int index) {
        index++;
        if (index >= maxElements) {
            index = 0;
        }
        return index;
    }

    /**
     * Decrements the internal index.
     *
     * @param index  the index to decrement
     * @return the updated index
     */
    private int decrement(int index) {
        index--;
        if (index < 0) {
            index = maxElements - 1;
        }
        return index;
    }

    /**
     * Returns an iterator over this queue's elements.
     *
     * @return an iterator over this queue's elements
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private int index = start;
            private int lastReturnedIndex = -1;
            private boolean isFirst = full;

            @Override
            public boolean hasNext() {
                return isFirst || index != end;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                isFirst = false;
                lastReturnedIndex = index;
                index = increment(index);
                return elements[lastReturnedIndex];
            }

            @Override
            public void remove() {
                if (lastReturnedIndex == -1) {
                    throw new IllegalStateException();
                }

                // First element can be removed quickly
                if (lastReturnedIndex == start) {
                    CircularFifoQueue.this.remove();
                    lastReturnedIndex = -1;
                    return;
                }

                int pos = lastReturnedIndex + 1;
                if (start < lastReturnedIndex && pos < end) {
                    // shift in one part
                    System.arraycopy(elements, pos, elements, lastReturnedIndex, end - pos);
                } else {
                    // Other elements require us to shift the subsequent elements
                    while (pos != end) {
                        if (pos >= maxElements) {
                            elements[pos - 1] = elements[0];
                            pos = 0;
                        } else {
                            elements[decrement(pos)] = elements[pos];
                            pos = increment(pos);
                        }
                    }
                }

                lastReturnedIndex = -1;
                end = decrement(end);
                elements[end] = null;
                full = false;
                index = decrement(index);
            }

        };
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("toArray is not implemented yet in CircularFifoQueue");
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException("toArray is not implemented yet in CircularFifoQueue");
    }

}
