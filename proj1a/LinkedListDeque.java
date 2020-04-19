public class LinkedListDeque<Item> {
    private class ItemNode {
        private Item item;
        private ItemNode next;
        private ItemNode prev;

        ItemNode(Item i, ItemNode n, ItemNode p) {
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

    public void addFirst(Item item) {
        sentinel.next = new ItemNode(item, null, sentinel);
        size++;
    }

    public void addLast(Item item) {
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

    public Item removeFirst() {
        Item first = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        size--;
        return first;
    }


    public Item removeLast() {
        Item last = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        size--;
        return last;
    }

    public Item get(int index) {
        ItemNode ptr = sentinel.next;
        if (index > size - 1) {
            return null;
        }

        for (int i = 0; i < index; i++) {
            ptr = ptr.next;
        }

        return ptr.item;
    }
}