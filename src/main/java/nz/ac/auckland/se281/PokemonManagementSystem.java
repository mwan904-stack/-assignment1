package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.List;
import nz.ac.auckland.se281.Characteristics.EvolutionType;

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

  private Pokemon findPokemonById(String id) {

    for (Pokemon pokemon : pokemons) {
      if (pokemon.getId().equalsIgnoreCase(id.trim())) {
        return pokemon;
      }
    }

    return null;
  }

  public void viewEvolutions(String pokemonId) {
    String id = pokemonId.trim();
    Pokemon p = findPokemonById(id);

    if (p == null) {
      MessageCli.POKEMON_NOT_FOUND.printMessage(id);
    } else {
      printEvolutions(p.getEvolutions());
    }
  }

  public void createEvolution(String evolutionName, String evolutionType, String pokemonId) {
    String trimmedName = evolutionName.trim();
    String trimmedType = evolutionType.trim();
    String trimmedPokemonId = pokemonId.trim();

    if (!(trimmedName.length() >= 3 && trimmedName.length() <= 10)) {
      MessageCli.EVOLUTION_NOT_CREATED_INVALID_EVOlUTION_NAME.printMessage(trimmedName);
      return;
    }

    Pokemon target = findPokemonById(pokemonId.trim());

    if (target == null) {
      MessageCli.EVOLUTION_NOT_CREATED_INVALID_POKEMON_ID.printMessage(trimmedPokemonId);
      return;
    }

    EvolutionType type = EvolutionType.fromString(trimmedType);

    Evolution evolution = new Evolution(trimmedName, type, target);
    List<Evolution> list = target.getEvolutions();
    list.add(evolution);

    MessageCli.EVOLUTION_CREATED.printMessage(
        type.toString().toLowerCase(), evolution.getName(), evolution.getId(), target.getName());
  }

  public void searchEvolutions(String keyword) {
    String target = keyword.trim().toLowerCase();

    List<Evolution> found = new ArrayList<>();

    for (Pokemon currentPokemon : pokemons) {
      for (Evolution currentEvolution : currentPokemon.getEvolutions()) {
        if (target.equals("*")) {
          found.add(currentEvolution);
        } else if (matchesEvolutionKeyword(currentEvolution, target)) {
          found.add(currentEvolution);
        }
      }
    }

    printEvolutions(found);
  }

  private boolean matchesEvolutionKeyword(Evolution evolution, String keyword) {
    Pokemon pokemon = evolution.getPokemon();

    if (evolution.getName().toLowerCase().contains(keyword)) return true;
    if (evolution.getType().toString().toLowerCase().contains(keyword)) return true;
    if (evolution.getId().toLowerCase().contains(keyword)) return true;
    if (pokemon.getRarity().toLowerCase().contains(keyword)) return true;
    if (pokemon.getType().toLowerCase().contains(keyword)) return true;
    return false;
  }

  private void printEvolutions(List<Evolution> evolutions) {
    if (evolutions.size() == 0) {
      MessageCli.EVOLUTIONS_FOUND.printMessage("are", "no", "s", ".");
      return;
    }

    if (evolutions.size() == 1) {
      MessageCli.EVOLUTIONS_FOUND.printMessage("is", "1", "", ":");
    } else {
      MessageCli.EVOLUTIONS_FOUND.printMessage("are", String.valueOf(evolutions.size()), "s", ":");
    }

    for (Evolution evolution : evolutions) {
      MessageCli.EVOLUTION_ENTRY.printMessage(
          evolution.getName(), evolution.getId(), evolution.getPokemon().getName());
    }
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
