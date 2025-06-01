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

  // might not need
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
    Country country = nameToCountry.get(name);
    return country;
  }

  public List<Country> getAdjacent(Country country) {
    return adjNodes.getOrDefault(country, new ArrayList<>());
  }

  public Country checkCountryExists(String name) throws CountryNotFoundException {
    Country country = nameToCountry.get(name);
    if (country == null) {
      String message = MessageCli.INVALID_COUNTRY.getMessage(name);
      throw new CountryNotFoundException(message);
    }
    return country;
  }

  // will need to edit.
  public List<Country> findShortestRoute(Country start, Country destination) {
    List<Country> visited = new ArrayList<>();
    Queue<Country> queue = new LinkedList<>();
    Map<Country, Country> parentMap = new HashMap<>();
    List<Country> shortestPath = new LinkedList<>();

    queue.add(start);
    visited.add(start);
    // stores country with the closer country
    parentMap.put(start, null);

    while (!queue.isEmpty()) {
      Country current = queue.poll();

      if (current.equals(destination)) {
        for (Country country = destination; country != null; parentMap.get(country))
          // adds to the front of the list
          shortestPath.addFirst(country);

        break;
      }

      for (Country neighbour : adjNodes.get(current)) {
        if (!visited.contains(neighbour)) {
          visited.add(neighbour);
          queue.add(neighbour);
          parentMap.put(neighbour, current);
        }
      }
    }

    return shortestPath;
  }
}
