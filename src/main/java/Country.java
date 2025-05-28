public class Country {
  String name;
  String continent;
  int fuelCost;

  public void country(String name, String continent, int fuelCost) {
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
}
