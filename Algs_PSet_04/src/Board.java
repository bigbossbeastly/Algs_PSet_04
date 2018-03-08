import edu.princeton.cs.algs4.StdRandom;

public class Board
{
    int[][] board;
    int dimension;
    
    public Board(int[][] blocks)
    {
        dimension = blocks.length;
        
        board = new int[blocks.length][blocks.length];
        
        for (int i = 0; i < blocks.length; i ++)
        {
            for (int j = 0; j < blocks[i].length; j++)
            {
                board[i][j] = blocks[i][j];
            }
        }
    }
    
    public int dimension()
    {
        return dimension;
    }
    
    /*
     * Return the desired value for the given coordinates
     */
    private int ConvertTo1D(int row, int col)
    {
        return row * dimension + col + 1;
    }
    
    public int hamming()
    {
        int numWrong = 0;
        
        for (int i = 0; i < board.length; i ++)
        {
            for (int j = 0; j < board[i].length; j++)
            {
                if (board[i][j] != ConvertTo1D(i, j))
                {
                    numWrong++;
                }
            }
        }
        
        return numWrong;
    }
    
    public int manhattan()
    {
        return -1;
    }
    
    public boolean isGoal()
    {
        return false;
    }
    
    public Board twin()
    {
        return new Board(new int[0][0]);
    }
    
    public boolean equals(Object y)
    {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        
        Board that = (Board) y;
        if(that.dimension() != this.dimension()) return false;
        
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[i].length; j++)
            {
                if (that.board[i][j] != board[i][j])
                {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    /*
    public Iterable<Board> neighbors()
    {
        
    }
    */
    
    public String toString()
    {
        String boardString = "";
        int defaultSpace = 1;
        int maxSpaces = String.valueOf(dimension * dimension).length() + defaultSpace;
        
        for (int i = 0; i < board.length; i ++)
        {
            for (int j = 0; j < board[i].length; j++)
            {
                // Append next element
                boardString += board[i][j];
                
                // Add spaces to account for greatest number of digits
                int mySpaces = String.valueOf(board[i][j]).length();
                boardString += new String(new char[maxSpaces-mySpaces]).replace("\0", " ");
                
                // new line
                if (j == dimension-1)
                {
                    boardString += "\n";
                }
            }
        }
        
        return boardString;
    }
    
    public static void main(String[] args)
    {
        int randNum = StdRandom.uniform(2, 5);
        int[][] tempArray = new int[randNum][randNum];
        
        int nextNum = 0;
        
        for (int i = 0; i < tempArray.length; i ++)
        {
            for (int j = 0; j < tempArray[i].length; j++)
            {
                tempArray[i][j] = ++nextNum;
            }
        }
        
        Board myBoard = new Board(tempArray);
        System.out.println(myBoard.toString());
        System.out.println(myBoard.hamming());
    }
}
