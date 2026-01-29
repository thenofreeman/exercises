public class HeapSort implements SortInterface {
  public static Double sort(Integer[] arr) {
    Integer[] sortedArray = arr.clone();

    long start = System.nanoTime();

    doHeapSort(sortedArray);

    long end = System.nanoTime();
    double duration = (end - start) / Math.pow(10, 6);

    return duration;
  }

  private static Integer[] doHeapSort(Integer[] arr) {
    for (int i = arr.length / 2 - 1; i >= 0; i--) {
      percolateDown(arr, i, arr.length);
    }

    for (int i = arr.length - 1; i > 0; i--) {
      swapReferences(arr, 0, i);
      percolateDown(arr, 0, i);
    }

    return arr;
  }

  private static int leftChildOfIndex(int i) {
    return 2 * i + 1;
  }

  private static void percolateDown(Integer[] arr, int i, int n) {
    int child = -1;
    Integer temp = arr[i];

    while (leftChildOfIndex(i) < n) {
      child = leftChildOfIndex(i);

      if (child != n - 1 && arr[child].compareTo(arr[child + 1]) < 0) {
        child++;
      }

      if (temp.compareTo(arr[child]) < 0) {
        arr[i] = arr[child];
      } else {
        break;
      }

      i = child;
    }

    arr[i] = temp;
  }

  public static void swapReferences(Integer[] arr, int idxa, int idxb) {
    Integer temp = arr[idxa];

    arr[idxa] = arr[idxb];
    arr[idxb] = temp;
  }
}
