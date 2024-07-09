import java.util.ArrayList;
import java.util.Stack;


public class MazeSolution extends MazeProblem{
	
	public ArrayList<Node> frontier = new ArrayList<Node>();
	public ArrayList<Node> explored = new ArrayList<Node>();
	public Node node;
	public int pathCost;
	public long startTime,endTime,totalTime;
	
	
	
	public Stack<Node> IDS(MazeProblem problem){

		/*The Frontier list is an array list working as a stack*
		 * add(0,state)==> adding at first element   ***********
		 * remove(0) ==> removing the first element   **********
		 * So its a stack with LIFO (Last in First out)   ******
		 *******************************************************
		 *The DFS chooses the last state in the frontier *******
		 * It works by expanding the state and test the leafs */

        startTime = System.currentTimeMillis();
        node = problem.initialState;

        //push a state at the top of the Frontier
        frontier.add(node);

        while(true) {
            //test if there is no more states to be tested
            if(frontier.isEmpty()) {
                System.out.println("There is no Solution..");
                return null;
            }

            // pop a state from the top of the frontier
            node = frontier.remove(0);

            //test for the goal
            if(problem.goalTest(node)) {
                System.out.println("For the Iterative Deepening Search:");
                endTime = System.currentTimeMillis();
                totalTime = (endTime-startTime);
                System.out.println("Time taken = " + totalTime + " mSec ");
                System.out.println("The Explored States = " + explored.size());
                return node.retPath();
            }

            //add the node to the explored
            explored.add(node);

            /*Here we want to collect the moves 
			 *that the algorithm used to get to the goal state 
			 *and update the Frontier in the process*/
            ArrayList<String> moves = problem.moves(node);

            for (String move : moves ) {
                Node child = problem.transition(node, move);
                updateFrontier(child,move,problem);
            }

        }

    }
	public Stack<Node> UCS(MazeProblem problem){
        
		/***********************************************
		 ***   For the Frontier is a normal arraylist **
		 ***   the UCS chooses the state of the       **
		 ***   lowest cost from the frontier          **
		 **********************************************/
        
        startTime = System.currentTimeMillis();
        node = problem.initialState;
        
        //add the state to the frontier
        frontier.add(node);
        
        while(true) {
            //test if there is no more states t obe tested
            if(frontier.isEmpty()) {
                System.out.println("There is no solution");
                return null;
            }
            
            //choosing the state of the smallest G in the frontier
            int G = Integer.MAX_VALUE; //a big number
            int indexOfState=-1;// the index of the state in the Frontier
            for(Node state : frontier) {
                if(state.G<G) {
                    indexOfState = frontier.indexOf(state);
                    G=state.G;
                }
            }
            node = frontier.remove(indexOfState);
            
            //test for the goal
            if(problem.goalTest(node)) {
                System.out.println("For the Uniform Cost Search:");
                endTime = System.currentTimeMillis();
                totalTime = (endTime-startTime);
                System.out.println("Time taken = " + totalTime + " mSec ");
                System.out.println("The Explored States = " + explored.size());    
                return node.retPath();
            }
            
            //add the node to the explored
            explored.add(node);
            
            ArrayList<String> moves = problem.moves(node);
            
            for (String move : moves ) {
                Node child = problem.transition(node, move);
                updateFrontier(child,move,problem);
            }
            
        }
        
    }
	public Stack<Node> Astar(MazeProblem problem){
		
		
		/*What A* Search Algorithm does is that at each step it picks the node according to a value-�f�
		 *which is a parameter equal to the sum of two other parameters � �g� and �h�. 
		 *At each step it picks the node/cell having the lowest �f�, and process that node.
		 *We define �g� and �h� as simply as possible below
		 *g = the movement cost to move from the starting point to a given square on the grid, following the path generated to get there.
		 *h = the estimated movement cost to move from that given square on the grid to the final destination. 
		 *This is often referred to as the heuristic, which is nothing but a kind of smart guess. 
		 *We really don�t know the actual distance until we find the path, because all sorts of things can be in the way (walls, door, etc.)*/

		startTime = System.currentTimeMillis();
		node = problem.initialState;
		
		//add the state to the frontier
		frontier.add(node);
		
		while(true) {
			//test if there is no more states to be tested
			if(frontier.isEmpty()) {
				System.out.println("There is no solution");
				return null;
			}
			
			int F=Integer.MAX_VALUE;
			int indexOfState=-1;
			
			for(Node state : frontier) {
				if(state.F<F) {
					indexOfState = frontier.lastIndexOf(state);
					F=state.F;
				}
				
				
			}
			
			node = frontier.remove(indexOfState);
			
			if(problem.goalTest(node)) {
				System.out.println("For the Astar Search:");
				endTime = System.currentTimeMillis();
				totalTime = (endTime-startTime);
				System.out.println("Time taken = " + totalTime + " mSec ");
				System.out.println("The Explored States = " + explored.size());
				
				return node.retPath();
			}
			
			//add the node to the explored
			explored.add(node);
			
			ArrayList<String> moves = problem.moves(node);
			
			for (String move : moves ) {
				Node child = problem.transition(node, move);
				updateFrontier(child,move,problem);
			}
			
		}
			
	}
	
	/*This function tests if the child state is either in the Frontier or Explored List 
	 * If its already in either one of them then returns true and break 
	 * If its not in none of them , we add it in the frontier list */
	
	public ArrayList<Node> updateFrontier(Node child, String move, MazeProblem problem){
		boolean found = false;
		
		//test the child state if it's in the Frontier
		for(Node state: frontier) {
			if(child.compareStates(state)) {
				if(child.G<state.G) 
					frontier.remove(state);
				else
					found =true;
				break;
			}
		}
		
		//test the child state if it's in the Explered
		for(Node state: explored) {
			if(child.compareStates(state)) {
				found=true; break;
			}
		}
		
		//if child isn't found in the Lists Frontier or Explored
		//then add it to the frontier and update it's parent and cost
		if(!found) {
			problem.cost(node, move);
			// adding the child State at the end of the frontier
			frontier.add(child);
		}
		return null;
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MazeProblem problem = new MazeProblem();
		Stack<Node> solution = new Stack<>();
		System.out.println("The Maze Before The Solution: ");
		System.out.println("----------------------------------");
		problem.displayMaze(solution);
		
		
		System.out.println("-----------------------------------");
		solution = new MazeSolution().IDS(problem);
		problem.displayMaze(solution);
		
		System.out.println("-----------------------------------");
		solution = new MazeSolution().UCS(problem);
		problem.displayMaze(solution);
		
		System.out.println("-----------------------------------");
		solution = new MazeSolution().Astar(problem);
		problem.displayMaze(solution);
		

	}

}
