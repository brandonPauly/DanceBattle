/*
Brandon Pauly
CSC 380 - Assignment 2
Dr. Gemmell
*/
package minmax;

import java.util.ArrayList;
import java.util.List;
import node.Node;

/**
 *
 * @author Brandon
 * 
 * A class that handles all of the MiniMax logic and will give a solution to the input.
 */
public class MinMax {
    
    /*
    The solution to the Dance Battle
    */
    private List<Node> solution;
    
    /*
    A constant representing the contestant Max
    */
    private final boolean MAX = true;
    
    /*
    A method that takes one parameter, the root Node of the Dance Battle tree.  The algorithm recursively calls itself on
    each child node until a terminal leaf is reached.  The winner is determined in that leaf, then as the recursive calls return,
    the minimax analysis is backed up towards the root node.  
    */
    public void miniMax(Node node){
        // calls the function recursively on each Nodes children in a depth-first fashion
        if (node.hasChildren()){
            List<Node> children = node.getChildren();
            for (Node n : children){
                miniMax(n);
            }
        }
        // catches terminal case, applies value to leaf, then returns to begin the backing-up
        else {
            if (node.whoseTurn() == MAX){
                node.setUtility(-1);
            }
            else {
                node.setUtility(1);
            }
            return;
        }
        // if it is Max's turn, attempt to maximize
        if (node.whoseTurn() == MAX){
            List<Node> children = node.getChildren();
            for (Node c : children){
                if (c.getUtility() == 1){
                    node.setUtility(1);
                    return;
                }
            }
            node.setUtility(-1);
        }
        // if it is Min's turn, attempt to minimize
        else {
            List<Node> children = node.getChildren();
            for (Node c : children){
                if (c.getUtility() == -1){
                    node.setUtility(-1);
                    return;
                }
            }
            node.setUtility(1);
        }
    }
    
    /*
    Traverses back through the tree to find the solution to the problem and returns the list of Nodes
    that comprise the solution
    */
    public List<Node> getSolution(Node node){
        solution = new ArrayList();
        while(node.hasChildren()){
            solution.add(node);
            List<Node> children = node.getChildren();
            if (node.whoseTurn() == MAX){
                Node next = null;
                for (Node c : children){
                    if (c.getUtility() == 1){
                        next = c;
                        break;
                    }
                    else {
                        next = c;
                    }
                }
                node = next;
            }
            else {
                Node next = null;
                for (Node c : children){
                    if (c.getUtility() == -1){
                        next = c;
                        break;
                    }
                    else {
                        next = c;
                    }
                }
                node = next;
            }
        }
        solution.add(node);
        return solution;
    }
    
}
