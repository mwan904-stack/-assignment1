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

    if (!isValidPokemonName(trimmedName)) {
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

    if (PokemonAlreadyExists(trimmedName, storedRarity)) {
      MessageCli.POKEMON_NOT_CREATED_ALREADY_EXISTS.printMessage(trimmedName, storedRarity);
      return;
    }

    String id = BuildPokemonId(trimmedName, rarity, pokemonType.getTypeName());

    Pokemon newPokemon = new Pokemon(trimmedName, pokemonType.getTypeName(), storedRarity, id);
    pokemons.add(newPokemon);

    MessageCli.POKEMON_CREATED.printMessage(
        trimmedName, storedRarity, pokemonType.getTypeName(), id);
  }

  private boolean isValidPokemonName(String name) {
    return name.length() >= 3 && name.length() <= 10;
  }

  private boolean PokemonAlreadyExists(String name, String rarity) {
    for (Pokemon pokemon : pokemons) {
      if (pokemon.getName().equalsIgnoreCase(name)
          && pokemon.getRarity().equalsIgnoreCase(rarity)) {
        return true;
      }
    }
    return false;
  }

  private int getPokemonCountByType(String pokemonTypeName) {
    int count = 0;
    for (Pokemon pokemon : pokemons) {
      if (pokemon.getType().equalsIgnoreCase(pokemonTypeName)) {
        count++;
      }
    }
    return count;
  }

  private String BuildPokemonId(
      String name, Characteristics.Rarity rarity, String pokemonTypeName) {

    String shortName = name.substring(0, 3).toUpperCase();
    String rarityCode = rarity.getName().substring(0, 1).toUpperCase();
    int nextNumber = getPokemonCountByType(pokemonTypeName) + 1;

    return shortName + "-" + rarityCode + "-" + String.format("%03d", nextNumber);
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
