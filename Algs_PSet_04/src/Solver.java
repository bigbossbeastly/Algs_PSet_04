
import java.util.ArrayList;
import java.util.Comparator;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver
{
    private int moves = 0;
    private boolean solved = false;
    
    // MinPQ with Manhattan board comparator
    private MinPQ<SearchNode> minPQ = new MinPQ<SearchNode>(new Comparator<SearchNode>()
    {
    	public int compare(SearchNode A, SearchNode B)
    	{
    		if (A.board.manhattan() + A.moves < B.board.manhattan() + B.moves)
    		{
    			return -1;
    		}
    		else if (A.board.manhattan() + A.moves > B.board.manhattan() + B.moves)
    		{
    			return 1;
    		}
    		
    		return 0;
    	}
    });
    
    private ArrayList<Board> solution = new ArrayList<Board>(); 
    
    public Solver(Board initial)
    {
        SearchNode firstNode = new SearchNode(initial, 0, null);
        minPQ.insert(firstNode);
        
        while (!solved)
        {
            // Remove lowest priority node & check for success
            SearchNode toInvestigate = minPQ.delMin();
            solution.add(toInvestigate.board);
            
            //System.out.println("NEXT DEQUEUE:");
            //System.out.println("Manhattan(" + toInvestigate.board.manhattan() + ") + Moves(" + toInvestigate.moves + ") = " + (toInvestigate.board.manhattan() + toInvestigate.moves));
            //System.out.println(toInvestigate.board.toString());
            //System.out.println("-----------------------------------");
            
            if (toInvestigate.board.isGoal())
            {
                System.out.println(toInvestigate.board.toString());
                solved = true;
            }
            else
            {
            	moves = toInvestigate.moves + 1;
            	
                // Add all neighboring boards & repeat
                for (Board board : toInvestigate.board.neighbors())
                {
                	
                	//System.out.println("Next neighbor (" + neighbor++ + ") || Manhattan: " + board.manhattan());
                	//System.out.println(board.toString());
                	
                	SearchNode nextNode = new SearchNode(board, moves, toInvestigate);
                	
                	if (!checkPriorNodes(nextNode))
                	{
                		//System.out.println("next board in queue: ");
                    	//System.out.println("Manhattan(" + board.manhattan() + ") + Moves(" + movesSoFar + ") = " + (board.manhattan() + movesSoFar));
                    	//System.out.println(board.toString());
                    	//System.out.println();
                    	
                		minPQ.insert(nextNode);
                	}
                }
            }
        }
    }
    
    private boolean checkPriorNodes(SearchNode node)
    {
    	SearchNode previousNode = node.prevNode;
    	
		while(previousNode != null)
		{
			if (previousNode.board.equals(node.board))
			{
				return true;
			}
			
			previousNode = previousNode.prevNode;
		}
    	
    	return false;
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
        return true;
    }
    
    public int moves()
    {
        return moves;
    }
    
    public Iterable<Board> solution()
    {
        return solution;
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
