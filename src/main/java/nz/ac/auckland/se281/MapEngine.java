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
  private Map<Continents, Integer> fuelCounts = new HashMap<>();
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
          fuelCounts.put(Continents.NORTHAMERICA, 0);
          fuelCounts.put(Continents.SOUTHAMERICA, 0);
          fuelCounts.put(Continents.AFRICA, 0);
          fuelCounts.put(Continents.EUROPE, 0);
          fuelCounts.put(Continents.ASIA, 0);
          fuelCounts.put(Continents.AUSTRALIA, 0);

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
      Continents continent = assignContinent(countryInfo[1]);

      Country addCountry = new Country(countryInfo[0], continent, Integer.parseInt(countryInfo[2]));
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

  public Continents assignContinent(String continent) {
    switch (continent) {
      case "South America":
        return Continents.SOUTHAMERICA;

      case "North America":
        return Continents.NORTHAMERICA;

      case "Africa":
        return Continents.AFRICA;

      case "Europe":
        return Continents.EUROPE;

      case "Asia":
        return Continents.ASIA;

      case "Australia":
        return Continents.AUSTRALIA;
      default:
        return null;
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
            check.getName(),
            check.getContinent().getName(),
            check.getFuelCost() + "",
            neighbourText);

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
    List<Continents> visitedContinents = new ArrayList<>();

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

      for (Country country : countryStats) {
        // skip first and last country
        if (country.getName().equals(startCountry.getName())
            || country.getName().equals(destination.getName())) {
          continue;
        }

        Continents continent;
        continent = country.getContinent();

        // add continent to lists
        if (!visitedContinents.contains(continent)) {
          visitedContinents.add(continent);
        }

        fuelCounts.put(continent, fuelCounts.get(continent) + country.getFuelCost());
      }

      // add in the printing
    }
    return;
  }
}
