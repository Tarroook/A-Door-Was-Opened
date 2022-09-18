class Items{
  // Potions give health, concotion makes max health go up, mana crystal gives mana back, pure crystal makes max mana go up.
  final static String[] ItemList = {"Lesser potion", "Medium potion", "Higher potion", "Mana crystal", "Vitality concoction", "Pure crystal"};

  public static void itemsType(String item){

    switch(item){

      case "Lesser potion":
        Main.clearConsole();
        
        Player.playerHealth += 25;
        Player.removeItem("Lesser potion");
        
        System.out.println("You use a lesser potion ! It restored " + UserView.colorText(118, "25 health") + ".");
        Main.sc.nextLine();
        break;

      case "Medium potion":
        Main.clearConsole();
        
        Player.playerHealth += 50;
        Player.removeItem("Medium potion");
        
        System.out.println("You use a medium potion ! It restored " + UserView.colorText(118, "50 health") + ".");
        Main.sc.nextLine();
        break;
      
      case "Higher potion":
        Main.clearConsole();
        
        Player.playerHealth += 100;
        Player.removeItem("Higher potion");
        
        System.out.println("You use a higher potion ! It restored " + UserView.colorText(118, "100 health") + ".");
        Main.sc.nextLine();
        break;

      case "Mana crystal":
        Main.clearConsole();
        
        Player.playerMana += 50;
        Player.removeItem("Mana crystal");

        System.out.println("You use a mana crystal ! It restored " + UserView.colorText(33,"50 Mana") + "!");
        Main.sc.nextLine();
        break;

      case "Vitality concoction":
        Player.playerMaxHealth += 25;
        Player.playerHealth += 25;
        Player.removeItem("Vitality concoction");

        System.out.println("You use a Vitality concoction ! Your Max HP went up by 25 !");
        Main.sc.nextLine();
        break;

      case "Pure crystal":
        Player.playerMaxMana += 25;
        Player.playerMana += 25;
        Player.removeItem("Pure crystal");

        System.out.println("You use a pure crystal ! Your Max Mana went up by 25 !");
        Main.sc.nextLine();
        break;
        
    }

    if (Player.playerHealth > Player.playerMaxHealth)
          Player.playerHealth = Player.playerMaxHealth;
          
    if (Player.playerMana > Player.playerMaxMana)
          Player.playerMana = Player.playerMaxMana;
    
    if (Main.gameState == "combat")
      Combat.playerattacked = true;

  }
  

  public static void inventory(){
    Main.clearConsole();
    System.out.println(" 1. Spells \n\n 2. Items \n\n 3. Equipment \n\n 4. Leave");

    char categoryInput = Main.awaitInput();
          
    switch (categoryInput){

    case '1':
      Main.clearConsole();
      UserView.display1DTable(3, Player.playerSpells);
      Main.sc.nextLine();
      inventory();
      break;

    case '2': 
      if (Player.playerItems.size() > 0){
        Main.clearConsole();
        UserView.display1DList(3, Player.playerItems);
        char itemsInput = Main.awaitInput();
        if (itemsInput >= '1' && itemsInput <= '9' && Character.getNumericValue(itemsInput) <= Player.playerItems.size()){
          int Input = Character.getNumericValue(itemsInput);
          Items.itemsType(Player.playerItems.get(Input - 1));
        }
        else if(itemsInput == 'c')
          inventory();
      }
      else {
        System.out.println("You don't have any items !");
        Main.sc.nextLine();
        inventory();    
      }
      break;

    case '3':
      Main.clearConsole();
      if (Player.playerEquipment.size() > 0){
        UserView.displayPlayerStats();
        UserView.EquipmentUI();
        char equipmentInput = Main.awaitInput();
        if (equipmentInput >= '1' && equipmentInput <= '9' && Character.getNumericValue(equipmentInput) <= Player.playerEquipment.size()){
          int Input = Character.getNumericValue(equipmentInput);
          equipGear(Player.playerEquipment.get(Input-1));
          Equipment.setEquipmentStats();
        }
        else if(equipmentInput == 'c')
          inventory();
      }
      else {
        UserView.displayPlayerStats();
        System.out.println();
        System.out.println("You don't have any equipment !");
        Main.sc.nextLine();
        inventory();
      }
      break;

    case '4':
      Main.gameState = "moving";
    }
  }

  public static void equipGear(String equipment){
    int type = Equipment.getEquipType(equipment);
    switch(type){
      case 0:
        Player.playerCurrentEquipment[type] = equipment;
        break;
      case 1:
        Player.playerCurrentEquipment[type] = equipment;
        break;
      case -1:
        System.out.println("Error equiping");
        break;
    }
  }
}