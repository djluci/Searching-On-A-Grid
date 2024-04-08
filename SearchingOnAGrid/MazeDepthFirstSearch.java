package SearchingOnAGrid;

import java.util.LinkedList;
import java.util.Stack;

public class MazeDepthFirstSearch extends AbstractMazeSearch{
    
    private Stack<Cell> stack;

    public MazeDepthFirstSearch(Maze maze){
        super(maze);
        this.stack = new Stack<Cell>();
    }

    @Override
    public Cell findNextCell() {
        Cell nextCell = stack.pop();
        return nextCell;
    }

    @Override
    public void addCell(Cell next) {
       stack.add(next);
    }

    @Override
    public int numRemainingCells() {
       return stack.size();
    }

}
