public class SimplePrintStrategy implements PrintStrategy {
    @Override
    public void print(SudokuGrid grid) {
      System.out.println("--------------------Affichage de la grille-------------------\n\n");
      int[][] gridValues = grid.getGrid();
      for (int row = 0; row < 9; row++) {
        for (int col = 0; col < 9; col++) {
          System.out.print(gridValues[row][col] + " ");
        }
        System.out.println();
      }
    }
  }