package SearchingOnAGrid;

import java.util.Random;

public class Exploration {
    
    // main method
    public static void main(String[] args) throws InterruptedException {
        for(int n = 0; n < 100; n++){
            Random rand = new Random();

    Maze myMaze = new Maze(20, 20, 0, 0, 0);
    AbstractMazeSearch mySerach = new MazeDepthFirstSearch(myMaze);
    int row1 = rand.nextInt(20);
    int col1 = rand.nextInt(20);

    while(!(myMaze.get(row1, col1).getType().equals(CellType.FREE))){

        //System.out.println("while loop1");
        row1 = rand.nextInt(20);
        col1 = rand.nextInt(20);
    }

    int row2 = rand.nextInt(20);
    int col2 = rand.nextInt(20);
    while(!(myMaze.get(row2, col2).getType().equals(CellType.FREE)) || (row2 == row1 && col2 == col1)){
        //System.out.println("while loop 2");
        row2 = rand.nextInt(20);
        col2 = rand.nextInt(20);
    }
    mySerach.search(myMaze.get(row1, col1), myMaze.get(row2, col2), false, 0);
    int cellsExplored = (20*20) - mySerach.numRemainingCells();
    System.out.println(cellsExplored);
        }
    }
}
