package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** This class is the main entry point. */
public class MapEngine {

  private List<Country> countryStats = new ArrayList<>();
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
      String currentCountry = edges[0];

      // for (int i = 1; i < edges.length; i++) {
      //   map.addEdge(edges[0], edges[i]);
      // }

    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    Scanner scanner = new Scanner(System.in);
    String country;
    String input;

    MessageCli.INSERT_COUNTRY.printMessage();

    // storing input
    input = scanner.nextLine();

    // sorting input
    country = Utils.capitalizeFirstLetterOfEachWord(input);

    scanner.close();

    for (Country check : countryStats) {
      if (country.equals(check.getName())) {
        MessageCli.COUNTRY_INFO.printMessage(
            check.getName(), check.getContinent(), check.getFuelCost() + "", "");
        return;
      }
    }
    // else throw exception - with message super set as message cli

  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {}
}
