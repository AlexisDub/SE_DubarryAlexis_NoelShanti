// Authors: DUBARRY Alexis & NOEL Shanti

public class Main {
    public static void main(String[] args) {
  
      if (args.length < 1) {
        System.out.println("Usage: java Main <filename>");
        return;
      }
  
      String filename = args[0];
  
      System.out.println("\nInformation : les coordonnés donnés sont a chaque fois de la forme (ligne, colonne) avec ligne et colonne compris entre 1 et 9.\n");
      // Créer un constructeur de grille
      SudokuGridBuilder gridBuilder = new SudokuGridBuilder();
  
      // Charger la grille depuis le fichier en argument
      gridBuilder.loadGridFromFile(filename);
      SudokuGrid grid = gridBuilder.build();
  
      // Choisir le mode d'affichage
      int x = gridBuilder.setPrintStrategy(grid);
      while (x == 1) {
        x = gridBuilder.setPrintStrategy(grid);
      }
  
      // Créer un solveur et initialiser avec la grille
      SudokuSolver solver = new SudokuSolver();
      solver.initializeGrid(gridBuilder);
  
      // Afficher la grille sans strategy
      //solver.getSudokuGrid().printGrid(); 
      // Afficher la grille avec strategy
      solver.getSudokuGrid().printStrat();
  
      // Afficher les valeurs possibles
      //solver.getSudokuGrid().printPossibleValues();
  
      // Résoudre la grille
      solver.solve();
  
    }
  }