package nz.ac.auckland.se281;

public class CountryNotFoundException extends Exception {
  public CountryNotFoundException(String input) {
    super(MessageCli.INVALID_COUNTRY.getMessage(input));
  }
}
