public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T[]) new Object[100];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        if (nextFirst > nextLast) {
            System.arraycopy(items, nextFirst, a, 0, items.length - nextFirst);
            System.arraycopy(items, 0, a, items.length - nextFirst, size + 2 - items.length + nextFirst);
        } else {
            System.arraycopy(items, nextFirst, a, 0, size + 2);
        }
        nextFirst = 0;
        nextLast = size + 1;
        items = a;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(T item) {
        if (size >= items.length - 3) {
            resize(items.length * 2);
        }
        items[nextFirst] = item;
        nextFirst = (nextFirst + items.length - 1) % items.length;
        size++;
        if ((float) size / items.length < 0.25 && size != 0) {
            resize(items.length / 2);
        }
    }

    public void printDeque() {
        for (int i = (nextFirst + 1) % items.length; i != nextLast; i = (i + 1) % items.length) {
            System.out.print(items[i]);
            System.out.print(" ");
        }
        System.out.println();
    }

    public void addLast(T item) {
        if (size >= items.length - 3) {
            resize(items.length * 2);
        }
        items[nextLast] = item;
        nextLast = (nextLast + 1) % items.length;
        size++;
        if ((float) size / items.length < 0.25 && size != 0) {
            resize(items.length / 2);
        }
    }

    public T get(int i) {
        if (i >= size) {
            return null;
        }
        return items[(nextFirst + i + 1) % items.length];
    }

    public int size() {
        return size;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }

        T x = get(0);
        nextFirst = (nextFirst + 1) % items.length;

        size--;

        if ((float) size / items.length < 0.25 && size != 0) {
            resize(items.length / 2);
        }

        return x;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T x = get(size - 1);
        nextLast = (nextLast + items.length - 1) % items.length;

        size--;
        if ((float) size / items.length < 0.25 && size != 0) {
            resize(items.length / 2);
        }

        return x;
    }


}
