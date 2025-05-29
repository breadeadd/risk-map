package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Graph {
  private Map<Country, List<Country>> adjNodes;
  private Map<String, Country> nameToCountry = new HashMap<>();

  public Graph() {
    this.adjNodes = new HashMap<>();
  }

  public void addNode(Country node) {
    adjNodes.putIfAbsent(node, new ArrayList<>());
    nameToCountry.putIfAbsent(node.getName(), node);
  }

  public void addEdge(Country node1, Country node2) {
    adjNodes.get(node1).add(node2);
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

  public Country getCountryByName(String name) {
    return nameToCountry.get(name);
  }

  public List<Country> getAdjacent(Country country) {
    return adjNodes.getOrDefault(country, new ArrayList<>());
  }

  public List<Country> breathFirstTraversal(Country root) {
    List<Country> visited = new ArrayList<>();
    Queue<Country> queue = new LinkedList<>();
    queue.add(root);
    visited.add(root);
    while (!queue.isEmpty()) {
      Country node = queue.poll();
      for (Country n : adjNodes.get(node)) {
        if (!visited.contains(n)) {
          visited.add(n);
          queue.add(n);
        }
      }
    }
    return visited;
  }
}
