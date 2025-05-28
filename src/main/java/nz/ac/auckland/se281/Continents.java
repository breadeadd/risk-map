package nz.ac.auckland.se281;

public enum Continents {
  NORTHAMERICA("North America"),
  SOUTHAMERICA("South America"),
  AFRICA("Africa"),
  EUROPE("Europe"),
  ASIA("Asia"),
  AUSTRALIA("Australia");

  private final String name;

  // Constructor (must be private or package-private)
  Continents(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
