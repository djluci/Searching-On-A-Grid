package SearchingOnAGrid;

import java.util.Comparator;

//MazeAStarSearch class inherits the methods from the AbstractMazeSearch class - and implements the unimplemented abstact methods
public class MazeAStarSearch extends AbstractMazeSearch{
    
    // priority queue instance field that stores the cells that need to be searched
    private PriorityQueue<Cell> priorityQueue;

    // constructor - creates a maze 
    // the algorithm then executes by comparing two cells priority 
    // higher priority is given to the cell that is closer to the target cells row and column and has a shorter path from 
    // the starting cell
    public MazeAStarSearch(Maze maze){
        super(maze);

        //System.out.println(getTarget() + "\n");
        priorityQueue = new Heap( new Comparator<Cell>(){

            //comparator - implements the comprarison of cells which is nessescay for the functionality of the priority queue
            // in the AStar Search algorithm
            public int compare(Cell cell1, Cell cell2){

                int pastSteps1 = traceback(cell1).size();
                int pastSteps2 = traceback(cell2).size();
        

                
                int stepsToTarget1 = (Math.abs((getTarget().getRow()) - cell1.getRow())) + Math.abs(((getTarget().getCol()) - cell1.getCol()));
                int stepsToTarget2 = (Math.abs((getTarget().getRow()) - cell2.getRow())) + Math.abs(((getTarget().getCol()) - cell2.getCol()));
                double sum1 = pastSteps1 + stepsToTarget1;
                double sum2 = pastSteps2 + stepsToTarget2;
                // System.out.println(getTarget() + "\n");
                // System.out.println(cell1);
                // System.out.println(pastSteps1);
                // System.out.println(stepsToTarget1);
                // System.out.println(sum1);
                // System.out.println(cell2);
                // System.out.println(pastSteps2);
                // System.out.println(stepsToTarget2);
                // System.out.println(sum2 + "\n");
        
                return Double.compare(sum1, sum2);
            }
        });
    }

    // finds the NextCell by polling the neightboring cell with the highest priority from the priority queue
    @Override
    public Cell findNextCell() {
        Cell next = priorityQueue.poll();
        //System.out.println(next);
        return next;
    }

    //adds a cell to the priority queue instance field
    @Override
    public void addCell(Cell next) {
        priorityQueue.offer(next);
    }

    // returns the number of remaining cells in the priority queue
    @Override
    public int numRemainingCells() {
        return priorityQueue.size();
    }
}
