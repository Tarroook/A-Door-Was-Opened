class StartMenu{
  static boolean firstLaunch = false;
  public static void menu(){
    if (!firstLaunch){
      Main.clearConsole();
      System.out.println("Please play the game in fullscreen. Press enter to continue.");
      Main.sc.nextLine();
      Cutscene.intro();
      firstLaunch = true;
    }
    Main.clearConsole();
    UserView.showTextFile("menus/title.txt");
    Main.sc.nextLine();

    do{
      Main.gameState = "startMenu";

      Main.clearConsole();

      UserView.showTextFile("menus/mainMenu.txt");

      try{
        Main.userInput = Main.sc.nextLine().charAt(0);
      }
      catch(StringIndexOutOfBoundsException e){}
      finally{}

      switch(Main.userInput){
        case '1':
          Main.dungeonRooms = new char[Main.maxRooms][Main.maxHeigth][Main.maxWidth];
          Main.roomNumber = 0;
          Main.currentRoom = MapManager.firstRoom();
          Main.dungeonRooms[Main.roomNumber] = Main.currentRoom;
          Player.resetStats();
          Main.gameState = "moving";
          break;
        case '2':
          break;
        case '3':
          Main.clearConsole();
          UserView.showTextFile("menus/credits.txt");
          Main.sc.nextLine();
          break;
        case '4':
          Main.clearConsole();
          System.exit(0);
        default:
          break;
      }
    }
    while(Main.gameState.equals("startMenu"));
  }
}