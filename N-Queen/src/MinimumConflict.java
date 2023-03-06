import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class MinimumConflict {

    public static void main(String[] args)
    {
        long start = System.currentTimeMillis();
        int n = 8; // size of the board (8x8 for standard N-Queen problem)
        int[] queens = new int[n]; // positions of the queens on the board
        for (int i = 0; i < n; i++) {
            queens[i] = ThreadLocalRandom.current().nextInt(n);
        }

        // iterate until no conflicts remain
        int iterations = 0;
        do {
            for (int i = 0; i < n; i++) {
                if (hasConflict(queens, i)) {
                    queens[i] = findMinConflictPosition(queens, i);
                }
            }

            // print the current board and number of conflicts
            System.out.println("Iteration " + iterations + ": ");
            printBoard(queens);
            System.out.println("Conflicts: " + countTotalConflicts(queens));
            System.out.println();
            iterations++;
            //Caps the program if iterations are too much
            if (iterations > 100)
            {
                System.out.println("Too many iterations...");
                System.exit(0);
            }
        } while (countTotalConflicts(queens) > 0);

        // print the final positions of the queens
        System.out.println("Final solution: ");
        printBoard(queens);
        System.out.println("Total iterations: " + iterations);
        long end = System.currentTimeMillis();

        NumberFormat formatter = new DecimalFormat("#0.00000");
        System.out.print("Execution time is " + formatter.format((end - start) / 1000d) + " seconds");
    }

    // prints the current board
    public static void printBoard(int[] queens)
    {
        for (int queen : queens) {
            for (int j = 0; j < queens.length; j++) {
                if (queen == j) {
                    System.out.print("Q ");
                } else {
                    System.out.print("X ");
                }
            }
            System.out.println();
        }
    }

    // returns the total number of conflicts on the board
    public static int countTotalConflicts(int[] queens)
    {
        int conflicts = 0;
        for (int i = 0; i < queens.length; i++) {
            if (queens[i] == -1) continue; // skip unassigned positions
            conflicts += countConflicts(queens, i);
        }
        return conflicts;
    }

    public static int countConflicts(int[] queens, int i)
    {
        int conflicts = 0;
        for (int j = 0; j < queens.length; j++) {
            if (i == j) continue; // skip the current queen
            if (queens[j] == -1) continue; // skip unassigned positions
            if (queens[i] == queens[j]) conflicts++; // same row
            if (Math.abs(i - j) == Math.abs(queens[i] - queens[j])) conflicts++; // same diagonal
        }
        return conflicts;
    }

    public static int findMinConflictPosition(int[] queens, int i)
    {
        int minConflicts = Integer.MAX_VALUE;
        int minColumn = -1;
        for (int column = 0; column < queens.length; column++) {
            queens[i] = column;
            int conflicts = countConflicts(queens, i);
            if (conflicts < minConflicts) {
                minConflicts = conflicts;
                minColumn = column;
            }
        }
        queens[i] = minColumn;
        return minColumn;
    }
    
    public static boolean hasConflict(int[] queens, int i)
    {
        for (int j = 0; j < queens.length; j++) {
            if (i == j) {
                continue;
            }
            if (queens[i] == queens[j]) {
                System.out.println("Column conflict between queens " + i + " and " + j);
                return true;
            }
            if (Math.abs(queens[i] - queens[j]) == Math.abs(i - j)) {
                System.out.println("Diagonal conflict between queens " + i + " and " + j);
                return true;
            }
        }
        return false;
    }
}
