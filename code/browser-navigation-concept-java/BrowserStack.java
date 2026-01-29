import java.util.Iterator;

public class BrowserStack<AnyType> implements Iterable<AnyType> {
    private BrowserLinkedList<AnyType> items = new BrowserLinkedList<>();

    public BrowserStack() {
        clear();
    }

    // get but leave the node at the top of the stack
    public AnyType top() {
        if (isEmpty())
            throw new java.util.EmptyStackException();

        return items.get(0);
    }

    // remove the node at the top of the stack and return it
    public AnyType pop() {
        if (isEmpty())
            throw new java.util.EmptyStackException();

        return items.removeLast();
    }

    // add a new node to the top of the stack
    public void push(AnyType x) {
        items.add(x);
    }

    public void clear() {
        items.clear();
    }

    public int size() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public Iterator<AnyType> iterator() {
        return new StackIterator();
    }

    private class StackIterator implements Iterator<AnyType> {
        private Iterator<AnyType> listIterator = items.iterator();

        public boolean hasNext() {
            return listIterator.hasNext();
        }

        public AnyType next() {
            return listIterator.next();
        }
    }

}
