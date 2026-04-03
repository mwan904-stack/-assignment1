package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.List;

public class PokemonManagementSystem {

  // Do not change the parameters of the constructor
  public PokemonManagementSystem() {
    pokemons = new ArrayList<>();
  }

  // ── Task 1: Search & Create Pokemon ─────────────────────────────────

  public void searchPokemon(String keyword) {
    List<Pokemon> matches = new ArrayList<>();
    String trimmedKeyword = keyword.trim().toLowerCase();

    if (trimmedKeyword.equals("*")) {
      matches.addAll(pokemons);
    } else {
      for (Pokemon pokemon : pokemons) {
        if (matchesKeyword(pokemon, trimmedKeyword)) {
          matches.add(pokemon);
        }
      }
    }

    if (matches.isEmpty()) {
      MessageCli.POKEMON_FOUND.printMessage("are", "no", ".");
      return;
    }

    if (matches.size() == 1) {
      MessageCli.POKEMON_FOUND.printMessage("is", "1", ":");
    } else {
      MessageCli.POKEMON_FOUND.printMessage("are", String.valueOf(matches.size()), ":");
    }
    for (Pokemon pokemon : matches) {
      MessageCli.POKEMON_ENTRY.printMessage(
          pokemon.getName(), pokemon.getRarity(), pokemon.getType(), pokemon.getId());
    }
  }

  private boolean matchesKeyword(Pokemon pokemon, String keyword) {
    return pokemon.getName().toLowerCase().contains(keyword)
        || pokemon.getType().toLowerCase().contains(keyword)
        || pokemon.getRarity().toLowerCase().contains(keyword)
        || pokemon.getId().toLowerCase().contains(keyword);
  }

  public void createPokemon(String pokemonName, String typeName, String rarityName) {
    String trimmedName = pokemonName.trim();
    String trimmedType = typeName.trim();
    String trimmedRarity = rarityName.trim();

    if (trimmedName.length() < 3 || trimmedName.length() > 10) {
      MessageCli.POKEMON_NOT_CREATED_INVALID_POKEMON_NAME.printMessage(trimmedName);
      return;
    }

    Characteristics.PokemonType pokemonType = Characteristics.PokemonType.fromString(trimmedType);
    if (pokemonType == null) {
      MessageCli.POKEMON_NOT_CREATED_INVALID_TYPE.printMessage(trimmedType);
      return;
    }

    Characteristics.Rarity rarity = Characteristics.Rarity.fromString(trimmedRarity);
    if (rarity == null) {
      MessageCli.POKEMON_NOT_CREATED_INVALID_RARITY.printMessage(trimmedRarity);
      return;
    }

    String storedRarity = trimmedRarity.toLowerCase();

    for (Pokemon pokemon : pokemons) {
      if (pokemon.getName().equalsIgnoreCase(trimmedName)
          && pokemon.getRarity().equalsIgnoreCase(storedRarity)) {
        MessageCli.POKEMON_NOT_CREATED_ALREADY_EXISTS.printMessage(trimmedName, storedRarity);
        return;
      }
    }

    String abbreviation = trimmedName.substring(0, 3).toUpperCase();
    String rarityInitial = rarity.getName().substring(0, 1).toUpperCase();

    int count = 1;
    for (Pokemon pokemon : pokemons) {
      if (pokemon.getType().equalsIgnoreCase(pokemonType.getTypeName())) {
        count++;
      }
    }

    String number = String.format("%03d", count);
    String pokemonId = abbreviation + "-" + rarityInitial + "-" + number;

    Pokemon newPokemon =
        new Pokemon(trimmedName, pokemonType.getTypeName(), storedRarity, pokemonId);
    pokemons.add(newPokemon);

    MessageCli.POKEMON_CREATED.printMessage(
        trimmedName, storedRarity, pokemonType.getTypeName(), pokemonId);
  }

  // ── Task 2: Evolutions ──────────────────────────────────────────────

  public void viewEvolutions(String pokemonId) {
    // TODO implement
  }

  public void createEvolution(String evolutionName, String evolutionType, String pokemonId) {
    // TODO implement
  }

  public void searchEvolutions(String keyword) {
    // TODO implement
  }

  // ── Task 3: Actions ─────────────────────────────────────────────────

  public void addAttackAction(String evolutionId, String[] options) {
    // TODO implement
  }

  public void addHealAction(String evolutionId, String[] options) {
    // TODO implement
  }

  public void addEffectAction(String evolutionId, String[] options) {
    // TODO implement
  }

  public void displayActions(String evolutionId) {
    // TODO implement
  }

  public void displayStrongestEvolutions() {
    // TODO implement
  }

  public void displayActionText(String actionId) {
    // TODO implement
  }

  private List<Pokemon> pokemons;
}
