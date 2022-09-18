class Equipment{

  final static String[] armorListR1  = {"Leather armor", "Rusty steel armor", "Sorcerer robe"};
  final static String[] weaponListR1 = {"Shortsword", "Small dagger", "Sorcerer wand", "Mage book"};

  final static String[] armorListR2  = {"Scout armor", "Knight armor", "Mage robe"};
  final static String[] weaponListR2 = {"Greatsword", "Greed knife", "Light spear", "Rare tome of magic", "Mage staff"};

  final static String[] armorListR3  = {"Enchanted light armor", "Demon king's guard armor", "Great mage's robe"};
  final static String[] weaponListR3 = {"Dark sword", "Vibrant spear", "Great mage's staff"};

  final static String[][] equipmentList = {armorListR1, armorListR2, armorListR3, weaponListR1, weaponListR2, weaponListR3};

  
  public static int getEquipType(String equipment){
    for(int i = 0; i < equipmentList.length; i++){
      for(String e : equipmentList[i]){
        if (e == equipment){
          if(i < 3)
            return 0;
          else
            return 1;
        }
      }
    }
    return -1;
  }


  public static void setEquipmentStats(){
    int critBonus = 0;
    int magBonus = 0;
    for(int e = 0; e < Player.playerCurrentEquipment.length; e++){
      if (e == 0){
        switch(Player.playerCurrentEquipment[e]){
          // Rarity 1
          case "Leather armor":
            Player.playerPhysDefense = 8;
            Player.playerMagDefense = 5;
            critBonus += 2;
            break;
          case "Rusty steel armor":
            Player.playerPhysDefense = 12;
            Player.playerMagDefense = 5;
            break;
          case "Sorcerer robe":
            Player.playerPhysDefense = 7;
            Player.playerMagDefense = 12;
            magBonus += 2;
            break;

          // Rarity 2
          case "Scout armor":
            Player.playerPhysDefense = 15;
            Player.playerMagDefense = 8;
            critBonus += 5;
            break;
          case "Knight armor":
            Player.playerPhysDefense = 22;
            Player.playerMagDefense = 8;
            break;
          case "Mage robe":
            Player.playerPhysDefense = 12;
            Player.playerMagDefense = 22;
            magBonus += 2;
            break;

          // Rarity 3
          case "Enchanted light armor":
            Player.playerPhysDefense = 26;
            Player.playerMagDefense = 14;
            critBonus += 12;
            break;
          case "Demon king's guard armor":
            Player.playerPhysDefense = 42;
            Player.playerMagDefense = 14;
            break;
          case "Great mage's robe":
            Player.playerPhysDefense = 22;
            Player.playerMagDefense = 42;
            magBonus += 4;
            break;
          case " ":
            Player.playerPhysDefense = 3;
            Player.playerMagDefense = 1;
            break;
        }
      }
      else{
        switch(Player.playerCurrentEquipment[e]){
          // Rarity 1
          case "Shortsword":
            Player.playerPhysPow = 17;
            break;
          case "Small dagger":
            Player.playerPhysPow = 14;
            critBonus += 4;
            break;
          case "Sorcerer wand":
            Player.playerMagPow = 17;
            magBonus += 2;
            break;
          case "Mage book":
            Player.playerMagPow = 14;
            magBonus += 5;
            break;

          // Rarity 2
          case "Greatsword":
            Player.playerPhysPow = 27;
            break;
          case "Greed knife":
            Player.playerPhysPow = 15;
            Player.playerRewardMultiplier = 2;
            critBonus += 4;
            break;
          case "Light spear":
            Player.playerPhysPow = 20;
            critBonus += 8;
            break;
          case "Rare tome of magic":
            Player.playerMagPow = 20;
            magBonus += 10;
            break;
          case "Mage staff":
            Player.playerMagPow = 27;
            magBonus += 4;
            break;

          // Rarity 3
          case "Dark sword":
            Player.playerPhysPow = 42;
            break;
          case "Vibrant spear":
            Player.playerPhysPow = 27;
            critBonus += 20;
            break;
          case "Great mage's staff":
            Player.playerMagPow = 42;
            magBonus += 15;
            break;
          case " ":
            Player.playerPhysPow = 10;
            Player.playerMagPow = 10;
            Player.playerRewardMultiplier = 1;
            break;
        }
      }
    }
    Player.playerCritChance = Player.playerBaseCrit + critBonus;
    Player.playerMagUse = magBonus;
  }
}