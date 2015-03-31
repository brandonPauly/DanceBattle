/*
Brandon Pauly
CSC 380 - Assignment 2
Dr. Gemmell
*/
package node;

import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author Brandon
 * 
 * Node class representing the different gameStates that are possible.  A root node is initially created, then each child node is
 * created when the parent node is complete.  Together representing the entire tree of possible move sequences in the Dance Battle.
 */
public class Node {
    
    /*
    The possible game states that can be reached from this Node
    */
    private ArrayList<Node> children;
    
    /*
    The number of possible moves that can be busted in the Dance Battle
    */
    private static int numberOfMoves;
    
    /*
    The state of all move sequences busted in the game so far
    */
    private boolean[][] gameState;
    
    /*
    The last move busted in the previous turn
    */
    private int lastMove;
    
    /*
    A boolean flag representing whose turn it is.  Max = true, Min = false
    */
    private boolean turn;
    
    /*
    A constant representing the contestant Max
    */
    private static final boolean MAX = true;
    
    /*
    A constant representing the contestant Min
    */
    private static final boolean MIN = false;
    
    /*
    The utility value of the node for the minimax algorithm
    */
    private int utility;
    
    /*
    The dance move sequence that was busted this turn
    */
    private DanceMove danceMove;
    
    /*
    The total number of nodes in the tree
    */
    private static int totalNodes;
    
    
    /*
    Constructor for the root node of the search tree.  Takes an int representing the
    number of moves that can be busted, an initial game state in the form of a two dimensional boolean array, 
    an int representing the last move busted in the last turn (-1 if no turns were taken previously), and an int 
    representing the number of turns taken thus far.
    */
    public Node(int nom, boolean[][] gs, int lm, int tt){
        children = new ArrayList();
        numberOfMoves = nom;
        gameState = gs;
        lastMove = lm;
        if (tt % 2 == 0){
            turn = MAX;
        }
        else {
            turn = MIN;
        }
        totalNodes++;
        successor();
    }
    
    /*
    Constructor for the children nodes of any particular node in the search tree.  Takes a Node reference to
    its parent node, a two dimensional boolean array representing the game state, an int representing the last
    move that was busted in the last turn, a boolean representing whose turn it is, and a DanceMove object that holds
    the two int values of the moves busted this turn.
    */
    public Node(Node parent, boolean[][] gs, int lm, boolean t, DanceMove dm){
        children = new ArrayList();
        gameState = gs;
        lastMove = lm;
        turn = t;
        danceMove = dm;
        totalNodes++;
        successor();
    }
    
    /*
    A public inner class that just holds two integer values representing the
    pair of moves that were busted in this turn.
    */
    public class DanceMove {
        
        /*
        The first move busted in the turn
        */
        private int moveA;
        
        /*
        The second move busted in the turn
        */
        private int moveB;
        
        /*
        Constructor for the DanceMove class.  Takes an int a for the first move
        of the turn, and an int b for the second move of the turn.
        */
        public DanceMove(int a, int b){
            moveA = a;
            moveB = b;
        }
        
        /*
        Accessor for the first move
        */
        public int getMoveA(){
            return moveA;
        }
        
        /*
        Accessor for the second move
        */
        public int getMoveB(){
            return moveB;
        }
    }
    
    /*
    Successor function that is called from each Node's constructor to build the tree.  The 
    function goes through and creates children for each possible sequence of moves that can be 
    executed from this state of the game.
    */
    public void successor(){
        if (lastMove == -1){
            for (int i = 0; i < numberOfMoves; i++){
                for (int j = 0; j < numberOfMoves; j++){
                    boolean[][] nextState = new boolean[numberOfMoves][numberOfMoves];
                    nextState[i][j] = true;
                    nextState[j][i] = true;
                    Node child = new Node(this, nextState, j, !turn, new DanceMove(i, j));
                    children.add(child);
                }
            }
        }
        else {
            
            for (int i = 0; i < numberOfMoves; i++){
                boolean[][] nextState = new boolean[numberOfMoves][numberOfMoves];
                for (int j = 0; j < numberOfMoves; j++){
                    for (int k = 0; k < numberOfMoves; k++){
                        if (gameState[j][k]){
                            nextState[j][k] = true;
                        }
                    }
                }
                if (!nextState[lastMove][i]){
                    nextState[lastMove][i] = true;
                    nextState[i][lastMove] = true;
                    Node child = new Node(this, nextState, i, !turn, new DanceMove(lastMove, i));
                    children.add(child);
                }
            }
            
        }  
    }
    
    /*
    Accessor method to return the utility value of this node, or the min/max value
    */
    public int getUtility(){
        return utility;
    }
    
    /*
    Setter method to set the utility value of this node, or the min/max value after analysis
    */
    public void setUtility(int u){
        utility = u;
    }
    
    /*
    Return true if this Node has children, in other words if this is a terminal game state
    */
    public boolean hasChildren(){
        return !children.isEmpty();
    }
    
    /*
    Returns true if this Node's turn goes to Max, false if it is Min
    */
    public boolean whoseTurn(){
        return turn;
    }
    
    /*
    Returns a list of all children of this Node
    */
    public List<Node> getChildren(){
        return children;
    }
    
    /*
    Accessor method to get the DanceMove object for this turn
    */
    public DanceMove getDanceMove(){
        return danceMove;
    }
    
    /*
    Accessor to retrieve the static value of all nodes created in the tree that this Node
    belongs to
    */
    public int getTotalNodes(){
        return totalNodes;
    }
}
