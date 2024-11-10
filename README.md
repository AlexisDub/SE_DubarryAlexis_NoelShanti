# SUDOKU SOLVER
Projet de Solver de Sudoku par DUBARRY Alexis et NOEL Shanti

-------------------------------------
Evaluation de la difficulté d'une grille : 
En lançant le Main, on peut choisir avec quelles règles maximum résoudre le sudoku (DR1, DR1 et DR2, DR1 et DR2 et DR3) qui ont des difficultés de résolvage croissantes.

Une grille qui arrive à être résolu avec DR1 seul (sans intervention de l'utilisateur) est une grille Facile.

Une grille qui arrive à être résolu avec <=DR2 (sans intervention de l'utilisateur) est une grille Moyenne.

Une grille qui arrive à être résolu avec <= DR3 (sans intervention de l'utilisateur) est une grille Difficile.

--------------------------------------
Utilisation :
1) javac *.java
2) java Main grillesudoku.txt (ex : java Main grilleDR1.txt )
--------------------------------------
GRILLES EXEMPLES : 
des grilles d'exemples de sudoku sont fournies.

grilleDR1.txt qui est une grille de Sudoku Facile ( qui peut être résolue avec DR1 seulement )

grilleDR2.txt qui est une grille de Sudoku Moyenne ( qui peut être résolue avec les DR<=2 )

grilleDR3.txt qui est une grille de Sudoku Difficile ( qui peut être résolue avec les DR<=3 )
