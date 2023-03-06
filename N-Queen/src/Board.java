import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

class Board {
    private final int[][] board;
    private int safeQueenPairs; // number of non-attacking pair of queens

    //Creates the board from a 2D array
    public Board(int[][] board)
    {
        this.board = board;
        ev();
    }

    public ArrayList<Integer> getQueenPositions()
    {
        ArrayList<Integer> pos = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            pos.add(getColumn(i));
        }
        return pos;
    }

    public int[][] getBoard() {
        return board;
    }

    public int getSQP() {
        return safeQueenPairs;
    }

    public void setSafeQueenPairs(int safeQueenPairs)
    {
        this.safeQueenPairs = safeQueenPairs;
    }

    public int getColumn(int row)
    {
        int index = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[row][i] == 1) {
                index = i;
            }
        }

        return index;
    }


    //Generates the board with whatever "size" is.
    //In our case the size is always 8
    public Board(int size)
    {
        board = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = 0;
            }
        }
        //calls random queen placement based on the "size" given
        RandomQueens(size);
        ev();
    }

    //ArrayList called positions
    public void ev() {
        safeQueenPairs = 0;
        ArrayList<Integer> pos = getQueenPositions();

        for (int i = 0; i < board.length - 1; i++)
        {
            safeQueenPairs += countSafe(pos, i);
        }

    }

    public int countSafe(ArrayList<Integer> position, int row)
    {
        int i, safeQueens = 0, count;

        for (i = row; i < board.length - 1; i++)
        {
            count = 0;
            if (Objects.equals(position.get(row), position.get(i + 1))) { // check if same column
                count++;
            }
            if ((position.get(row) + row) == (position.get(i + 1) + (i + 1))) { // check diagonal
                count++;
            }
            if (count <= 0) {
                safeQueens++;
            }
        }
        return safeQueens;

    }
    @Override
    public String toString()
    {
        StringBuilder b = new StringBuilder();
        for (int[] ints : board) {
            for (int j = 0; j < board.length; j++) {
                if (ints[j] == 0) {
                    b.append(" X ");
                } else {
                    b.append(" Q ");
                }
            }
            b.append("\n");
        }
        return b.toString();
    }

    //Random queen generation on every row (hence the row variables being the only iteration)
    public void RandomQueens(int size)
    {
        Random rand = new Random();
        for (int row = 0; row < size; row++) {
            board[row][rand.nextInt(size - 1)] = 1;
        }
    }


}
