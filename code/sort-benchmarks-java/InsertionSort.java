public class InsertionSort implements SortInterface {
  public static Double sort(Integer[] arr) {
    return sort(arr, 0, arr.length);
  }

  public static Double sort(Integer[] arr, int left, int right) {
    Integer[] sortedArray = arr.clone();

    long start = System.nanoTime();

    doInsertionSort(sortedArray, left, right);

    long end = System.nanoTime();
    double duration = (end - start) / Math.pow(10, 6);

    return duration;
  }

  private static void doInsertionSort(Integer[] arr, int left, int right) {
    int j;

    for (int p = left + 1; p < right; p++) {
      Integer temp = arr[p];

      for (j = p; j > left && temp.compareTo(arr[j - 1]) < 0; j--) {
        arr[j] = arr[j - 1];
      }

      arr[j] = temp;
    }
  }
}
