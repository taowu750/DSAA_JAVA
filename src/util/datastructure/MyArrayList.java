package util.datastructure;

import util.exception.CapacityOverflowException;

import java.util.*;

/**
 * 实现ArrayList
 *
 * @author wutao
 */
@SuppressWarnings("unchecked")
public class MyArrayList<T> implements List<T> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final int GROWTH_FACTOR = 2;

    private int capacity;
    private int size;
    private int modCount;
    private Object[] items;


    public MyArrayList() {
        capacity = DEFAULT_CAPACITY;
        size = 0;
        modCount = 0;
        items = new Object[capacity];
    }

    public MyArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            capacity = initialCapacity;
            size = 0;
            modCount = 0;
            items = new Object[capacity];
        } else {
            throw new IllegalArgumentException("Parameter \"initialCapacity\" cannot be less than or equal to 0!");
        }
    }

    public MyArrayList(Iterable<T> iterable) {
        this();
        for (T t : iterable) {
            add(t);
        }
    }

    public MyArrayList(T[] items) {
        this();
        for (T item : items) {
            add(item);
        }
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iter();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(items, size);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < size) {
            return (T1[]) Arrays.copyOf(items, size, a.getClass());
        }
        System.arraycopy(items, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

    @Override
    public boolean add(T t) {
        if (!ensureCapacity()) {
            return false;
        }
        items[size++] = t;
        modCount++;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o != null) {
            for (int i = 0; i < size; i++) {
                if (o.equals(items[i])) {
                    System.arraycopy(items, i + 1, items, i, size - 1 - i);
                    modCount++;
                    size--;

                    return true;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (items[i] == null) {
                    System.arraycopy(items, i + 1, items, i, size - 1 - i);
                    modCount++;
                    size--;

                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c != null) {
            for (T item : c) {
                add(item);
            }
        } else {
            throw new NullPointerException("The parameter \"c\" cannot be null!");
        }

        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c != null) {
            for (Object o : c) {
                Iterator<T> iter = iterator();

                if (o != null) {
                    while (iter.hasNext()) {
                        T item = iter.next();
                        if (o.equals(item)) {
                            iter.remove();
                        }
                    }
                } else {
                    while (iter.hasNext()) {
                        T item = iter.next();
                        if (item == null) {
                            iter.remove();
                        }
                    }
                }
            }
        } else {
            throw new NullPointerException("The parameter \"c\" cannot be null!");
        }

        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            items[i] = null;
        }
        modCount++;
        size = 0;
    }

    @Override
    public T get(int index) {
        checkIndex(index);

        return (T) items[index];
    }

    @Override
    public T set(int index, T element) {
        checkIndex(index);

        T old = (T) items[index];
        items[index] = element;

        return old;
    }

    @Override
    public void add(int index, T element) {
        checkIndex(index);

        if (ensureCapacity()) {
            System.arraycopy(items, index, items, index + 1, size - index);
            items[index] = element;
            modCount++;
            size++;
        } else {
            throw new CapacityOverflowException("The capacity of this list has reached the maximum limit!");
        }
    }

    @Override
    public T remove(int index) {
        checkIndex(index);

        T old = (T) items[index];
        System.arraycopy(items, index + 1, items, index, size - 1 - index);
        modCount++;
        size--;

        return old;
    }

    @Override
    public int indexOf(Object o) {
        if (o != null) {
            for (int i = 0; i < size; i++) {
                if (items[i] != null && items[i].equals(o)) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (items[i] == null) {
                    return i;
                }
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o != null) {
            for (int i = size - 1; i >= 0; i--) {
                if (o.equals(items[i])) {
                    return i;
                }
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (items[i] == null) {
                    return i;
                }
            }
        }

        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ListIter();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new ListIter(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[");

        for (T item : this) {
            s.append(item).append(", ");
        }
        if (size > 0) {
            s.delete(s.length() - 2, s.length());
        }
        s.append("]");

        return s.toString();
    }

    public void trimToSize() {
        if (size > 0) {
            changeCapacity(size);
        }
    }


    private class Iter implements Iterator<T> {
        private int currentIndex = 0;
        private int expectedModCount = modCount;
        private boolean okToRemove = false;


        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public T next() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException("You cannot change the structure of the list being " +
                        "iterated!");
            }
            if (!hasNext()) {
                throw new NoSuchElementException("Iteration to last element!");
            }
            okToRemove = true;
            return (T) items[currentIndex++];
        }

        @Override
        public void remove() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException("You cannot change the structure of the list being " +
                        "iterated!");
            }
            if (!okToRemove) {
                throw new IllegalStateException("The remove operation must be performed after the next operation!");
            }
            okToRemove = false;
            expectedModCount++;
            MyArrayList.this.remove(--currentIndex);
        }
    }

    private class ListIter implements ListIterator<T> {
        private int currentIndex = 0;
        private int expectedModCount = modCount;
        /**
         * 等于0表示没有进行next或previous操作；<br/>
         * 等于1表示进行了next操作；<br/>
         * 等于2表示进行了previous操作
         */
        private int nextOrPrevious = 0;


        public ListIter() {
        }

        public ListIter(int index) {
            if (index < 0 || index > size) {
                throw new IllegalArgumentException("index = " + index + " -> The parameter \"index\" must be greater " +
                        "than 0 and less than or equal to the number of elements in the list!");
            }

            currentIndex = index;
        }


        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public T next() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException("You cannot change the structure of the list being " +
                        "iterated!");
            }
            if (!hasNext()) {
                throw new NoSuchElementException("Iteration to last element!");
            }

            nextOrPrevious = 1;
            return (T) items[currentIndex++];
        }

        @Override
        public boolean hasPrevious() {
            return currentIndex > 0;
        }

        @Override
        public T previous() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException("You cannot change the structure of the list being " +
                        "iterated!");
            }
            if (!hasPrevious()) {
                throw new NoSuchElementException("Iteration to first element!");
            }

            nextOrPrevious = 2;

            return (T) items[--currentIndex];
        }

        @Override
        public int nextIndex() {
            return currentIndex;
        }

        @Override
        public int previousIndex() {
            return currentIndex - 1;
        }

        @Override
        public void remove() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException("You cannot change the structure of the list being " +
                        "iterated!");
            }
            if (nextOrPrevious == 0) {
                throw new IllegalStateException("The remove operation must be performed after the next/previous " +
                        "operation!");
            }

            if (nextOrPrevious == 1) {
                MyArrayList.this.remove(--currentIndex);
            } else {
                MyArrayList.this.remove(currentIndex);
            }
            expectedModCount++;
            nextOrPrevious = 0;
        }

        @Override
        public void set(T t) {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException("You cannot change the structure of the list being " +
                        "iterated!");
            }
            if (nextOrPrevious == 0) {
                throw new IllegalStateException("The set operation must be performed after the next/previous " +
                        "operation!");
            }

            if (nextOrPrevious == 1) {
                MyArrayList.this.set(currentIndex - 1, t);
            } else {
                MyArrayList.this.set(currentIndex, t);
            }
            nextOrPrevious = 0;
        }

        @Override
        public void add(T t) {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException("You cannot change the structure of the list being " +
                        "iterated!");
            }

            expectedModCount++;
            MyArrayList.this.add(currentIndex++, t);
        }
    }


    private boolean ensureCapacity() {
        if (capacity != Integer.MAX_VALUE) {
            if (size == capacity) {
                if (capacity < Integer.MAX_VALUE / GROWTH_FACTOR) {
                    changeCapacity(capacity * GROWTH_FACTOR);
                } else {
                    changeCapacity(Integer.MAX_VALUE);
                }
            }

            return true;
        }

        return false;
    }

    private void changeCapacity(int newCapacity) {
        items = Arrays.copyOf(items, newCapacity);
        capacity = newCapacity;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("index = " + index + " -> The parameter \"index\" must be greater than" +
                    " 0 and less than the number of elements in the list!");
        }
    }
}
