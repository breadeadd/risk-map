package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
  private Map<Country, List<Country>> adjNodes;

  public Graph() {
    this.adjNodes = new HashMap<>();
  }

  public void addNode(Country node) {
    adjNodes.putIfAbsent(node, new ArrayList<>());
  }

  public void addEdge(Country node) {
    addNode(node); // might not need?
    adjNodes.get(node);
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Country n : adjNodes.keySet()) {
      sb.append(n);
      sb.append("->");
      sb.append(adjNodes.get(n));
      sb.append(System.lineSeparator());
    }
    return sb.toString();
  }
}
