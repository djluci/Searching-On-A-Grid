package SearchingOnAGrid;

import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class Maze implements Iterable<Cell>{

    //Iterator which iterates through all the Cells in the Maze row and column by column
    public Iterator<Cell> iterator() {
        return new Iterator<Cell>() {
            int r, c;

            public boolean hasNext() {
                return r < getRows();
            }

            public Cell next() {
                Cell next = get(r, c);
                c++;
                if (c == getCols()) {
                    r++;
                    c = 0;
                }
                return next;
            }
        };
    }

    // Number of rows and columns in this Maze
    private int rows, cols;

    // Density of this Maze. Each cell independently has probability of being an Obstacle
    private double densityOfObstacles;

    private double densityOfIce;

    private double densityOfMud;

    // 2-Day array of Cells making up this Maze
    private Cell[][] landscape;

    // Constructs a Maze with the given number of rows and columns. Each Cell
    // independently has probabiliy of being an Obstacle
    //
    // @param rows    the number of rows.
    // @param columns the number of columns.
    // @param density the probability of any individual Cell being an OBSTACLE.

    public Maze(int rows, int columns, double densityOfObstacles, double densityOfIce, double densityOfMud){
        this.rows = rows;
        this.cols = columns;
        this.densityOfObstacles = densityOfObstacles;
        this.densityOfIce = densityOfIce;
        this.densityOfMud = densityOfMud;
        landscape = new Cell[rows][columns];
        reinitialize();
    }

    // Initialized every Cell in the Maze
    public void reinitialize() {
        Random rand = new Random();
        for (int r = 0; r < rows; r++){
            for(int c = 0; c < cols; c++){
                double randDouble = rand.nextDouble();
                if(randDouble < densityOfObstacles){
                    landscape[r][c] = new Cell(r, c, CellType.OBSTACLE);
                } else if(randDouble < densityOfObstacles + densityOfIce){
                    landscape[r][c] = new Cell(r, c, CellType.ICE);
                } else if(randDouble < densityOfObstacles + densityOfIce + densityOfMud){
                    landscape[r][c] = new Cell(r, c, CellType.MUD);
                }else{
                    landscape[r][c] = new Cell(r, c, CellType.FREE);
                }

                // landscape[r][c] = new Cell(r, c, rand.nextDouble() < densityOfObstacles ? CellType.OBSTACLE : CellType.FREE);
            }
        }
    }

    // calls RESET on every Cell in this Maze
    public void reset(){
        for(Cell cell : this)
            cell.reset();
    }

    // Returns the number of rows in the Maze
    public int getRows(){
        return rows;
    }

    // Returns the number of columns in the Maze
    public int getCols(){
        return cols;
    }

    // Return the Cell at the specidifed row and Column in the maze
    public Cell get(int row, int col) {
        return landscape[row][col];
    }
    
    // Returns a LinkedList of the non-OBSTACLE Cells neighboring the specified CELL
    public LinkedList<Cell> getNeighbors(Cell c) {
        LinkedList<Cell> cells = new LinkedList<Cell>();
        int[][] steps = new int[][] { { -1, 0 }, { 1, 0 }, { 0, 1 }, { 0, -1 } };
        for (int[] step : steps) {
            int nextRow = c.getRow() + step[0];
            int nextCol = c.getCol() + step[1];
            if (nextRow >= 0 && nextRow < getRows() && nextCol >= 0 && nextCol < getCols()
                    && get(nextRow, nextCol).getType() != CellType.OBSTACLE)
                cells.addLast(get(nextRow, nextCol));
        }
        return cells;
    }

    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("-".repeat(cols + 3) + "\n");
        for (Cell[] cells : landscape) {
            output.append("| ");
            for (Cell cell : cells) {
                output.append(cell.getType() == CellType.OBSTACLE ? 'X' : ' ');
            }
            output.append("|\n");
        }
        return output.append("-".repeat(cols + 3)).toString();
    }
    
    // Calls drawType on every Cell in the Maze
    public void draw(Graphics g, int scale){
        for (Cell cell : this)
            cell.drawType(g, scale);
    }

    public static void main(String[] args) {
        Maze ls = new Maze(7, 7, .2, .05, .05);
        System.out.println(ls);
    }
}
