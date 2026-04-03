package nz.ac.auckland.se281;

public class Pokemon {

  private final String id;
  private final String name;
  private final String type;
  private final String rarity;

  public Pokemon(String name, String type, String rarity, String id) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.rarity = rarity;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  public String getRarity() {
    return rarity;
  }

  @Override
  public String toString() {
    return name + " (" + rarity + ", " + type + ", " + id + ")";
  }
}
