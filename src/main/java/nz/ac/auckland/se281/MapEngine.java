package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** This class is the main entry point. */
public class MapEngine {

  private Set<Country> countryStats = new HashSet<>();
  private Map<String, Integer> fuelCounts = new HashMap<>();
  private Graph map;

  public MapEngine() {
    // add other code here if you want
    map = new Graph();

    // initializing hashmap
    class Initialization {
      private boolean isInitialized = false;

      public void initializeCounts() {
        // Assigning keys and values
        if (!isInitialized) {
          // initalise counts
          fuelCounts.put("North America", 0);
          fuelCounts.put("South America", 0);
          fuelCounts.put("Africa", 0);
          fuelCounts.put("Europe", 0);
          fuelCounts.put("Asia", 0);
          fuelCounts.put("Australia", 0);

          isInitialized = true;
        }
      }
    }

    new Initialization().initializeCounts();

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
    List<String> visitedContinents = new ArrayList<>();

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

      // Creating continent info
      // can add continent to a hashmap count? with Continent + fuel count
      // make sure to exclude first and last countries from calculation

      // might need to change to shortest path
      for (Country country : shortestPath) {
        System.out.println(country.getName() + "," + country.getContinent());

        String continent;
        continent = country.getContinent();

        // add continent to lists
        if (!visitedContinents.contains(continent)) {
          visitedContinents.add(continent);
        }

        // skip first and last country - fuel count
        if (country.getName().equals(startCountry.getName())
            || country.getName().equals(destination.getName())) {
          continue;
        } else if (shortestPath.contains(country)) {
          fuelCounts.put(continent, fuelCounts.get(continent) + country.getFuelCost());
        }
      }

      // add in the printing
      // "[Europe (10), Asia (22), Australia (5)]"
      String continentMessage = "[";

      for (String visited : visitedContinents) {
        continentMessage = continentMessage.concat(visited + " (");
        continentMessage = continentMessage.concat(fuelCounts.get(visited) + "), ");
      }

      // last iteration
      continentMessage = continentMessage.trim();
      continentMessage = continentMessage.substring(0, continentMessage.length() - 2);
      continentMessage = continentMessage.concat("]");

      // print message
      MessageCli.CONTINENT_INFO.printMessage(continentMessage);

      /////////////////////////
      // sorting out fuel costs
      int fuelCost = 0;

      for (String visited : visitedContinents) {
        fuelCost += fuelCounts.get(visited);
      }

      MessageCli.FUEL_INFO.printMessage(fuelCost + "");
    }
    return;
  }
}
