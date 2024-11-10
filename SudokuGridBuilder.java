import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SudokuGridBuilder {
  private SudokuGrid grid;

  public SudokuGridBuilder() {
    grid = SudokuGrid.getInstance();
  }

  public SudokuGridBuilder setCellValue(int row, int col, int value) {
    grid.setValue(row, col, value);
    return this;
  }

  public SudokuGrid build() {
    return grid;
  }

  public int setPrintStrategy(SudokuGrid grid) {
    System.out.println("--------------------Choix de l'affichage-------------------\n");
    System.out.println("Veuillez choisir le mode d'affichage : \nMode simple : 1\nMode détaillé : 2");
    int choix = grid.getScanner().nextInt();
    if (choix != 1 && choix != 2) {
      System.out.println("Vous avez rentré un mauvais choix.\n");
      return 1;
    } else {
      if (choix == 1) {
        grid.ps = new SimplePrintStrategy();
        return 0;
      } else {
        grid.ps = new DetailedPrintStrategy();
        return 0;
      }
    }
  }

  // Charger une grille depuis un fichier
  public SudokuGridBuilder loadGridFromFile(String filename) {
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
      String line;
      int row = 0;
      while ((line = br.readLine()) != null && row < 9) {
        String[] values = line.split(",");

        for (int col = 0; col < 9; col++) {
          int value = 0; // Valeur par défaut pour une case vide

          if (col < values.length) {
            value = values[col].trim().isEmpty() ? 0 : Integer.parseInt(values[col].trim());
          }
          setCellValue(row, col, value);
          if (value != 0) {
            grid.possibleValues[row][col].clear();
          }
        }
        row++;
      }
    } catch (IOException e) {
      System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
    } catch (NumberFormatException e) {
      System.err.println("Erreur de format dans le fichier : " + e.getMessage());
    }

    return this;
  }
}