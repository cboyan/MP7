
public class Board {
	//will set the board as an array of ints
		char[][] rows;
		
		boolean custom;
		
		int dim;
		// a 10 by 10 board for a standard game
		public Board() {
			rows = new char[10][10];
			for (int i=0; i < 10; i++) {
				for (int j=0; j<10; j++) {
					rows[i][j] = '⚪';
				}
				
			}
			dim = 10;
			custom = false;
		}
		//possible can change the board dimesnsions if we do custom game
		public Board(int n) {
			rows = new char[n][n];
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					rows[i][j] = '⚪';
				}
			}
			dim = n;
			custom = true;
		}
		
		//returns the board
		public char[][] getBoard() {
			return rows;
		}
		
		//this sets a point on the board to a specfic value
		//for example if a player gets a hit then they can change that point from a zero to make the hit recoginizable
		public char[][] setPoint(int x, int y, char value) {
			rows[x][y] = value;
			return rows;
		}
		
		public int getDim() {
			return this.dim;
		}
		
		//prints the board so I can just call this function easily when I need to print out a board for the player
		public void printBoard() {
			for (int i = 0; i < getBoard().length; i++) {
				for (int j = 0; j < getBoard()[0].length; j++) {
					System.out.print(getBoard()[i][j] + " ");
				}
				System.out.println("");
			}
		}
}
