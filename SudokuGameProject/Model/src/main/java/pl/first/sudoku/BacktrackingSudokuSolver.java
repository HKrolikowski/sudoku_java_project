package pl.first.sudoku;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BacktrackingSudokuSolver implements SudokuSolver {
    private List<Integer> random = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

    public void solve(SudokuBoard board) {
        Collections.shuffle(random);
        int[][] beginNumber = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                boolean isGood = false;
                if (beginNumber[i][j] == 0) {
                    //pierwszy  raz w danym miejscu
                    Collections.shuffle(random);
                    beginNumber[i][j] = random.get(0);
                    board.set(i, j, beginNumber[i][j]);
                    do {
                        if (isCorrect(board, i, j)) {
                            isGood = true;
                            break;
                        }
                        board.set(i, j, board.get(i, j) % 9 + 1);
                    } while (board.get(i, j) != beginNumber[i][j]);
                } else {
                    //po cofnieciu bo dalsze nie pasowaly
                    board.set(i, j, board.get(i, j) % 9 + 1);
                    while (board.get(i, j) != beginNumber[i][j]) {
                        if (isCorrect(board, i, j)) {
                            isGood = true;
                            break;
                        }
                        board.set(i, j, board.get(i, j) % 9 + 1);
                    }
                }
                //cofniecie do poprzedniego miejsca
                if (!isGood) {
                    beginNumber[i][j] = 0;
                    board.set(i, j, 0);
                    if (j == 0) {
                        j = 7;
                        i -= 1;
                    } else {
                        j -= 2;
                    }
                }
            }
        }
    }

    private boolean isCorrect(SudokuBoard board, int row, int column) {
        int number = board.get(row, column);
        //sprawdzenie  wierszy
        for (int i = 0; i < column; i++) {
            if (number == board.get(row, i)) {
                return false;
            }
        }
        //sprawdzenie kolumn
        for (int i = 0; i < row; i++) {
            if (number == board.get(i, column)) {
                return false;
            }
        }
        //sprawdzanie kwadratow
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int startRow = i / 3;
                int startColumn = j / 3;
                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {
                        int currentRow = startRow * 3 + k;
                        int currentColumn = startColumn * 3 + l;
                        if (board.get(currentRow, currentColumn) == board.get(i, j)
                                && currentRow != i && currentColumn != j
                                && board.get(i, j) != 0) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}