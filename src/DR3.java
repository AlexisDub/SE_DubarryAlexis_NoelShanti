import java.util.HashSet;
import java.util.Set;

public class DR3 extends DeductionRule {

  private Set < Pair > dejaTraite = new HashSet < > (); // Paires déjà traitées

  @Override
  public boolean apply(SudokuGrid grid) {
    boolean madeChange = false;
    System.out.println("------DR3------");

    // Appliquer la règle des paires nues sur chaque ligne, colonne
    for (int i = 0; i < 9; i++) {
      madeChange |= applyNakedPairsInRow(grid, i);
      madeChange |= applyNakedPairsInColumn(grid, i);
    }

    // Appliquer la règle pour chaque bloc de 3x3

    for (int rowBlock = 0; rowBlock < 9; rowBlock += 3) {
      for (int colBlock = 0; colBlock < 9; colBlock += 3) {
        madeChange |= applyNakedPairsInBlock(grid, rowBlock, colBlock);
      }
    }

    if (madeChange) {
      System.out.println("La règle DR3 (paires nues) a été appliquée.");
    } else {
      System.out.println("La règle DR3 (paires nues) n'a pas pu être appliquée.");
    }
    return madeChange;
  }

  // Appliquer la règle des paires nues dans une ligne
  private boolean applyNakedPairsInRow(SudokuGrid grid, int row) {
    boolean madeChange = false;

    // Rechercher toutes les paires possibles dans la ligne
    for (int col1 = 0; col1 < 8; col1++) {
      for (int col2 = col1 + 1; col2 < 9; col2++) {
        Pair pair = new Pair(row * 9 + col1, row * 9 + col2);
        if (!dejaTraite.contains(pair)) {
          // Vérifier si les deux cases ont exactement les mêmes valeurs possibles
          Set < Integer > possibleValues1 = grid.getPossibleValues(row, col1);
          Set < Integer > possibleValues2 = grid.getPossibleValues(row, col2);

          if (possibleValues1.size() == 2 && possibleValues1.equals(possibleValues2)) {
            // Vérifier s'il n'y a pas d'autres cases avec les mêmes valeurs possibles
            boolean isPairOnly = true;
            for (int col = 0; col < 9; col++) {
              if (col != col1 && col != col2 && grid.getPossibleValues(row, col).equals(possibleValues1)) {
                isPairOnly = false; // Trouvé plus de deux cases avec les mêmes valeurs possibles
                break;
              }
            }

            if (isPairOnly) {
              System.out.println("case " + (row + 1) + " " + (col1 + 1) + " et case " + (row + 1) + " " + (col2 + 1) + " ont les mêmes valeurs possibles qui sont : " + possibleValues1 + " - paires nues DR3 (ligne)");
              // Si c'est une paire nue

              //System.out.println("possval1: " + possibleValues1);
              //System.out.println("possval2: " + possibleValues2);

              madeChange = true;

              // Éliminer ces valeurs des autres cases de la ligne
              for (int col = 0; col < 9; col++) {
                if (col != col1 && col != col2) {
                  grid.possibleValues[row][col].removeAll(possibleValues1);
                }
              }
              dejaTraite.add(pair);
            }
          }
        }
      }
    }

    return madeChange;
  }

  // Appliquer la règle des paires nues dans une colonne
  private boolean applyNakedPairsInColumn(SudokuGrid grid, int col) {
    boolean madeChange = false;

    // Rechercher toutes les paires possibles dans la colonne
    for (int row1 = 0; row1 < 8; row1++) {
      for (int row2 = row1 + 1; row2 < 9; row2++) {
        Pair pair = new Pair(row1 * 9 + col, row2 * 9 + col);
        if (!dejaTraite.contains(pair)) {
          // Vérifier si les deux cases ont exactement les mêmes valeurs possibles
          Set < Integer > possibleValues1 = grid.getPossibleValues(row1, col);
          Set < Integer > possibleValues2 = grid.getPossibleValues(row2, col);

          if (possibleValues1.size() == 2 && possibleValues1.equals(possibleValues2)) {
            // Vérifier s'il n'y a pas d'autres cases avec les mêmes valeurs possibles
            boolean isPairOnly = true;
            for (int row = 0; row < 9; row++) {
              if (row != row1 && row != row2 && grid.getPossibleValues(row, col).equals(possibleValues1)) {
                isPairOnly = false; // Trouvé plus de deux cases avec les mêmes valeurs possibles
                break;
              }
            }

            if (isPairOnly) {
              System.out.println("case " + (row1 + 1) + " " + (col + 1) + " et case " + (row2 + 1) + " " + (col + 1) + " ont les mêmes valeurs possibles " + possibleValues1 + " - paires nues DR3 (colonne)");

              // System.out.println("possval1: " + possibleValues1);
              // System.out.println("possval2: " + possibleValues2);

              // Si c'est une paire nue
              madeChange = true;

              // Éliminer ces valeurs des autres cases de la colonne
              for (int row = 0; row < 9; row++) {
                if (row != row1 && row != row2) {
                  grid.possibleValues[row][col].removeAll(possibleValues1);
                }
              }
              dejaTraite.add(pair);
            }
          }
        }
      }
    }

    return madeChange;
  }

  // Appliquer la règle des paires nues dans un bloc 3x3
  private boolean applyNakedPairsInBlock(SudokuGrid grid, int startRow, int startCol) {
    boolean madeChange = false;

    // Rechercher toutes les paires possibles dans le bloc
    for (int i = 0; i < 8; i++) {
      for (int j = i + 1; j < 9; j++) {
        int row1 = startRow + i / 3;
        int col1 = startCol + i % 3;
        int row2 = startRow + j / 3;
        int col2 = startCol + j % 3;

        Pair pair = new Pair(row1 * 9 + col1, row2 * 9 + col2);
        if (!dejaTraite.contains(pair)) {

          Set < Integer > possibleValues1 = grid.getPossibleValues(row1, col1);
          Set < Integer > possibleValues2 = grid.getPossibleValues(row2, col2);

          if (possibleValues1.size() == 2 && possibleValues1.equals(possibleValues2)) {
            // Vérifier s'il n'y a pas d'autres cases avec les mêmes valeurs possibles
            boolean isPairOnly = true;
            int count = 0;
            for (int r = startRow; r < startRow + 3; r++) {
              for (int c = startCol; c < startCol + 3; c++) {
                Set < Integer > possibleValues = grid.getPossibleValues(r, c);
                if (possibleValues.equals(possibleValues1)) {
                  count++;
                  if (count > 1) {
                    isPairOnly = false; // Trouvé plus de deux cases avec les mêmes valeurs possibles
                    break;
                  }
                }
              }
              if (!isPairOnly) break;
            }

            if (isPairOnly) {
              System.out.println("case " + (row1 + 1) + " " + (col1 + 1) + " et case " + (row2 + 1) + " " + (col2 + 1) + " val possible verif " + possibleValues1 + " - paires nues DR3 (bloc) ");

              // System.out.println("possval1: " + possibleValues1);
              // System.out.println("possval2: " + possibleValues2);

              // Si c'est une paire nue
              madeChange = true;

              // Éliminer ces valeurs des autres cases du bloc
              for (int r = startRow; r < startRow + 3; r++) {
                for (int c = startCol; c < startCol + 3; c++) {
                  if ((r != row1 || c != col1) && (r != row2 || c != col2)) {
                    grid.possibleValues[r][c].removeAll(possibleValues1);
                  }
                }
              }
              dejaTraite.add(pair);
            }
          }
        }
      }
    }

    return madeChange;
  }

  // Paire de cases
  private static class Pair {
    int cell1;
    int cell2;

    Pair(int cell1, int cell2) {
      this.cell1 = cell1;
      this.cell2 = cell2;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Pair pair = (Pair) o;
      return (cell1 == pair.cell1 && cell2 == pair.cell2) || (cell1 == pair.cell2 && cell2 == pair.cell1);
    }

  }
}
