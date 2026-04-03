package nz.ac.auckland.se281;

public class Pokemon {

  private String name;
  private String type;
  private String rarity;
  private String id;

  public Pokemon(String name, String type, String rarity, String id) {
    this.name = name;
    this.type = type;
    this.rarity = rarity;
    this.id = id;
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

  public String getId() {
    return id;
  }
}
