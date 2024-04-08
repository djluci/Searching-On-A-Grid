package SearchingOnAGrid;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;


public abstract class AbstractMazeSearch {
    
    // Instace fields for the maze
    // starting cell
    // target cell
    // current cell
    private Maze myMaze;
    private Cell start;
    private Cell target;
    private Cell cur;

    // constructor, intalizes a maze object with null values for the cell objects
    public AbstractMazeSearch(Maze maze) {
        myMaze = maze;
        cur = null;
        start = null;
        target = null;
    }

    // Abstract methods are defined in this abstract class, 
    // However they will be implemented in the subclasses (our three different searching algorithms)
    public abstract Cell findNextCell();
    public abstract void addCell(Cell next);
    public abstract int numRemainingCells();

    // returns the maze object
    public Maze getMaze(){
        return this.myMaze;
    }

    // sets target cell to a specified cell
    public void setTarget(Cell target){
        this.target = target;
    }

    // Returns target cell 
    public Cell getTarget(){
        return this.target;
    }

    // sets the current cell to a specified cell location
    public void setCur(Cell cell){
        this.cur = cell;
    }

    // gets current cell
    public Cell getCur(){
        return this.cur;
    }

    //sets the starting cell to a specifiedc starting cell location 
    public void setStart(Cell start){
        this.start = start;
        this.start.setPrev(start);
    }

    // gets the starting cell location location
    public Cell getStart(){
        return this.start;
    }

    // resets the the maze so the cell fields are all null
    public void reset(){
        this.start = null;
        this.cur = null;
        this.target = null;
    }

    // traceback method traces back from the target to the starting cell in the shortest path we took. 
    public LinkedList<Cell> traceback(Cell cell){
        Cell curCell = cell;
        LinkedList<Cell> path = new LinkedList<>();
    
        while (curCell != null){
            // add curCell to the front of path

            path.addFirst(curCell);

            // if (curCell is the start){
            //     return path; // we've completed the path from the start to the specified cell
            // }

            if(curCell == this.start){
                return path;
            }
            curCell = curCell.getPrev();
        } return null; // we weren't able to find a path, so we return null
    }

    /* search method implements how a search occurrs in a high-level manner
     * 
     * the cell instance feilds are set to specified values.
     * Then, the current cell will treverse the maze in it's designated instructional pattern as specified by 
     * the findNextCell method, specified in the subclasses of this Abstract class
     * 
     * Note:
     * The method is altered to make use of the extension for ice and mud cells. 
     * the ice cell will slow the speed of the search dramatically, but moving straight across a ice cell will 
     * speed the cells movement up
     * 
     * with the case of a mud cell, both turns and moving straight across them will slow the cells movement. 
     */
    public LinkedList<Cell> search(Cell start, Cell target, boolean display, int delay) throws InterruptedException{


        //set the display to be null - a display will be created only if dispaly == true
        MazeSearchDisplay myDisplay = null;


        if(display){
            myDisplay = new MazeSearchDisplay(this, 35);
        }

        setStart(start);
        setTarget(target);
        setCur(start);

        


        addCell(start);
        while(numRemainingCells() > 0){
            Cell nextCell = findNextCell();
            if(display){

                LinkedList<Cell> path = traceback(this.cur);
                if(path.getFirst() == null){  
                //your on the first cell and it's an ice or mud cell  
                    if(cur.getType() == CellType.ICE){
                    Thread.sleep(delay /4);
                    } 
                    if(nextCell.getType() == CellType.MUD){
                    Thread.sleep(delay * 6);
                    }
                }else{

                    //your turning 
                        if((cur.getRow() == path.getFirst().getRow()) && nextCell.getRow() != cur.getRow()){
                            if((cur.getCol() == path.getFirst().getCol()) && nextCell.getCol() != cur.getCol()){
                                //your currently on an ice cell while turning
                                if(cur.getType() == CellType.ICE){
                                    Thread.sleep(delay * 6);
                                }

                                //you are currently on mud while turning
                                if(cur.getType() == CellType.MUD){
                                    Thread.sleep(delay * 5);
                                }
                                

                            }
                    }
                    else if(cur.getType() == CellType.ICE){
                            Thread.sleep(delay /4);
                            } 
                    else if(nextCell.getType() == CellType.MUD){
                            Thread.sleep(delay * 6);
                        } else{
                            Thread.sleep(delay);
                        }     
                    }
                    myDisplay.repaint();  
            }

            setCur(nextCell);
            
            for(Cell neighbor: myMaze.getNeighbors(cur)){
                if(neighbor.getPrev() == null){
                    neighbor.setPrev(cur);
                    addCell(neighbor);
                    if(neighbor.equals(target)){
                        return traceback(target);
                    }
                }
            }
        }

        return null;

       
    }

    // Draws the specified cell characteristics onto the maze
    public void draw(Graphics g, int scale) {

        // Draws the base version of the maze
        getMaze().draw(g, scale);

        // Draws the paths taken by the searcher
        getStart().drawAllPrevs(getMaze(), g, scale, Color.RED);

        // Draws the start cell
        getStart().draw(g, scale, Color.BLUE);

        // Draws the target cell
        getTarget().draw(g, scale, Color.RED);

        // Draws the current cell
        getCur().draw(g, scale, Color.MAGENTA);
    
        // If the target has been found, draws the path taken by the searcher to reach
        // the target sans backtracking.
        if (getTarget().getPrev() != null) {
            Cell traceBackCur = getTarget().getPrev();
            while (!traceBackCur.equals(getStart())) {
                traceBackCur.draw(g, scale, Color.GREEN);
                traceBackCur = traceBackCur.getPrev();
            }
            getTarget().drawPrevPath(g, scale, Color.BLUE);
        }
    }

}
