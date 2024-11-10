public class DR2 extends DeductionRule {

    @Override
    public boolean apply(SudokuGrid grid) {
      boolean madeChange = false;
      System.out.println("------DR2------");
  
      // Appliquer la règle des singletons cachés sur chaque ligne, colonne et bloc
      for (int i = 0; i < 9; i++) {
        madeChange |= applyHiddenSingleInRow(grid, i);
        madeChange |= applyHiddenSingleInColumn(grid, i);
      }
  
      // Appliquer la règle pour chaque bloc de 3x3
      for (int rowBlock = 0; rowBlock < 9; rowBlock += 3) {
        for (int colBlock = 0; colBlock < 9; colBlock += 3) {
          madeChange |= applyHiddenSingleInBlock(grid, rowBlock, colBlock);
        }
      }
  
      if (madeChange) {
        System.out.println("La règle DR2 (singleton caché) a été appliquée.");
      } else {
        System.out.println("La règle DR2 (singleton caché) n'a pas pu être appliquée.");
      }
      return madeChange;
    }
  
    // Appliquer la règle des singletons cachés dans une ligne
    private boolean applyHiddenSingleInRow(SudokuGrid grid, int row) {
      boolean madeChange = false;
  
      for (int value = 1; value <= 9; value++) {
        int foundCol = -1;
  
        for (int col = 0; col < 9; col++) {
          if (grid.getGrid()[row][col] == 0 && grid.getPossibleValues(row, col).contains(value)) {
            if (foundCol == -1) {
              foundCol = col; // Première fois que la valeur est trouvée dans cette ligne
            } else {
              foundCol = -1; // La valeur apparaît dans plusieurs cellules, donc ce n'est pas un singleton caché
              break;
            }
          }
        }
  
        if (foundCol != -1) {
          System.out.println("case " + (row + 1) + " " + (foundCol + 1) + " changée en " + value + " - singleton caché DR2 (ligne)");
          grid.setValue(row, foundCol, value);
          grid.possibleValues[row][foundCol].clear();
          madeChange = true;
        }
      }
  
      return madeChange;
    }
  
    // Appliquer la règle des singletons cachés dans une colonne
    private boolean applyHiddenSingleInColumn(SudokuGrid grid, int col) {
      boolean madeChange = false;
  
      for (int value = 1; value <= 9; value++) {
        int foundRow = -1;
  
        for (int row = 0; row < 9; row++) {
          if (grid.getGrid()[row][col] == 0 && grid.getPossibleValues(row, col).contains(value)) {
            if (foundRow == -1) {
              foundRow = row; // Première fois que la valeur est trouvée dans cette colonne
            } else {
              foundRow = -1; // La valeur apparaît dans plusieurs cellules, donc ce n'est pas un singleton caché
              break;
            }
          }
        }
  
        if (foundRow != -1) {
          System.out.println("case " + (foundRow + 1) + " " + (col + 1) + " changée en " + value + " - singleton caché DR2 (colonne)");
          grid.setValue(foundRow, col, value);
          grid.possibleValues[foundRow][col].clear();
          madeChange = true;
        }
      }
  
      return madeChange;
    }
  
    // Appliquer la règle des singletons cachés dans un bloc de 3x3
    private boolean applyHiddenSingleInBlock(SudokuGrid grid, int startRow, int startCol) {
      boolean madeChange = false;
  
      for (int value = 1; value <= 9; value++) {
        int foundRow = -1;
        int foundCol = -1;
  
        for (int i = 0; i < 9; i++) {
          int row = startRow + i / 3;
          int col = startCol + i % 3;
  
          if (grid.getGrid()[row][col] == 0 && grid.getPossibleValues(row, col).contains(value)) {
            if (foundRow == -1 && foundCol == -1) {
              foundRow = row;
              foundCol = col; // Première fois que la valeur est trouvée dans ce bloc
            } else {
              foundRow = -1;
              foundCol = -1; // La valeur apparaît dans plusieurs cellules, donc ce n'est pas un singleton caché
              break;
            }
          }
        }
  
        if (foundRow != -1 && foundCol != -1) {
          System.out.println("case " + (foundRow + 1) + " " + (foundCol + 1) + " changée en " + value + "  - singleton caché DR2 (bloc)");
          grid.setValue(foundRow, foundCol, value);
          grid.possibleValues[foundRow][foundCol].clear();
          madeChange = true;
        }
      }
  
      return madeChange;
    }
  }