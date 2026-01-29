import java.util.List;
import java.util.Map;
import java.util.Set;

public class PlotData {
  PlotData(Map<String, List<Double>> datasets, List<Integer> xAxisIncrements) {
    this.datasets = datasets;
    this.keys = datasets.keySet();
    this.xAxisIncrements = xAxisIncrements;
  }

  Map<String, List<Double>> datasets;
  Set<String> keys;
  List<Integer> xAxisIncrements;

}
