class MapManager{
  
  final static char empty = '.';
  final static char entranceDoor = '⤆';
  final static char exitDoor = '⤇';
  final static char firstDoor = '#';
  final static char shopDoor = '$';
  final static char chest = '₥';
  final static char roof = '☲';
  final static char wall = '☵';


  public static char[][] generateRoom(int x, int y){
    Main.roomNumber++;
    char[][] grid = new char[x][y];
    do{
      int hauteur = grid.length;
      int largeur = grid[0].length;

      int entranceDoorPos = Main.randomNum(1, hauteur - 2);
      int exitDoorPos = Main.randomNum(1, hauteur - 2);
      
    
      for(int i = 0; i < hauteur; i++){
        for(int j = 0; j < largeur; j++){

          if(i == 0 || i == hauteur - 1)
            grid[i][j] = roof;

          else if (j == 0 && i == entranceDoorPos){
            if (Main.roomNumber == 1)
              grid[i][j] = firstDoor;
            else
              grid[i][j] = entranceDoor;
          }

          else if (j == 0)
            grid[i][j] = wall;

          else if (j == largeur - 1 && i == exitDoorPos)
            grid[i][j] = exitDoor;

          else if (j == largeur - 1)
            grid[i][j] = wall;
        
          else
            grid[i][j] = empty;
        }
      }

      if (Main.roomNumber == Main.maxRooms / 4 || Main.roomNumber == Main.maxRooms / 2 || Main.roomNumber == Main.maxRooms * 3 / 4){
        int shopDoorPos = Main.randomNum(1, largeur - 2); 
        grid[0][shopDoorPos] = shopDoor;
      }

      createEnt(grid, Player.playerChar, 1, entranceDoorPos);

      generateWalls(grid);
      //System.out.println("generated map");
    } while (!pathfinding(grid));
    generateEnemies(grid);

    return grid;
  }



  public static void generateEnemies(char[][] map){
    int hauteur = map.length;
    int largeur = map[0].length;
    int enemyNumber = Main.randomNum(Main.roomNumber / 2, Main.roomNumber);
    int count = 0;
    while(count <= enemyNumber){
      int enemyPosX = Main.randomNum(2, largeur - 2);
      int enemyPosY = Main.randomNum(1, hauteur - 2);
      map[enemyPosY][enemyPosX] = Enemy.createEnemy();
      count++;
    }
  }


  public static void generateWalls(char[][] map){

    int hauteur = map.length;

    for (int i = 1; i < map.length - 1; i++){
      for (int j = 1; j < map[0].length - 1; j++){
        if (Main.randomNum(0,100) < 15 + (Main.roomNumber * 2)){
          if (map[i][j+1] != exitDoor && map[i][j] != Player.playerChar){
            map[i][j] = wall;
          }
        }
      }
    }
    
    int[] playerPos = getEntityPosition(map, Player.playerChar);

    if (playerPos[1]-1 != 0)
      map[playerPos[1]-1][playerPos[0]] = empty;

    if (playerPos[1]+1 != hauteur-1)
      map[playerPos[1]+1][playerPos[0]] = empty;

    map[playerPos[1]][playerPos[0]+1] = empty;

  }

  public static char[][] firstRoom(){
    Main.roomNumber = 0;

    char[][] grid = new char[7][7];
    int hauteur = grid.length;
    int largeur = grid[0].length;

    for(int i = 0; i < hauteur; i++){
        for(int j = 0; j < largeur; j++){

          if(i == 0 || i == hauteur - 1)
            grid[i][j] = roof;

          else if (j == 0)
            grid[i][j] = wall;

          else if (j == largeur - 1 && i == hauteur / 2)
            grid[i][j] = exitDoor;
          else if (j == largeur - 1)
            grid[i][j] = wall;

          else if (j == largeur / 2 && i == hauteur / 2)
            grid[i][j] = chest;
          else
            grid[i][j] = empty;
        }
      }
    

    createEnt(grid, Player.playerChar, 1, hauteur / 2);
    
    return grid;
  }

  // Generates final boss room
  public static char[][] FinalRoom(){
    Main.roomNumber++;

    char[][] grid = new char[20][20];
    int hauteur = grid.length;
    int largeur = grid[0].length;

    int entranceDoorPos = 9;
    for(int i = 0; i < hauteur; i++){
      for(int j = 0; j < largeur; j++){

        if((i == 0 && j >= 8) || (i == hauteur - 1 && j >= 8) || (i == 7 && j <= 7) || (i == 11 && j <= 7))
          grid[i][j] = roof;

        else if (j == 0 && i == entranceDoorPos)
          grid[i][j] = entranceDoor;

        else if ((j == 0 && i < 12 && i > 6) || (j == 8 && i > 0 && i < 9) || (j == 8 && i > 9 && i < 19))
          grid[i][j] = wall;

        else if (j == largeur - 1)
          grid[i][j] = wall;

        else if (i == 9 && j == 16)
          grid[i][j] = Enemy.boss;
        
        else if ((i < 7 && j < 8) || i > 11 && j < 8)
          grid[i][j] = ' ';
        else
          grid[i][j] = empty;
      }
    }
    

    createEnt(grid, Player.playerChar, 1, entranceDoorPos);
    
    return grid;
  }

  // Creates an entity at a given x, y position on an array
  public static void createEnt(char[][]tab, char ent, int x, int y){
      tab[y][x] = ent;
  }

  // Returns a int {x, y} of the position of an entity
  public static int[] getEntityPosition(char[][] tab, char entity){
    int[] pos = new int[2];
    for(int y = 0; y < tab.length; y++){
      for(int x = 0; x < tab[0].length; x++){
        if(tab[y][x] == entity){
          pos[0] = x;
          pos[1] = y;
        }
      }
    }
    return pos;
  }

  public static boolean pathfinding(char[][] lab){

    boolean changed = false;
    int goodPath = 0;
    int target = 0;
    int[][] path = new int[lab.length][lab[0].length];

    for (int i = 0; i < lab.length; i++){
      for (int j = 0; j < lab[i].length; j++){
        // 1000 == walls
        if (lab[i][j] == roof || lab[i][j] == wall){
          path[i][j] = 1000;
        }
        // 1001 == doors
        else if (lab[i][j] == firstDoor || lab[i][j] == entranceDoor || lab[i][j] == shopDoor){
          target++;
          path[i][j] = 1001;
        }
        // 999 == empty
        else{
          path[i][j] = 999;
        }
        // 0 == right next to exit door == starting point
        if (j < lab[i].length-1){
          if (lab[i][j+1] == exitDoor)
            path[i][j] = 0;
        }
      }
    }

    do{
      
      changed = false;
      
      for (int i = 1; i < path.length-1; i++){
        for (int j = 1; j < path[i].length-1; j++){
          // If tile hasn't been changed yet
          if(path[i][j] == 999){
            // If the tile below the one we are checking is inferior to above/left/right then current tile = below + 1
            if (path[i+1][j] < path[i-1][j] && path[i+1][j] < path[i][j-1] && path[i+1][j] < path[i][j+1] && path[i+1][j] < 999){
              path[i][j] = path[i+1][j] + 1;
              changed = true;
            }
            // Same but for tile above
            else if (path[i-1][j] < path[i+1][j] && path[i-1][j] < path[i][j-1] && path[i-1][j] < path[i][j+1] && path[i-1][j] < 999){
              path[i][j] = path[i-1][j] + 1;
              changed = true;
            }
            // Same but for tile on the right
            else if (path[i][j+1] < path[i-1][j] && path[i][j+1] < path[i+1][j] && path[i][j+1] < path[i][j-1] && path[i][j+1] < 999){
              path[i][j] = path[i][j+1] + 1;
              changed = true;
            }
            // Same but for tile on the left
            else if (path[i][j-1] < path[i-1][j] && path[i][j-1] < path[i+1][j] && path[i][j-1] < path[i][j+1] && path[i][j-1] < 999){
              path[i][j] = path[i][j-1] + 1;
              changed = true;
            }
          }
        }
      }
    } while (changed);

    for (int i = 0; i < path.length; i++){
      if (path[i][0] == 1001){
        if (path[i][1] > 0 && path[i][1] < 999){
          goodPath++;
        }
      }
    }
    for(int j = 0; j < path[0].length; j++){
      if(path[0][j] == 1001){
        if (path[1][j] > 0 && path[1][j] < 999){
          goodPath++;
        }
      }
    }
    
    /*
    // Debug
    for (int i = 0; i < path.length; i++){
      for (int j = 0; j < path[i].length; j++){
        if(path[i][j] <= 999 && path[i][j] >= 100){
          System.out.print(path[i][j] + "     ");
        }
        else if (path[i][j] <= 99 && path[i][j] >= 10){
          System.out.print(path[i][j] + "      ");
        }
        else if (path[i][j] <= 9){
          System.out.print(path[i][j] + "       ");
        }
        else{
          System.out.print(path[i][j] + "    ");
        }
      }
      System.out.println();
    }
    System.out.println();
    */
    
    return goodPath == target;
  }
}
