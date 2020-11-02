package util.datastructure;

import util.exception.CapacityOverflowException;

import java.util.*;

/**
 * 实现LinkedList
 *
 * @author wutao
 */
public class MyLinkedList<T> implements List<T> {
    private int  size;
    private int  modCount;
    private Node head;
    private Node tail;


    public MyLinkedList() {
        size = 0;
        modCount = 0;
        head = new Node(null, null, null);
        tail = new Node(null, head, null);
        head.next = tail;
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
        Node find = head.next;

        if (o != null) {
            while (find != tail) {
                if (find.item != null && find.item.equals(o)) {
                    return true;
                }

                find = find.next;
            }
        } else {
            while (find != tail) {
                if (find.item == null) {
                    return true;
                }

                find = find.next;
            }
        }

        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iter();
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
    public boolean add(T t) {
        checkSize();

        addBefore(t, tail);

        return true;
    }

    @Override
    public boolean remove(Object o) {
        Node p = head.next;

        if (o != null) {
            while (p != tail) {
                if (p.item != null && p.item.equals(o)) {
                    removeNode(p);

                    return true;
                }

                p = p.next;
            }
        } else {
            while (p != tail) {
                if (p.item == null) {
                    removeNode(p);

                    return true;
                }

                p = p.next;
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
                        if (item != null && item.equals(o)) {
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
        Node remove = head.next, next;

        while (remove != tail) {
            next = remove.next;
            remove.item = null;
            remove.prev = null;
            remove.next = null;
            remove = next;
        }
        head.next = tail;
        tail.next = head;

        size = 0;
        modCount++;
    }

    @Override
    public T get(int index) {
        checkIndex(index);

        return getNode(index).item;
    }

    @Override
    public T set(int index, T element) {
        checkIndex(index);

        Node seted = getNode(index);
        T    old   = seted.item;
        seted.item = element;

        return old;
    }

    @Override
    public void add(int index, T element) {
        checkSize();
        checkIndex(index);

        addBefore(element, getNode(index));
    }

    @Override
    public T remove(int index) {
        checkIndex(index);

        return removeNode(getNode(index));
    }

    @Override
    public int indexOf(Object o) {
        Node find = head.next;

        if (o != null) {
            for (int i = 0; i < size; i++) {
                if (find.item != null && find.item.equals(o)) {
                    return i;
                }
                find = find.next;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (find.item == null) {
                    return i;
                }
                find = find.next;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        Node find = tail.prev;

        if (o != null) {
            for (int i = size - 1; i >= 0; i--) {
                if (find.item != null && find.item.equals(o)) {
                    return i;
                }
                find = find.prev;
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (find.item == null) {
                    return i;
                }
                find = find.prev;
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

    public void addFirst(T item) {
        checkSize();

        if (size > 0) {
            addBefore(item, head.next);
        } else {
            addBefore(item, tail);
        }
    }

    public T removeFirst() {
        if (size > 0) {
            return removeNode(head.next);
        }

        return null;
    }

    public void addLast(T item) {
        checkSize();

        addBefore(item, tail);
    }

    public T removeLast() {
        if (size > 0) {
            return removeNode(tail.prev);
        }

        return null;
    }

    public T getFirst() {
        if (size > 0) {
            return head.next.item;
        }

        return null;
    }

    public T getLast() {
        if (size > 0) {
            return tail.prev.item;
        }

        return null;
    }

    /**
     * sec2_2_18: 链表排序。
     */
    public void sort() {
        if (size < 2 || !Comparable.class.isInstance(head.next.item)) {
            return;
        }

        for (int sz = 1; sz < size; sz += sz) {
            for (int lo = 0; lo < size - sz; lo += sz + sz) {
                // FIXME: 简单的实现，实际上可以通过 loNode 获取 midNode 和 hiNode
                Node le = getNode(lo), ri = getNode(lo + sz - 1).next;
                Node hiNextNode = getNode(Math.min(lo + sz * 2 - 1, size - 1)).next;

                // 如果右半部分的最小值大于等于左半部分的最大值，就不用排序了
                //noinspection unchecked
                if (((Comparable) ri.item).compareTo(ri.prev.item) >= 0)
                    continue;

                while (le != ri && ri != hiNextNode) {
                    Comparable l = (Comparable) le.item;
                    Comparable r = (Comparable) ri.item;
                    // 如果左边的节点小于右边，那么只移动左边节点；
                    // 如果右边的节点小于左边，将右边的节点插入到左边前面，然后右边节点移动
                    //noinspection unchecked
                    if (l.compareTo(r) < 0) {
                        le = le.next;
                    } else {
                        Node temp = ri.next;
                        ri.prev.next = ri.next;
                        ri.next.prev = ri.prev;
                        ri.next = le;
                        ri.prev = le.prev;
                        le.prev.next = ri;
                        le.prev = ri;
                        ri = temp;
                    }
                }
            }
        }
    }

    /**
     * sec2_2_18: 打乱链表。实现一个分治算法，使用线性对数级别的时间和对数级别的
     * 额外空间随机打乱一条链表。
     */
    public void shuffle() {
        // 自底向上

        // 循环: 选择分块的大小，从 1 到size - 1
        for (int sz = 1; sz < size; sz++) {
            // 循环: 选择每块的开始
            for (int lo = 0; lo < size - sz; lo += sz * 2) {
                // 随机选取 sz 数量的左半部分和右半部分的元素交换
                int mid = lo + sz - 1, hi = Math.min(lo + sz * 2 - 1, size - 1);

            }
        }
    }


    private class Node {
        T    item;
        Node prev;
        Node next;

        Node(T item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    private class Iter implements Iterator<T> {
        private Node    currentNode      = head.next;
        private int     expectedModCount = modCount;
        private boolean okToRemove       = false;


        @Override
        public boolean hasNext() {
            return currentNode != tail;
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
            T item = currentNode.item;
            currentNode = currentNode.next;

            return item;
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

            MyLinkedList.this.removeNode(currentNode.prev);
            expectedModCount++;
            okToRemove = false;
        }
    }

    private class ListIter implements ListIterator<T> {
        private Node currentNode      = head.next;
        private int  currentIndex     = 0;
        private int  expectedModCount = modCount;
        /**
         * 等于0表示没有进行next或previous操作；<br/>
         * 等于1表示进行了next操作；<br/>
         * 等于2表示进行了previous操作
         */
        private int  nextOrPrevious   = 0;


        public ListIter() {
        }

        public ListIter(int index) {
            if (index < 0 || index > size) {
                throw new IllegalArgumentException("index = " + index + " -> The parameter \"index\" must be greater " +
                        "than 0 and less than or equal to the number of elements in the list!");
            }

            currentIndex = index;
            currentNode = getNode(index);
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

            T item = currentNode.item;
            currentNode = currentNode.next;
            nextOrPrevious = 1;
            currentIndex++;

            return item;
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
            currentNode = currentNode.prev;
            nextOrPrevious = 2;
            currentIndex--;

            return currentNode.item;
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
                MyLinkedList.this.removeNode(currentNode.prev);
                currentIndex--;
            } else {
                Node removed = currentNode;
                currentNode = currentNode.next;
                MyLinkedList.this.removeNode(removed);
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
                currentNode.prev.item = t;
            } else {
                currentNode.item = t;
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
            MyLinkedList.this.addBefore(t, currentNode);
            currentIndex++;
        }
    }


    private Node addBefore(T item, Node pn) {
        Node newNode = new Node(item, pn.prev, pn);

        pn.prev.next = newNode;
        pn.prev = newNode;

        size++;
        modCount++;

        return newNode;
    }

    private Node getNode(int index) {
        Node p;

        if (index < size / 2) {
            p = head.next;

            for (int i = 0; i < index; i++) {
                p = p.next;
            }
        } else {
            p = tail;

            for (int i = size; i > index; i--) {
                p = p.prev;
            }
        }

        return p;
    }

    private T removeNode(Node node) {
        node.next.prev = node.prev;
        node.prev.next = node.next;

        node.prev = null;
        node.next = null;

        size--;
        modCount++;

        return node.item;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("index = " + index + " -> The parameter \"index\" must be greater than" +
                    " 0 and less than the number of elements in the list!");
        }
    }

    private void checkSize() {
        if (size == Integer.MAX_VALUE) {
            throw new CapacityOverflowException("The capacity of this list has reached the maximum limit!");
        }
    }
}
