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
        if (size != 0) {
            ItemNode newNode = new ItemNode(item, sentinel.next, sentinel);
            sentinel.next.prev = newNode;
            sentinel.next = newNode;
        } else {
            ItemNode newNode = new ItemNode(item, sentinel, sentinel);
            sentinel.next = newNode;
            sentinel.prev = newNode;
        }
        size++;
    }

    public void addLast(T item) {
        if (size != 0) {
            ItemNode newNode = new ItemNode(item, sentinel, sentinel.prev);
            sentinel.prev.next = newNode;
            sentinel.prev = newNode;
        } else {
            ItemNode newNode = new ItemNode(item, sentinel, sentinel);
            sentinel.prev = newNode;
            sentinel.next = newNode;
        }
        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void printDeque() {
        ItemNode ptr = sentinel;
        while (size != 0) {
            System.out.print(ptr.next.item);
            System.out.print(" ");
            ptr = ptr.next;
            size--;
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
