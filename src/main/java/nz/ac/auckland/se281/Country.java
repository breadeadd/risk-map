package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.List;

public class Country {
  private String id;
  private String name;
  private String continent;
  private int fuelCost;
  private List<Country> neighbours = new ArrayList<>();

  public Country(String name, String continent, int fuelCost) {
    this.name = name;
    this.continent = continent;
    this.fuelCost = fuelCost;

    return;
  }

  public String getName() {
    return name;
  }

  public String getContinent() {
    return continent;
  }

  public int getFuelCost() {
    return fuelCost;
  }

  public void addNeighbours(Country neighbour) {
    neighbours.add(neighbour);
    return;
  }

  public String printNeighbours() {
    String textOut = "[";
    for (Country neighbour : neighbours) {
      textOut = textOut.concat(neighbour.getName());
      textOut = textOut.concat(", ");
    }

    // last iteration
    textOut = textOut.trim();
    textOut = textOut.substring(0, textOut.length() - 1);
    textOut = textOut.concat("]");

    return textOut;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;

    Country other = (Country) obj;
    return name.equals(other.name);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }
}
