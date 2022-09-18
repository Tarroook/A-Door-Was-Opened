class Enemy{
  final static char[] t1enemies = {'ӧ', 'Ω'};
  final static char[] t2enemies = {'♞','䷅'};
  final static char[] t3enemies = {'⍩','⊛'};
  final static char boss = '♅';
  final static char[][] enemies = {t1enemies, t2enemies, t3enemies, {boss}};

  public static char createEnemy(){
    int randomEnemy;
    if (Main.roomNumber <= Main.maxRooms * 1 / 3){
      randomEnemy = Main.randomNum(0, enemies[0].length - 1);
      return enemies[0][randomEnemy];
    }
    else if (Main.roomNumber <= Main.maxRooms * 2 / 3){
      randomEnemy = Main.randomNum(0, enemies[1].length - 1);
      return enemies[1][randomEnemy];
    }
    else{
      randomEnemy = Main.randomNum(0, enemies[2].length - 1);
      return enemies[2][randomEnemy];
    }
  }

  public static String getEnemy(char currentEnemy){
    if(currentEnemy == enemies[0][0])
      return "Bat";
    else if(currentEnemy == enemies[0][1])
      return "Slime";
    else if(currentEnemy == enemies[1][0])
      return "Cursed Horse";
    else if (currentEnemy == enemies[1][1])
      return "Golem";
    else if (currentEnemy == enemies[2][0])
      return "Quacking Knight";
    else if (currentEnemy == enemies[2][1])
      return "Demon king's guard";
    else if (currentEnemy == enemies[3][0])
      return "Demon King";
    else
      return "error";
  }


}