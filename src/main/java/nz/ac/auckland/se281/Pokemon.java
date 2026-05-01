package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.List;

public class Pokemon {

  private final String id;
  private final String name;
  private final String type;
  private final String rarity;
  private List<Evolution> evolutions;

  public Pokemon(String name, String type, String rarity, String id) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.rarity = rarity;
    this.evolutions = new ArrayList<>();
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

  public List<Evolution> getEvolutions() {
    return evolutions;
  }
}
