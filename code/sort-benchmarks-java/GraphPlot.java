import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GraphPlot {
  private static final String PLOT_COMMAND = "gnuplot";
  private static final long TIMEOUT_MAX = 15;

  // private static final String DEFAULT_TITLE = "Title";
  // private static final String DEFAULT_X_LABEL = "X-Axis";
  // private static final String DEFAULT_Y_LABEL = "Y-Axis";
  private static final String DEFAULT_OUTFILE = "graphplot.png";
  private static final String DEFAULT_PNG_CMD = "pngcairo size 800,600 enhanced font 'Verdana,10'";

  public static void plot(PlotData plotdata, PlotInfo plotinfo) {
    plot(plotdata, plotinfo, DEFAULT_OUTFILE);
  }

  public static void plot(PlotData plotdata, PlotInfo plotinfo, String outfile) {
    if (plotdata == null) {
      System.err.println("No plot data provided.");

      return;
    }

    ProcessBuilder processBuilder = new ProcessBuilder(PLOT_COMMAND);
    Process process = null;
    PrintWriter plotWriter = null;

    try {
      process = processBuilder.start();
      plotWriter = new PrintWriter(new OutputStreamWriter(process.getOutputStream()));

      plotWriter.println("set terminal " + DEFAULT_PNG_CMD);
      plotWriter.println("set output '" + escapeString(outfile) + "'");

      plotWriter.println("set title '" + escapeString(plotinfo.title) + "'");
      plotWriter.println("set xlabel '" + escapeString(plotinfo.xAxisLabel) + "'");
      plotWriter.println("set ylabel '" + escapeString(plotinfo.yAxisLabel) + "'");
      plotWriter.println("set grid");
      plotWriter.println("set key outside right top");
      // plotWriter.println("set logscale x");
      // plotWriter.println("set logscale y");

      StringBuilder plotCommand = new StringBuilder("plot ");
      List<String> dataKeys = new ArrayList<>(plotdata.keys);
      for (int i = 0; i < dataKeys.size(); i++) {
        String key = dataKeys.get(i);

        plotCommand.append("'-' with linespoints title '")
            .append(escapeString(key))
            .append("'");

        if (i < dataKeys.size() - 1) {
          plotCommand.append(", \\\n");
        }
      }
      plotWriter.println(plotCommand.toString());

      for (String key : dataKeys) {
        List<Double> values = plotdata.datasets.get(key);
        List<Integer> xIncrements = plotdata.xAxisIncrements;

        if (values != null) {
          for (int i = 0; i < values.size(); i++) {
            plotWriter.println(xIncrements.get(i) + " " + values.get(i));
          }
        }

        plotWriter.println("e");
      }

      plotWriter.flush();
      plotWriter.close();

      boolean success = process.waitFor(TIMEOUT_MAX, TimeUnit.SECONDS);

      if (!success) {
        process.destroyForcibly();

        throw new RuntimeException("GNUPLOT process timed out after " + TIMEOUT_MAX);
      }

      if (process.exitValue() != 0) {
        StringBuilder errorOutput = new StringBuilder();
        try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
          String line;
          while ((line = errorReader.readLine()) != null) {
            errorOutput.append(line).append(System.lineSeparator());
          }
        }
        throw new RuntimeException("GNUPLOT errored with code: " + process.exitValue() +
            ".\nGnuplot output:\n" + errorOutput.toString());
      } else {
        System.out.println("GNUPLOT graph generated successfully at: " + outfile);
      }

    } catch (InterruptedException ie) {
      System.err.println(ie.getMessage());
    } catch (IOException ioe) {
      System.err.println(ioe.getMessage());
    } finally {
      if (plotWriter != null) {
        plotWriter.close();
      }

      if (process != null) {
        closeStream(process.getInputStream());
        closeStream(process.getOutputStream());
        process.destroy();

      }
    }

  }

  public static String escapeString(String s) {
    if (s == null)
      return "";

    return s.replace("'", "''");
  }

  public static void closeStream(java.io.Closeable stream) {
    if (stream != null) {
      try {
        stream.close();
      } catch (IOException e) {
        System.err.println("Unable to close stream: " + e.getMessage());
      }
    }
  }

}
