public class QuadProbingHashTable<AnyType> implements ContainerType<AnyType> {
    public QuadProbingHashTable() {
        this(DEFAULT_TABLE_SIZE);
    }

    public QuadProbingHashTable(int size) {
        allocateArray(size);
        clear();
    }

    public boolean insert(AnyType value) {
        int currentPosition = getPosition(value);

        if (!isActive(currentPosition)) {
            elements[currentPosition] = new HashEntry<>(value);

            if (++size > elements.length / 2) {
                rehash();
            }

            return true;
        }

        return false;
    }

    public boolean remove(AnyType value) {
        int currentPosition = getPosition(value);

        if (isActive(currentPosition)) {
            elements[currentPosition].active = false;

            return true;
        }

        return false;
    }

    public boolean contains(AnyType value) {
        int currentPosition = getPosition(value);

        return isActive(currentPosition);
    }

    public void print() {
        for (HashEntry<AnyType> element : elements) {
            if (element.active) {
                System.out.print(element.value + " ");
            }
        }
    }

    public void clear() {
        size = 0;

        for (int i = 0; i < elements.length; i++) {
            elements[i] = null;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private static final int DEFAULT_TABLE_SIZE = 11;

    private HashEntry<AnyType>[] elements;
    private int size;

    @SuppressWarnings("unchecked")
    private void allocateArray(int newSize) {
        elements = new HashEntry[nextPrime(newSize)];
    }

    private boolean isActive(int position) {
        if (elements[position] != null) {
            return elements[position].active;
        }

        return false;
    }

    private int getPosition(AnyType value) {
        int offset = 1;

        int currentPosition = hash(value);

        while(elements[currentPosition] != null && !elements[currentPosition].value.equals(value)) {
            currentPosition += offset;

            offset += 2;
            if (currentPosition >= elements.length) {
                currentPosition -= elements.length;
            }
        }

        return currentPosition;
    }

    private void rehash() {
        HashEntry<AnyType>[] oldElements = elements;

        allocateArray(nextPrime(2*elements.length));
        size = 0;

        for (int i = 0; i < oldElements.length; i++) {
            if (oldElements[i] != null && oldElements[i].active) {
                insert(oldElements[i].value);
            }
        }
    }

    private int hash(AnyType x) {
        int hashValue = x.hashCode();

        hashValue %= elements.length;

        if (hashValue < 0) {
            hashValue += elements.length;
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

    private static class HashEntry<AnyType> {
        public AnyType value;
        public boolean active;

        public HashEntry(AnyType value) {
            this(value, true);
        }

        public HashEntry(AnyType value, boolean active) {
            this.value = value;
            this.active = active;
        }
    }

}
