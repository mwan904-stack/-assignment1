package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** You cannot modify this class! */
public class Main {
  public enum Command {
    // Task 1 commands
    SEARCH_POKEMON(
        1, "Search for Pokemon by keyword <KEYWORD> in their name, type, rarity or pokemonId"),
    CREATE_POKEMON(3, "Create a new Pokemon with <POKEMON_NAME>, <TYPE> and <RARITY>"),

    // Task 2 commands
    VIEW_EVOLUTIONS(1, "View all evolutions for <POKEMON_ID>"),
    CREATE_EVOLUTION(
        3, "Create evolution with <EVOLUTION_NAME> and <EVOLUTION_TYPE> for <POKEMON_ID>"),
    SEARCH_EVOLUTIONS(
        1,
        "Search evolutions by <KEYWORD> in evolution name, evolution type, evolutionId, pokemon"
            + " rarity or pokemon type"),

    // Task 3 commands
    ADD_ATTACK_ACTION(
        1,
        "Add a attack action for an evolution identified by <EVOLUTION_ID>",
        "Action name",
        "Action element type",
        "Action strength (1-5)"),
    ADD_HEAL_ACTION(
        1,
        "Add a heal action for an evolution identified by <EVOLUTION_ID>",
        "Action name",
        "Action element type",
        "Action strength (1-5)"),
    ADD_EFFECT_ACTION(
        1,
        "Add an effect action for an evolution identified by <EVOLUTION_ID>",
        "Action name",
        "Action element type",
        "Action strength (1-5)",
        "Action target (self or opponent)",
        "Action target stat (e.g., speed stat)",
        "Effect direction (e.g., increased, decreased)"),
    DISPLAY_ACTIONS(1, "Display all actions for an evolution identified by <EVOLUTION_ID>"),
    DISPLAY_ACTION_TEXT(1, "Display the action description text identified with <ACTION_ID>"),
    DISPLAY_STRONGEST_ACTIONS(
        0, "Display the top three Pokemon evolutions with the strongest actions"),

    // System commands
    HELP(0, "Print usage"),
    EXIT(0, "Exit the application");

    private final int numArgs;
    private final String message;
    private final String[] optionPrompts;

    private Command(final int numArgs, final String message) {
      this(numArgs, message, new String[] {});
    }

    private Command(final int numArgs, final String message, final String... optionPrompts) {
      this.numArgs = numArgs;
      this.message = message;
      this.optionPrompts = optionPrompts;
    }

    public boolean hasArguments() {
      return numArgs > 0;
    }

    public int getNumArgs() {
      return numArgs;
    }

    public boolean hasOptions() {
      return optionPrompts.length > 0;
    }

    public int getNumOptions() {
      return optionPrompts.length;
    }

    public String getOptionPrompt(final int index) {
      return optionPrompts[index];
    }
  }

  private static final String COMMAND_PREFIX = "281-pokemon-management-system> ";

  public static void main(final String[] args) {
    new Main(new Scanner(System.in), new PokemonManagementSystem()).start(false);
  }

  public static String help() {
    final StringBuilder sb = new StringBuilder();

    for (final Command command : Command.values()) {
      sb.append(command).append("\t");

      // Add extra padding to align the argument counts
      // if the command name is less than the tab width.
      if (command.toString().length() < 16) {
        sb.append("\t");
      }

      // Add more for even shorter command names.
      if (command.toString().length() < 8) {
        sb.append("\t");
      }

      if (command.numArgs > 0) {
        sb.append("[")
            .append(command.numArgs)
            .append(" argument" + (command.numArgs > 1 ? "s" : "") + "]");
      } else {
        sb.append("[no args]");
      }

      sb.append("\t").append(command.message).append(System.lineSeparator());
    }

    return sb.toString();
  }

  private final Scanner scanner;

  private final PokemonManagementSystem system;

  public Main(final Scanner scanner, final PokemonManagementSystem system) {
    this.scanner = scanner;
    this.system = system;
  }

  public void start() {
    start(true);
  }

  // called early in test flow - James
  public void start(boolean debug) {
    System.out.println(help());

    // Prompt and process commands until the exit command.
    String command;
    do {
      System.out.print(COMMAND_PREFIX);
      command = scanner.nextLine().trim();
      if (debug) {
        System.out.println(command);
      }
    } while (processCommand(command, debug));
  }

  private static String[] splitWithQuotes(String input) {
    List<String> items = new ArrayList<>();

    // Match based on spaces, while respecting words surrounded by single quotes
    Matcher matcher = Pattern.compile("('(?:[^']+|'')*'\\S*|\\S+)").matcher(input);

    while (matcher.find()) {
      String matched = matcher.group(1);

      // Remove the surrounding quotes
      if (matched.startsWith("'") && matched.endsWith("'")) {
        matched = matched.substring(1, matched.length() - 1);
      }

      items.add(matched);
    }

    return items.toArray(new String[0]);
  }

  private boolean processCommand(String input, boolean debug) {
    // Remove whitespace at the beginning and end of the input.
    input = input.trim();

    final String[] args = splitWithQuotes(input);

    // In case the user pressed "Enter" without typing anything
    if (args.length == 0) {
      return true;
    }

    // Allow any case, and dashes to be used instead of underscores.
    final String commandStr = args[0].toUpperCase().replaceAll("-", "_");

    final Command command;

    try {
      // Command names correspond to the enum names.
      command = Command.valueOf(commandStr);
    } catch (final Exception e) {
      MessageCli.COMMAND_NOT_FOUND.printMessage(commandStr);
      return true;
    }

    // Check if the number of arguments provided don't match up as required for that command
    if (!checkArgs(command, args)) {
      MessageCli.WRONG_ARGUMENT_COUNT.printMessage(
          String.valueOf(command.getNumArgs()), command.getNumArgs() != 1 ? "s" : "", commandStr);

      return true;
    }

    switch (command) {
      case SEARCH_POKEMON:
        system.searchPokemon(args[1]);
        break;
      case CREATE_POKEMON:
        system.createPokemon(args[1], args[2], args[3]);
        break;
      case CREATE_EVOLUTION:
        system.createEvolution(args[1], args[2], args[3]);
        break;
      case VIEW_EVOLUTIONS:
        system.viewEvolutions(args[1]);
        break;
      case SEARCH_EVOLUTIONS:
        system.searchEvolutions(args[1]);
        break;
      case ADD_ATTACK_ACTION:
        system.addAttackAction(args[1], processOptions(command, debug));
        break;
      case ADD_HEAL_ACTION:
        system.addHealAction(args[1], processOptions(command, debug));
        break;
      case ADD_EFFECT_ACTION:
        system.addEffectAction(args[1], processOptions(command, debug));
        break;
      case DISPLAY_ACTIONS:
        system.displayActions(args[1]);
        break;
      case DISPLAY_ACTION_TEXT:
        system.displayActionText(args[1]);
        break;
      case DISPLAY_STRONGEST_ACTIONS:
        system.displayStrongestEvolutions();
        break;
      case EXIT:
        MessageCli.END.printMessage();
        // Signal that the program should exit.
        return false;
      case HELP:
        System.out.println(help());
        break;
    }

    // Signal that another command is expected.
    return true;
  }

  private String[] processOptions(final Command command, boolean debug) {
    final String[] options = new String[command.getNumOptions()];

    // Prompt the user for each option
    for (int i = 0; i < command.getNumOptions(); i++) {
      System.out.print("\t" + command.getOptionPrompt(i) + ": ");
      options[i] = scanner.nextLine().trim();

      // Print out the response in case we are in automated testing mode
      if (debug) {
        System.out.println(options[i]);
      }
    }
    return options;
  }

  private boolean checkArgs(final Command command, final String[] args) {
    return command.getNumArgs() == args.length - 1;
  }
}
