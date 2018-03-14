
import java.util.ArrayList;
import java.util.Comparator;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver
{
    private int moves = 0;
    private boolean solved = false;
    private boolean solvable;
    
    // MinPQ with Manhattan board comparator
    private MinPQ<SearchNode> minPQ = new MinPQ<SearchNode>(new Comparator<SearchNode>()
    {
    	public int compare(SearchNode A, SearchNode B)
    	{
    		int aManhattan = A.board.manhattan() + A.moves;
    		int bManahattan = B.board.manhattan() + B.moves;
    		
    		if (aManhattan < bManahattan)
    		{
    			return -1;
    		}
    		else if (aManhattan > bManahattan)
    		{
    			return 1;
    		}
    		
    		return 0;
    	}
    });
    
    private MinPQ<SearchNode> minPQComp = new MinPQ<SearchNode>(new Comparator<SearchNode>()
    {
    	public int compare(SearchNode A, SearchNode B)
    	{
    		int aManhattan = A.board.manhattan() + A.moves;
    		int bManahattan = B.board.manhattan() + B.moves;
    		
    		if (aManhattan < bManahattan)
    		{
    			return -1;
    		}
    		else if (aManhattan > bManahattan)
    		{
    			return 1;
    		}
    		
    		return 0;
    	}
    });
    
    private ArrayList<Board> solution = new ArrayList<Board>(); 
    
    public Solver(Board initial)
    {
        if (initial == null)
        {
            throw new java.lang.IllegalArgumentException();
        }
        
        SearchNode firstNode = new SearchNode(initial, 0, null);
        SearchNode firstNodeComp = new SearchNode(initial.twin(), 0, null);
        
        minPQ.insert(firstNode);
        minPQComp.insert(firstNodeComp);
        
        while (!solved)
        {
            // Remove lowest priority node & check for success
            SearchNode toInvestigate = minPQ.delMin();
            SearchNode toInvestigateComp = minPQComp.delMin();
            
            if (toInvestigate.board.isGoal() || toInvestigateComp.board.isGoal())
            {
            	if (toInvestigateComp.board.isGoal())
            	{
            		solvable = false;
            		break;
            	}
            	else
            	{
	                solved = true;
	                solvable = true;
	                moves = 0;
	                
	                Stack<Board> temp = new Stack<Board>();
	                while (toInvestigate.prevNode != null)
	                {
	                    moves++;
	                    temp.push(toInvestigate.board);
	                    solution.add(toInvestigate.board);
	                    toInvestigate = toInvestigate.prevNode;
	                }
	                
	                // Swap order by popping from stack until its empty
	                while (!temp.isEmpty())
	                {
	                	solution.add(temp.pop());
	                }
	                
	                solution.add(initial);
            	}
            }
            else
            {
            	moves = toInvestigate.moves + 1;
            	
                // Add all neighboring boards & repeat
                for (Board board : toInvestigate.board.neighbors())
                {
                    if (toInvestigate.prevNode == null || !board.equals(toInvestigate.prevNode.board))
                    {
                        SearchNode nextNode = new SearchNode(board, moves, toInvestigate);
                		minPQ.insert(nextNode);
                    }
                }
                
                // Add all neighboring boards to twin compare
                for (Board board : toInvestigateComp.board.neighbors())
                {
                    if (toInvestigateComp.prevNode == null || !board.equals(toInvestigateComp.prevNode.board))
                    {
                        SearchNode nextNode = new SearchNode(board, moves, toInvestigateComp);
                		minPQComp.insert(nextNode);
                    }
                }
            }
        }
    }
    
    
    /*------------------------------------------------------
     * Create a node to store our progress
     ------------------------------------------------------*/
    private class SearchNode
    {
        Board board;
        int moves;
        SearchNode prevNode;
        
        public SearchNode(Board Board, int Moves, SearchNode PreviousNode)
        {
            board = Board;
            moves = Moves;
            prevNode = PreviousNode;
        }
    }
    
    public boolean isSolvable()
    {
        return solvable;
    }
    
    public int moves()
    {
        if (isSolvable())
        {
            return moves;
        }
        
        return -1;
        
    }
    
    public Iterable<Board> solution()
    {
        if (isSolvable())
        {
            return solution;
        }
        
        return null;
    }
    
    public static void main(String[] args) 
    {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                blocks[i][j] = in.readInt();
            }
        }
        
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
        {
            StdOut.println("No solution possible");
        }
        else 
        {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
            {
                StdOut.println(board);
            }
        }
    }
}
