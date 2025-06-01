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

    MessageCli.INSERT_COUNTRY.printMessage();
    country = checkCountryInput();

    // printing info
    for (Country check : countryStats) {
      if (country.equals(check.getName())) {
        String neighbourText = "[" + check.printNeighbours() + "]";
        MessageCli.COUNTRY_INFO.printMessage(
            check.getName(), check.getContinent(), check.getFuelCost() + "", neighbourText);

        return;
      }
    }

    MessageCli.INVALID_COUNTRY.printMessage(country);

    return;
    // else throw exception - with message super set as message cli
  }

  // throwing exception method
  public String checkCountryInput() {
    String input;
    while (true) {
      try {
        // storing input
        input = Utils.scanner.nextLine();

        // sorting input
        input = Utils.capitalizeFirstLetterOfEachWord(input);
        map.checkCountryExists(input);
      } catch (CountryNotFoundException e) {
        System.out.println(e.getMessage());
        continue;
      }
      break;
    }
    return input;
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {
    String start;
    String end;

    Country startCountry = null;
    Country destination = null;

    List<Country> shortestPath = null;

    // starting country
    MessageCli.INSERT_SOURCE.printMessage();
    start = checkCountryInput();

    // check if valid

    // destination country
    MessageCli.INSERT_DESTINATION.printMessage();
    end = checkCountryInput();

    // check if valid

    // if same
    if (start.equals(end)) {
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
      return;
    } else {
      // converting to country type
      for (Country check : countryStats) {
        if (start.equals(check.getName())) {
          startCountry = check;
        }

        if (end.equals(check.getName())) {
          destination = check;
        }
      }

      shortestPath = map.findShortestRoute(startCountry, destination);

      // creating string
      String routeMessage = "[";
      for (Country country : shortestPath) {
        routeMessage = routeMessage.concat(country.getName());
        routeMessage = routeMessage.concat(", ");
      }

      // last iteration
      routeMessage = routeMessage.trim();
      routeMessage = routeMessage.substring(0, routeMessage.length() - 1);
      routeMessage = routeMessage.concat("]");

      MessageCli.ROUTE_INFO.printMessage(routeMessage);
    }
    return;
  }
}
