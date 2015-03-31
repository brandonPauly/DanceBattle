/*
Brandon Pauly
CSC 380 - Assignment 2
Dr. Gemmell
*/
package dancebattle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import minmax.MinMax;
import node.Node;

/**
 *
 * @author Brandon Pauly
 * 
 * Point of execution for the DanceBattle Project.  The main method has file strings that can be commented out/in to choose 
 * which test file will be run.  A buffered reader reads the file and gathers the data from the test file by getting the first integer
 * representing the number of possible moves in the battle, followed by a newline character.  The next line is the number of moves made
 * in the game thus far, followed by the newline character.  Then subsequent lines represent the moves made so far, alternating between 
 * contestants.  Each move made so far is represented by an integer, then a single space, then another integer, then the newline character.
 * The root node is then made by building the initial state of the game with the moves so far represented by a two dimensional boolean array, 
 * and giving that initial state to the root node, as well as the number of moves made so far, the last move made, and the total possible moves.
 * The minimax algorithm is then run on the root node, to apply the min/max values to each node, and the solution is returned by calling the 
 * getSolution function from the MinMax class.  The moves busted in sequence are then printed to System.out and the winner is declared.
 */
public class DanceBattle {
    private static final boolean MAX = true;
    private static final boolean MIN = false;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        /*
        Comment in/out the following three lines to switch to an alternate test file
        */
        //String file = "data/testcase1.txt";
        String file = "data/testcase2.txt";
        //String file = "data/testcase3.txt";
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        ArrayList<String> lines = new ArrayList();
        // read each line of the file
        while ((line = br.readLine()) != null) {
           lines.add(line);
        }
        br.close();
        // get the number of moves that can be made
        String nom = lines.get(0);
        int numberOfMoves = Integer.parseInt(nom);
        // get the number of sequences made so far
        String not = lines.get(1);
        int numberOfTurns = Integer.parseInt(not);
        // create the initial game state with any moves busted so far
        boolean[][] initialState = new boolean[numberOfMoves][numberOfMoves];
        int lastMove = -1;
        for (int i = 2; i < lines.size(); i++){
            Scanner scanner = new Scanner(lines.get(i));
            int moveA = scanner.nextInt();
            int moveB = scanner.nextInt();
            initialState[moveA][moveB] = true;
            initialState[moveB][moveA] = true;
            if (i == lines.size()-1){
                lastMove = moveB;
            }
        }
        // create the root node from the data
        Node root = new Node(numberOfMoves, initialState, lastMove, numberOfTurns);
        MinMax minmax = new MinMax();
        // evaluate min/max, get the solution, and print to screen
        minmax.miniMax(root);
        List<Node> solution = minmax.getSolution(root);
        for (int i = 1; i < solution.size(); i++){
            int moveA = solution.get(i).getDanceMove().getMoveA();
            int moveB = solution.get(i).getDanceMove().getMoveB();
            System.out.println("Move busted: " + moveA + " " + moveB);
        }
        if (solution.get(solution.size() - 1).whoseTurn() == MAX){
            System.out.println("Min is the winner.");
        }
        else {
            System.out.println("Max is the winner.");
        }
    }
    
}
