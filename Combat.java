class Combat{
  static boolean playerattacked;

  static String currentEnemy;
  static int enemyHealth;
  static int enemyMaxHealth;
  static int enemyPhysPow;
  static int enemyMagPow;
  static int enemyPhysDefense;
  static int enemyMagDefense;
  static String enemyStatus;
  final static int enemyCritChance = 1;
  static String[] enemySpells = new String[1];
  static int reward;
  static int turn;
  static String enemySprite;
  static String enemyAttackingSprite;
  static String enemyDeadSprite;
  static int randomTurn;

  public static void combatLoop(){

    switch(currentEnemy){
      case "Bat":
        enemySprite = "combatSprites/combatbat.txt";
        enemyAttackingSprite = "combatSprites/enemies/batattack.txt";
        enemyDeadSprite = "combatSprites/dead/batdead.txt";
        enemyMaxHealth = 35;
        enemyHealth = enemyMaxHealth;
        enemyPhysPow = 1;
        enemyPhysDefense = 1;
        enemyMagPow = 3;
        enemyMagDefense = 2;
        enemyStatus = "None";
        enemySpells[0] = "Tornado";
        reward = Main.randomNum(50,100);
        break;

      case "Slime":
        enemySprite = "combatSprites/combatslime.txt";
        enemyAttackingSprite = "combatSprites/enemies/slimeattack.txt";
        enemyDeadSprite = "combatSprites/dead/slimedead.txt";
        enemyMaxHealth = 40;
        enemyHealth = enemyMaxHealth;
        enemyPhysPow = 3;
        enemyPhysDefense = 2;
        enemyMagPow = 0;
        enemyMagDefense = 1;
        enemyStatus = "None";
        enemySpells[0] = " ";
        reward = Main.randomNum(50,150);
        break;

      case "Golem":
        enemySprite = "combatSprites/combatgolem.txt";
        enemyAttackingSprite = "combatSprites/enemies/golemattack.txt";
        enemyDeadSprite = "combatSprites/dead/golemdead.txt";
        enemyMaxHealth = 140;
        enemyHealth = enemyMaxHealth;
        enemyPhysPow = 13;
        enemyPhysDefense = 13;
        enemyMagPow = 2;
        enemyMagDefense = 11;
        enemyStatus = "None";
        enemySpells[0] = "Earth Smash";
        reward = Main.randomNum(200,350);
        randomTurn = Main.randomNum(5, 12);
        break;

      case "Cursed Horse":
        enemySprite = "combatSprites/combatcursedhorse.txt";
        enemyAttackingSprite = "combatSprites/enemies/horsefireball.txt";
        enemyDeadSprite = "combatSprites/dead/horsedead.txt";
        enemyMaxHealth = 125;
        enemyHealth = enemyMaxHealth;
        enemyPhysPow = 5;
        enemyPhysDefense = 7;
        enemyMagPow = 12;
        enemyMagDefense = 13;
        enemyStatus = "None";
        enemySpells[0] = "Fireball";
        reward = Main.randomNum(150,300);
        break;

      case "Quacking Knight":
        enemySprite = "combatSprites/combatduck.txt";
        enemyAttackingSprite = "combatSprites/enemies/duckattack.txt";
        enemyDeadSprite = "combatSprites/dead/duckdead";
        enemyMaxHealth = 200;
        enemyHealth = enemyMaxHealth;
        enemyPhysPow = 15;
        enemyPhysDefense = 0;
        enemyMagPow = 0;
        enemyMagDefense = 5;
        enemyStatus = "None";
        enemySpells[0] = "Ducking end";
        reward = Main.randomNum(700,900);
        break;

      case "Demon king's guard":
        enemySprite = "combatSprites/combatknight.txt";
        enemyAttackingSprite = "combatSprites/enemies/knightattack.txt";
        enemyDeadSprite = "combatSprites/dead/knightdead.txt";
        enemyMaxHealth = 225;
        enemyHealth = enemyMaxHealth;
        enemyPhysPow = 25;
        enemyPhysDefense = 22;
        enemyMagPow = 0;
        enemyMagDefense = 15;
        enemyStatus = "None";
        enemySpells[0] = " ";
        reward = Main.randomNum(800,1000);
        break;

      case "Demon King":
        enemySprite = "combatSprites/combatdarklord.txt";
        enemyAttackingSprite = "combatSprites/enemies/darklordattack.txt";
        enemyDeadSprite = "combatSprites/dead/darklorddead";
        enemyMaxHealth = 400;
        enemyHealth = enemyMaxHealth;
        enemyPhysPow = 26;
        enemyPhysDefense = 24;
        enemyMagPow = 30;
        enemyMagDefense = 25;
        enemyStatus = "None";
        enemySpells[0] = "Omega Blast";
        reward = Main.randomNum(500,1000);
        break;

      default:
        break;
    }

    turn = 0;

    while(enemyHealth >= 1 && Player.playerHealth >= 1){
      playerattacked = false;
      
      Main.clearConsole();
     
      UserView.combatUI(enemySprite, "uis/combatUI.txt");
      
      char combatInput = Main.awaitInput();

      switch (combatInput){

        case '1':
          playerAttack("physic");
          playerattacked = true;
          break;

        case '2':
          playerAttack("magic");
          break;

        case '3': 
          Main.clearConsole();
          if (Player.playerItems.size() > 0){
            UserView.showTextFile("combatSprites/protagchoose.txt");
            UserView.display1DList(3, Player.playerItems);
            char itemsInput = Main.awaitInput();
            if (itemsInput >= '1' && itemsInput <= '9' && Character.getNumericValue(itemsInput) <= Player.playerItems.size()){
              int Input = Character.getNumericValue(itemsInput);
              Items.itemsType(Player.playerItems.get(Input - 1));
            }
          }
          else {
            System.out.println("You don't have any items !");
            Main.sc.nextLine();
          }
          break;

        case '4':
          Main.clearConsole();
          System.out.println("You defend !");
          Player.playerMana += 10;

          if (Player.playerMana > Player.playerMaxMana)
            Player.playerMana = Player.playerMaxMana;

          Main.sc.nextLine();
          enemyPhysPow /= 2;
          enemyMagPow /= 2;
          enemyAttack();
          enemyPhysPow *= 2;
          enemyMagPow *= 2;
          break;
      
        default:
          break;
      }
      
      if (playerattacked){
        enemyAttack();
        Spells.spellTurns();
      }

      turn++;

    }// end of while

    Spells.charmTurn = 0;
    Spells.freezeTurn = 0;
    Spells.sleepTurn = 0;
    Spells.duckTurn = 0;
    Spells.reflect = false;
    
    if (Player.playerHealth < 1){
      Main.clearConsole();
      UserView.showTextFile("combatSprites/dead/protagdead.txt");
      System.out.println("You lost...");
      Main.sc.nextLine();
      GameManager.start();
    }

    if (enemyHealth < 1){
      if (currentEnemy == "Demon King"){
        Cutscene.ending();
        StartMenu.menu();
      }
      else{
        Main.clearConsole();
        UserView.showTextFile(enemyDeadSprite);
        System.out.println("You win !");
        Player.playerMoney += reward * Player.playerRewardMultiplier;
        System.out.println("You found " + UserView.ANSI_YELLOW + reward + " Gold" + UserView.ANSI_RESET + ".");
        Main.sc.nextLine();
        Main.gameState = "moving";
      }
    }
  }


  public static void playerAttack(String attackType){
    switch(attackType){
      case "physic" : // Different sprites depending on weapon equipped ? Should be possible since can't have more than 1 weapon
        Main.clearConsole();
        UserView.showTextFile("combatSprites/protagattack.txt");
        System.out.println("You attack !");
        
        int playerDamage = physAttack(Player.playerPhysPow, Player.playerCritChance, enemyPhysDefense);
        System.out.println(currentEnemy + " received " + playerDamage + " damage !");
        enemyHealth -= playerDamage;
        Main.sc.nextLine();
        break;
        
      case "magic" :
        Main.clearConsole();
        UserView.showTextFile("combatSprites/protagchoose.txt");
        System.out.println("Your Mana: "+Player.playerMana);
        UserView.display1DTable(3, Player.playerSpells);

        char magicInput = Main.awaitInput();

        int Input;
        if (magicInput >= '1' && magicInput <= '9' && Character.getNumericValue(magicInput) <= Player.playerSpells.length){
          Input = Character.getNumericValue(magicInput);
          Spells.playerSpells(Player.playerSpells[Input-1]);
        }

      default :
        break;
    }
  }


  public static void enemyAttack(){
    Main.clearConsole();
    int damages;
    
    if (enemyHealth > 0){

      switch (currentEnemy){

        case "Golem":
          if(enemyStatus == "None"){
            if (!Spells.reflect){
              if (turn == randomTurn){
                UserView.showTextFile(enemyAttackingSprite);
                Spells.enemySpell("Earth Smash");
              }

              else{
                UserView.showTextFile(enemyAttackingSprite);
                damages = physAttack(enemyPhysPow,enemyCritChance, Player.playerPhysDefense);
                System.out.println("Golem attacks !");
                System.out.println("It deals "+ damages +" damages !");
                Player.playerHealth -= damages;
              }
            }
            else{
              UserView.showTextFile(enemyAttackingSprite);
              damages = physAttack(enemyPhysPow,enemyCritChance, Player.playerPhysDefense);
              System.out.println("Golem attacks ! But the move has been reflected !");
              System.out.println("It deals "+ damages +" damages !");
              enemyHealth -= damages;
              Spells.reflect = false;
            }
          }
          else{
            UserView.showTextFile("combatSprites/asleep.txt");
            System.out.println("The enemy is sleeping !");
          }

          break;
        
        case "Cursed Horse":
          if(enemyStatus == "None"){
            if (!Spells.reflect){
              UserView.showTextFile(enemyAttackingSprite);
              Spells.enemySpell("Fireball");
            }
            else{
              UserView.showTextFile(enemyAttackingSprite);
              damages = physAttack(enemyMagPow,enemyCritChance, Player.playerMagDefense);
              System.out.println("Cursed Horse attacks ! But the move has been reflected !");
              System.out.println("It deals "+ damages +" damages !");
              enemyHealth -= damages;
              Spells.reflect = false;
            }
          }

          else{
            UserView.showTextFile("combatSprites/asleep.txt");
            System.out.println("The enemy is sleeping !");
          }

          break;

        case "Quacking Knight":
          if(enemyStatus == "None"){
            if (!Spells.reflect){
              if (turn == 0){
                Spells.enemySpell("Ducking End");
              }
              
              else{
                UserView.showTextFile(enemyAttackingSprite);
                damages = physAttack(enemyPhysPow,enemyCritChance, Player.playerPhysDefense);
                System.out.println("Quack.");
                System.out.println("It deals "+ damages +" damage !");
                Player.playerHealth -= damages;
              }
            }

            else{
              UserView.showTextFile(enemyAttackingSprite);
              damages = physAttack(enemyPhysPow,enemyCritChance, Player.playerPhysDefense);
              System.out.println("Quacking Knight attacks ! But the move has been reflected !");
              System.out.println("It deals "+ damages +" damages !");
              enemyHealth -= damages;
              Spells.reflect = false;
            }
          }

          else{
            UserView.showTextFile("combatSprites/asleep.txt");
            System.out.println("The enemy is sleeping !");
          }

          break;
        
        case "Slime":
          
          if(enemyStatus == "None"){
            if (!Spells.reflect){
              damages = physAttack(enemyPhysPow,enemyCritChance, Player.playerPhysDefense);
              UserView.showTextFile(enemyAttackingSprite);
              System.out.println("Slime attacks !");
              System.out.println("It deals "+ damages +" damages !");
              Player.playerHealth -= damages;
            }
            else{
              UserView.showTextFile(enemyAttackingSprite);
              damages = physAttack(enemyPhysPow,enemyCritChance, Player.playerPhysDefense);
              System.out.println("Slime attacks ! But the move has been reflected !");
              System.out.println("It deals "+ damages +" damages !");
              enemyHealth -= damages;
              Spells.reflect = false;
            }
          }

          else{
            UserView.showTextFile("combatSprites/asleep.txt");
            System.out.println("The enemy is sleeping !");
          }

          break;

        case "Bat":
          if(enemyStatus == "None"){
            if (!Spells.reflect){
              Spells.enemySpell("Tornado");
            }
          

            else{
              UserView.showTextFile(enemyAttackingSprite);
              damages = physAttack(enemyMagPow,enemyCritChance, Player.playerMagDefense);
              System.out.println("Bat attacks ! But the move has been reflected !");
              System.out.println("It deals "+ damages +" damages !");
              enemyHealth -= damages;
              Spells.reflect = false;
            }
          }

          else{
            UserView.showTextFile("combatSprites/asleep.txt");
            System.out.println("The enemy is sleeping !");
          }

          break;

        case "Demon king's guard":
          if(enemyStatus == "None"){
            if (!Spells.reflect){
              damages = physAttack(enemyPhysPow,enemyCritChance, Player.playerPhysDefense);
              UserView.showTextFile(enemyAttackingSprite);
              System.out.println("Demon king's guard attacks !");
              System.out.println("It deals "+ damages +" damages !");
              Player.playerHealth -= damages;
            }
            else{
              UserView.showTextFile(enemyAttackingSprite);
              damages = physAttack(enemyPhysPow,enemyCritChance, Player.playerPhysDefense);
              System.out.println("Demon king's guard attacks ! But the move has been reflected !");
              System.out.println("It deals "+ damages +" damages !");
              enemyHealth -= damages;
              Spells.reflect = false;
            }
          }

          else{
            UserView.showTextFile("combatSprites/asleep.txt");
            System.out.println("The enemy is sleeping !");
          }

          break;

        case "Demon King":
          if(enemyStatus == "None"){
            if(!Spells.reflect){
              if(enemyHealth > enemyHealth/2){
                damages = physAttack(enemyPhysPow,enemyCritChance, Player.playerPhysDefense);
                UserView.showTextFile(enemyAttackingSprite);
                System.out.println("Demon King attacks !");
                System.out.println("It deals "+ damages +" damages !");
                Player.playerHealth -= damages;
              }

              else{
                if(Main.randomNum(1,3) == 1){
                  Spells.enemySpell("Omega Blast");
                }

                else{
                  damages = physAttack(enemyPhysPow,enemyCritChance, Player.playerPhysDefense);
                  UserView.showTextFile(enemyAttackingSprite);
                  System.out.println("Demon King attacks !");
                  System.out.println("It deals "+ damages +" damages !");
                  Player.playerHealth -= damages;
                }
              }
            }

            else {
              UserView.showTextFile(enemyAttackingSprite);
              damages = physAttack(enemyMagPow,enemyCritChance, Player.playerMagDefense);
              System.out.println("Demon King attacks ! But the move has been reflected !");
              System.out.println("It deals "+ damages +" damages !");
              enemyHealth -= damages;
              Spells.reflect = false;
            }
          }

          else{
            UserView.showTextFile("combatSprites/asleep.txt");
            System.out.println("The enemy is sleeping !");
          }
          break;

        default:
          break;
      }
      Main.sc.nextLine();
    }
  }


  public static int physAttack(int attackerPow, int critChance, int receiverDef){
    int damage = (attackerPow - receiverDef);

    if (damage <= 0)
      damage = 1;
    
    int crit = Main.randomNum(0, 100);
    if (crit <= critChance){
      System.out.println(UserView.colorText(202, "Critical hit !"));
      damage *= 2;
    }
    return damage + Main.randomNum(0, 5);
  }
}