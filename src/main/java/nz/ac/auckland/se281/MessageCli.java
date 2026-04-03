package nz.ac.auckland.se281;

/* You cannot modify this class! */

public enum MessageCli {
  POKEMON_FOUND("There %s %s matching Pokemon found%s"),
  POKEMON_CREATED("Successfully created a '%s', '%s' rarity, '%s' type, ('%s')."),
  POKEMON_NOT_CREATED_INVALID_POKEMON_NAME(
      "Pokemon not created: '%s' is not a valid Pokemon name."),
  POKEMON_NOT_CREATED_INVALID_TYPE("Pokemon not created: '%s' is an invalid Pokemon Type."),
  POKEMON_NOT_CREATED_INVALID_RARITY("Pokemon not created: '%s' is an invalid Pokemon rarity."),
  POKEMON_NOT_CREATED_ALREADY_EXISTS("Pokemon not created: '%s' ('%s') already exists."),
  POKEMON_NOT_FOUND("Pokemon not found: '%s' is an invalid pokemon ID."),
  POKEMON_ENTRY("* %s (%s rarity, type '%s', '%s')"),
  EVOLUTIONS_FOUND("There %s %s matching evolution%s found%s"),
  EVOLUTION_CREATED("Successfully created %s evolution '%s' ('%s') for '%s'."),
  EVOLUTION_NOT_CREATED_INVALID_EVOlUTION_NAME(
      "Evolution not created: '%s' is not a valid evolution name."),
  EVOLUTION_NOT_CREATED_INVALID_POKEMON_ID("Evolution not created: '%s' is an invalid pokemon ID."),
  EVOLUTION_NOT_FOUND("Evolution not found: '%s' is an invalid evolution ID."),
  EVOLUTION_ENTRY("  * %s: [%s] from %s"),
  ACTION_ADDED("%s action '%s' added successfully for evolution '%s'."),
  ACTION_NOT_ADDED_INVALID_EVOLUTION_ID("Action not added: '%s' is an invalid evolution ID."),
  ACTIONS_FOUND("There %s %s action%s for evolution '%s'."),
  ACTION_NOT_FOUND("Action not found: '%s' is an invalid action ID."),
  ACTION_ENTRY_HEADER("  * %s action (%s) '%s' for '%s'."),
  NO_ACTIONS_IN_EVOLUTION("No actions found in %s (%s)."),
  TOP_EVOLUTION(
      "The evolution with the %s strongest action is '%s', with '%s' of action power of '%s'"),
  ACTION_TEXT("%s %s %s with %s, %s %s was %s."),
  CUSTOM("%s"),
  COMMAND_NOT_FOUND(
      "Error! Command not found! (run 'help' for the list of available commands): \"%s\""),
  WRONG_ARGUMENT_COUNT(
      "Error! Incorrect number of arguments provided. Expected %s argument%s for the \"%s\""
          + " command"),

  END("You closed the terminal. Goodbye.");

  private final String msg;

  private MessageCli(final String msg) {
    this.msg = msg;
  }

  public String getMessage(final String... args) {
    String tmpMessage = msg;

    for (final String arg : args) {
      tmpMessage = tmpMessage.replaceFirst("%s", arg);
    }

    return tmpMessage;
  }

  public void printMessage(final String... args) {
    System.out.println(getMessage(args));
  }

  @Override
  public String toString() {
    return msg;
  }
}
