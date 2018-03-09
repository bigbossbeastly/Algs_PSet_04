import edu.princeton.cs.algs4.StdRandom;

public class Board
{
    int[][] board;
    int[] emptyLocation = new int[2];
    int dimension;
    int manhattanCount = 0;
    int hammingCount = 0;
    
    public Board(int[][] blocks)
    {
        dimension = blocks.length;
        
        board = new int[dimension][dimension];
        
        for (int i = 0; i < dimension; i ++)
        {
            for (int j = 0; j < dimension; j++)
            {
                board[i][j] = blocks[i][j];
                
                if (board[i][j] == 0)
                {
                    emptyLocation[0] = i;
                    emptyLocation[1] = j;
                }
                else
                {
                 // Calculate hamming
                    int boardID = ConvertTo1D(i, j);
                    if (board[i][j] != (boardID))
                    {
                        hammingCount++;
                    }
                    
                    
                    // Calculate manhattan
                    int[] coords = new int[2];
                    coords = ConvertTo2D(boardID);
                    manhattanCount += Math.abs((i - coords[0])) + Math.abs((j - coords[1]));
                    
                    System.out.println("Number: " + boardID);
                    System.out.println("X should be: " + coords[0]);
                    System.out.println("Y should be: " + coords[1]);
                    System.out.println("Instead they are: " + i + " and " + j);
                }
                
                
            }
        }
    }
    
    public int dimension()
    {
        return dimension;
    }
    
    /*----------------------------------------------------
     * Return the desired value for the given coordinates
     ---------------------------------------------------*/
    private int ConvertTo1D(int row, int col)
    {
        return row * dimension + col + 1;
    }
    
    private int[] ConvertTo2D(int value)
    {
        int[] coords = new int[2];
        coords[0] = value % dimension;
        coords[1] = value / dimension;
        return coords;
    }
    
    public int hamming()
    {
        return hammingCount;
    }
    
    public int manhattan()
    {
        return manhattanCount;
    }
    
    public boolean isGoal()
    {
        if (manhattanCount == 0)
        {
            return true;
        }
        
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
        int randNum = StdRandom.uniform(3, 4);
        int[][] tempArray = new int[randNum][randNum];
        
        int nextNum = 0;
        
        int test[] = { 8, 1, 3, 4, 0, 2, 7, 6, 5 };
        
        for (int i = 0; i < tempArray.length; i ++)
        {
            for (int j = 0; j < tempArray[i].length; j++)
            {
                tempArray[i][j] = test[nextNum++];
            }
        }
        
        Board myBoard = new Board(tempArray);
        System.out.println(myBoard.toString());
        System.out.println("Hamming: " + myBoard.hamming());
        System.out.println("Manhattan: " + myBoard.manhattan());
        
    }
}
