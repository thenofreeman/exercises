public class MergeSort implements SortInterface {
  public static Double sort(Integer[] arr) {
    Integer[] sortedArray = arr.clone();

    long start = System.nanoTime();

    doMergeSort(sortedArray);

    long end = System.nanoTime();
    double duration = (end - start) / Math.pow(10, 6);

    return duration;
  }

  private static void doMergeSort(Integer[] arr) {
    Integer[] tempArray = new Integer[arr.length];

    mergeSort(arr, tempArray, 0, arr.length - 1);
  }

  private static void mergeSort(Integer[] arr, Integer[] tempArray, int left, int right) {
    if (left < right) {
      int center = (left + right) / 2;

      mergeSort(arr, tempArray, left, center);
      mergeSort(arr, tempArray, center + 1, right);

      merge(arr, tempArray, left, center + 1, right);
    }
  }

  private static void merge(Integer[] arr, Integer[] tempArray, int leftIndex, int rightIndex, int rightEnd) {
    int leftEnd = rightIndex - 1;
    int tempIndex = leftIndex;
    int nElements = rightEnd - leftEnd + 1;

    while (leftIndex <= leftEnd && rightIndex <= rightEnd) {
      if (arr[leftIndex].compareTo(arr[rightIndex]) <= 0) {
        tempArray[tempIndex++] = arr[leftIndex++];
      } else {
        tempArray[tempIndex++] = arr[rightIndex++];
      }
    }

    while (leftIndex <= leftEnd) {
      tempArray[tempIndex++] = arr[leftIndex++];
    }

    while (rightIndex <= rightEnd) {
      tempArray[tempIndex++] = arr[rightIndex++];
    }

    for (int i = 0; i < nElements; i++, rightEnd--) {
      arr[rightEnd] = tempArray[rightEnd];
    }
  }

}
