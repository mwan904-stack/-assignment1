package nz.ac.auckland.se281;

/* You cannot modify this class! */

public class Characteristics {

  public enum PokemonType {
    FIRE("Fire", "FIR"),
    WATER("Water", "WAT"),
    GRASS("Grass", "GRA"),
    ELECTRIC("Electric", "ELE");

    private final String typeName;
    private final String typeAbbreviation;

    PokemonType(String typeName, String typeAbbreviation) {
      this.typeName = typeName;
      this.typeAbbreviation = typeAbbreviation;
    }

    public String getTypeName() {
      return this.typeName;
    }

    public String getTypeAbbreviation() {
      return this.typeAbbreviation;
    }

    @Override
    public String toString() {
      return this.getTypeName();
    }

    public static PokemonType fromString(String text) {
      for (PokemonType type : PokemonType.values()) {
        if (type.typeName.equalsIgnoreCase(text) || type.typeAbbreviation.equalsIgnoreCase(text)) {
          return type;
        }
      }
      return null;
    }
  }

  public enum Rarity {
    COMMON("Common"),
    SHINY("Shiny");

    private final String name;

    Rarity(String name) {
      this.name = name;
    }

    public String getName() {
      return this.name;
    }

    @Override
    public String toString() {
      return this.name;
    }

    public static Rarity fromString(String text) {
      for (Rarity rarity : Rarity.values()) {
        if (rarity.name.equalsIgnoreCase(text)) {
          return rarity;
        }
      }
      return null;
    }
  }

  public enum EvolutionType {
    BASE("Base"),
    FIRST("First"),
    FINAL("Final"),
    OTHER("Other");

    private final String name;

    EvolutionType(String name) {
      this.name = name;
    }

    public String getName() {
      return this.name;
    }

    @Override
    public String toString() {
      return this.name;
    }

    public static EvolutionType fromString(String text) {
      for (EvolutionType type : EvolutionType.values()) {
        if (type.name.equalsIgnoreCase(text)) {
          return type;
        }
      }
      return OTHER;
    }
  }

  public enum ActionType {
    ATTACK("Attack"),
    HEAL("Heal"),
    EFFECT("Effect");

    private final String name;

    ActionType(String name) {
      this.name = name;
    }

    public String getName() {
      return this.name;
    }

    @Override
    public String toString() {
      return this.name;
    }
  }
}
