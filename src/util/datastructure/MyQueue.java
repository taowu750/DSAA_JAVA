package util.datastructure;

import util.exception.CapacityOverflowException;

import java.util.*;

/**
 * 队列。允许插入 null 值。
 *
 * @author wutao
 */
@SuppressWarnings({"unchecked", "Duplicates"})
public class MyQueue<T> implements Queue<T> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final int GROWTH_FACTOR = 2;


    private int size;
    private int capacity;
    private int head;
    private int tail;
    private Object[] items;


    public MyQueue() {
        size = 0;
        head = -1;
        tail = -1;
        capacity = DEFAULT_CAPACITY;
        items = new Object[capacity];
    }

    public MyQueue(int initialCapacity) {
        if (initialCapacity > 0) {
            size = 0;
            head = -1;
            tail = -1;
            capacity = initialCapacity;
            items = new Object[capacity];
        } else {
            throw new IllegalArgumentException("Parameter \"initialCapacity\" cannot be less than or equal to 0!");
        }
    }


    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean add(T t) {
        ensureCapacity();
        tail = (tail + 1) % capacity;
        items[tail] = t;
        size++;

        return true;
    }

    @Override
    public boolean offer(T t) {
        ensureCapacity();
        tail = (tail + 1) % capacity;
        items[tail] = t;
        size++;

        return true;
    }

    @Override
    public T remove() {
        if (size > 0) {
            head = (head + 1) % capacity;
            T result = (T) items[head];
            items[head] = null;
            size--;

            return result;
        } else {
            throw new NoSuchElementException("The stack is empty!");
        }
    }

    @Override
    public T poll() {
        if (size > 0) {
            head = (head + 1) % capacity;
            T result = (T) items[head];
            items[head] = null;
            size--;

            return result;
        } else {
            return null;
        }
    }

    @Override
    public T element() {
        if (size > 0) {
            return (T) items[(head + 1) % capacity];
        } else {
            throw new NoSuchElementException("The stack is empty!");
        }
    }

    @Override
    public T peek() {
        if (size > 0) {
            return (T) items[(head + 1) % capacity];
        } else {
            return null;
        }
    }

    @Override
    public void clear() {
        int count = 0;
        int i = head;
        while (count++ < size) {
            i = (i + 1) % capacity;
            items[i] = null;
        }
        head = tail = -1;
        size = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int current = head;
            int size = MyQueue.this.size;

            @Override
            public boolean hasNext() {
                return size > 0;
            }

            @Override
            public T next() {
                current = (current + 1) % capacity;
                size--;

                return (T) items[current];
            }
        };
    }


    private void ensureCapacity() {
        if (capacity != Integer.MAX_VALUE) {
            if (size == capacity) {
                if (capacity < Integer.MAX_VALUE / GROWTH_FACTOR) {
                    changeCapacity(capacity * GROWTH_FACTOR);
                } else {
                    changeCapacity(Integer.MAX_VALUE);
                }

                if (head >= tail) {
                    int length = size - head - 1;
                    System.arraycopy(items, head + 1, items, capacity - length, length);
                    head = capacity - length - 1;
                }
            }
        } else {
            throw new CapacityOverflowException("The capacity of this stack has reached the maximum limit!");
        }
    }

    private void changeCapacity(int newCapacity) {
        items = Arrays.copyOf(items, newCapacity);
        capacity = newCapacity;
    }


    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }
}
