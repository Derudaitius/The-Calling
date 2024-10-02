
import java.util.ArrayList;
import java.util.HashMap;

/**class for the main character
 * The goal of this class is to contain all information relevant to saving the game as well
 * as information that represents the main character
 *
 * special events happen during the game (the mother tree dies, demon is blinded, etc.),
 * and the decisions made in these events are also recorded and saved to the player class
 */
public class Player {

    private Integer HP;
    private Integer DemonHP; //the demon's health
    private boolean blindDemon; //player calls the sprite with their whistle, and the sprite blinds the demon for an attack de-buff
    private boolean poisonKnife; //player has the knife and poision, and uses poision on the knife for attack buff
    private String position;
    private String name;
    private int trickedCount; //the amount of times you fell for the demons tricks
    private boolean noHesitation; //followed voice immediately rather than going into village first
    private boolean dreamHelp; //attempted to move in dream, and received help from mother tree
    private boolean impHelp; //Imp helps to make poison
    private boolean motherTreeAlive;
    private boolean demonAlive;
    private HashMap<String, Boolean> inventory;
    private ArrayList items = new ArrayList();

    // Constructor with default health set to 10
    public Player() {
        this.name = name;
        this.HP = 10;
        this.DemonHP = 30;
        this.blindDemon = false;
        this.poisonKnife = false;
        this.trickedCount = 0;
        this.motherTreeAlive = true;
        this.demonAlive = true;
        this.noHesitation = false;
        this.dreamHelp = false;
        this.impHelp = false;
        this.inventory = new HashMap<>();
        defaultItems();
    }

    // Get HighScore
    public int getHighScore() {
        return highScore();
    }

    /** The highscore calculator gives an aggregate score based on both the players decisions
     * the Idea is that maintaining the highest health possible will result in a high score, but
     * completing challenges will also add to that score.
     */
    private int highScore() {
        int score = (HP * 10);
        if (motherTreeAlive) {
            score += 50; //+50 if mother tree is alive
        }
        if (demonAlive) {
            score -= 50; //-50 if demon is alive
        } else {
            score += 50; //if demon is dead, +50
        }
        if (this.hasItem("Orion's beard")) {
            score += 25;// if Orions's beard was not used, +25
        }
        if (this.hasItem("Poison")) {
            score += 25;// if Poison was not used, +25
        }
        return score;
    }

    // Initialize items (set to false)
    private void defaultItems() {
        inventory.put("Knife", false);
        items.add("Knife");
        inventory.put("Rope", false);
        items.add("Rope");
        inventory.put("Compass", false);
        items.add("Compass");
        inventory.put("Whistle", false);
        items.add("Whistle");
        inventory.put("Orion's beard", false);
        items.add("Orion's beard");
        inventory.put("Raw meat", false);
        items.add("Raw meat");
        inventory.put("Nightshade", false);
        items.add("Nightshade");
        inventory.put("Poison", false);
        items.add("Poison");

    }
    // resets all the items to false
    private void resetItems() {
        inventory.put("Knife", false);
        inventory.put("Rope", false);
        inventory.put("Compass", false);
        inventory.put("Whistle", false);
        inventory.put("Orion's beard", false);
        inventory.put("Raw meat", false);
        inventory.put("Nightshade", false);
        inventory.put("Poison", false);
    }
    // Set Poison Knife
    public void setPoisonKnife () {
        this.poisonKnife = true;
    }
    // Get Poison Knife
    public boolean getPoisonKnife() {
        return poisonKnife;
    }

    // Set Blind Demon
    public void setBlindDemon() {
        this.blindDemon = true;
    }
    // Get Blind Demon
    public boolean getBlindDemon() {
        return blindDemon;
    }

    // Get DemonHP
    public int getDemonHP() {
        return DemonHP;
    }
    // Set DemonHP
    public void setDemonHP(int DemonHP) {
        this.DemonHP = DemonHP;
    }

    // Get impHelp
    public boolean getImpHelp() {
        return impHelp;
    }
    // Set impHelp
    public void setImpHelp(boolean impHelp) {
        this.impHelp = impHelp;
    }

    // Get Tricked Count
    public int getTrickedCount() {
        return trickedCount;
    }
    // Set Tricked Count
    public void setTrickedCount(int trickedCount) {
        this.trickedCount = trickedCount;
    }

    // Set motherTreeAlive
    public void setMotherTreeAlive(boolean motherTreeAlive) {
        this.motherTreeAlive = motherTreeAlive;
    }
    // Get motherTreeAlive
    public boolean getMotherTreeAlive() {
        return motherTreeAlive;
    }

    // Set demonAlive
    public void setDemonAlive(boolean demonAlive) {
        this.demonAlive = demonAlive;
    }
    // Get demonAlive
    public boolean getDemonAlive() {
        return demonAlive;
    }

    // Set dreamHelp
    public void setDreamHelp(boolean dreamHelp) {
        this.dreamHelp = dreamHelp;
    }
    // Get dreamHelp
    public boolean getDreamHelp() {
        return dreamHelp;
    }

    // Set noHessitation
    public void setNoHesitation(boolean noHesitation) {
        this.noHesitation = noHesitation;
    }
    // Get noHessitation
    public boolean getNoHesitation() {
        return noHesitation;
    }

    // Method to acquire an item
    public void acquireItem(String itemName){
        try {
            inventory.put(itemName, true);
            System.out.println(name + " acquired " + itemName + "!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to remove an item
    public void removeItem(String itemName) {
        try {
            inventory.put(itemName, false);
            System.out.println(name + " lost " + itemName + "!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Method to check if the character has a specific item
    public boolean hasItem(String itemName) {
        return inventory.getOrDefault(itemName, false);
    }

    // Get HP
    public String getHP() {
        return HP.toString();
    }
    public int getHPNum() {
        return HP;
    }

    // Set HP
    public void setHP(int HP) {
        if (HP < 0) {
            this.HP = 0; // Set HP to 0 if it's negative since you cant be less than dead
        } else {
            this.HP = HP;
        }
    }

    // Get name
    public String getName() {
        return name;
    }

    // Set name
    public void setName(String name) {
        this.name = name;
    }

    //get position
    public String getPosition() {
        return position;
    }

    //set position
    public void setPosition(String position) {
        this.position = position;
    }

    // Get inventory
    public String getItems() {
        String def = new String("None");
        String playerItems = "";
        for(int i = 0; i < items.size(); i++) {
            if (playerItems != "" && inventory.getOrDefault(items.get(i), false) ) {
                playerItems += ", " + items.get(i);
            } else if (inventory.getOrDefault(items.get(i), false)){
                playerItems += items.get(i);
            }
        }
        if (!playerItems.equals("")) {
            return playerItems;
        }
        else {
            return def;
        }

    }

    //resets the players stats to base stats
    public void resetPlayer() {
        this.name = name;
        this.HP = 10;
        this.DemonHP = 30;
        this.trickedCount = 0;
        this.motherTreeAlive = true;
        this.demonAlive = true;
        this.noHesitation = false;
        this.dreamHelp = false;
        this.impHelp = false;
        this.inventory = new HashMap<>();
        resetItems();
    }

}