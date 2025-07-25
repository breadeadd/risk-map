package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.List;

public class Country {
  private String name;
  private String continent;
  private int fuelCost;
  private List<Country> neighbours = new ArrayList<>();

  // setting variables
  public Country(String name, String continent, int fuelCost) {
    this.name = name;
    this.continent = continent;
    this.fuelCost = fuelCost;
  }

  // getters
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
  }

  // create neighbour string
  public String printNeighbours() {
    String textOut = "";
    for (Country neighbour : neighbours) {
      textOut = textOut.concat(neighbour.getName());
      textOut = textOut.concat(", ");
    }

    // last iteration
    textOut = textOut.trim();
    textOut = textOut.substring(0, textOut.length() - 1);

    return textOut;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      // if both objects are the same instance
      // no need to check further
      return true;
    }
    // if obj is null or not of the same class, return false
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    Country other = (Country) obj;
    return name.equals(other.name);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }
}
