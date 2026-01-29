import java.util.Iterator;

public class BrowserLinkedList<AnyType> implements Iterable<AnyType> {
    private int listSize = 0;
    private int modCount = 0;

    private BrowserNode<AnyType> beginMarker;
    private BrowserNode<AnyType> endMarker;

    public BrowserLinkedList() {
        clearList();
    }

    public void clear() {
        clearList();
    }

    // drop references for garbage collection
    private void clearList() {
        beginMarker = new BrowserNode<AnyType>(null, null, null);
        endMarker = new BrowserNode<AnyType>(null, beginMarker, null);
        beginMarker.next = endMarker;

        listSize = 0;
        modCount++;
    }

    public int size() {
        return listSize;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean add(AnyType x) {
        add(size(), x);

        return true;
    }

    public void add(int idx, AnyType x) {
        addBefore(getNode(idx, 0, size()), x);
    }

    public AnyType get(int idx) {
        return getNode(idx).data;
    }

    // set value at index
    public AnyType set(int idx, AnyType newValue) {
        BrowserNode<AnyType> p = getNode(idx);
        AnyType oldValue = p.data;
        p.data = newValue;

        return oldValue;
    }
    
    // remove node at index
    public AnyType remove(int idx) {
        return remove(getNode(idx));
    }

    // helper to remove specifically the last node at O(1)
    public AnyType removeLast() {
        return remove(endMarker.prev);
    }

    // add a node before a particular one
    private void addBefore(BrowserNode<AnyType> p, AnyType x) {
        BrowserNode<AnyType> newNode = new BrowserNode<>(x, p.prev, p);
        newNode.prev.next = newNode;
        p.prev = newNode;

        listSize++;
        modCount++;
    }

    // unlink the passed node from its siblings
    private AnyType remove(BrowserNode<AnyType> p) {
        p.next.prev = p.prev;
        p.prev.next = p.next;

        listSize--;
        modCount++;

        return p.data;
    }

    // return the node at index
    private BrowserNode<AnyType> getNode(int idx) {
        return getNode(idx, 0, size() - 1);
    }

    // helper for returning a node at index within bounds
    private BrowserNode<AnyType> getNode(int idx, int lower, int upper) {
        BrowserNode<AnyType> p;

        if (idx < lower || idx > upper)
            throw new IndexOutOfBoundsException();

        if (idx < size() / 2) {
            p = beginMarker.next;

            for (int i = 0; i < idx; i++)
                p = p.next;
        } else {
            p = endMarker;

            for (int i = size(); i > idx; i--)
                p = p.prev;
        }

        return p;
    }

    public Iterator<AnyType> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<AnyType> {
        private BrowserNode<AnyType> current = beginMarker.next;

        private int expectedModCount = modCount;
        private boolean okToRemove = false;

        // is it okay to get the next node?
        public boolean hasNext() {
            return current != endMarker;
        }

        // get the next node in list
        public AnyType next() {
            if (modCount != expectedModCount)
                throw new java.util.ConcurrentModificationException();
            
            if (!hasNext())
                throw new java.util.NoSuchElementException();
            
            AnyType nextItem = current.data;
            current = current.next;

            okToRemove = true;

            return nextItem;
        }

        // remove the last seen node
        public void remove() {
            if (modCount != expectedModCount)
                throw new java.util.ConcurrentModificationException();
            
            if (!okToRemove)
                throw new IllegalStateException();

            BrowserLinkedList.this.remove(current.prev);            
            
            expectedModCount++;
            okToRemove = false;
        }
    }

    private static class BrowserNode<AnyType> {
        public BrowserNode(AnyType d, BrowserNode<AnyType> p, BrowserNode<AnyType> n) {
            data = d;
            prev = p;
            next = n;
        }        

        public AnyType data;
        public BrowserNode<AnyType> prev;
        public BrowserNode<AnyType> next;
    }

}
