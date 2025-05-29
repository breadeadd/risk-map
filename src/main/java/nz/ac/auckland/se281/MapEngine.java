package nz.ac.auckland.se281;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** This class is the main entry point. */
public class MapEngine {

  private Set<Country> countryStats = new HashSet<>();
  private Graph map;

  public MapEngine() {
    // add other code here if you want
    map = new Graph();

    loadMap(); // keep this method invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {

    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();

    // make countries from file to list "countries"
    // countryinfo[] <-- loco name, cont, fuel

    // saving all the countries
    for (String country : countries) {
      String[] countryInfo = country.split(",");
      Country addCountry =
          new Country(countryInfo[0], countryInfo[1], Integer.parseInt(countryInfo[2]));
      // might need to turn countryInfo[1] to an enum

      countryStats.add(addCountry);
      map.addNode(addCountry);
    }

    for (String adjacents : adjacencies) {
      String[] edges = adjacents.split(",");
      String country = edges[0];
      Country currentCountry = map.getCountryByName(country);

      // adding edges
      for (int i = 1; i < edges.length; i++) {
        String neighbour = edges[i];
        Country neighbourCountry = map.getCountryByName(neighbour);

        map.addEdge(currentCountry, neighbourCountry);

        // add neighbours to country
        currentCountry.addNeighbours(neighbourCountry);
      }
    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    String country;
    String input;

    MessageCli.INSERT_COUNTRY.printMessage();

    // storing input
    input = Utils.scanner.nextLine();

    // sorting input
    country = Utils.capitalizeFirstLetterOfEachWord(input);

    // printing info
    for (Country check : countryStats) {
      if (country.equals(check.getName())) {
        MessageCli.COUNTRY_INFO.printMessage(
            check.getName(),
            check.getContinent(),
            check.getFuelCost() + "",
            check.printNeighbours());
      }
    }

    return;
    // else throw exception - with message super set as message cli

  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}
