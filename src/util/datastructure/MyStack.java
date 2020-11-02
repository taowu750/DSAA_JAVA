package util.datastructure;

import util.exception.CapacityOverflowException;

import java.util.Arrays;
import java.util.Iterator;

/**
 * 栈。允许插入 null 值。
 *
 * @author wutao
 */
@SuppressWarnings("unchecked")
public class MyStack<T> implements Iterable<T> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final int GROWTH_FACTOR = 2;


    private int size;
    private int capacity;
    private Object[] items;


    public MyStack() {
        size = 0;
        capacity = DEFAULT_CAPACITY;
        items = new Object[capacity];
    }

    public MyStack(int initialCapacity) {
        if (initialCapacity > 0) {
            size = 0;
            capacity = DEFAULT_CAPACITY;
            items = new Object[capacity];
        } else {
            throw new IllegalArgumentException("Parameter \"initialCapacity\" cannot be less than or equal to 0!");
        }
    }


    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public T top() {
        if (size > 0) {
            return (T) items[size - 1];
        } else {
            return null;
        }
    }

    public T pop() {
        if (size > 0) {
            T result = (T) items[size - 1];
            items[--size] = null;

            return result;
        } else {
            return null;
        }
    }

    public void push(T item) {
        ensureCapacity();
        items[size++] = item;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            items[i] = null;
        }
        size = 0;
    }


    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int size = MyStack.this.size;

            @Override
            public boolean hasNext() {
                return size > 0;
            }

            @Override
            public T next() {
                return (T) items[--size];
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
            }
        } else {
            throw new CapacityOverflowException("The capacity of this stack has reached the maximum limit!");
        }
    }

    private void changeCapacity(int newCapacity) {
        items = Arrays.copyOf(items, newCapacity);
        capacity = newCapacity;
    }
}
