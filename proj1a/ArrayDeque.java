public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T[]) new Object[100];
        size = 0;
        nextFirst = 0;
        nextLast = 0;
    }

    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        System.arraycopy(items, 0, a, 0, size);
        items = a;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(T item) {
        items[nextFirst] = item;
        nextFirst = (nextFirst + items.length - 1) % items.length;
        size++;
    }

    public void printDeque() {
        for (int i = nextFirst; i < size; i++) {
            System.out.print(items[i]);
            System.out.print(" ");
        }
    }

    public void addLast(T item) {
        items[nextLast] = item;
        nextLast = (nextLast + 1) % items.length;
        size++;
    }

    public T get(int i) {
        return items[(nextFirst + i + 1) % items.length];
    }

    public int size() {
        return size;
    }

    public T removeFirst() {
        T x = get(0);
        nextFirst = (nextFirst + 1) % items.length;


        if (size / items.length < 0.25 && size != 0) {
            resize( items.length / 2);
        }

        return x;
    }

    public T removeLast() {
        T x = get(size - 1);

        nextLast = (nextLast + items.length - 1) % items.length;

        if (size / items.length < 0.25 && size != 0) {
            resize( items.length / 2);
        }

        return x;
    }
    
}
