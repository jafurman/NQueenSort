import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class main {

    public static void main(String[] args)
    {
        //Start time
        Scanner scan = new Scanner(System.in);
        System.out.println("This Program Uses Two Different Methods To Solve The N-Queen Problem");
        System.out.println("Type [1] For Steepest-Ascent Hill Climbing");
        System.out.println("Type [2] For Minimum Conflicts Algorithm");
        int answer = scan.nextInt();
            main program = new main();
            program.start(answer);

    }

    public void start(int answer)
    {
        long startTime = System.currentTimeMillis();
        int size = 8; //we want 8 for the board because of the prompt
            Board initial = new Board(size);
            System.out.println("Heuristic Value: " + initial.getSQP());
            System.out.println("[Starting Board] \n" + initial);
            System.out.println("[Hill Climb]");
            if (answer == 1)
            {
                hillClimbingMethod(initial);
            }
            else if (answer == 2)
            {
                System.out.println("Was going to put the other method here, but the board class and everything was too much. So I made two mains :)");
            }

            System.out.println("[Random Restart]");
            randomRestart(initial, startTime);
        }

        //Start The Hill Climbing method
    public void hillClimbingMethod(Board board)
    {
        boolean localMax = false;
        Board currentBoard = new Board(board.getBoard()); // copy of the board
        int iterations = 0;
        int globalMax = getGoalValue(currentBoard.getBoard().length);

        while (true)
        {
            //This checks if the board configuration we currently have is the "goal" and if yes it breaks out of the for loop to say SOLVED
            if (currentBoard.getSQP() == globalMax) {
                System.out.println("!!Solution found!!");
                System.out.println("Iterations: " + iterations);
                System.out.println("Heuristic Value: " + currentBoard.getSQP());
                System.out.println("[Board]: \n" + currentBoard);
                break;
            } else {
                //If not, it tries another configuration and if there is a localMax the board starts over
                for (int i = 0; i < currentBoard.getBoard().length; i++)
                {
                    Board bestSecondTry = generateSuccessor(currentBoard, i);
                    if (bestSecondTry.getSQP() > currentBoard.getSQP()) {
                        currentBoard = bestSecondTry;
                        iterations++;
                        localMax = false;
                    } else {
                        localMax = true;
                    }
                }
                if (localMax) {
                    System.out.println("!!Local Maximum Found!!");
                    System.out.println("Iterations: " + iterations);
                    System.out.println("Heuristic Value: " + currentBoard.getSQP());
                    System.out.println("[Local Maximum Board]: \n" + currentBoard);
                    break;
                }
            }
        }
    }

    //If the algorithm runs into a position where it deems restart necessary to find a beter solution, it will do so
    public void randomRestart(Board board, long startTime)
    {
        Board currentBoard = new Board(board.getBoard());
        int iterations = 0, numRestarts = 0, numLocalMax = 0, totalIterations = 0, average = 0;
        int globalMax = getGoalValue(currentBoard.getBoard().length);
        boolean isLocalMax = false;

        while (true)
        {
            totalIterations += iterations;
            if (currentBoard.getSQP() == globalMax) { // check if the board configuration is the goal board

                try {
                    average = totalIterations / numLocalMax;
                } catch (Exception ignored) {

                }
                System.out.println("!!Solution Found!!");
                //This is to test the runtime, I placed it underneath when the solution is found
                long endTime = System.currentTimeMillis();
                System.out.println("Heuristic Value: " + currentBoard.getSQP());
                System.out.println("Restarts: " + numRestarts);
                System.out.println("Average length of runs: " + average);
                System.out.println("Iterations: " + iterations);
                System.out.println("[Board Final]: \n" + currentBoard);
                NumberFormat formatter = new DecimalFormat("#0.00000");
                System.out.print("Execution time is " + formatter.format((endTime - startTime) / 1000d) + " seconds");
                break;
            } else {
                for (int x = 0; x < currentBoard.getBoard().length; x++)
                {
                    Board bestSuccessor = generateSuccessor(currentBoard, x);
                    if (bestSuccessor.getSQP() > currentBoard.getSQP())
                    {
                        currentBoard = bestSuccessor;
                        iterations++;
                        isLocalMax = false;
                    } else {
                        isLocalMax = true;
                    }
                }

                if (isLocalMax) {
                    numRestarts++;
                    numLocalMax++;
                    currentBoard = new Board(currentBoard.getBoard().length);
                }
            }
        }
    }

    //This method Board will generate a successor that is best given the current board configuration
    //Will eventually get the Queen to a column it isn't attacked from
    public Board generateSuccessor(Board board, int row)
    {
        ArrayList<Board> children = new ArrayList<>();
        Board bestChild;

        for (int col = 0; col < board.getBoard().length; col++) {
            if (board.getBoard()[row][col] != 1) { // the element is not a queen
                int[][] child = new int[board.getBoard().length][board.getBoard().length];
                child[row][col] = 1; // move queen to this column

                for (int i = 0; i < child.length; i++) {
                    if (i != row) {
                        child[i] = board.getBoard()[i];
                    }
                }
                children.add(new Board(child)); // create board object from the generated new board
                // add successor to children list
            }
        }

        bestChild = children.get(0);

        for (int z = 1; z < children.size(); z++) {
            int bestEv = bestChild.getSQP();
            int nextEv = children.get(z).getSQP();

            if (nextEv > bestEv) {
                bestChild = children.get(z);
            } else if (nextEv == bestEv) {
                Random rand = new Random();
                int choose = rand.nextInt(2);
                if (choose == 1) {
                    bestChild = children.get(z);
                }
            }
        }

        return bestChild;

    }

    //Returns 1-(N-1) summation
    public int getGoalValue(int size) {
        int sum = 0;
        for (int i = 0; i < size; i++) {
            sum += i;
        }
        return sum;
    }

}

