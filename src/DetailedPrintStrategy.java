public class DetailedPrintStrategy implements PrintStrategy {
    @Override
    public void print(SudokuGrid grid) {
      System.out.println("--------------------Affichage de la grille-------------------\n\n");
      int[][] gridValues = grid.getGrid();
  
      // Afficher les coordonnées des colonnes
      System.out.print("    ");
      for (int col = 1; col <= 9; col++) {
        System.out.print(col + " ");
        if (col % 3 == 0) {
          System.out.print("  ");
        }
      }
      System.out.println();
  
      for (int row = 0; row < 9; row++) {
        if (row % 3 == 0) {
          System.out.println("  -------------------------");
        }
        // Afficher les coordonnées des lignes
        System.out.print((row + 1) + " | ");
        for (int col = 0; col < 9; col++) {
          System.out.print(gridValues[row][col] + " ");
          if (col % 3 == 2) {
            System.out.print("| ");
          }
        }
        System.out.println();
      }
      System.out.println("  -------------------------");
    }
  }
