class Spells{

  static int manaCost;
  static String spellName;
  final static String[] spellList = {"Fireball", "Charm", "Freeze", "Blast", "Sleep", "Heal", "Drain health", "Blood sacrifice", "Reflect"};
  static int charmTurn = 0;
  static int duckTurn = 0;
  static int freezeTurn = 0;
  static int sleepTurn = 0;
  static boolean reflect = false;

  public static void playerSpells(String spell){
    int spellDamageBonus = 0;
    switch (spell){

      case "Fireball":
        spellDamageBonus = 2;
        manaCost = 20 - Player.playerMagUse;
        if(checkMana(manaCost)){
          Main.clearConsole();
          UserView.showTextFile("combatSprites/spells/fireball.txt");

          int damages = Player.playerMagPow - Combat.enemyMagDefense + Main.randomNum(0, 5);
          if (damages < 1)
            damages = 1;
          damages += spellDamageBonus;
          System.out.println("You cast Fireball !");
          System.out.println("You did " + damages + " damage!");
          Main.sc.nextLine();
          
          Combat.enemyHealth -= damages;
          Player.playerMana -= manaCost;
          Combat.playerattacked = true;
        }
        break;


      case "Charm":
        manaCost = 20 - Player.playerMagUse;
        if(checkMana(manaCost)){
          Main.clearConsole();
          UserView.showTextFile("combatSprites/spells/charm.txt");
          System.out.println("You use your " + UserView.colorText(200, "charm") +"! The enemy's attack is halved !");
          Main.sc.nextLine();
          charmTurn = 6;
          Combat.enemyPhysPow /= 2;
          Combat.enemyMagPow /= 2;
          
          Player.playerMana -= manaCost;
          Combat.playerattacked = true;
        }
        break;

      case "Freeze":
        manaCost = 15 - Player.playerMagUse;
        spellDamageBonus = 0;
        if(checkMana(manaCost)){
          Main.clearConsole();
          UserView.showTextFile("combatSprites/spells/freeze.txt");

          int damages = Player.playerMagPow - Combat.enemyMagDefense + Main.randomNum(0, 5) - 5;
          if (damages < 1)
            damages = 1;
          damages += spellDamageBonus;
          System.out.println("You cast Freeze !");
          System.out.println("You did " + damages + " damage! The enemy's next attack will be weakened !");
          Main.sc.nextLine();
          
          Combat.enemyHealth -= damages;
          Player.playerMana -= manaCost;
          freezeTurn = 1;
          Combat.playerattacked = true;
        }
        break;
      
      case "Blast":
        manaCost = 50 - Player.playerMagUse;
        spellDamageBonus = 15;
        if(checkMana(manaCost)){
          Main.clearConsole();
          UserView.showTextFile("combatSprites/spells/blast.txt");

          int damages = Player.playerMagPow - Combat.enemyMagDefense + Main.randomNum(0, 5) - 5;
          if (damages < 1)
            damages = 1;
          damages += spellDamageBonus;
          System.out.println("You cast Blast !");
          System.out.println("You did " + damages + " damage!");
          Main.sc.nextLine();
          
          Combat.enemyHealth -= damages;
          Player.playerMana -= manaCost;
          Combat.playerattacked = true;
        }
        break;

      case "Sleep":
        manaCost = 35 - Player.playerMagUse;
        if(checkMana(manaCost)){
          Main.clearConsole();
          UserView.showTextFile("combatSprites/spells/sleep.txt");

          sleepTurn = Main.randomNum(3,5);
          System.out.println("You cast Sleep ! The enemy will be asleep for "+sleepTurn+" turns !");
          Main.sc.nextLine();
          Combat.enemyStatus = "asleep";
          Player.playerMana -= manaCost;
          Combat.playerattacked = true;
        }
        break;

      case "Heal":
        manaCost = 25 - Player.playerMagUse;
        if(checkMana(manaCost)){
          Main.clearConsole();
          UserView.showTextFile("combatSprites/spells/heal.txt");

          Player.playerHealth += 50;
          if (Player.playerHealth > Player.playerMaxHealth)
            Player.playerHealth = Player.playerMaxHealth;
        
          System.out.println("You use heal ! You restore 50 health !");
          Main.sc.nextLine();
          Player.playerMana -= manaCost;
          Combat.playerattacked = true;
        }
        break;

      case "Drain health":
        manaCost = 35 - Player.playerMagUse;
        spellDamageBonus = 5;
        if(checkMana(manaCost)){
          Main.clearConsole();
          UserView.showTextFile("combatSprites/spells/drainhealth.txt");

          int damages = Player.playerMagPow - Combat.enemyMagDefense + Main.randomNum(0, 5);
          if (damages < 1)
            damages = 1;
          damages += spellDamageBonus;
          int healed = damages/2;
          
          Player.playerHealth += healed;
          if (Player.playerHealth > Player.playerMaxHealth)
            Player.playerHealth = Player.playerMaxHealth;

          System.out.println("You cast Drain health !");
          System.out.println("You did " + damages + " damage and healed "+healed+" Health !");
          Main.sc.nextLine();
          
          Combat.enemyHealth -= damages;
          Player.playerMana -= manaCost;
          Combat.playerattacked = true;
        }

        break;

      case "Blood sacrifice":
        manaCost = 40 - Player.playerMagUse;
        spellDamageBonus = 20;
        if(checkMana(manaCost)){
          Main.clearConsole();
          if (Player.playerHealth <= 20){
            System.out.println("Are you crazy ?! You don't have enough health !");
            Main.sc.nextLine();
          }
          else{
            Player.playerHealth -= 20;
            int damages = Player.playerMagPow - Combat.enemyMagDefense + Main.randomNum(0, 5);
            if (damages < 1)
            damages = 1;
            damages += spellDamageBonus;
            
            UserView.showTextFile("combatSprites/spells/bloodsacrifice.txt");
            System.out.println("You cast Blood Sacrifice !");
            System.out.println("You did " + damages + " damage ! But you also lost 20 health...");
            Main.sc.nextLine();
          
            Combat.enemyHealth -= damages;
            Player.playerMana -= manaCost;
            Combat.playerattacked = true;
          }

        }
        break;

      case "Reflect":
        manaCost = 30 - Player.playerMagUse;
        if(checkMana(manaCost)){
          Main.clearConsole();
          UserView.showTextFile("combatSprites/spells/reflect.txt");
          System.out.println("You cast Reflect ! Your opponent will take the next hit they deal !");
          Main.sc.nextLine();

          Combat.playerattacked = true;
          Player.playerMana -= manaCost;
          reflect = true;
        }
      
      default:
        break;
    
    }
  }


  public static void enemySpell(String spell){
    
    int damages;
    
    switch (spell){

      case "Fireball":
        damages = Combat.enemyMagPow - Player.playerMagDefense + Main.randomNum(0,5);
        if (damages < 1)
            damages = 1;
        System.out.println(Combat.currentEnemy + " casts Fireball!");
        System.out.println("It did " + damages + " damages !");
          
        Player.playerHealth -= damages;
        break;

      case "Earth Smash":
        damages = (Combat.enemyPhysPow - Player.playerPhysDefense + Main.randomNum(0,5)) * 3;
        if (damages < 1)
            damages = 1;
        System.out.println(Combat.currentEnemy + " Uses Earth Smash!");
        System.out.println("It did " + damages + " damages !");
        Player.playerHealth -= damages;
        break;

      case "Ducking End":
        UserView.showTextFile("combatSprites/spells/quackingend/starttorun.txt");
        System.out.println("Run away!  " + Combat.currentEnemy + " has used " + UserView.colorText(160,"Ducking end") + "!");
        duckTurn = 11;
        break;

      case "Tornado":
        UserView.showTextFile("combatSprites/enemies/batattack.txt");
        System.out.println("The bat uses Tornado !");
        damages = Combat.enemyMagPow - Player.playerMagDefense + Main.randomNum(0,5);
        if (damages < 1)
            damages = 1;
        System.out.println("It did " + damages + " damages !");
        Player.playerHealth -= damages;
        break;

      case "Omega Blast":
        UserView.showTextFile("combatSprites/spells/omegablast.txt");
        System.out.println("The Demon King uses his Omega Blast !");
        damages = Combat.enemyMagPow - Player.playerMagDefense + Main.randomNum(0,5);
        if (damages < 1)
            damages = 1;
        damages += 15;
        System.out.println("It did " + damages + " damages !");
        Player.playerHealth -= damages;
        break;

    }
  }

  public static void spellTurns(){

    if (charmTurn != 0){
        charmTurn--;
        if (charmTurn == 0){
          Main.clearConsole();
          System.out.println("The enemy is no longer " + UserView.colorText(200, "charmed") + "!");
          Combat.enemyPhysPow *= 2;
          Combat.enemyMagPow *= 2;
          Main.sc.nextLine();
        }
      }

    if (freezeTurn != 0)
        freezeTurn--;

    if (sleepTurn != 0){
      Main.clearConsole();
      sleepTurn--;
      if(sleepTurn == 0){
        System.out.println("The enemy has woken up !");
        Combat.enemyStatus = "None";
        Main.sc.nextLine();
      }
    }
    
    if (duckTurn != 0 && Combat.playerattacked){
      Main.clearConsole();
      duckTurn--;
      if (duckTurn > 0){
        System.out.println("Quack. (You shall " + UserView.colorText(52, "perish") + " in " + duckTurn + " turns. Prepare.)");
        Main.sc.nextLine();
      }
      else{
        UserView.showTextFile("combatSprites/spells/quackingend/farewell.txt");
        System.out.println("Quack. (Your time is up, now " + UserView.colorText(52, "repent") + ".)");
        Main.sc.nextLine();
        Player.playerHealth = 0;
      }
    }
  }

  public static boolean checkMana(int cost){
    if (Player.playerMana < manaCost){
      System.out.println("Not enough " + UserView.colorText(33, "Mana") + "!");
      Main.sc.nextLine();
      return false;
    }
    else{
      return true;
    }
  }
}