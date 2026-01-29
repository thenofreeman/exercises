import java.util.Iterator;

public class BrowserQueue<AnyType> implements Iterable<AnyType> {
    private BrowserArrayList<AnyType> items = new BrowserArrayList<>();

    public BrowserQueue() {
        clear();
    }

    // add element to the back of the queue
    public void enqueue(AnyType x) {
        items.add(x);
    }

    // get the first element (leave it there)
    public AnyType first() {
        return items.get(0);
    }

    // remove the first element
    public AnyType dequeue() {
        return items.remove(0);
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
        return items.iterator();
    }

}
