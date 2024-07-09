import java.util.ArrayList;
//import java.util.Random;
import java.util.Stack;

public class MazeProblem
{
	private final String UP = "up";
	private final String DOWN = "down";
	private final String LEFT = "left";
	private final String RIGHT = "right";
	private final int mazeLength = 13;
	private final int mazeHeight = 11;

	public Node[][] maze = new Node[mazeHeight][mazeLength];
	public Node initialState;
	public Node goalState = new Node();
	public Node D ,X,Empty;

	public int sRow,sCol;
	public int gRow,gCol;

	/*In this function we create a maze 
	 * that places in random rows and columns either doors , walls , or just normal nodes 
	 * we do that by checking for each random state (state = [row][col]) if it isn't already a wall or a door 
	 * and if the state isn't the start or goal */
	public MazeProblem()
	{
		//Initializing the maze nodes...
		for(int row=0; row<mazeHeight; row++){	
			for(int col=0; col<mazeLength; col++){

			//Calculating the Heuristic of each node
			double length = Math.pow((col-gCol),2);
			double base = Math.pow((row-gRow),2);

			int state_H = (int) (Math.sqrt(length+base)*10);
			maze[row][col] = new Node(row,col,state_H);
			}
		}

		//Randomize doors and blocks nodes
		//Random rand = new Random();

		//Specify the initial node in the maze
		sRow = 0;		
		sCol = 0;
		initialState = maze[sRow][sCol];

		//Specify the goal node in the maze
		gRow = 10;		
		gCol = 12;
		goalState = maze[gRow][gCol];
		/*
		 * this code section generates a random maze, instead of the standard maze given, and is fully fuctional in case you want to check it  
		//Specifying the nodes in the maze
		int numOfBlocks = 49 ;// (int) (mazeLength*mazeHeight*0.4);

		for(int i=0; i<numOfBlocks; i++)
		{
			int row = rand.nextInt(mazeHeight);
			int col = rand.nextInt(mazeLength);

			if(!maze[row][col].block || !maze[row][col].compareStates(initialState) || !maze[row][col].compareStates(goalState))
			{
				maze[row][col].block = true;
			}											//If in the particular maze[row][col] there is already a wall or the start or the goal 
														//decrease i because we want to have as many walls as we initialized ,in our case 49
			else 
				i--;	
		}
		
		int numOfDoors = 14 ;//(int) (mazeLength*mazeHeight*0.2);
		for(int i=0; i<numOfDoors; i++)
		{
			int row = rand.nextInt(mazeHeight);
			int col = rand.nextInt(mazeLength);

			if(!maze[row][col].door || !maze[row][col].compareStates(initialState) || !maze[row][col].compareStates(goalState) || !maze[row][col].block )
			{
				maze[row][col].door = true;
			}
			else 
			i--;						//If in the particular maze[row][col] there is already a door , a wall or the start or the goal 
						 				//decrease i because we want to have as many walls as we initialized ,in our case 14	
		}*/ 
		/*putting the doors and the walls in the maze grid, manually*/
		
		//row 0--------------------
		maze[0][2].block = true;
		maze[0][7].block = true;
		maze[0][8].block = true;
		maze[0][10].block = true;
		maze[0][12].block = true;
		//row 1--------------------
		maze[1][3].door = true;
		maze[1][4].block = true;
		maze[1][10].door = true;
		maze[1][12].block = true;
		//row 2--------------------
		maze[2][0].block = true;
		maze[2][2].block = true;
		maze[2][7].block = true;
		maze[2][8].block = true;
		maze[2][10].block = true;
		maze[2][12].block = true;
		//row 3--------------------
		maze[3][2].door = true;
		maze[3][3].block = true;
		maze[3][5].door = true;
		maze[3][6].block = true;
		maze[3][9].door = true;
		//row 4--------------------
		maze[4][0].block = true;
		maze[4][1].block = true;
		maze[4][4].block= true;
		maze[4][8].block = true;
		maze[4][9].block = true;
		maze[4][10].block = true;
		maze[4][11].block = true;
		//row 5--------------------
		maze[5][1].door = true;
		maze[5][3].block = true;
		maze[5][5].block= true;
		maze[5][8].block = true;
		maze[5][9].block = true;
		//row 6--------------------
		maze[6][0].block = true;
		maze[6][2].door = true;
		maze[6][3].block= true;
		maze[6][5].block = true;
		maze[6][6].block = true;
		maze[6][7].door = true;
		maze[6][10].door = true;
		maze[6][11].block= true;
		maze[6][12].door = true;
		//row 7--------------------
		maze[7][1].block = true;
		maze[7][4].door = true;
		maze[7][8].block= true;
		//row 8--------------------
		maze[8][2].door = true;
		maze[8][5].block = true;
		maze[8][6].block= true;
		maze[8][8].block = true;
		maze[8][9].block = true;
		maze[8][10].block = true;
		maze[8][11].block = true;
		//row 9--------------------
		maze[9][0].block = true;
		maze[9][1].block = true;
		maze[9][6].block = true;
		maze[9][12].door = true;
		//row 10--------------------
		maze[10][0].block = true;
		maze[10][1].block = true;
		maze[10][3].block= true;
		maze[10][4].block = true;
		maze[10][7].door = true;
		maze[10][8].block = true;
		maze[10][9].block = true;
		maze[10][10].block = true;
				
		
	}
	
	/*This is the function to define the moves that can be made
	 * if it is not a wall or if it is a door , we proceed collecting the moves*/
	
	public ArrayList<String> moves(Node state)
	{
		ArrayList<String> moves = new ArrayList<String>();

		if(state.row+1<mazeHeight){
			if(!maze[state.row+1][state.col].block || maze[state.row+1][state.col].door)
				moves.add(DOWN);		
		}
		if(state.row-1>=0){
			if(!maze[state.row-1][state.col].block || maze[state.row-1][state.col].door)
				moves.add(UP);		
		}
		if(state.col+1<mazeLength){
			if(!maze[state.row][state.col+1].block || maze[state.row][state.col+1].door)
				moves.add(LEFT);		
		}
		if(state.col-1>=0){
			if(!maze[state.row][state.col-1].block || maze[state.row][state.col-1].door)
				moves.add(RIGHT);		
		}
		return moves;
	}
	
	/*This is the function used to make the transitions in the maze
	 * we check each time the move made by the algorithm
	 * */
	
	public Node transition(Node state,String move) 
	{
		if(move.equals(RIGHT))
		{
			maze[state.row][state.col].neighbours.add(maze[state.row][state.col-1]);
			return maze[state.row][state.col-1];
		}
		else if(move.equals(LEFT))
		{
			maze[state.row][state.col].neighbours.add(maze[state.row][state.col+1]);
			return maze[state.row][state.col+1];
		}
		else if(move.equals(DOWN))
		{
			maze[state.row][state.col].neighbours.add(maze[state.row+1][state.col]);
			return maze[state.row+1][state.col];
		}
		else if(move.equals(UP))
		{
			maze[state.row][state.col].neighbours.add(maze[state.row-1][state.col]);
			return maze[state.row-1][state.col];
		}
		else
			return null;
	}

	public boolean goalTest(Node state)										//Here we check if we reached the goal state 
	{
		if((goalState.row == state.row) && (goalState.col == state.col))
			return true;
		else
			return false;
	}

	public int cost(Node state , String move)										/*In this function we define the cost of the move that was made, 
																					*if with the move we hit a door then the cost is +2 ,	
																					*if its a normal node, the cost is +1 */
	{
		if(move.equals(RIGHT))
		{
			if(maze[state.row][state.col-1].door == true) {
			maze[state.row][state.col-1].parent = maze[state.row][state.col];
			maze[state.row][state.col-1].G = maze[state.row][state.col].G +2;
			maze[state.row][state.col-1].calF();								//update the final cost after each move 
			}
			else
			{
				maze[state.row][state.col-1].parent = maze[state.row][state.col];
				maze[state.row][state.col-1].G = maze[state.row][state.col].G +1;
				maze[state.row][state.col-1].calF();
			}
		}
		else if(move.equals(LEFT))
		{
			if(maze[state.row][state.col+1].door == true) 
			{
				maze[state.row][state.col+1].parent = maze[state.row][state.col];
				maze[state.row][state.col+1].G = maze[state.row][state.col].G +2;
				maze[state.row][state.col+1].calF();
				}
				else
				{
					maze[state.row][state.col+1].parent = maze[state.row][state.col];
					maze[state.row][state.col+1].G = maze[state.row][state.col].G +1;
					maze[state.row][state.col+1].calF();
				}
		}
		else if(move.equals(DOWN))
		{
			if(maze[state.row+1][state.col].door == true) {
				maze[state.row+1][state.col].parent = maze[state.row][state.col];
				maze[state.row+1][state.col].G = maze[state.row][state.col].G +2;
				maze[state.row+1][state.col].calF();
				}
				else
				{
					maze[state.row+1][state.col].parent = maze[state.row][state.col];
					maze[state.row+1][state.col].G = maze[state.row][state.col].G +1;
					maze[state.row+1][state.col].calF();
				}
		}
		else if(move.equals(UP))
		{
			if(maze[state.row-1][state.col].door == true) {
				maze[state.row-1][state.col].parent = maze[state.row][state.col];
				maze[state.row-1][state.col].G = maze[state.row][state.col].G +2;
				maze[state.row-1][state.col].calF();
				}
				else
				{
					maze[state.row-1][state.col].parent = maze[state.row][state.col];
					maze[state.row-1][state.col].G = maze[state.row][state.col].G +1;
					maze[state.row-1][state.col].calF();
				}
		}
		
		return 1;
	}
	
	/*we use this function to display the full maze. 
	 * we check if the path is not empty and if so then 
	 * we start displaying every state of the maze using two loops
	 * and every time we compare the states to see if it's either a wall, a door , the start or the goal state 
	 * we also display the path taken */
	
	
	public void displayMaze(Stack<Node> path)
	{
		if(path!=null){
			for(int row=0; row<mazeHeight; row++){
				for(int col=0; col<mazeLength; col++){
					if(maze[row][col].compareStates(initialState))
						System.out.print("S");
					else if(maze[row][col].compareStates(goalState))
						System.out.print("G");
					else if(path.contains(maze[row][col])){
						System.out.print("*");
					}
					else if(maze[row][col].door)
						System.out.print("D");
					else if(maze[row][col].block)
						System.out.print("X");
					else
						System.out.print(" ");
					
					}
				System.out.println();
			}
		}	
	}

    public Node[][] getMaze() {
        return maze;
    }
}

