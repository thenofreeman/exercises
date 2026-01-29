public class PlotInfo {
  PlotInfo(String title, String xAxisLabel, String yAxisLabel) {
    this.title = title.trim();
    this.xAxisLabel = xAxisLabel.trim();
    this.yAxisLabel = yAxisLabel.trim();
  }

  String title;
  String xAxisLabel;
  String yAxisLabel;
}
