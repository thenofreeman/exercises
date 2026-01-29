import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class TopologicalSort {
  // For reading from file with multiple testcases
  private static final boolean BATCH_MODE = false;

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    // necessary for file mode, read until no more test cases
    while (scanner.hasNextLine()) {

      String result = "";

      try {
        Vertex[] adjacencyList = buildGraph(scanner);
        Vertex[] ordredVertices = sortGraph(adjacencyList);

        StringBuilder sb = new StringBuilder();
        for (Vertex v : ordredVertices) {
          sb.append(v.id + " ");
        }
        result = sb.toString().stripTrailing();

      } catch (EdgeCountException ece) {
        result = ece.getMessage();
      } catch (CycleDetectedException cde) {
        result = cde.getMessage();
      }

      // Print extra information if in file batch/debug mode
      if (BATCH_MODE) {
        String expectedResult = scanner.nextLine().substring(1).strip();

        if (expectedResult.equals(result)) {
          System.out.print("PASSED: ");
        } else {
          System.out.print("FAILED: ");
        }

        System.out.println(result);
      } else {
        System.out.println(result);

        break;
      }
    }

    scanner.close();
  }

  public static Vertex[] buildGraph(Scanner scanner) throws EdgeCountException {
    final int nVertices = scanner.nextInt();
    final int nEdges = scanner.nextInt();

    if (nEdges < (nVertices - 1)) {
      throw new EdgeCountException("Disconnected Graph.");
    }

    if (nEdges > nVertices * (nVertices - 1)) {
      throw new EdgeCountException("Too many possible edges.");
    }

    Vertex[] adjacencyList = new Vertex[nVertices];

    for (int i = 0; i < nVertices; i++) {
      adjacencyList[i] = new Vertex(i);
    }

    for (int i = 0; i < nEdges; i++) {
      int u = scanner.nextInt();
      int v = scanner.nextInt();

      adjacencyList[v].indegree++;
      adjacencyList[u].outedges.add(adjacencyList[v]);
    }

    scanner.nextLine(); // read trailing line and discard

    return adjacencyList;
  }

  public static Vertex[] sortGraph(Vertex[] adjacencyList) throws CycleDetectedException {
    Vertex[] orderedVertices = new Vertex[adjacencyList.length];

    Queue<Vertex> q = new LinkedList<Vertex>();

    int counter = 0;

    for (Vertex v : adjacencyList)
      if (v.indegree == 0)
        q.add(v);

    while (!q.isEmpty()) {
      Vertex v = q.remove();
      orderedVertices[counter++] = v;

      for (Vertex w : v.outedges)
        if (--w.indegree == 0)
          q.add(w);
    }

    if (counter != adjacencyList.length)
      throw new CycleDetectedException("Cycle Detected.");

    return orderedVertices;
  }
}

class Vertex {
  public final int id;
  public int indegree;
  public LinkedList<Vertex> outedges;

  public Vertex(int id) {
    this.id = id;
    this.indegree = 0;
    this.outedges = new LinkedList<>();
  }
}

class EdgeCountException extends Exception {
  public EdgeCountException(String message) {
    super(message);
  }
}

class CycleDetectedException extends Exception {
  public CycleDetectedException(String message) {
    super(message);
  }
}

/*
 * testcases
 * 
 * 1
 * 0
 * =0
 * 
 * 5
 * 4
 * 0 1
 * 0 2
 * 1 3
 * 2 4
 * =0 1 2 3 4
 * 
 * 8
 * 9
 * 0 1
 * 1 3
 * 1 4
 * 0 2
 * 2 4
 * 2 5
 * 3 6
 * 4 6
 * 6 7
 * =0 1 2 3 4 5 6 7
 * 
 * 4
 * 4
 * 0 1
 * 1 2
 * 2 3
 * 3 1
 * =Cycle Detected.
 * 
 * 5
 * 4
 * 1 0
 * 2 0
 * 3 1
 * 3 2
 * =3 4 1 2 0
 * 
 * 4
 * 3
 * 0 3
 * 1 3
 * 2 3
 * =0 1 2 3
 * 
 */
