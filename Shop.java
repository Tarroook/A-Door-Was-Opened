class Shop{

  static boolean firstTime = false;
  
  static String[] shop1Items = {" ", " ", " ", " "};
  static String[] shop2Items = {" ", " ", " ", " "};
  static String[] shop3Items = {" ", " ", " ", " "};
  static String[] shop1Spells = {" ", " ", " ", " "};
  static String[] shop2Spells = {" ", " ", " ", " "};
  static String[] shop3Spells = {" ", " ", " ", " "};
  static String[] shop1Equipment = {" ", " ", " ", " "};
  static String[] shop2Equipment = {" ", " ", " ", " "};
  static String[] shop3Equipment = {" ", " ", " ", " "};
  static String[][] shop1 = {shop1Items, shop1Spells, shop1Equipment};
  static String[][] shop2 = {shop2Items, shop2Spells, shop2Equipment};
  static String[][] shop3 = {shop3Items, shop3Spells, shop3Equipment};


  public static void firstVisit(){
    UserView.playDialogue("shop/shopkeeper.txt", "shop/dialogue/firstDialogue.txt");
    firstTime = true;
  }


  public static void shopLoop(){

    String[] itemsSold = new String[4];
    String[] spellsSold = new String[4];
    String[] equipmentSold = new String[4];

    if (!firstTime)
      firstVisit();
    else
      randomLine("shop/shopkeeper.txt", "shop/dialogue/greetDialogue.txt");
    
    setShopStuff(itemsSold, spellsSold, equipmentSold);
    
    boolean exitShop = false;
    while(!exitShop){
      Main.clearConsole();
      UserView.showTextFile("shop/shopkeeper.txt");
      UserView.showTextFile("uis/shopUI.txt");
      char shopInput = Main.awaitInput();
      switch(shopInput){
        case '1':
          selectStuff2Sell(itemsSold);
          break;
        case '2':
          selectStuff2Sell(spellsSold);
          break;
        case '3':
          selectStuff2Sell(equipmentSold);
          break;
        case '4':
          randomLine("shop/shopkeeper.txt", "shop/dialogue/byeDialogue.txt");
          exitShop = true;
          break;
        default:
          break;
      }
    }
    Main.gameState = "moving";
  }
  

  public static void selectStuff2Sell(String[] typeSold){
    char choiceInput;
    int choice;
    Main.clearConsole();
    UserView.showTextFile("shop/shopkeeper.txt");
    System.out.println(UserView.colorText(220, "Gold : " + Player.playerMoney));
    UserView.display1DTable(2, typeSold);
    choiceInput = Main.awaitInput();
    choice = Main.char2NumChoice(choiceInput, typeSold);
    if (choice > 0 && choice <= typeSold.length)
      buyItem(typeSold[choice - 1]);
  }


  public static void buyItem(String item){
    int price = setPrice(item);
    int type = setType(item);
    System.out.println(UserView.colorText(220, "This " + item + " costs " + price + " gold, would you like to buy it ?"));
    if(Main.YNChoice()){
      if (Player.playerMoney >= price){
        Player.playerMoney -= price;
        switch(type){
          case 1:
            Player.addItem(item);
            break;
          case 2:
            Player.addSpell(item);
            break;
          case 3:
            Player.playerEquipment.add(item);
            break;
        }
        System.out.println(UserView.colorText(220, "Thank you for your patronage."));
        Main.sc.nextLine();
      }
      else{
        System.out.println(UserView.colorText(220, "You don't have enough money."));
        Main.sc.nextLine();
      }
    }
  }
  

  public static void randomLine(String frame, String dialogue){
    //Main.clearConsole();
    UserView.showTextFile(frame);
    System.out.println();
    System.out.println(UserView.colorText(220, UserView.dialogueDisplay(true, 0, dialogue)));
    Main.sc.nextLine();
  }


  public static void setShopStuff(String[] items, String[] spells, String[] equipment){
    if(Main.roomNumber == Main.maxRooms / 4 ){
      if(shop1[0][0] == " "){
        setShopRarity(items, spells, equipment, 1);
        shop1[0] = items;
        shop1[1] = spells;
        shop1[2] = equipment;
      }
      else{
        for(int i = 0; i < items.length; i++){
          items[i] = shop1[0][i];
        }
        for(int s = 0; s < spells.length; s++){
          spells[s] = shop1[1][s];
        }
        for(int e = 0; e < items.length; e++){
          equipment[e] = shop1[2][e];
        }
      }
    }
    else if(Main.roomNumber == Main.maxRooms / 2){
      if(shop2[0][0] == " "){
        setShopRarity(items, spells, equipment, 2);
        shop2[0] = items;
        shop2[1] = spells;
        shop2[2] = equipment;
      }
      else{
        for(int i = 0; i < items.length; i++){
          items[i] = shop2[0][i];
        }
        for(int s = 0; s < spells.length; s++){
          spells[s] = shop2[1][s];
        }
        for(int e = 0; e < items.length; e++){
          equipment[e] = shop2[2][e];
        }
      }
    }
    else if(Main.roomNumber == Main.maxRooms * 3 / 4){
      if(shop3[0][0] == " "){
        setShopRarity(items, spells, equipment, 3);
        shop3[0] = items;
        shop3[1] = spells;
        shop3[2] = equipment;
      }
      else{
        for(int i = 0; i < items.length; i++){
          items[i] = shop3[0][i];
        }
        for(int s = 0; s < spells.length; s++){
          spells[s] = shop3[1][s];
        }
        for(int e = 0; e < items.length; e++){
          equipment[e] = shop3[2][e];
        }
      }
    }
    else{
      System.out.println("Error generating shop");
    }
  }

  public static void setShopRarity(String[] items, String[] spells, String[] equipment, int rarity){
    int count;
    int random;
    // Items
    do{
      for(int i = 0; i < items.length; i++){
        random = Main.randomNum(0, Items.ItemList.length - 1);
        items[i] = Items.ItemList[random];
      }
      
      count = 0;
      for(String i : items){
        for (int y = 0; y < items.length; y++){
          if (i == items[y]){
            count++;
          }
        }
      }
    }while(count > 4);


    // Spells
    do{
      for(int i = 0; i < spells.length; i++){
        random = Main.randomNum(0, Spells.spellList.length - 1);
        spells[i] = Spells.spellList[random];
      }
      
      count = 0;
      for(String s : spells){
        for (int y = 0; y < spells.length; y++){
          if (s == spells[y]){
            count++;
            // Doesn't sell charm and fireball
            if(s == Player.playerSpells[0] || s == Player.playerSpells[1])
              count++;
          }
        }
      }
    }while(count > 4);


    // Equipment
    do{
      switch(rarity){
        case 1:
          for(int i = 0; i < equipment.length / 2; i++){
            random = Main.randomNum(0, Equipment.armorListR1.length - 1);
            equipment[i] = Equipment.armorListR1[random];
          }
          for(int i = equipment.length / 2; i < equipment.length; i++){
            random = Main.randomNum(0, Equipment.weaponListR1.length - 1);
            equipment[i] = Equipment.weaponListR1[random];
          }
          count = 0;
          for(String e : equipment){
            for (int y = 0; y < equipment.length; y++){
              if (e == equipment[y]){
                count++;
              }
            }
          }
          break;
        case 2:
          for(int i = 0; i < equipment.length / 2; i++){
            random = Main.randomNum(0, Equipment.armorListR2.length - 1);
            equipment[i] = Equipment.armorListR2[random];
          }
          for(int i = equipment.length / 2; i < equipment.length; i++){
            random = Main.randomNum(0, Equipment.weaponListR2.length - 1);
            equipment[i] = Equipment.weaponListR2[random];
          }
          count = 0;
          for(String e : equipment){
            for (int y = 0; y < equipment.length; y++){
              if (e == equipment[y]){
                count++;
              }
            }
          }
          break;
        case 3:
          for(int i = 0; i < equipment.length / 2; i++){
            random = Main.randomNum(0, Equipment.armorListR3.length - 1);
            equipment[i] = Equipment.armorListR3[random];
          }
          for(int i = equipment.length / 2; i < equipment.length; i++){
            random = Main.randomNum(0, Equipment.weaponListR3.length - 1);
            equipment[i] = Equipment.weaponListR3[random];
          }
          count = 0;
          for(String e : equipment){
            for (int y = 0; y < equipment.length; y++){
              if (e == equipment[y]){
                count++;
              }
            }
          }
          break;
        default:
          System.out.println("Rarity error");
          break;
      }
    }while(count > 4);
  }


  public static int setType(String item){
    for(String i : Items.ItemList){
      if(item == i)
        return 1;
    }
    for(String s : Spells.spellList){
      if(item == s)
        return 2;
    }
    for(int i = 0; i < Equipment.equipmentList.length; i++){
      for(int y = 0; y < Equipment.equipmentList[i].length; y++){
        if(item == Equipment.equipmentList[i][y])
          return 3;
      }
    }
    return 0;
  }


  public static int setPrice(String item){
    int price = 0;
    switch(item){
      // Armors
      case "Leather armor":
        price = 150;
        break;
      case "Rusty steel armor":
        price = 200;
        break;
      case "Sorcerer robe":
        price = 175;
        break;
      case "Scout armor":
        price = 300;
        break;
      case "Knight armor":
        price = 400;
        break;
      case "Mage robe":
        price = 350;
        break;
      case "Enchanted light armor":
        price = 600;
        break;
      case "Demon king's guard armor":
        price = 800;
        break;
      case "Great mage's robe":
        price = 700;
        break;
      // Weapons
      case "Shortsword":
        price = 100;
        break;
      case "Small dagger":
        price = 75;
        break;
      case "Sorcerer wand":
        price = 100;
        break;
      case "Mage book":
        price = 125;
        break;
      case "Greatsword":
        price = 200;
        break;
      case "Greed knife":
        price = 75;
        break;
      case "Light spear":
        price = 180;
        break;
      case "Rare tome of magic":
        price = 200;
        break;
      case "Mage staff":
        price = 150;
        break;
      case "Dark sword":
        price = 500;
        break;
      case "Vibrant spear":
        price = 400;
        break;
      case "Great mage's staff":
        price = 450;
        break;
      //Items
      case "Lesser potion":
        price = 100;
        break;
      case "Medium potion":
        price = 150;
        break;
      case "Higher potion":
        price = 200;
        break;
      case "Mana crystal":
        price = 50;
        break;
      case "Vitality concoction":
        price = 300;
        break;
      case "Pure crystal":
        price = 300;
        break;
      // Spells
      case "Freeze":
        price = 100;
        break;
      case "Blast":
        price = 200;
        break;
      case "Sleep":
        price = 200;
        break;
      case "Heal":
        price = 300;
        break;
      case "Drain health":
        price = 300;
        break;
      case "Blood sacrifice":
        price = 400;
        break;
      case "Reflect":
        price = 300;
        break;
    }
    return price;
  }
}