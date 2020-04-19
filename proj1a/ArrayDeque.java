public class ArrayDeque<Item> {
    private Item[] items;
    private int size;
    private int start;

    public AList() {
        items = (Item[]) new Object[100];
        size = 0;
        start = 0;
    }

    private void resize(int capacity) {
        Item[] a = (Item[]) new Object[capacity];
        System.arraycopy(items, 0, a, 0, size);
        items = a;
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void printDeque() {
        for (int i = start; i < size; i++) {
            System.out.print(items[i]);
            System.out.print(" ");
        }
    }

    public void addLast(Item x) {
        if (size == items.length) {
            resize(size * 2);
        }

        items[start + size] = x;
        size = size + 1;
    }

    public Item get(int i) {
        return items[start + i];
    }

    public int size() {
        return size;
    }

    public Item removeFirst() {
        if (size == 0) {
            return null;
        }

        start ++;


    }

    public Item removeLast() {
        if (size == 0) {
            return null;
        }
        Item x = get(size - 1);
        items[start + size - 1] = null;
        size = size - 1;
        return x;
    }
}