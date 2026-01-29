import java.util.LinkedList;
import java.util.List;

public class ChainingHashTable<AnyType> implements ContainerType<AnyType> {
    public ChainingHashTable() {
        this(DEFAULT_TABLE_SIZE);
    }

    @SuppressWarnings("unchecked")
    public ChainingHashTable(int size) {
        buckets = new LinkedList[nextPrime(size)];

        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new LinkedList<>();
        }
    }

    public boolean insert(AnyType x) {
        List<AnyType> whichBucket = buckets[hash(x)];

        if (!whichBucket.contains(x)) {
            whichBucket.add(x);

            if (++currentSize > buckets.length) {
                rehash();
            }

            return true;
        }

        return false;
    }

    public boolean remove(AnyType x) {
        List<AnyType> whichBucket = buckets[hash(x)];

        if (whichBucket.contains(x)) {
            whichBucket.remove(x);

            currentSize++;

            return true;
        }

        return false;
    }

    public boolean contains(AnyType x) {
        List<AnyType> whichBucket = buckets[hash(x)];

        return whichBucket.contains(x);
    }

    public void print() {
        for (List<AnyType> bucket : this.buckets) {
            for (AnyType value : bucket) {
                System.out.print(value + " ");
            }
        }
    }

    public void clear() {
        for(int i = 0; i < buckets.length; i++) {
            buckets[i].clear();
        }

        currentSize = 0;
    }

    public boolean isEmpty() {
        return currentSize == 0;
    }

    private static final int DEFAULT_TABLE_SIZE = 101;

    private List<AnyType>[] buckets;
    private int currentSize;

    @SuppressWarnings("unchecked")
    private void rehash() {
        List<AnyType>[] oldBuckets = buckets;

        buckets = new List[nextPrime(2*buckets.length)];

        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new LinkedList<>();
        }

        currentSize = 0;

        for (List<AnyType> bucket : oldBuckets) {
            for (AnyType item : bucket) {
                insert(item);
            }
        }
    }

    private int hash(AnyType x) {
        int hashValue = x.hashCode();

        hashValue %= buckets.length;

        if (hashValue < 0) {
            hashValue += buckets.length;
        }

        return hashValue;
    }

    private static int nextPrime(int n) {
        if (n % 2 == 0) {
            n++;
        }

        while (!isPrime(n)) {
            n += 2;
        }

        return n;
    }

    private static boolean isPrime(int n) {
        if (n == 2 || n == 3) {
            return true;
        }

        if (n == 1 || n % 2 == 0) {
            return false;
        }

        for (int i = 3; i*i <=n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

}
