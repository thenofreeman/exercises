public interface ContainerType<AnyType> {
    public boolean insert(AnyType x);
    public boolean remove(AnyType x);
    public boolean contains(AnyType x);
    public void clear();
    public boolean isEmpty();
    public void print();
}
