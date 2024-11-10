import java.util.Set;
public class DR1 extends DeductionRule {
  @Override
  public boolean apply(SudokuGrid grid) {
    boolean madeChange = false;
    System.out.println("------DR1------");
    // On parcourt la grille pour chaque case
    for (int row = 0; row < 9; row++) {
      for (int col = 0; col < 9; col++) {
        Set < Integer > possible = grid.getPossibleValues(row, col);
        if (possible.size() == 1) {
          int value = possible.iterator().next();
          grid.setValue(row, col, value);
          grid.possibleValues[row][col].clear();
          System.out.println("case " + (row + 1) + " " + (col + 1) + " changée en " + value + "  - singleton nu DR1");
          madeChange = true;
        }

      }
    }
    if (madeChange) {
      System.out.println("La règle DR 1 a été appliquée.");
    } else {
      System.out.println("La règle DR 1 n'a pas pu être appliquée.");
    }
    return madeChange;
  }
}