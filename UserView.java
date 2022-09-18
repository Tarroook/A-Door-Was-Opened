import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


class UserView{

  public static final String ANSI_PURPLE = /*"\u001B[35m"*/ "\u001b[38;5;129m";
  public static final String ANSI_BROWN = "\u001b[38;5;172m";
  public static final String ANSI_BEIGE = "\u001b[38;5;180m";
  public static final String ANSI_LBLUE = "\u001b[38;5;33m";
  public static final String ANSI_RED = "\u001b[38;5;196m";
  public static final String ANSI_PINK = "\u001b[38;5;200m";
  public static final String ANSI_YELLOW = "\u001b[38;5;220m";
  public static final String ANSI_RESET = "\u001B[0m";
  static String userInfo = " ";


  public static void showMap(char[][]tab){
    int hauteur = tab.length;
    int largeur = tab[0].length;
    int[] playerPos = MapManager.getEntityPosition(tab, Player.playerChar);

    System.out.println("Room : " + Main.roomNumber);
    for(int y = 0; y < hauteur; y++){
      for(int x = 0; x < largeur; x++){
        if (playerPos[0] == x && playerPos[1] == y)
          System.out.print(ANSI_PURPLE + tab[y][x] + ANSI_RESET + "   ");
        else if ((tab[y][x] == MapManager.roof && (y == 0 || y == hauteur - 1)) || (tab[y][x] == MapManager.wall && (x == 0 || x == largeur - 1)))
          System.out.print(ANSI_BROWN + tab[y][x] + ANSI_RESET + "   ");
        else if (tab[y][x] == MapManager.empty)
          System.out.print(ANSI_BEIGE + tab[y][x] + ANSI_RESET + "   ");
        else{
          boolean printed = false;
          for(int i = 0; i < Enemy.enemies.length; i++){
            for(char e : Enemy.enemies[i]){
              if (e == tab[y][x]){
                System.out.print(ANSI_RED + tab[y][x] + ANSI_RESET + "   ");
                printed = true;
              }
            }
          }
          if (!printed)
            System.out.print(tab[y][x] + "   ");
        }
      }
      System.out.println();
    }
    System.out.println();
  }


  public static void movingUI(){
    System.out.println("\n" + ANSI_RED + "HP : " + ANSI_RESET + Player.playerHealth + "/" + Player.playerMaxHealth + "\t \t \t" 
                            + ANSI_LBLUE + "Mana : " + ANSI_RESET + Player.playerMana + "/" + Player.playerMaxMana + "\t \t \t" 
                            + ANSI_YELLOW + "Gold : " + ANSI_RESET + Player.playerMoney);
    showTextFile("uis/userUI.txt");
  }


  public static void combatUI(String textFile1, String textFile2){
    showTextFile(textFile1);
    System.out.println("\n" + ANSI_RED + "Your HP : " + ANSI_RESET + Player.playerHealth + " / " + Player.playerMaxHealth + "\t \t \t \t \t \t \t \t" + Combat.currentEnemy +"'s HP : "+ Combat.enemyHealth + "/" + Combat.enemyMaxHealth + "\n");
    System.out.println(ANSI_LBLUE + "Your Mana : " + ANSI_RESET + Player.playerMana + "/" + Player.playerMaxMana);
    showTextFile(textFile2);
  }


  public static void display1DTable(int width, String[] tab){
    for (int i = 0; i < 144; i++){
      System.out.print("@");
    }

    for (int j = 0; j < tab.length; j++){
      if(j % width == 0)
        System.out.println("\n");

      System.out.print("\t" + (j+1) + ": "+ tab[j] + "\t");
    }
    
    System.out.println("\n\n\tc: Cancel \n");
    
    for (int k = 0; k < 144; k++){
      System.out.print("@");
    }
    System.out.println();
  }


  public static void display1DList(int width, ArrayList<String> list){
    for (int i = 0; i < 144; i++){
      System.out.print("@");
    }
    for (int j = 0; j < list.size(); j++){
      if(j % width == 0)
        System.out.println("\n");
      System.out.print("\t" + (j+1) + ": "+ list.get(j) + "\t");
    }
    System.out.println("\n\n\tc: Cancel \n");
    for (int k = 0; k < 144; k++){
      System.out.print("@");
    }
    System.out.println();
  }


  public static void EquipmentUI(){

    for (int i = 0; i < 144; i++){
      System.out.print("@");
    }

    for (int j = 0; j < Player.playerEquipment.size(); j++){
      boolean printed = false;
      if(j % 4 == 0)
        System.out.println("\n");
      for(String e : Player.playerCurrentEquipment){
        if(e == Player.playerEquipment.get(j)){
          System.out.print("\t" + (j+1) + ": "+ Player.playerEquipment.get(j) + " (" + colorText(118, "E") +")" +"\t");
          printed = true;
        }
      }
      if(!printed)
        System.out.print("\t" + (j+1) + ": "+ Player.playerEquipment.get(j) + "\t");
    }
    
    System.out.println("\n\n\tc: Cancel \n");
    
    for (int k = 0; k < 144; k++){
      System.out.print("@");
    }
    System.out.println();
  }

  
  public static void displayPlayerStats(){
    int spaces = 50;
    String nextPrint;
    nextPrint = colorText(196, "Health : ") + Player.playerHealth + " / " + Player.playerMaxHealth;
    System.out.print(nextPrint);
    for(int i = nextPrint.length(); i <= spaces; i++)
      System.out.print(" ");
    System.out.print(colorText(21, "Mana : ") + Player.playerMana + " / " + Player.playerMaxMana);
    System.out.println();

    nextPrint = "Strength : " + Player.playerPhysPow;
    System.out.print(nextPrint);
    for(int i = nextPrint.length(); i <= spaces; i++)
      System.out.print(" ");
    System.out.print("Magic power : " + Player.playerMagPow);
    System.out.println();

    nextPrint = "Dexterity : " + Player.playerCritChance;
    System.out.print(nextPrint);
    for(int i = nextPrint.length(); i <= spaces; i++)
      System.out.print(" ");
    System.out.print("Mana control : " + Player.playerMagUse);
    System.out.println();

    nextPrint = "Physical defense : " + Player.playerPhysDefense;
    System.out.print(nextPrint);
    for(int i = nextPrint.length(); i <= spaces; i++)
      System.out.print(" ");
    System.out.print("Magic defense : " + Player.playerMagDefense);
    System.out.println();

    System.out.println("Luck : " + Player.playerRewardMultiplier);
  }


  public static String colorText(int value, String text){
    return ("\u001b[38;5;" + value + "m" + text + ANSI_RESET);
  }


  public static String dialogueDisplay(boolean isRandom, int lineNumber, String dialogueFile){
    int totalLines = 0;
    try (BufferedReader br = new BufferedReader(new FileReader(dialogueFile))) {
      String line;
      while ((line = br.readLine()) != null) {
        totalLines++;
      }
    }catch(IOException e){}

    String chosedLine = "none";
    if (isRandom){
      int random = Main.randomNum(0, totalLines - 1);
      try (BufferedReader br = new BufferedReader(new FileReader(dialogueFile))){
        for(int i = 0; i < random; ++i)
          br.readLine();
        chosedLine = br.readLine();
      }catch(IOException e){}
    }
    else{
      try (BufferedReader br = new BufferedReader(new FileReader(dialogueFile))){
        for(int i = 0; i < lineNumber; ++i)
          br.readLine();
        chosedLine = br.readLine();
      }catch(IOException e){}
    }
    return chosedLine;
  }


  public static int getLineNumber(String file){
    int totalLines = 0;
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = br.readLine()) != null) {
        totalLines++;
      }
    }catch(IOException e){}
    return totalLines;
  }


  public static void playDialogue(String frame, String text){
    int totalLines = UserView.getLineNumber(text);
    for (int i = 0; i < totalLines; i++){
      Main.clearConsole();
      UserView.showTextFile(frame);
      System.out.println();
      System.out.println(UserView.colorText(220, UserView.dialogueDisplay(false, i, text)));
      Main.sc.nextLine();
    }
  }


  public static void showTextFile(String file){
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = br.readLine()) != null) {
        System.out.println(line);
      }
    }catch(IOException e){}
  }


  public static void centerPrintln(String text){
    int screenWidth = 180;
    int size = text.length();
    int spaces = (screenWidth / 2) - size;
    for(int i = 0; i <= spaces; i++)
      System.out.print(" ");
    System.out.println(text);
  }
}