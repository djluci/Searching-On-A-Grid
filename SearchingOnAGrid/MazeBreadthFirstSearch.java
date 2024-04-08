package SearchingOnAGrid;

import java.util.LinkedList;
import java.util.Queue;

// class inherits and implements the abstract methods from AbstractMazeSearch
public class MazeBreadthFirstSearch extends AbstractMazeSearch{
    
    //instance field for the Queue which will hold our cell objects 
    private Queue<Cell> queue;

    // creates a maze and intializes the queue
    public MazeBreadthFirstSearch(Maze maze){
        super(maze);
        this.queue = new LinkedList<>();
    }

    // find nexCell by polling from queue of cells
    @Override
    public Cell findNextCell() {
        return this.queue.poll();
    }

    // add cell to the queue
    @Override
    public void addCell(Cell next) {
        this.queue.add(next);
    }

    // Returns the number of remaining cells in the maze
    @Override
    public int numRemainingCells() {
        return this.queue.size();
    }

}
