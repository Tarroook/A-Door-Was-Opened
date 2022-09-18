class GameManager{
  public static void start(){
    StartMenu.menu();
  }

  public static void moving(char Uinput){

    switch(Uinput){
      case 'z':
        Player.movePlayer(Main.currentRoom, 0, -1);
        break;
      case 's':
        Player.movePlayer(Main.currentRoom, 0, 1);
        break;
      case 'q':
        Player.movePlayer(Main.currentRoom, -1, 0);
        break;
      case 'd':
        Player.movePlayer(Main.currentRoom, 1, 0);
        break;
      case 'i':
        UserView.userInfo = "You open your inventory";
        Main.gameState = "inventory";
        break;
      default :
        break;
    }
    if (Main.gameState.equals("moving")){
      UserView.showMap(Main.currentRoom);
      UserView.movingUI();
    }
    System.out.println("\n"+ UserView.userInfo);
  }


  public static void fighting (char Uinput){
    System.out.println(Combat.currentEnemy);
    
    Combat.combatLoop();
  }

  public static void shopping (char Uinput){
    Shop.shopLoop();
  }

  public static void inventory (char Uinput){
    Items.inventory();
  }
}