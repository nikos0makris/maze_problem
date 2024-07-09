import java.util.ArrayList;
import java.util.Stack;

public class Node {
    public int row,col;
    public int F,G,H;
    public String parentMove;
    
    public Node parent;
    public ArrayList<Node> neighbours = new ArrayList<Node>();
    
    //specify is the node node or block
    //its initially false which means normal node
    public boolean block = false;
    public boolean door = false;
    
    public Node() {}
    
    public Node (int r, int c, int h) {
        this.row = r;
        this.col = c;
        this.H = h;
        this.G = 0;
        this.F = this.H + this.G;
        this.parent = null;
    }
    
    public Node(Node p) {
        this.row = p.row;
        this.col = p.col;
        this.H = p.H;
        this.G = p.G;
        this.F = p.F;
        this.parent = p.parent;
        this.neighbours = p.neighbours;
        this.block = p.block;
        this.door = p.door;
    }
    
    public boolean compareStates(Node state) {  //standard function in which we compare the two states if the are not the same return false else return true
        if(this.row != state.row)				//we do that for the row and the column
            return false;
        else if(this.col != state.col)
            return false;
        return true;
    }
    
    public void calF() {					//Here we calculate the final cost
        this.F = this.H + this.G ;
    }
    
    public Stack<Node> retPath(){								//This is the function that return the path taken from start to goal 
        Stack<Node> reversedPath = new Stack<Node>();           //We use two stacks to do so collecting both the the nodes of the path 
        Stack<Node> path = new Stack<Node>();                   //as well as reversing the states and using an array list we collect the moves to get from start to goal
        
        //collecting the nodes of the path
        Node node = this;
        do {
            reversedPath.push(node);
            node = node.parent;
        }while(node.parent != null);
        reversedPath.push(node);
        
        //reversing the states in the stack
        while(!reversedPath.isEmpty())
            path.push(reversedPath.pop());
        
        //collecting the moves from start to goal
        ArrayList<String> moves = new ArrayList<>();
        for(Node state : path) {
            moves.add(state.parentMove);
            System.out.println("(" + state.row + "," + state.col + ")" + " - ");
            
        }
        System.out.println("");
        //display the Solution
        System.out.println("Cost = " + this.G);
        return path;
    }
}
