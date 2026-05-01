package nz.ac.auckland.se281;

import static nz.ac.auckland.se281.Main.Command.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
  MainTest.Task1.class,
  MainTest.Task2.class,
  // MainTest.Task3.class,
  // MainTest.YourTests.class, // Uncomment this line to run your own tests
})
public class MainTest {

  @FixMethodOrder(MethodSorters.NAME_ASCENDING)
  public static class Task1 extends CliTest {

    public Task1() {
      super(Main.class);
    }

    @Test
    public void T1_xx_zero_pokemon() throws Exception {
      runCommands(SEARCH_POKEMON, "*", EXIT);

      assertContains("There are no matching Pokemon found.");
      assertDoesNotContain("There is", true);
    }

    @Test
    public void T1_xx_create_pokemon_name() throws Exception {
      runCommands(CREATE_POKEMON, "'Charmander'", "'Fire'", "'common'", EXIT);

      assertContains("Successfully created a 'Charmander'");
      assertDoesNotContain("Pokemon not created", true);
      assertDoesNotContain("There is", true);
      assertDoesNotContain("There are", true);
    }

    @Test
    public void T1_xx_create_pokemon_type() throws Exception {
      runCommands(CREATE_POKEMON, "'Charmander'", "'Fire'", "'common'", EXIT);

      assertContains("Successfully created a 'Charmander'");
      assertContains("'Fire' type,");
      assertDoesNotContain("Pokemon not created", true);
      assertDoesNotContain("There is", true);
      assertDoesNotContain("There are", true);
    }

    @Test
    public void T1_xx_create_pokemon_name_abbreviation() throws Exception {
      runCommands(CREATE_POKEMON, "'Charmander'", "'Fire'", "'common'", EXIT);

      assertContains("Successfully created a 'Charmander'");
      assertContains("'Fire' type,");
      assertContains("CHA");
      assertDoesNotContain("Pokemon not created", true);
      assertDoesNotContain("There is", true);
      assertDoesNotContain("There are", true);
    }

    @Test
    public void T1_xx_create_pokemon_id_full() throws Exception {
      runCommands(CREATE_POKEMON, "'Charmander'", "'Fire'", "'common'", EXIT);

      assertContains(
          "Successfully created a 'Charmander', 'common' rarity, 'Fire' type, ('CHA-C-001').");
      assertDoesNotContain("Pokemon not created", true);
      assertDoesNotContain("There is", true);
      assertDoesNotContain("There are", true);
    }

    @Test
    public void T1_xx_create_pokemon_invalid_type() throws Exception {
      runCommands(CREATE_POKEMON, "'Charmander'", "'XYZ'", "'common'", EXIT);

      assertContains("Pokemon not created: 'XYZ' is an invalid Pokemon Type.");
      assertDoesNotContain("Successfully", true);
      assertDoesNotContain("There is", true);
      assertDoesNotContain("There are", true);
    }

    @Test
    public void T1_xx_create_pokemon_with_abbrev_type() throws Exception {
      runCommands(CREATE_POKEMON, "'Pikachu'", "'WAT'", "'common'", EXIT);

      assertContains(
          "Successfully created a 'Pikachu', 'common' rarity, 'Water' type, ('PIK-C-001').");
      assertDoesNotContain("Pokemon not created", true);
      assertDoesNotContain("There is", true);
      assertDoesNotContain("There are", true);
    }

    @Test
    public void T1_xx_create_pokemon_saved() throws Exception {
      runCommands(
          CREATE_POKEMON,
          "'Charmander'",
          "'Fire'",
          "'common'", //
          SEARCH_POKEMON,
          "*", //
          EXIT);

      assertContains("There is 1 matching Pokemon found:");
      assertContains("* Charmander (common rarity, type 'Fire', 'CHA-C-001')");
      assertDoesNotContain("Pokemon not created", true);
      assertDoesNotContain("There are", true);
    }

    @Test
    public void T1_xx_create_pokemon_saved_two() throws Exception {
      runCommands(
          CREATE_POKEMON,
          "'Charmander'",
          "'Fire'",
          "'common'", //
          CREATE_POKEMON,
          "'Squirtle'",
          "'Water'",
          "'common'", //
          SEARCH_POKEMON,
          "*", //
          EXIT);

      assertContains("There are 2 matching Pokemon found:");
      assertContains("* Charmander (common rarity, type 'Fire', 'CHA-C-001')");
      assertContains("* Squirtle (common rarity, type 'Water', 'SQU-C-001')");
      assertDoesNotContain("Pokemon not created", true);
      assertDoesNotContain("There is", true);
    }

    @Test
    public void T1_xx_create_pokemon_same_type() throws Exception {
      runCommands(
          CREATE_POKEMON,
          "'Squirtle'",
          "'Water'",
          "'common'",
          CREATE_POKEMON,
          "'Gyarados'",
          "'Water'",
          "'common'",
          CREATE_POKEMON,
          "'Seadra'",
          "'WAT'",
          "'common'",
          SEARCH_POKEMON,
          "*", //
          EXIT);

      assertContains(
          "Successfully created a 'Squirtle', 'common' rarity, 'Water' type, ('SQU-C-001').");
      assertContains(
          "Successfully created a 'Gyarados', 'common' rarity, 'Water' type, ('GYA-C-002').");
      assertContains(
          "Successfully created a 'Seadra', 'common' rarity, 'Water' type, ('SEA-C-003').");
      assertContains("There are 3 matching Pokemon found:");
      assertContains("* Squirtle (common rarity, type 'Water', 'SQU-C-001')");
      assertContains("* Gyarados (common rarity, type 'Water', 'GYA-C-002')");
      assertContains("* Seadra (common rarity, type 'Water', 'SEA-C-003')");
      assertDoesNotContain("Pokemon not created", true);
      assertDoesNotContain("There is", true);
    }

    @Test
    public void T1_xx_create_14_pokemon() throws Exception {
      runCommands(unpack(CREATE_14_POKEMON, EXIT));

      assertContains(
          "Successfully created a 'Pikachu', 'common' rarity, 'Electric' type, ('PIK-C-001').");
      assertContains(
          "Successfully created a 'Voltorb', 'common' rarity, 'Electric' type, ('VOL-C-002').");
      assertContains(
          "Successfully created a 'Bulbasaur', 'common' rarity, 'Grass' type, ('BUL-C-001').");
      assertContains("Pokemon not created: 'Bulbasaur' ('common') already exists.");
      assertContains(
          "Successfully created a 'Bulbasaur', 'shiny' rarity, 'Grass' type, ('BUL-S-002').");
      assertContains(
          "Successfully created a 'Charmander', 'common' rarity, 'Fire' type, ('CHA-C-001').");
      assertContains("Pokemon not created: 'shiney' is an invalid Pokemon rarity.");
      assertContains(
          "Successfully created a 'Squirtle', 'shiny' rarity, 'Water' type, ('SQU-S-001').");
      assertContains("Pokemon not created: 'Squirtle' ('shiny') already exists.");
      assertContains(
          "Successfully created a 'Poliwag', 'common' rarity, 'Water' type, ('POL-C-002').");
      assertContains(
          "Successfully created a 'Psyduck', 'common' rarity, 'Water' type, ('PSY-C-003').");
      assertContains("Pokemon not created: 'Psyduck' ('common') already exists.");
      assertContains(
          "Successfully created a 'Staryu', 'common' rarity, 'Water' type, ('STA-C-004').");
      assertContains("Pokemon not created: 'Psyduck' ('common') already exists.");
    }

    @Test
    public void T1_xx_create_14_pokemon_saved() throws Exception {
      runCommands(unpack(CREATE_14_POKEMON, SEARCH_POKEMON, "*", EXIT));

      assertContains(
          "Successfully created a 'Pikachu', 'common' rarity, 'Electric' type, ('PIK-C-001').");
      assertContains(
          "Successfully created a 'Voltorb', 'common' rarity, 'Electric' type, ('VOL-C-002').");
      assertContains(
          "Successfully created a 'Bulbasaur', 'common' rarity, 'Grass' type, ('BUL-C-001').");
      assertContains("Pokemon not created: 'Bulbasaur' ('common') already exists.");
      assertContains(
          "Successfully created a 'Bulbasaur', 'shiny' rarity, 'Grass' type, ('BUL-S-002').");
      assertContains(
          "Successfully created a 'Charmander', 'common' rarity, 'Fire' type, ('CHA-C-001').");
      assertContains("Pokemon not created: 'shiney' is an invalid Pokemon rarity.");
      assertContains(
          "Successfully created a 'Squirtle', 'shiny' rarity, 'Water' type, ('SQU-S-001').");
      assertContains("Pokemon not created: 'Squirtle' ('shiny') already exists.");
      assertContains(
          "Successfully created a 'Poliwag', 'common' rarity, 'Water' type, ('POL-C-002').");
      assertContains(
          "Successfully created a 'Psyduck', 'common' rarity, 'Water' type, ('PSY-C-003').");
      assertContains("Pokemon not created: 'Psyduck' ('common') already exists.");
      assertContains(
          "Successfully created a 'Staryu', 'common' rarity, 'Water' type, ('STA-C-004').");
      assertContains("Pokemon not created: 'Psyduck' ('common') already exists.");

      assertContains("There are 10 matching Pokemon found:");
      assertContains("* Pikachu (common rarity, type 'Electric', 'PIK-C-001')");
      assertContains("* Voltorb (common rarity, type 'Electric', 'VOL-C-002')");
      assertContains("* Bulbasaur (common rarity, type 'Grass', 'BUL-C-001')");
      assertContains("* Bulbasaur (shiny rarity, type 'Grass', 'BUL-S-002')");
      assertContains("* Charmander (common rarity, type 'Fire', 'CHA-C-001')");
      assertContains("* Squirtle (shiny rarity, type 'Water', 'SQU-S-001')");
      assertContains("* Poliwag (common rarity, type 'Water', 'POL-C-002')");
      assertContains("* Psyduck (common rarity, type 'Water', 'PSY-C-003')");
      assertContains("* Staryu (common rarity, type 'Water', 'STA-C-004')");
    }

    @Test
    public void T1_xx_search_pokemon_specific_types() throws Exception {
      runCommands(unpack(CREATE_14_POKEMON, SEARCH_POKEMON, "'water'", EXIT));

      assertContains("There are 5 matching Pokemon found:");
      assertContains("* Squirtle (shiny rarity, type 'Water', 'SQU-S-001')");
      assertContains("* Poliwag (common rarity, type 'Water', 'POL-C-002')");
      assertContains("* Psyduck (common rarity, type 'Water', 'PSY-C-003')");
      assertContains("* Staryu (common rarity, type 'Water', 'STA-C-004')");

      assertDoesNotContain("There is", true);
      assertDoesNotContain("There are 14", true);
    }

    @Test
    public void T1_xx_search_pokemon_common() throws Exception {
      runCommands(unpack(CREATE_14_POKEMON, SEARCH_POKEMON, "'common'", EXIT));
      assertContains("There are 8 matching Pokemon found:");
      assertContains("* Pikachu (common rarity, type 'Electric', 'PIK-C-001')");
      assertContains("* Voltorb (common rarity, type 'Electric', 'VOL-C-002')");
      assertContains("* Bulbasaur (common rarity, type 'Grass', 'BUL-C-001')");
      assertContains("* Charmander (common rarity, type 'Fire', 'CHA-C-001')");
      assertContains("* Poliwag (common rarity, type 'Water', 'POL-C-002')");
      assertContains("* Psyduck (common rarity, type 'Water', 'PSY-C-003')");
      assertContains("* Staryu (common rarity, type 'Water', 'STA-C-004')");

      assertDoesNotContain("There are no matching Pokemon found.");
      assertDoesNotContain("There is", true);
    }

    @Test
    public void T1_xx_search_pokemon_specific_type_abbreviation() throws Exception {
      runCommands(unpack(CREATE_14_POKEMON, SEARCH_POKEMON, "gra", EXIT));

      assertContains("There are 2 matching Pokemon found:");
      assertContains("* Bulbasaur (common rarity, type 'Grass', 'BUL-C-001')");
      assertContains("* Bulbasaur (shiny rarity, type 'Grass', 'BUL-S-002')");

      assertDoesNotContain("There is", true);
    }

    public static class YourTests extends CliTest {

      public YourTests() {
        super(Main.class);
      }
    }
  }

  @FixMethodOrder(MethodSorters.NAME_ASCENDING)
  public static class Task2 extends CliTest {

    public Task2() {
      super(Main.class);
    }

    @Override
    public void reset() {
      // BookingReferenceGenerator.reset();
    }

    @Test
    public void T2_xx_view_evolutions_invalid_pokemon() throws Exception {
      runCommands(unpack(CREATE_14_POKEMON, VIEW_EVOLUTIONS, "UHOH-AKL-789", EXIT));

      assertContains("Pokemon not found: 'UHOH-AKL-789' is an invalid pokemon ID.");
      assertDoesNotContain("There is", true);
      assertDoesNotContain("There are", true);
    }

    @Test
    public void T2_xx_view_pokemon_no_evolutions() throws Exception {
      runCommands(unpack(CREATE_14_POKEMON, VIEW_EVOLUTIONS, "PIK-C-001", EXIT));

      assertContains("There are no matching evolutions found.");
      assertDoesNotContain("There is", true);
    }

    @Test
    public void T2_xx_create_evolution_invalid_pokemon() throws Exception {
      runCommands(
          unpack(CREATE_14_POKEMON, CREATE_EVOLUTION, "'Pikachu'", "BASE", "'WACT-AKL-789'", EXIT));

      assertContains("Evolution not created: 'WACT-AKL-789' is an invalid pokemon ID.");
      assertDoesNotContain("Successfully created evolution", true);
    }

    @Test
    public void T2_xx_create_evolution_success_evolution_id() throws Exception {
      runCommands(
          unpack(CREATE_14_POKEMON, CREATE_EVOLUTION, "'Raichu'", "First", "'PIK-C-001'", EXIT));

      assertContains("Successfully created first evolution");
      assertContains("PIK-C-001-FIRST");
      assertDoesNotContain("Evolution not created", true);
    }

    @Test
    public void T2_xx_create_evolution_success_full_details() throws Exception {
      runCommands(
          unpack(CREATE_14_POKEMON, CREATE_EVOLUTION, "'Raichu'", "First", "'PIK-C-001'", EXIT));

      assertContains(
          "Successfully created first evolution 'Raichu' ('PIK-C-001-FIRST') for 'Pikachu'.");
      assertDoesNotContain("Evolution not created", true);
    }

    @Test
    public void T2_xx_saved_evolution_details_ignore_spaces_single_pokemon() throws Exception {
      runCommands(
          unpack(
              CREATE_14_POKEMON,
              CREATE_EVOLUTION,
              "'   Charmeleon   '",
              "First",
              "'CHA-C-001'",
              CREATE_EVOLUTION,
              "'Charizard'",
              "Final",
              "'CHA-C-001'",
              VIEW_EVOLUTIONS,
              "'CHA-C-001'",
              EXIT));

      assertContains("There are 2 matching evolutions found:");
      assertContains("* Charmeleon: [CHA-C-001-FIRST] from Charmander");
      assertContains("* Charizard: [CHA-C-001-FINAL] from Charmander");
      assertDoesNotContain("There is", true);
    }

    @Test
    public void T2_xx_create_23_evolutions() throws Exception {
      runCommands(unpack(CREATE_14_POKEMON, CREATE_23_EVOLUTIONS, EXIT));

      assertContains("Successfully created base evolution");
      assertContains("Successfully created first evolution");
      assertContains("Successfully created final evolution");
      assertDoesNotContain("Evolution not created", true);
    }

    @Test
    public void T2_xx_saved_23_evolutions_3_each() throws Exception {
      runCommands(
          unpack(
              CREATE_14_POKEMON,
              CREATE_23_EVOLUTIONS,
              VIEW_EVOLUTIONS,
              "'CHA-C-001'",
              VIEW_EVOLUTIONS,
              "'BUL-C-001'",
              VIEW_EVOLUTIONS,
              "'SQU-C-005'",
              EXIT));

      assertContains("There are 3 matching evolutions found:");
      assertDoesNotContain("Evolution not created", true);
      assertDoesNotContain("There is 1", true);
      assertDoesNotContain("There are 2", true);
    }

    @Test
    public void T2_xx_search_evolutions_all_no_evolutions_exist() throws Exception {
      runCommands(unpack(CREATE_14_POKEMON, SEARCH_EVOLUTIONS, "*", EXIT));

      assertContains("There are no matching evolutions found.");
      assertDoesNotContain("There is", true);
      assertDoesNotContain("There are 23", true);
    }

    @Test
    public void T2_xx_search_evolutions_found_all() throws Exception {
      runCommands(unpack(CREATE_14_POKEMON, CREATE_23_EVOLUTIONS, SEARCH_EVOLUTIONS, "*", EXIT));

      assertContains("There are 23 matching evolutions found:");
      assertDoesNotContain("There are 24", true);
    }

    @Test
    public void T2_xx_search_evolution_found_keyword_evolution_name() throws Exception {
      runCommands(
          unpack(CREATE_14_POKEMON, CREATE_23_EVOLUTIONS, SEARCH_EVOLUTIONS, "Raichu", EXIT));

      assertContains("There is 1 matching evolution found:");
      assertContains("* Raichu: [PIK-C-001-FIRST] from Pikachu");
      assertDoesNotContain("zero", true);
      assertDoesNotContain("There are", true);
    }

    @Test
    public void T2_xx_search_evolutions_found_keyword_evolution_name_three_single_pokemon()
        throws Exception {
      runCommands(unpack(CREATE_14_POKEMON, CREATE_23_EVOLUTIONS, SEARCH_EVOLUTIONS, "cha", EXIT));

      assertContains("There are 3 matching evolutions found:");
      assertContains("* Charmander: [CHA-C-001-BASE] from Charmander");
      assertContains("* Charmeleon: [CHA-C-001-FIRST] from Charmander");
      assertContains("* Charizard: [CHA-C-001-FINAL] from Charmander");
      assertDoesNotContain("zero", true);
      assertDoesNotContain("There is 1", true);
    }

    @Test
    public void T2_xx_search_evolutions_found_keyword_evolution_type() throws Exception {
      runCommands(unpack(CREATE_14_POKEMON, CREATE_23_EVOLUTIONS, SEARCH_EVOLUTIONS, "Base", EXIT));

      assertContains("There are 9 matching evolutions found:");
      assertContains(" * Pikachu: [PIK-C-001-BASE] from Pikachu");
      assertContains("  * Voltorb: [VOL-C-002-BASE] from Voltorb");
      assertContains("  * Bulbasaur: [BUL-C-001-BASE] from Bulbasaur");
      assertContains("  * Charmander: [CHA-C-001-BASE] from Charmander");
      assertContains("  * Squirtle: [SQU-S-001-BASE] from Squirtle");
      assertContains("  * Poliwag: [POL-C-002-BASE] from Poliwag");
      assertContains("  * Psyduck: [PSY-C-003-BASE] from Psyduck");
      assertContains("  * Staryu: [STA-C-004-BASE] from Staryu");
      assertContains("  * Squirtle: [SQU-C-005-BASE] from Squirtle");
      assertDoesNotContain("zero", true);
      assertDoesNotContain("There is 1", true);
    }

    @Test
    public void T2_xx_search_evolution_found_keyword_pokemon_type() throws Exception {
      runCommands(
          unpack(CREATE_14_POKEMON, CREATE_23_EVOLUTIONS, SEARCH_EVOLUTIONS, "Water", EXIT));

      assertContains("There are 13 matching evolutions found:");
      assertContains("* Squirtle: [SQU-S-001-BASE] from Squirtle");
      assertContains("  * Wartortle: [SQU-S-001-FIRST] from Squirtle");
      assertContains("  * Blastoise: [SQU-S-001-FINAL] from Squirtle");
      assertContains("  * Poliwag: [POL-C-002-BASE] from Poliwag");
      assertContains("  * Poliwhirl: [POL-C-002-FIRST] from Poliwag");
      assertContains("  * Poliwrath: [POL-C-002-FINAL] from Poliwag");
      assertContains("  * Psyduck: [PSY-C-003-BASE] from Psyduck");
      assertContains("  * Golduck: [PSY-C-003-FIRST] from Psyduck");
      assertContains("  * Staryu: [STA-C-004-BASE] from Staryu");
      assertContains("  * Starmie: [STA-C-004-FIRST] from Staryu");
      assertContains("  * Squirtle: [SQU-C-005-BASE] from Squirtle");
      assertContains("  * Wartortle: [SQU-C-005-FIRST] from Squirtle");
      assertDoesNotContain("zero", true);
      assertDoesNotContain("There is 1", true);
    }

    @Test
    public void T2_xx_search_evolutions_found_keyword_rarity() throws Exception {
      runCommands(
          unpack(CREATE_14_POKEMON, CREATE_23_EVOLUTIONS, SEARCH_EVOLUTIONS, "Shiny", EXIT));

      assertContains("There are 3 matching evolutions found:");
      assertContains("* Squirtle: [SQU-S-001-BASE] from Squirtle");
      assertContains("  * Wartortle: [SQU-S-001-FIRST] from Squirtle");
      assertContains("  * Blastoise: [SQU-S-001-FINAL] from Squirtle");
      assertDoesNotContain("zero", true);
      assertDoesNotContain("There is 1", true);
    }
  }

  @FixMethodOrder(MethodSorters.NAME_ASCENDING)
  public static class Task3 extends CliTest {

    public Task3() {
      super(Main.class);
    }

    @Override
    public void reset() {
      // BookingReferenceGenerator.reset();
    }

    @Test
    public void T3_xx_display_actions_no_actions() throws Exception {
      runCommands(
          unpack(
              CREATE_14_POKEMON,
              CREATE_23_EVOLUTIONS,
              DISPLAY_ACTIONS,
              "CHA-C-001-BASE",
              DISPLAY_ACTIONS,
              "CHA-C-001-FIRST",
              DISPLAY_ACTIONS,
              "CHA-C-001-FINAL",
              EXIT));

      assertContains("There are no recorded actions for evolution 'Charmander'.");
      assertContains("There are no recorded actions for evolution 'Charmeleon'.");
      assertContains("There are no recorded actions for evolution 'Charizard'.");
      assertDoesNotContain("There is 1", true);
    }

    @Test
    public void T3_xx_add_attack_action_invalid_evolution() throws Exception {
      runCommands(ADD_ATTACK_ACTION, "BUL-C-001-001", options("Vine Whip", "Grass", "1"), EXIT);

      assertContains("Action not added: 'BUL-C-001-001' is an invalid evolution ID.");
      assertDoesNotContain("added successfully for evolution", true);
    }

    @Test
    public void T3_xx_add_attack_action_everything_ok() throws Exception {
      runCommands(
          unpack(
              CREATE_14_POKEMON,
              CREATE_23_EVOLUTIONS,
              ADD_ATTACK_ACTION,
              "BUL-C-001-FINAL",
              options("Vine Whip", "Grass", "1"),
              EXIT));

      assertContains("added successfully for evolution ");
      assertDoesNotContain("Action not added", true);
    }

    @Test
    public void T3_xx_add_other_actions_everything_ok() throws Exception {
      runCommands(
          unpack(
              CREATE_14_POKEMON,
              CREATE_23_EVOLUTIONS,
              ADD_HEAL_ACTION,
              "PIK-C-001-FIRST",
              options("Rest", "Normal", "1"),
              ADD_EFFECT_ACTION,
              "CHA-C-001-FINAL",
              options("Growl", "Normal", "1", "opponent", "attack stat", "decreased"),
              EXIT));
      assertContains("Heal action '");
      assertContains("Effect action '");
      assertContains("added successfully for evolution");
      assertDoesNotContain("Action not added", true);
    }

    @Test
    public void T3_xx_attack_action_saved() throws Exception {
      runCommands(
          unpack(
              CREATE_14_POKEMON,
              CREATE_23_EVOLUTIONS,
              ADD_ATTACK_ACTION,
              "BUL-C-001-BASE",
              options("Vine Whip", "Grass", "1"),
              DISPLAY_ACTIONS,
              "BUL-C-001-BASE",
              EXIT));

      assertContains("There is 1 action for evolution 'Bulbasaur'.");
      assertContains("* Attack action (BUL-C-001-BASE-A1) 'Vine Whip' for 'Bulbasaur'.");
      // assertContains("\"Could be better\"");
      assertDoesNotContain("There are", true);
    }

    public void T3_xx_effect_action_saved() throws Exception {
      runCommands(
          unpack(
              CREATE_14_POKEMON,
              CREATE_23_EVOLUTIONS,
              ADD_EFFECT_ACTION,
              "CHA-C-001-FINAL",
              options("Dragon Dance", "Dragon", "1", "self", "attack stat", "increased"),
              DISPLAY_ACTIONS,
              "CHA-C-001-FINAL",
              EXIT));

      assertContains("There is 1 action for evolution 'Charizard'.");
      assertContains("* Effect action (CHA-C-001-FINAL-A1) 'Dragon Dance' for 'Charizard'.");

      assertDoesNotContain("There are", true);
    }

    @Test
    public void T3_xx_display_actions_multiple_actions() throws Exception {
      runCommands(
          unpack(
              CREATE_14_POKEMON,
              CREATE_23_EVOLUTIONS,
              ADD_ATTACK_ACTION,
              "BUL-C-001-BASE",
              options("Vine Whip", "Grass", "1"),
              ADD_HEAL_ACTION,
              "BUL-C-001-FIRST",
              options("Synthesis", "Grass", "1"),
              ADD_EFFECT_ACTION,
              "CHA-C-001-FINAL",
              options("Dragon Dance", "Dragon", "1", "self", "attack stat", "increased"),
              DISPLAY_ACTIONS,
              "BUL-C-001-BASE",
              DISPLAY_ACTIONS,
              "BUL-C-001-FIRST",
              DISPLAY_ACTIONS,
              "CHA-C-001-FINAL",
              EXIT));

      assertContains("There is 1 action for evolution 'Bulbasaur'.");
      assertContains("* Attack action (BUL-C-001-BASE-A1) 'Vine Whip' for 'Bulbasaur'.");
      assertContains("There is 1 action for evolution 'Ivysaur'.");
      assertContains("* Heal action (BUL-C-001-FIRST-A1) 'Synthesis' for 'Ivysaur'.");
      assertContains("There is 1 action for evolution 'Charizard'.");
      assertContains("* Effect action (CHA-C-001-FINAL-A1) 'Dragon Dance' for 'Charizard'.");
      assertDoesNotContain("There are", true);
      assertDoesNotContain("Need to email", true);
    }

    // RANK_EVOLUTIONS
    @Test
    public void T3_xx_rank_evolutions_no_actions() throws Exception {
      runCommands(unpack(CREATE_14_POKEMON, CREATE_23_EVOLUTIONS, DISPLAY_STRONGEST_ACTIONS, EXIT));

      assertContains("No actions found in Pikachu (PIK-C-001-BASE).");
      assertContains("No actions found in Raichu (PIK-C-001-FIRST).");
      assertContains("No actions found in Voltorb (VOL-C-002-BASE).");
      assertContains("No actions found in Electrode (VOL-C-002-FIRST).");
      assertContains("No actions found in Bulbasaur (BUL-C-001-BASE).");
      assertContains("No actions found in Ivysaur (BUL-C-001-FIRST).");
      assertContains("No actions found in Venusaur (BUL-C-001-FINAL).");
      assertContains("No actions found in Charmander (CHA-C-001-BASE).");
      assertContains("No actions found in Charmeleon (CHA-C-001-FIRST).");
      assertContains("No actions found in Charizard (CHA-C-001-FINAL).");
      assertContains("No actions found in Squirtle (SQU-S-001-BASE).");
      assertContains("No actions found in Wartortle (SQU-S-001-FIRST).");
      assertContains("No actions found in Blastoise (SQU-S-001-FINAL).");
      assertContains("No actions found in Poliwag (POL-C-002-BASE).");
      assertContains("No actions found in Poliwhirl (POL-C-002-FIRST).");
      assertContains("No actions found in Poliwrath (POL-C-002-FINAL).");
      assertContains("No actions found in Psyduck (PSY-C-003-BASE).");
      assertContains("No actions found in Golduck (PSY-C-003-FIRST).");
      assertContains("No actions found in Staryu (STA-C-004-BASE).");
      assertContains("No actions found in Starmie (STA-C-004-FIRST).");
      assertContains("No actions found in Squirtle (SQU-C-005-BASE).");
      assertContains("No actions found in Wartortle (SQU-C-005-FIRST).");
      assertContains("No actions found in Blastoise (SQU-C-005-FINAL).");
      assertDoesNotContain("The evolution with the strongest actions", true);
    }

    @Test
    public void T3_xx_rank_evolutions_two_actions() throws Exception {
      runCommands(
          unpack(
              CREATE_14_POKEMON,
              CREATE_23_EVOLUTIONS,
              ADD_ATTACK_ACTION,
              "PIK-C-001-BASE",
              options("Thunderbolt", "Electric", "3"),
              ADD_EFFECT_ACTION,
              "CHA-C-001-FINAL",
              options("Dragon Dance", "Dragon", "4", "self", "attack stat", "increased"),
              DISPLAY_STRONGEST_ACTIONS,
              EXIT));

      assertContains(
          "The evolution with the 1st strongest action is 'Charizard', with 'Dragon Dance' of"
              + " action power of '4'");
      assertContains(
          "The evolution with the 2nd strongest action is 'Pikachu', with 'Thunderbolt' of action"
              + " power of '3'");
    }

    @Test
    public void T3_xx_rank_evoluations_with_multiple_actions() throws Exception {
      runCommands(
          unpack(
              CREATE_14_POKEMON,
              CREATE_23_EVOLUTIONS,
              ADD_ATTACK_ACTION,
              "PIK-C-001-BASE",
              options("Thunderbolt", "Electric", "3"),
              ADD_HEAL_ACTION,
              "PIK-C-001-BASE",
              options("REST", "Psychic", "1"),
              ADD_EFFECT_ACTION,
              "CHA-C-001-FINAL",
              options("Dragon Dance", "Dragon", "4", "self", "attack stat", "increased"),
              ADD_HEAL_ACTION,
              "CHA-C-001-FINAL",
              options("REST", "Psychic", "1"),
              ADD_ATTACK_ACTION,
              "SQU-C-005-BASE",
              options("Water Gun", "Water", "2"),
              ADD_ATTACK_ACTION,
              "PSY-C-003-BASE",
              options("Scratch", "Normal", "1"),
              DISPLAY_STRONGEST_ACTIONS,
              EXIT));

      assertContains(
          "The evolution with the 1st strongest action is 'Charizard', with 'Dragon Dance' of"
              + " action power of '4'");
      assertContains(
          "The evolution with the 2nd strongest action is 'Pikachu', with 'Thunderbolt' of action"
              + " power of '3'");
      assertContains(
          "The evolution with the 3rd strongest action is 'Squirtle', with 'Water Gun' of action"
              + " power of '2'");
    }

    @Test
    public void T3_xx_rank_evolutions_different_rarities() throws Exception {
      runCommands(
          unpack(
              CREATE_14_POKEMON,
              CREATE_23_EVOLUTIONS,
              ADD_ATTACK_ACTION,
              "SQU-S-001-BASE",
              options("Hydro Pump", "Water", "3"),
              ADD_ATTACK_ACTION,
              "SQU-C-005-BASE",
              options("Water Gun", "Water", "2"),
              DISPLAY_STRONGEST_ACTIONS,
              EXIT));

      assertContains(
          "The evolution with the 1st strongest action is 'Squirtle', with 'Hydro Pump' of action"
              + " power of '3'");
      assertContains(
          "The evolution with the 2nd strongest action is 'Squirtle', with 'Water Gun' of action"
              + " power of '2'");
      assertDoesNotContain("The evolution with the 3rd", true);
    }

    @Test
    public void T3_xx_display_action_text_attack() throws Exception {
      runCommands(
          unpack(
              CREATE_14_POKEMON,
              CREATE_23_EVOLUTIONS,
              ADD_ATTACK_ACTION,
              "SQU-S-001-BASE",
              options("Hydro Pump", "Water", "3"),
              DISPLAY_ACTION_TEXT,
              "SQU-S-001-BASE-A1",
              EXIT));

      assertContains(
          "Squirtle attacked its opponent with Hydro Pump, its opponent's health was reduced.");
    }

    @Test
    public void T3_xx_display_action_text_heal() throws Exception {
      runCommands(
          unpack(
              CREATE_14_POKEMON,
              CREATE_23_EVOLUTIONS,
              ADD_HEAL_ACTION,
              "SQU-S-001-BASE",
              options("Rest", "Psychic", "2"),
              DISPLAY_ACTION_TEXT,
              "SQU-S-001-BASE-A1",
              EXIT));

      assertContains("Squirtle healed itself with Rest, its health was increased.");
      assertDoesNotContain("Squirtle attacked", true);
    }

    @Test
    public void T3_xx_display_action_text_effect() throws Exception {
      runCommands(
          unpack(
              CREATE_14_POKEMON,
              CREATE_23_EVOLUTIONS,
              ADD_EFFECT_ACTION,
              "SQU-S-001-BASE",
              options("Rain Dance", "Water", "2", "self", "attack stat", "increased"),
              DISPLAY_ACTION_TEXT,
              "SQU-S-001-BASE-A1",
              EXIT));

      assertContains("Squirtle buffed itself with Rain Dance, its attack stat was increased.");

      assertDoesNotContain("Squirtle attacked", true);
      assertDoesNotContain("Squirtle healed", true);
    }

    @Test
    public void T3_xx_display_action_text_effect_2() throws Exception {
      runCommands(
          unpack(
              CREATE_14_POKEMON,
              CREATE_23_EVOLUTIONS,
              ADD_EFFECT_ACTION,
              "SQU-S-001-BASE",
              options("Bubble", "Water", "2", "opponent", "speed stat", "decreased"),
              DISPLAY_ACTION_TEXT,
              "SQU-S-001-BASE-A1",
              EXIT));

      assertContains(
          "Squirtle debuffed its opponent with Bubble, its opponent's speed stat was decreased.");

      assertDoesNotContain("Squirtle buffed", true);
      assertDoesNotContain("Squirtle attacked", true);
      assertDoesNotContain("Squirtle healed", true);
    }
  }

  @FixMethodOrder(MethodSorters.NAME_ASCENDING)
  public static class YourTests extends CliTest {

    public YourTests() {
      super(Main.class);
    }

    @Override
    public void reset() {}

    @Test
    public void T4_01_add_your_own_tests_as_needed() throws Exception {}
  }

  private static final Object[] CREATE_14_POKEMON =
      new Object[] {
        CREATE_POKEMON,
        "'Pikachu'", // Electric
        "'Electric'",
        "'common'",
        CREATE_POKEMON,
        "'Voltorb'", // Electric
        "'Electric'",
        "'common'",
        CREATE_POKEMON,
        "'Bulbasaur'", // Grass
        "'Grass'",
        "'common'",
        CREATE_POKEMON,
        "'Bulbasaur'", // Grass
        "'Grass'",
        "'common'",
        CREATE_POKEMON,
        "'Bulbasaur'", // Grass
        "'Grass'",
        "'shiny'",
        CREATE_POKEMON,
        "'Charmander'", // Fire
        "'Fire'",
        "'common'",
        CREATE_POKEMON,
        "'Squirtle'", // Water
        "'Water'",
        "'shiney'",
        CREATE_POKEMON,
        "'Squirtle'", // Water
        "'Water'",
        "'shiny'",
        CREATE_POKEMON,
        "'Squirtle'", // Water
        "'Water'",
        "'shiny'",
        CREATE_POKEMON,
        "'Poliwag'", // Water
        "'Water'",
        "'common'",
        CREATE_POKEMON,
        "'Psyduck'", // Water
        "'Water'",
        "'common'",
        CREATE_POKEMON,
        "'Psyduck'", // Water
        "'Water'",
        "'common'",
        CREATE_POKEMON,
        "'Staryu'", // Water
        "'Water'",
        "'common'",
        CREATE_POKEMON,
        "'Squirtle'", // Water
        "'Water'",
        "'common'",
      };

  private static final Object[] CREATE_23_EVOLUTIONS =
      new Object[] {
        CREATE_EVOLUTION, // 1
        "'Pikachu'",
        "Base",
        "'PIK-C-001'",
        CREATE_EVOLUTION, // 2
        "'Raichu'",
        "First",
        "'PIK-C-001'",
        CREATE_EVOLUTION, // 3
        "'Charmander'",
        "Base",
        "'CHA-C-001'",
        CREATE_EVOLUTION, // 4
        "'Charmeleon'",
        "First",
        "'CHA-C-001'",
        CREATE_EVOLUTION, // 5
        "'Charizard'",
        "Final",
        "'CHA-C-001'",
        CREATE_EVOLUTION, // 6
        "'Squirtle'",
        "Base",
        "'SQU-S-001'",
        CREATE_EVOLUTION, // 7
        "'Wartortle'",
        "First",
        "'SQU-S-001'",
        CREATE_EVOLUTION, // 8
        "'Blastoise'",
        "Final",
        "'SQU-S-001'",
        CREATE_EVOLUTION, // 9
        "'Bulbasaur'",
        "Base",
        "'BUL-C-001'",
        CREATE_EVOLUTION, // 10
        "'Ivysaur'",
        "First",
        "'BUL-C-001'",
        CREATE_EVOLUTION, // 11
        "'Venusaur'",
        "Final",
        "'BUL-C-001'",
        CREATE_EVOLUTION, // 12
        "'Poliwag'",
        "Base",
        "'POL-C-002'",
        CREATE_EVOLUTION, // 13
        "'Poliwhirl'",
        "First",
        "'POL-C-002'",
        CREATE_EVOLUTION, // 14
        "'Poliwrath'",
        "Final",
        "'POL-C-002'",
        CREATE_EVOLUTION, // 15
        "'Staryu'",
        "Base",
        "'STA-C-004'",
        CREATE_EVOLUTION, // 16
        "'Starmie'",
        "First",
        "'STA-C-004'",
        CREATE_EVOLUTION, // 17
        "'Psyduck'",
        "Base",
        "'PSY-C-003'",
        CREATE_EVOLUTION, // 18
        "'Golduck'",
        "First",
        "'PSY-C-003'",
        CREATE_EVOLUTION, // 19
        "'Voltorb'",
        "Base",
        "'VOL-C-002'",
        CREATE_EVOLUTION, // 20
        "'Electrode'",
        "First",
        "'VOL-C-002'",
        CREATE_EVOLUTION, // 21
        "'Squirtle'",
        "Base",
        "'SQU-C-005'",
        CREATE_EVOLUTION, // 22
        "'Wartortle'",
        "First",
        "'SQU-C-005'",
        CREATE_EVOLUTION, // 23
        "'Blastoise'",
        "Final",
        "'SQU-C-005'",
      };

  private static Object[] unpack(Object[] commands, Object... more) {
    List<Object> all = new ArrayList<Object>();
    all.addAll(List.of(commands));

    for (Object item : more) {
      // String[] are options for certain commands, so treat as a single item
      if (item instanceof Object[] && !(item instanceof String[])) {
        all.addAll(Arrays.asList((Object[]) item));
      } else {
        all.add(item);
      }
    }
    return all.toArray(new Object[0]);
  }

  private static String[] options(String... options) {
    List<String> all = new ArrayList<String>();
    all.addAll(List.of(options));
    return all.toArray(new String[all.size()]);
  }
}
