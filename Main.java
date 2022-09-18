import java.util.Scanner;


class Main {

  final static Scanner sc = new Scanner(System.in);

  static String gameState;
  static char userInput;

  static int roomNumber;
  static int maxWidth = 25;
  static int maxHeigth = 25;
  static int minWidth = 12;
  static int minHeigth = 7;
  static int maxRooms = 13;

  static char[][] currentRoom;
  static char[][][] dungeonRooms;
  
  public static void main(String[] args) {

    GameManager.start();

    while(true){

      clearConsole();

      switch(gameState){
        case "moving":
          GameManager.moving(userInput);
          userInput = awaitInput();
          break;
        case "fighting":
          GameManager.fighting(userInput);
          break;
        case "shopping":
          GameManager.shopping(userInput);
          break;
        case "inventory":
          GameManager.inventory(userInput);
          break;
        default:
          System.out.println("Game state error");
          break;
      }
      UserView.userInfo = " ";
    }
  }



  public static void clearConsole(){
    System.out.println("\003[H\033[2J");
    System.out.flush();
  }


  public static int randomNum(int min, int max){
    if(min == max)
      return max;
    int range = max - min + 1;
    int rand = (int)(Math.random() * range) + min;
    return rand;
  }


  public static int char2NumChoice(char input, String[] tab){
      if (input >= '1' && input <= '9' && Character.getNumericValue(input) <= tab.length)
          return Character.getNumericValue(input);
      else
        return 0;
  }


  public static boolean YNChoice(){
    System.out.println();
    for(int i = 0; i < 3; i++)
      System.out.print("    ");
    System.out.print("y : Yes");
    for(int i = 0; i < 3; i++)
      System.out.print("    ");
    System.out.println("n : No");
    System.out.println();
    char input = awaitInput();
    return input == 'y';
  }


  public static char awaitInput(){
    char input = ' ';
    try{
      System.out.print("➜ ");
      input = Main.sc.nextLine().charAt(0);
    }
    catch(StringIndexOutOfBoundsException e){
      input = ' ';
    } 
    finally{
      return input;
    }
  }
}