import java.util.Iterator;

// Implements a circular ArrayList
public class BrowserArrayList<AnyType> implements Iterable<AnyType> {
    private static final int DEFAULT_CAPACITY = 5;

    private int listSize = 0;
    private AnyType[] listItems;

    private int offset = 0;

    public BrowserArrayList() {
        clearList();
    }

    public void clear() {
        clearList();
    }

    private void clearList() {
        listSize = 0;
        offset = 0;
        ensureCapacity(DEFAULT_CAPACITY);
    }

    public int size() {
        return listSize;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    // fit array to not waste space
    public void trimToSize() {
        ensureCapacity(size());
    }

    // Return a member of the list by index
    public AnyType get(int idx) {
        if (isEmpty())
            throw new ArrayIndexOutOfBoundsException();

        if (idx < 0 || idx >= size())
            throw new ArrayIndexOutOfBoundsException();

        idx = absoluteIndex(idx);

        return listItems[idx];
    }

    // overwrite an element at index
    public AnyType set(int idx, AnyType newValue) {
        if (isEmpty())
            throw new ArrayIndexOutOfBoundsException();

        if (idx < 0 || idx >= size())
            throw new ArrayIndexOutOfBoundsException();

        idx = absoluteIndex(idx);

        AnyType oldValue = listItems[idx];
        listItems[idx] = newValue;

        return oldValue;
    }

    // Supressing warnings of array cast
    // Per Weiss (p. 68), this is unavoidable with Generic collections
    @SuppressWarnings("unchecked") 
    public void ensureCapacity(int newCapacity) {
        if (newCapacity < listSize)
            return;

        AnyType[] oldItems = listItems;

        listItems = (AnyType[]) new Object[newCapacity];

        for (int i = 0; i < size(); i++)
            listItems[i] = oldItems[absoluteIndex(i)];

        offset = 0;
    }

    // add an element at the end of the list
    public boolean add(AnyType x) {
        add(size(), x);

        return true;
    }

    // add an element at index and rotate rest of the elements 
    public void add(int idx, AnyType x) {
        if (listItems.length == size())
            ensureCapacity(size() * 2 + 1);
        
        for (int i = size(); i > idx; i--)
            listItems[absoluteIndex(i)] = listItems[absoluteIndex(i - 1)];

        idx = absoluteIndex(idx);

        listItems[idx] = x;

        listSize++;
    }

    // remove at index and shift all elements to fill gap
    public AnyType remove(int idx) {
        AnyType removedItem = listItems[absoluteIndex(idx)];

        for (int i = idx; i < size(); i++)
            listItems[absoluteIndex(i)] = listItems[absoluteIndex(i + 1)];

        listSize--;

        return removedItem;
    }

    public Iterator<AnyType> iterator() {
        return new ArrayListIterator();
    }

    // calculates the appropriate offset for indexing the circular array
    private int absoluteIndex(int idx) {
        return (offset + idx) % listItems.length;
    }

    private class ArrayListIterator implements Iterator<AnyType> {
        private int current = 0;

        public boolean hasNext() {
            return current < size();
        }

        public AnyType next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException();
            
            return listItems[absoluteIndex(current++)];
        }

        public void remove() {
            BrowserArrayList.this.remove(absoluteIndex(--current));
        }
    }

}
