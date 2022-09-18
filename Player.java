import java.util.ArrayList;

class Player{
  
  static char playerChar = '☿';

  static int playerMaxHealth = 100;
  static int playerHealth = playerMaxHealth;
  static int playerMaxMana = 100;
  static int playerMana = 100;
  static int playerMoney = 0;

  static int playerPhysPow = 10;
  static int playerMagPow = 10;
  static int playerMagUse = 0;

  static int playerPhysDefense = 3;
  static int playerMagDefense = 1;

  final static int playerBaseCrit = 8;
  static int playerCritChance = 8; // crit chance == random between 1 and hundred if random < crit chance then crit
  static int playerRewardMultiplier = 1;
  
  static String[] playerSpells = {"Fireball", "Charm"};
  //static String[] playerItems = new String [0];
  static ArrayList<String> playerItems = new ArrayList<String>();
  static ArrayList<String> playerEquipment = new ArrayList<String>();
  static String[] playerCurrentEquipment = {" ", " "}; // [0] == armor [1] == weapon


  public static void movePlayer(char[][]map, int x, int y){
    int[] playerPos = MapManager.getEntityPosition(map, playerChar);

    // Moves player
    if(map[playerPos[1] + y] [playerPos[0] + x] == MapManager.empty){
      map[playerPos[1]][playerPos[0]] = MapManager.empty;
      map[playerPos[1] + y] [playerPos[0] + x] = playerChar;
      Main.gameState = "moving";
    }

    // Opens chest
    if(map[playerPos[1] + y] [playerPos[0] + x] == MapManager.chest){
      String itemName;
      if(Main.roomNumber == 0){
        itemName = "Lesser potion";
        UserView.userInfo = "You found a " + UserView.colorText(118, itemName);
        addItem(itemName);
      }
      else{
        int random = Main.randomNum(0, Items.ItemList.length - 1);
        itemName = Items.ItemList[random];
        UserView.userInfo = "You found a " + UserView.colorText(118, itemName);
        addItem(itemName);
      }
      map[playerPos[1]][playerPos[0]] = MapManager.empty;
      map[playerPos[1] + y] [playerPos[0] + x] = playerChar;
      Main.gameState = "moving";
    }
    
    // Enters next room
    else if (map[playerPos[1] + y] [playerPos[0] + x] == MapManager.exitDoor){
      if(Main.roomNumber < Main.maxRooms - 2){
        // If room alerady generated
        if (Main.dungeonRooms[Main.roomNumber + 1][0][0] == MapManager.roof){
          Main.roomNumber++;
          Main.currentRoom = Main.dungeonRooms[Main.roomNumber];
          Main.gameState = "moving";
        }
        // Else generate a new one
        else{
          Main.currentRoom = MapManager.generateRoom(Main.randomNum(Main.minWidth, Main.maxWidth - (Main.maxRooms - Main.roomNumber)), Main.randomNum(Main.minHeigth, Main.maxHeigth - (Main.maxRooms - Main.roomNumber)));
          Main.dungeonRooms[Main.roomNumber] = Main.currentRoom;
          Main.gameState = "moving";
        }
      }
      else{
        Main.currentRoom = MapManager.FinalRoom();
        Main.dungeonRooms[Main.roomNumber] = Main.currentRoom;
        Main.gameState = "moving";
      }
    }

    // Return to previous room
    else if (map[playerPos[1] + y] [playerPos[0] + x] == MapManager.entranceDoor){
      Main.roomNumber--;
      Main.currentRoom = Main.dungeonRooms[Main.roomNumber];
      Main.gameState = "moving";
    }
    
    // Enter shop
    else if(map[playerPos[1] + y] [playerPos[0] + x] == MapManager.shopDoor){
      UserView.userInfo = "You are entering the " + UserView.colorText(226, "shop") + ".";
      Main.gameState = "shopping";
    }

    // Starts combat with enemy and moves player over enemy for when combat ends
    else{
      for(int i = 0; i < Enemy.enemies.length; i++){
        for(char e : Enemy.enemies[i]){
          if(map[playerPos[1] + y] [playerPos[0] + x] == e){
            Combat.currentEnemy = Enemy.getEnemy(e);
            UserView.userInfo = "You are getting attacked by a " + UserView.colorText(196, Combat.currentEnemy);
            Main.gameState = "fighting";
            map[playerPos[1]][playerPos[0]] = MapManager.empty;
            map[playerPos[1] + y] [playerPos[0] + x] = playerChar;
          }
        }
      }
    }
  }

  public static void resetStats(){
    playerMaxHealth = 100;
    playerHealth = playerMaxHealth;
    playerMaxMana = 100;
    playerMana = 100;
    playerMoney = 0;
    playerPhysPow = 10;
    playerMagPow = 10;
    playerPhysDefense = 3;
    playerMagDefense = 1;
    playerCritChance = 8; 
    playerRewardMultiplier = 1;
    playerSpells = new String[2]; // defaut2
    playerSpells[0] = "Fireball";
    playerSpells[1] = "Charm";
    playerItems = new ArrayList<String>();
    playerEquipment = new ArrayList<String>();
    String[] playerCurrentEquipment = new String[2];
    playerCurrentEquipment[0] = " ";
    playerCurrentEquipment[1] = " ";
  }

  public static void addSpell(String spell){

    String[] oldSpells = playerSpells;
    playerSpells = new String[oldSpells.length + 1];
    
    for (int i = 0; i < playerSpells.length; i++){

      if (i == playerSpells.length - 1)
        playerSpells[i] = spell;

      else
        playerSpells[i] = oldSpells[i];
    }
  }

  public static void addItem(String item){
    playerItems.add(item);
  }

  public static void removeItem(String item){
    playerItems.remove(item);
  }
}