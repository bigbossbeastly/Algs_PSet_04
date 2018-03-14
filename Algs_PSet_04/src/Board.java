import java.util.ArrayList;

import edu.princeton.cs.algs4.StdRandom;

public class Board
{
    private int[][] board;
    private int [] empty;
    private int dimension;
    private int manhattanCount = 0;
    private int hammingCount = 0;
    
    // Use array list so we don't need to manage array resizing & collection is Iterable
    private ArrayList<Board> neighbors = new ArrayList<Board>(); 
    
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
                    empty = new int[2];
                    empty[0] = i;
                    empty[1] = j;
                }
                else
                {
                    // Store Hamming
                    if (board[i][j] != (ConvertTo1D(i, j)))
                    {
                        hammingCount++;
                    }
                    
                    // Store Manhattan
                    int[] coords = ConvertTo2D(board[i][j] - 1);
                    manhattanCount += (Math.abs((i - coords[1])) + Math.abs((j - coords[0])));
                    
                    /* DEBUG
                    System.out.println("Game value: " + board[i][j] + " || Grid index: " + ConvertTo1D(i, j) + " || 'should be' x/y coords: " + coords[0] + ", " + coords[1] + " || Actual x/y coords: " + i + ", " + j);
                    System.out.println("Array Value: " + board[i][j] + " || Board index " + boardID + " (" + i + " , " + j + ")");
                    System.out.println("2D X should be: " + coords[0] + " (diff = " + Math.abs((i - coords[0])) + ")");
                    System.out.println("2D Y should be: " + coords[1] + " (diff = " + Math.abs((j - coords[1])) + ")");
                    System.out.println();
                    */
                }
            }
        }
    }
    
    public int dimension()
    {
        return dimension;
    }
    
    
    /*---------------------------------------------------------------------
     * Return the 1d index for the given coordinates (with a +1 offset)
     --------------------------------------------------------------------*/
    private int ConvertTo1D(int row, int col)
    {
        return (row * dimension + col) + 1;
    }
    
    /*----------------------------------------------------
     * Returns the row and col values for the given 1d index
     ---------------------------------------------------*/
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
    
    private void findNeighbors()
    {
        // Find neighbors, swap positions & all data, then create and add new board
        if (empty[0] > 0)
        {
            // Copy board into new board
            int[][] boardCopy = new int[dimension][dimension];
            for (int i = 0; i < dimension; i ++)
            {
                for (int j = 0; j < dimension; j++)
                {
                    boardCopy[i][j] = board[i][j];
                }
            }
            
            // Swap 0 with space above
            boardCopy[empty[0]][empty[1]] = boardCopy[empty[0]-1][empty[1]];
            boardCopy[empty[0]-1][empty[1]] = 0;
            Board upSwapped = new Board(boardCopy);
            neighbors.add(upSwapped);
        }
        
        // Down
        if (empty[0] < dimension - 1)
        {
            // Copy board into new board
            int[][] boardCopy = new int[dimension][dimension];
            for (int i = 0; i < dimension; i ++)
            {
                for (int j = 0; j < dimension; j++)
                {
                    boardCopy[i][j] = board[i][j];
                }
            }
            
            // Swap 0 with space above
            boardCopy[empty[0]][empty[1]] = boardCopy[empty[0]+1][empty[1]];
            boardCopy[empty[0]+1][empty[1]] = 0;
            Board downSwapped = new Board(boardCopy);
            neighbors.add(downSwapped);
        }
        
        // Left
        if (empty[1] > 0)
        {
            // Copy board into new board
            int[][] boardCopy = new int[dimension][dimension];
            for (int i = 0; i < dimension; i ++)
            {
                for (int j = 0; j < dimension; j++)
                {
                    boardCopy[i][j] = board[i][j];
                }
            }
            
            // Swap 0 with space above
            boardCopy[empty[0]][empty[1]] = boardCopy[empty[0]][empty[1]-1];
            boardCopy[empty[0]][empty[1]-1] = 0;
            Board leftSwapped = new Board(boardCopy);
            neighbors.add(leftSwapped);
        }
        
        // Right
        if (empty[1] < dimension -1)
        {
            // Copy board into new board
            int[][] boardCopy = new int[dimension][dimension];
            for (int i = 0; i < dimension; i ++)
            {
                for (int j = 0; j < dimension; j++)
                {
                    boardCopy[i][j] = board[i][j];
                }
            }
            
            // Swap 0 with space above
            boardCopy[empty[0]][empty[1]] = boardCopy[empty[0]][empty[1]+1];
            boardCopy[empty[0]][empty[1]+1] = 0;
            Board rightSwapped = new Board(boardCopy);
            neighbors.add(rightSwapped);
        }
    }
    
    public Iterable<Board> neighbors()
    {
    	if (neighbors.size() == 0)
    	{
    		findNeighbors();
    	}
        return neighbors;
    }
    
    public String toString() 
    {
        StringBuilder s = new StringBuilder();
        s.append(dimension + "\n");
        for (int i = 0; i < dimension; i++) 
        {
            for (int j = 0; j < dimension; j++) 
            {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    
    /*
    
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
        Solver solve = new Solver(myBoard);
        
        for (Board board : solve.solution())
        {
            System.out.println(board.toString());
        }
        
        
        System.out.println("Total moves taken: " + solve.moves());
    }
    */
}
