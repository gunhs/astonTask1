import java.util.*;
import java.util.function.Consumer;

public class MyArrayList<E> implements MyList<E> {

    public static final String OUT_OF_BOUND_ERR_MSG = "Вы достигли предела";
    /**
     * <p>Динамический массив, имплементирующий интерфейс MyList.</p>
     * <p>Реализует все методы работы со списками, может хранить все типы данных, кроме примитивных, включая null.
     * В дополнение к реализации интерфейса MyList этот класс предоставляет методы для манипулирования размером массива,
     * который используется внутри для хранения списка.</p>
     * <p>Обратите внимание, что эта реализация не синхронизирована.Требуется синхронизация извне</p>
     * <p>Операции size, isEmpty, get (по индексу), set, remove (по индексу) выполняются за постоянное время.
     * Операция добавления в конец списка выполняется за константу O(1), в середину требуется O(n) времени.
     * Операция remove выполняется за время O(n)
     * Все операции выполняются за линейное время.</p>
     * <p>Каждый экземпляр MyArrayList имеет начальный размер (ёмкость) 10. Динамически увеличивается
     * на 0,75 при достижении максимума</p>
     */

    private Object[] array;
    private int size;
    private int capacity = 10;

    public MyArrayList() {
        array = new Object[capacity];
    }

    public int size() {
        return size;
    }

    private void grow() {
        capacity += array.length * 0.75;
        if (capacity < 0) capacity = Integer.MAX_VALUE;
        Object[] newArray = new Object[capacity];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

    private void checkSize() {
        if (size == Integer.MAX_VALUE) throw new ArrayIndexOutOfBoundsException(OUT_OF_BOUND_ERR_MSG);
        if (array.length == size) {
            grow();
        }
    }

    private void checkIndex(int index) {
        if (index > size || index < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public void add(E element) {
        checkSize();
        array[size] = element;
        size++;
    }

    public void add(E element, int index) {
        checkIndex(index);
        if (index == size) {
            add(element);
            return;
        }
        checkSize();
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = element;
        size++;
    }

    @SuppressWarnings("unchecked")
    public E get(int index) {
        checkIndex(index);
        return (E) array[index];
    }

    public void set(E element, int index) {
        checkIndex(index);
        array[index] = element;
    }

    public void remove(E element) {
        for (int i = 0; i < size; i++) {
            if (array[i] != null && array[i].equals(element)) {
                delete(i);
                return;
            }
        }
    }

    public void remove(int index) {
        checkIndex(index);
        delete(index);
    }

    private void delete(int index) {
        Object[] newArray = new Object[size - 1];
        System.arraycopy(array, 0, newArray, 0, index);
        System.arraycopy(array, index + 1, newArray, index, size - (index + 1));
        array = newArray;
        size--;
    }

    public void clear() {
        array = new Object[array.length];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(E element) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(element)) {
                return true;
            }
        }
        return false;
    }

    public Iterator<E> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<E> {
        int cursor;
        int lastRet = -1;

        Itr() {
        }

        public boolean hasNext() {
            return cursor != size;
        }

        @SuppressWarnings("unchecked")
        public E next() {
            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();
            if (i >= array.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return (E) array[lastRet = i];
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            try {
                MyArrayList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @SuppressWarnings("unchecked")
        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            Objects.requireNonNull(action);
            int i = cursor;
            if (i < size) {
                if (i >= array.length)
                    throw new ConcurrentModificationException();
                for (; i < size; i++)
                    action.accept((E) array[i]);
                cursor = i;
                lastRet = i - 1;
            }
        }
    }

    /**
     * Сравнивает два списка по содержимому.
     *
     * @return возвращает true, если все элементы коллекции равны.
     */
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof MyArrayList)) {
            return false;
        }
        if (((MyArrayList<?>) o).size != size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (array[i] != ((MyArrayList<?>) o).get(i)) {
                return false;
            }
        }
        return true;
    }


    /**
     * Сортирует список. Средняя сложность выполнения O(log n).
     * Элементы списка должны быть сравниваемыми
     */
    public void sort() {
        quickSort(array, 0, size - 1);
    }

    private void quickSort(Object[] array, int l, int r) {
        if (l > r) {
            return;
        }
        int pivot = partition(array, l, r);
        quickSort(array, l, pivot - 1);
        quickSort(array, pivot + 1, r);
    }

    @SuppressWarnings("unchecked")
    private int partition(Object[] array, int l, int r) {
        var pivot = array[r];
        int ptr = l - 1;
        for (int i = l; i < r; i++) {
            if (((Comparable<? super E>) pivot).compareTo((E) array[i]) > 0) {
                ptr++;
                swap(ptr, i, array);
            }
        }
        swap(ptr + 1, r, array);
        return ptr + 1;
    }

    private void swap(int ptr, int i, Object[] array) {
        var temp = array[ptr];
        array[ptr] = array[i];
        array[i] = temp;
    }

}
