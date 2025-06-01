package nz.ac.auckland.se281;

public enum Continents {
  NORTHAMERICA("North America"),
  SOUTHAMERICA("South America"),
  AFRICA("Africa"),
  EUROPE("Europe"),
  ASIA("Asia"),
  AUSTRALIA("Australia");

  private String name;

  Continents(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
