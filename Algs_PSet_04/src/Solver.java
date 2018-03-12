
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver
{
    int moves = 0;
    boolean solved = false;
    MinPQ<SearchNode> minPQ = new MinPQ<SearchNode>(COMPARATOR_FUNCTION);
    
    public Solver(Board initial)
    {
        System.out.println("start board!");
        System.out.println(initial.toString());
        
        SearchNode firstNode = new SearchNode(initial, 0, null);
        minPQ.insert(firstNode);
        
        while (!solved)
        {
            // Remove lowest priority node & check for success
            SearchNode toInvestigate = minPQ.delMin();
            if (toInvestigate.myBoard.isGoal())
            {
                System.out.println("Solved!");
                System.out.println(toInvestigate.myBoard.toString());
                solved = true;
            }
            else
            {
                // Add all neighboring boards & repeat
                for (Board board : toInvestigate.myBoard.neighbors())
                {
                    SearchNode nextNode = new SearchNode(board, ++moves, toInvestigate);
                    minPQ.insert(nextNode);
                }
            }
        }
    }
    
    /*------------------------------------------------------
     * Create a node to store our progress
     ------------------------------------------------------*/
    public class SearchNode
    {
        Board myBoard;
        int myMoves;
        SearchNode prevNode;
        
        public SearchNode(Board Board, int Moves, SearchNode PreviousNode)
        {
            myBoard = Board;
            myMoves = Moves;
            prevNode = PreviousNode;
        }
    }
    
    public boolean isSolvable()
    {
        return false;
    }
    
    public int moves()
    {
        return moves;
    }
    
    /*
    public Iterable<Board> solution()
    {
        
    }
    */
    
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
            //for (Board board : solver.solution())
            //{
            //    StdOut.println(board);
            //}
        }
    }
}
