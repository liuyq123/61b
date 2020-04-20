public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int start;

    public ArrayDeque() {
        items = (T[]) new Object[100];
        size = 0;
        start = 0;
    }

    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        System.arraycopy(items, start, a, 0, size);
        items = a;
        start = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(T item) {
        if (start != 0) {
            items[start - 1] = item;
            size++;
            if (start != 0) {
                start--;
            }
        } else {
            T[] a = (T[]) new Object[items.length];
            a[0] = item;
            System.arraycopy(items, 0, a, 1, size);
            items = a;
            size++;
        }
    }

    public void printDeque() {
        for (int i = start; i < size; i++) {
            System.out.print(items[i]);
            System.out.print(" ");
        }
    }

    public void addLast(T x) {
        if (size + start == items.length && size != 0) {
            resize(size * 2);
        }

        items[start + size] = x;
        size = size + 1;
    }

    public T get(int i) {
        return items[start + i];
    }

    public int size() {
        return size;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T x = get(0);
        start++;
        size--;

        if (size / items.length < 0.25 && size != 0) {
            resize( items.length / 2);
        }

        return x;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T x = get(start + size - 1);
        size = size - 1;

        if (size / items.length < 0.25 && size != 0) {
            resize( items.length / 2);
        }

        return x;
    }

    /** public static void main(String[] args) {
        ArrayDeque<Integer> ArrayDeque = new ArrayDeque<>();
        ArrayDeque.addFirst(0);
        ArrayDeque.removeLast();
        ArrayDeque.addFirst(2);
        ArrayDeque.removeFirst();
        ArrayDeque.addFirst(4);
        ArrayDeque.removeFirst();
        ArrayDeque.addLast(6);
    }
     */
}
