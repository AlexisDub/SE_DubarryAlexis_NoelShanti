import java.util.ArrayList;
import java.util.List;

public class SudokuSolver {
  private SudokuGrid sudokuGrid;
  private List < DeductionRule > rules;

  public SudokuSolver() {}

  // Initialiser la grille de Sudoku
  public void initializeGrid(SudokuGridBuilder builder) {
    this.rules = new ArrayList < > ();
    this.sudokuGrid = builder.build();
    int x = choixRules();
    while (x == 1) {
      x = choixRules();
    }
  }

  // Méthode de résolution
  public void solve() {
    boolean changed;
    boolean filledOk = true;

    while (!isSolved() && filledOk) {
      do {
        changed = applyRules();
      } while (changed && !isSolved());

      if (!isSolved()) {
        System.out.println("Aucune règle appliquée, vous devez entrer un chiffre dans une case vide.");
        fillEmptyCell();
        // on verifie la validité de la grille avec la nouvelle case
        if (grilleValide() == 0) {
          System.out.println("La grille n'est pas valide.");
          filledOk = false;
        } else {
          filledOk = true;
        }
      } else {
        filledOk = false;
      }
    }

    if (!isSolved()) {
      System.out.println("La grille n'a pas pu être résolue avec les règles de déduction actuelles.");
    } else {
      System.out.println("Grille résolue avec succès !");
      sudokuGrid.printStrat();
    }

    sudokuGrid.getScanner().close();
  }

  // Appliquer les règles successivement
  private boolean applyRules() {
    boolean changed = false;
    System.out.println("------Règles------\n");
    for (DeductionRule rule: rules) {
      changed |= rule.apply(sudokuGrid);
    }
    sudokuGrid.printStrat();
    // Pour afficher les valeurs possibles de chaque cases :
    //sudokuGrid.printPossibleValues();

    return changed;
  }

  // Vérifier si la grille est résolue
  public boolean isSolved() {
    for (int row = 0; row < 9; row++) {
      for (int col = 0; col < 9; col++) {
        if (sudokuGrid.getGrid()[row][col] == 0) { // Utilisation de 0 pour une case vide
          return false;
        }
      }
    }
    return true;
  }

  public SudokuGrid getSudokuGrid() {
    return sudokuGrid;
  }

  private void fillEmptyCell() {
    int row, col, value;

    try {
      System.out.println("--------------------Choix de la case a remplir-------------------\n");
      System.out.println("Veuillez entrer les coordonnées de la case vide (ligne, colonne) et une valeur (1-9):");
      System.out.print("Ligne (1-9) : ");
      row = sudokuGrid.getScanner().nextInt() - 1;
      System.out.print("Colonne (1-9) : ");
      col = sudokuGrid.getScanner().nextInt() - 1;
      System.out.print("Valeur (1-9) : ");
      value = sudokuGrid.getScanner().nextInt();

      // Vérification que la case est vide et que la valeur est valide
      if (row <= 0 || row > 9 || col <= 0 || col > 9 || value < 1 || value > 9 || sudokuGrid.getGrid()[row][col] == 0) {
        sudokuGrid.setValue(row, col, value);
        sudokuGrid.possibleValues[row][col].clear();
        System.out.println("Case remplie avec succès.");
      } else {

        System.out.println("Entrée invalide. Assurez-vous que la case est vide et que la valeur est entre 1 et 9.");
        System.out.println("valeur actuelle de la case " + row + " " + col + " : " + sudokuGrid.getGrid()[row][col]);
      }

    } catch (Exception e) {
      System.out.println("Entrée invalide. Veuillez entrer un nombre entier.");
      sudokuGrid.getScanner().next(); // Consomme l'entrée invalide pour éviter une boucle infinie
    }

  }

  public void addRule(DeductionRule rule) {
    rules.add(rule);
  }

  public int choixRules() {
    System.out.println("--------------------Choix des règles de déduction-------------------\n");
    System.out.println("Veuillez choisir juqu'à quelles règles de déduction appliquer  : \nDR1 (seul) : 1\nDR2 (et DR1) : 2\nDR3 (et DR1,DR2): 3");
    int choixDR = sudokuGrid.getScanner().nextInt();
    if (choixDR != 1 && choixDR != 2 && choixDR != 3) {
      System.out.println("Vous avez rentré un mauvais choix.\n");
      return 1;
    } else {
      if (choixDR == 1) {
        addRule(new DR1());
      } else {
        if (choixDR == 2) {
          addRule(new DR1());
          addRule(new DR2());
        } else {
          addRule(new DR1());
          addRule(new DR2());
          addRule(new DR3());
        }
      }
      return 0;
    }
  }

  public int grilleValide() {
    // Vérifier les lignes
    for (int i = 0; i < 9; i++) {
      boolean[] seen = new boolean[9];
      for (int j = 0; j < 9; j++) {
        int num = sudokuGrid.getGrid()[i][j];
        if (num != 0) {
          if (seen[num - 1]) {
            return 0; // Doublon trouvé
          }
          seen[num - 1] = true;
        }
      }
    }

    // Vérifier les colonnes
    for (int j = 0; j < 9; j++) {
      boolean[] seen = new boolean[9];
      for (int i = 0; i < 9; i++) {
        int num = sudokuGrid.getGrid()[i][j];
        if (num != 0) {
          if (seen[num - 1]) {
            return 0; // Doublon trouvé
          }
          seen[num - 1] = true;
        }
      }
    }

    // Vérifier les sous-grilles 3x3
    for (int row = 0; row < 9; row += 3) {
      for (int col = 0; col < 9; col += 3) {
        boolean[] seen = new boolean[9];
        for (int i = 0; i < 3; i++) {
          for (int j = 0; j < 3; j++) {
            int num = sudokuGrid.getGrid()[row + i][col + j];
            if (num != 0) {
              if (seen[num - 1]) {
                return 0; // Doublon trouvé
              }
              seen[num - 1] = true;
            }
          }
        }
      }
    }

    return 1; // Grille valide
  }

}