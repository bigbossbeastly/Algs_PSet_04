
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver
{
    int moves = 0;
    boolean solved = false;
    Node lastNode;
    MinPQ<Node> minPQ = new MinPQ<Node>();
    
    public Solver(Board initial)
    {
        Node firstNode = new Node(initial, 0, null);
        minPQ.insert(firstNode);
        lastNode = firstNode;
        
        while (!solved)
        {
            // Iterate through all neighboring boards and insert the one with the lowest
            // manhattan.
            
            moves++;
            Node toInvestigate = minPQ.delMin();
            
            Board newBoard;
            Node nextNode = new Node(newBoard, moves, lastNode);
            minPQ.insert(nextNode);
            lastNode = nextNode;
        }
    }
    
    /*------------------------------------------------------
     * Create a node to store our progress
     ------------------------------------------------------*/
    public class Node
    {
        public Node(Board Board, int Moves, Node PreviousNode)
        {
            Board myBoard = Board;
            int myMoves = Moves;
            Node prevNode = PreviousNode;
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
