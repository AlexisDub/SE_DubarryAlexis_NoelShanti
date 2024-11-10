import java.util.HashSet;
import java.util.Set;
import java.util.Scanner;

public class SudokuGrid {
  private static SudokuGrid instance; // Instance unique du singleton
  private int[][] grid; // Grille de Sudoku
  public Set < Integer > [][] possibleValues; // Valeurs possibles
  public PrintStrategy ps;
  private Scanner scanner;

  @SuppressWarnings("unchecked")
  private SudokuGrid() {
    grid = new int[9][9];
    possibleValues = new Set[9][9];
    scanner = new Scanner(System.in);

    // Initialiser toutes les cases avec toutes les valeurs possibles
    for (int row = 0; row < 9; row++) {
      for (int col = 0; col < 9; col++) {
        possibleValues[row][col] = new HashSet < > (); // Initialiser avec un HashSet
        for (int value = 1; value <= 9; value++) {
          possibleValues[row][col].add(value); // Ajouter toutes les valeurs possibles
        }
      }
    }
  }

  // Méthode pour obtenir l'instance unique de SudokuGrid
  public static SudokuGrid getInstance() {
    if (instance == null) {
      instance = new SudokuGrid();
    }
    return instance;
  }

  // Méthode pour mettre à jour les valeurs possibles
  public void updatePossibleValues(int row, int col, int value) {
    // Supprimer la valeur ajoutée des possibles dans la même ligne, colonne et carré
    for (int i = 0; i < 9; i++) {
      // Supprimer de la même ligne
      possibleValues[row][i].remove(value);
      // Supprimer de la même colonne
      possibleValues[i][col].remove(value);
    }

    // Supprimer de la même case de 3x3
    int boxRowStart = (row / 3) * 3;
    int boxColStart = (col / 3) * 3;
    for (int i = boxRowStart; i < boxRowStart + 3; i++) {
      for (int j = boxColStart; j < boxColStart + 3; j++) {
        possibleValues[i][j].remove(value);
      }
    }
  }

  // Méthodes pour accéder à la grille et aux valeurs possibles
  public int[][] getGrid() {
    return grid;
  }

  public Set < Integer > getPossibleValues(int row, int col) {
    return possibleValues[row][col];
  }

  public void setValue(int row, int col, int value) {
    grid[row][col] = value;
    updatePossibleValues(row, col, value);
  }

  // Méthode pour afficher la grille (optionnelle, pour le débogage)
  public void printGrid() {
    for (int row = 0; row < 9; row++) {
      for (int col = 0; col < 9; col++) {
        System.out.print(grid[row][col] + " ");
      }
      System.out.println();
    }
  }

  public void printStrat() {
    this.ps.print(this);
  }

  // Méthode pour afficher les valeurs possibles
  public void printPossibleValues() {
    for (int row = 0; row < 9; row++) {
      for (int col = 0; col < 9; col++) {
        System.out.print("Case (" + (row + 1) + ", " + (col + 1) + ") : " + possibleValues[row][col] + " et VAL ACTUELLE : " + grid[row][col] + "\n");
      }
      System.out.println();
    }
  }

  public Scanner getScanner() {
    return this.scanner;
  }
}