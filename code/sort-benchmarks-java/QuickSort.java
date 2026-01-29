public class QuickSort implements SortInterface {
  public static Double sort(Integer[] arr) {
    return sort(arr, 2);
  }

  public static Double sort(Integer[] arr, int cutoff) {
    if (cutoff < 2)
      throw new IllegalArgumentException("Cutoff cannot be less than 2.");

    Integer[] sortedArray = arr.clone();

    long start = System.nanoTime();

    doQuickSort(sortedArray, cutoff);

    long end = System.nanoTime();
    double duration = (end - start) / Math.pow(10, 6);

    return duration;
  }

  private static void doQuickSort(Integer[] arr, int cutoff) {
    quicksort(arr, 0, arr.length - 1, cutoff);
  }

  private static Integer medianOf3(Integer[] arr, int left, int right) {
    int center = (left + right) / 2;

    if (arr[center].compareTo(arr[left]) < 0) {
      swapReferences(arr, left, center);
    }

    if (arr[right].compareTo(arr[left]) < 0) {
      swapReferences(arr, left, right);
    }

    if (arr[right].compareTo(arr[center]) < 0) {
      swapReferences(arr, center, right);
    }

    swapReferences(arr, center, right - 1);

    return arr[right - 1];
  }

  private static void quicksort(Integer[] arr, int left, int right, int cutoff) {
    if (left + cutoff <= right) {
      Integer pivot = medianOf3(arr, left, right);

      int i = left;
      int j = right - 1;

      while (true) {
        while (arr[++i].compareTo(pivot) < 0)
          ;

        while (arr[--j].compareTo(pivot) > 0)
          ;

        if (i < j) {
          swapReferences(arr, i, j);
        } else {
          break;
        }
      }

      swapReferences(arr, i, right - 1);

      quicksort(arr, left, i - 1, cutoff);
      quicksort(arr, i + 1, right, cutoff);
    } else {
      InsertionSort.sort(arr, left, right);
    }
  }

  public static void swapReferences(Integer[] arr, int idxa, int idxb) {
    Integer temp = arr[idxa];

    arr[idxa] = arr[idxb];
    arr[idxb] = temp;
  }
}
