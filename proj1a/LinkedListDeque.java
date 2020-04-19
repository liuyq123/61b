public class LinkedListDeque<T> {
    private class ItemNode {
        private T item;
        private ItemNode next;
        private ItemNode prev;

        ItemNode(T i, ItemNode n, ItemNode p) {
            item = i;
            next = n;
            prev = p;
        }
    }

    private ItemNode sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new ItemNode(null, null, null);
        size = 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(T item) {
        sentinel.next = new ItemNode(item, null, sentinel);
        size++;
    }

    public void addLast(T item) {
        sentinel.prev = new ItemNode(item, sentinel, null);
        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void printDeque() {
        ItemNode ptr = sentinel;
        while (ptr.next != null) {
            System.out.print(ptr.next.item);
            System.out.print(" ");
            ptr = ptr.next;
        }
    }

    public T removeFirst() {
        T first = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        size--;
        return first;
    }


    public T removeLast() {
        T last = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        size--;
        return last;
    }

    public T get(int index) {
        ItemNode ptr = sentinel.next;
        if (index > size - 1) {
            return null;
        }

        for (int i = 0; i < index; i++) {
            ptr = ptr.next;
        }

        return ptr.item;
    }

    public T getRecursive(int index) {
        if (index == 0) {
            return sentinel.next.item;
        }
        return getRecursive(index - 1);
    }
}