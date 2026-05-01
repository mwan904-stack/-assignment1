package nz.ac.auckland.se281;

public class Evolution {
  private String name;
  private Characteristics.EvolutionType type;
  private String id;
  private Pokemon pokemons;

  public Evolution(String name, Characteristics.EvolutionType type, Pokemon pokemons) {
    this.name = name;
    this.type = type;
    this.pokemons = pokemons;
    this.id = pokemons.getId().concat("-").concat(type.name());
  }

  public String getName() {
    return name;
  }

  public Characteristics.EvolutionType getType() {
    return type;
  }

  public String getId() {
    return id;
  }

  public Pokemon getPokemon() {
    return pokemons;
  }
}
