import java.util.Random; // generating a random array
import java.util.List;
import java.text.DecimalFormat; // 0.00 output format
import java.util.Arrays; // convertion arrays to lists
import java.util.LinkedHashMap; // storing a sort results by name
import java.util.LinkedList; // for helping generate the random unique array
import java.util.Map;

public class Main {
  private static final Random rand = new Random();
  private static final DecimalFormat df = new DecimalFormat("0.00");

  public static void main(String[] args) {

    Integer[] nValues = {
        10,
        50,
        500,
        1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000,
        10000,
    };

    List<Double> zeroIntitializedDoubleArray = new LinkedList<>();
    for (int i = 0; i < nValues.length; i++) {
      zeroIntitializedDoubleArray.add(i, 0.0);
    }

    System.out.println("Running trials ...");

    List<Double> insertionTimes = new LinkedList<>(zeroIntitializedDoubleArray);
    List<Double> heapTimes = new LinkedList<>(zeroIntitializedDoubleArray);
    List<Double> mergeTimes = new LinkedList<>(zeroIntitializedDoubleArray);
    List<Double> quickTimes = new LinkedList<>(zeroIntitializedDoubleArray);
    List<Double> quickCutoff10Times = new LinkedList<>(zeroIntitializedDoubleArray);
    List<Double> quickCutoff50Times = new LinkedList<>(zeroIntitializedDoubleArray);
    List<Double> quickCutoff200Times = new LinkedList<>(zeroIntitializedDoubleArray);

    final int nTrials = 10;

    for (int trial = 0; trial < nTrials; trial++) {
      for (int i = 0; i < nValues.length; i++) {
        int n = nValues[i];

        Integer[] arr = getRandomUniqueArray(n);

        insertionTimes.set(i, insertionTimes.get(i) + InsertionSort.sort(arr));
        heapTimes.set(i, heapTimes.get(i) + HeapSort.sort(arr));
        mergeTimes.set(i, mergeTimes.get(i) + MergeSort.sort(arr));
        quickTimes.set(i, quickTimes.get(i) + QuickSort.sort(arr));
        quickCutoff10Times.set(i, quickCutoff10Times.get(i) + QuickSort.sort(arr, 10));
        quickCutoff50Times.set(i, quickCutoff50Times.get(i) + QuickSort.sort(arr, 50));
        quickCutoff200Times.set(i, quickCutoff200Times.get(i) + QuickSort.sort(arr, 200));
      }

      System.out.println("Trial " + (trial + 1) + " completed ...");
    }

    System.out.println("\nAveraging results ...");
    System.out.println("\nFor N = ");

    for (int i = 0; i < nValues.length; i++) {
      System.out.printf("%7s", nValues[i]
          + (i != nValues.length - 1 ? "," : ""));
    }
    System.out.println();

    Map<String, List<Double>> timedata = new LinkedHashMap<>();

    for (int i = 0; i < quickTimes.size(); i++) {
      insertionTimes.set(i, insertionTimes.get(i) / nTrials);
      heapTimes.set(i, heapTimes.get(i) / nTrials);
      mergeTimes.set(i, mergeTimes.get(i) / nTrials);
      quickTimes.set(i, quickTimes.get(i) / nTrials);
      quickCutoff10Times.set(i, quickCutoff10Times.get(i) / nTrials);
      quickCutoff50Times.set(i, quickCutoff50Times.get(i) / nTrials);
      quickCutoff200Times.set(i, quickCutoff200Times.get(i) / nTrials);
    }

    timedata.put("Insertion Sort", insertionTimes);
    timedata.put("Heap Sort", heapTimes);
    timedata.put("Merge Sort", mergeTimes);
    timedata.put("Quick Sort", quickTimes);
    timedata.put("Quick Sort (Cutoff 10)", quickCutoff10Times);
    timedata.put("Quick Sort (Cutoff 50)", quickCutoff50Times);
    timedata.put("Quick Sort (Cutoff 200)", quickCutoff200Times);

    for (String key : timedata.keySet()) {
      System.out.println("\n" + key);

      for (int i = 0; i < nValues.length; i++) {
        System.out.printf("%7s", df.format(timedata.get(key).get(i))
            + (i != nValues.length - 1 ? "," : ""));
      }
      System.out.println("in ms");
    }

    System.out.println("\nGenerating Graph ...");

    PlotData plotData = new PlotData(timedata, Arrays.asList(nValues));
    PlotInfo plotinfo = new PlotInfo("Sort Time Comparisions", "N Elements", "Average Time (ms) over 10 Trials");

    GraphPlot.plot(plotData, plotinfo);

    System.out.println("\nTests completed!");
  }

  public static void printArray(Integer[] arr) {
    for (int val : arr) {
      System.out.print(val + " ");
    }

    System.out.println();
  }

  public static Integer[] getRandomArray(int n) {
    Integer[] array = new Integer[n];

    for (int i = 0; i < n; i++) {
      array[i] = rand.nextInt(n + 1);
    }

    return array;
  }

  public static Integer[] getRandomUniqueArray(int n) {
    LinkedList<Integer> unusedValues = new LinkedList<Integer>();

    for (int i = 0; i < n; i++) {
      unusedValues.push(i);
    }

    Integer[] array = new Integer[n];

    for (int i = 0; i < n; i++) {
      int idxToRemove = rand.nextInt(unusedValues.size());
      array[i] = unusedValues.remove(idxToRemove);
    }

    return array;
  }

}
