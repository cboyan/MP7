import java.util.Scanner;
//import java.util.concurrent.TimeUnit;
//using the main class where the game is played and the game where the player is innitated 
public class Player {
	//the player name
		String name;
		//the board the player is shooting into
		Board hello;
		//the current board that player has
		Board second;
		
		//creates a player object. Takes two board objects so it can identify the board the player is shooting into (board1)
		//and the board that represents the players board (board2)
		public Player(String n, Board board, Board secondBoard) {
			name = n;
			hello = board;
			second = secondBoard;
		}
		//saves the name for easy access later
		public String getName() {
			return name;
		}
		//saves for easy access into the board the player is shooting into
		public Board getFirst() {
			return hello;
		}
		//saves for easy access of the players current board
		public Board getSecond() {
			return second;
		}
		
		//where the game starts
		public static void main (String args[]) {
			Scanner Keyboard = new Scanner(System.in);
			System.out.println("Welcome to battleship!");
			//will create a rule function that will read the rules if the user wants to
			System.out.print("Would you like to read the rules (1 for yes 0 for no)? ");
			int rule = Keyboard.nextInt();
			if (rule == 1) {
				Rules();
			}
			Board first;
			Board second;
			Board third;
			Board fourth;
			System.out.print("\nWould you like to use a standard 10x10 board, or customize a board?\n"
					+ "(1 for standard, 0 for custom)");
			int b = Keyboard.nextInt();
			if (b==1) {
			//creates the boards (first and second refer to player one and third and fourth refer to player two)
				first = new Board();
				second = new Board();
				third = new Board();
				fourth = new Board();
			} else {
				System.out.print("\nPlease input your dimension size");
				int a = Keyboard.nextInt();
				first = new Board(a);
				second = new Board(a);
				third = new Board(a);
				fourth = new Board(a);
			}
			//in a standard game the battle ship will have the following lengths
			//in an array for the simple reason that it is easier to access later when you want to acess the ships
			Battleship[] ships = new Battleship[]{new Battleship(2),new Battleship(3),new Battleship(3),new Battleship(4),new Battleship(5)};
			
			//takes the names of the players and creates the player objects with the correct boards
			//for some reason the input is only successfully taking the name of the player 2
		    //System.out.print("What is the name of player one");
			//String person1 = Keyboard.nextLine();
			//System.out.println(person1);
			System.out.println("");
			System.out.println("Name of Player 1: ");
			String n1 = Keyboard.next();
			System.out.println("\nName of Player 2: ");
			String n2 = Keyboard.next();
			Player contestent1 = new Player(n1, first, second);
			//System.out.println("What is the name of player two");
			//String person2 = Keyboard.nextLine();
			Player contestent2 = new Player(n2, third, fourth);
			
			//calls a method PlaceShips which places the ship where the user wants
			//have to call the method one by one for each ship
			//will try to make where it takes an array of ships and then can do all the placing at once
			System.out.println(contestent1.getName() + ", it's time to place your ships!");
			System.out.println(" ");
			PlaceShips(contestent1, ships[0]);
			PlaceShips(contestent1,ships[1]);
			PlaceShips(contestent1, ships[2]);
			PlaceShips(contestent1, ships[3]);
			PlaceShips(contestent1, ships[4]);
			System.out.println("");
			System.out.println("Here is your final board:");
			contestent1.second.printBoard();
			Wait(contestent1, contestent2);
			
			System.out.println(contestent2.getName() + ", it's time to place your ships!");
			PlaceShips(contestent2, ships[0]);
			PlaceShips(contestent2,ships[1]);
			PlaceShips(contestent2, ships[2]);
			PlaceShips(contestent2, ships[3]);
			PlaceShips(contestent2, ships[4]);
			System.out.println("");
			System.out.println("Here is your final board:");
			contestent2.second.printBoard();
			Wait(contestent2,contestent1);
			
			System.out.println("Now that all the ships are placed, let's start playing. " + contestent1.getName() + " will start!");
			Play(contestent1, contestent2);
			
			
		}
		public static void Play(Player player1, Player player2) {
			Scanner Keyboard = new Scanner(System.in);
			System.out.println(player1.getName() + ", it's your turn");
			boolean flag = true;
			while (flag) {
				System.out.println("");
				ShowBoards(player1);
				System.out.print("What x coordinate would you like to attack? ");
				int x = Keyboard.nextInt() - 1;
				System.out.println("");
				System.out.print("What y coordinate would you like to attack? ");
				int y = Keyboard.nextInt() - 1; 
				if (x < 0 || x >= player1.second.getDim() || y < 0 || y >= player2.second.getDim()) {
					System.out.println("Sorry your input is out of bounds for the board please re-enter");
					System.out.println("");
				}
				else if (player2.second.getBoard()[x][y] == '❌' || player2.second.getBoard()[x][y] == '✅') {
					System.out.println("Sorry you have already tried to fire at this location please choose a new location + \n");
					System.out.println("");
				}
				else {
					if (player2.getSecond().getBoard()[x][y] == '⚓') {
						flag = false;
						System.out.println("Wow you got a hit!");
						
						player2.getSecond().setPoint(x, y, '✅');
						player1.getFirst().setPoint(x, y, '✅');
					}
					else {
						flag = false;
						System.out.println("Sorry you missed");
						System.out.println(player2.second.getBoard()[x][y]);
	     				player2.getSecond().setPoint(x, y, '❌');
						player1.getFirst().setPoint(x, y, '❌');
					}
				}		
			}
			ShowBoards(player1);
			if (player1.getWinner(player2.second)) {
				System.out.println("Congrats, " + player1.getName() + ", you win!!");
				return;
			}
			Wait(player1, player2);
			
			System.out.println(player2.getName() + ", it's your turn!");
			boolean flag1 = true;
			while (flag1) {
				ShowBoards(player2);
				System.out.println("");
				System.out.print("What x coordinate would you like to attack? ");
				int x = Keyboard.nextInt() - 1;
				System.out.println("");
				System.out.print("What y corrdinate would you like to attack? ");
				int y = Keyboard.nextInt() - 1; 
				if (x < 0 || x >= player1.second.getDim() || y < 0 || y >= player2.second.getDim()) {
					System.out.println("Sorry, your input is out of bounds for the board. Please re-enter \n");
					System.out.println("");
				}
				else if (player1.second.getBoard()[x][y] == '❌' || player1.second.getBoard()[x][y] == '✅') {
					System.out.println("Sorry, you have already tried to fire at this location. Please choose a new location \n");
					System.out.println("");
				}
				else {
					flag1 = false;
					if (player1.second.getBoard()[x][y] == '⚓') {
						System.out.println("Wow you got a hit!");
						player1.second.setPoint(x, y, '✅');
						player2.hello.setPoint(x, y, '✅');
					}
					else  {
						System.out.println("Sorry you missed");
						System.out.println(player1.getSecond().getBoard()[x][y]);
						player1.second.setPoint(x, y, '❌');
						player2.hello.setPoint(x, y, '❌');
					}
				}		
			}
			ShowBoards(player2);
			if (player1.getWinner(player1.second)) {
				System.out.println("Congrats, " + player2.getName() + ", you win!!");
				return;
			}
			Wait(player2, player1);
			Play(player1, player2);
		}
		
		public boolean getWinner(Board board) {
			for (int i = 0; i < board.getBoard().length; i++) {
				for (int j = 0; j < board.getBoard()[0].length; j++) {
					if (board.getBoard()[i][j] == '⚓') {
						return false;
					}
				}
			}
			return true;
		}
		
		public static void Wait(Player player, Player player2) {
			Scanner Keyboard = new Scanner(System.in);
			System.out.println(player.name + " press any number to end your turn");
			Keyboard.nextInt();
			for (int i  = 0; i < 30; i++)
				System.out.println(" ");
			System.out.println(player.name + " your turn will end in");
			System.out.println("3");
			try { Thread.sleep(1000); }
			catch(InterruptedException ex) {Thread.currentThread().interrupt();}
			System.out.println("2");
			try { Thread.sleep(1000); }
			catch(InterruptedException ex) {Thread.currentThread().interrupt();}
			System.out.println("1");
			try { Thread.sleep(1000); }
			catch(InterruptedException ex) {Thread.currentThread().interrupt();}
			for (int i  = 0; i < 30; i++)
				System.out.println(" ");
			
			return;
		}
		
		//will simply print out the rules when called
		public static void Rules() {
			System.out.print("\nPlayers take turns firing shots (by making a location guess) to attack enemy ships.\n" + 
					"On your turn, choose an (x,y) point identifying a row and column on your target grid. "
					+ "\nA ❌ appears if there is no ship there or ✅ if you guessed a space that contained a ship.\n" + 
					"When one of your ships is hit, a ✅ appears at the location of the hit on your board."
					+ "\nThe ships are different sizes and have different numbers of space. When one of your ships "
					+ "\nhas every space occupied by ✅, your opponent has sunk your ship.\n" + 
					"\n" + 
					"The first player to sink all opposing ships wins the game.\n\n");
		}
		
		//method that places the ships
		public static void PlaceShips(Player player, Battleship ship) {
			Scanner Keyboard = new Scanner(System.in);
			// a flag because we need to keep asking for the placement of the ship until input is possible
			System.out.println("Here is your current board");
			player.second.printBoard();
			boolean flag = true;
			while (flag) {
				System.out.println(" ");
				//asking for the x and y coordinate for the head of the ship
				System.out.print("For the ship of size " + ship.getLength() + " what x cordinate do you want to place it? ");
				int y = Keyboard.nextInt() - 1; 
				System.out.println(" ");
				System.out.print("and for what y cordinate? ");
				int x = Keyboard.nextInt() - 1;
				//if x and y is out of the bounds of the board then error message will apear
				//and the user will have to input the data again until it is inside the bounds
				if (x < 0 || x >= 10 || y < 0 || y >= 10) {
					System.out.println("Sorry your input is incorrect plese re-enter");	
				}
				//if x and y is in bounds then we go to asking the orientation of the ship
				//for example if the user puts their coordinates as 2,3 and their orientation as North
				//then the head of the ship will be at 2,3 and the rest of the ship will be in the positive y direction
				else {
					System.out.println("What direction would you like to put your ship");
					System.out.println("1: South");
					System.out.println("2: North");
					System.out.println("3: East");
					System.out.println("4: West");
					int choice = Keyboard.nextInt();
					int solve = 0;
					//if the direction is West 
					if (choice == 1) {
						//this loop checks if all the values on the board that this ship would take up are in fact empty (marked by zero)
						//if any value is not zero (means already taken up by another ship then console forces to re-enter input
						if (x + ship.getLength() > 10) {
							System.out.println("Sorry that input is incorrect you have to re-enter");
						}
						else {
							for (int i = x; i < x + ship.getLength(); i++) {
								if (player.second.getBoard()[i][y] != '⚪' ) {
									System.out.println("Sorry that input is incorrect you have to re-enter");
									i = x + ship.getLength();
									solve = 1;
								}
							}
							//if all the values are in fact empty and the ship can be placed then the ship is placed successfully 
							for (int i = x; i < x + ship.getLength() && solve == 0; i++) {
								player.getSecond().setPoint(i, y, '⚓');
								//flag is set to false so while loops can terminate and input is not re-entered 
								flag = false;
							}
						}
					}
					//if the direction is East
					if (choice == 2) {
						//this loop checks if all the values on the board that this ship would take up are in fact empty (marked by zero)
						//if any value is not zero (means already taken up by another ship then console forces to re-enter input
						if (x - ship.getLength() < -1) {
							System.out.println("Sorry that input is incorrect you have to re-enter!!");
						}
						else {
							for (int i = x; i > x - ship.getLength(); i--) {
								if (player.second.getBoard()[i][y] != '⚪' )  {
									System.out.println("Sorry that input is incorrect you have to re-enter");
									i = 0;
									solve = 1;
								}
							}
							//if all the values are in fact empty and the ship can be placed then the ship is placed successfully
							for (int i = x; i > x - ship.getLength() && solve == 0; i--) {
								player.getSecond().setPoint(i, y, '⚓');
								//flag is set to false so while loops can terminate and input is not re-entered
								flag = false;
							}
						}
					}
					//if the direction is South
					if (choice == 3) {
						//this loop checks if all the values on the board that this ship would take up are in fact empty (marked by zero)
						//if any value is not zero (means already taken up by another ship then console forces to re-enter input
						if (y + ship.getLength() > 10) {
							System.out.println("Sorry that input is incorrect you have to re-enter");
						}
						else {
							for (int i = y; i < y + ship.getLength(); i++) {
								if (player.second.getBoard()[x][i] != '⚪' ) {
									System.out.println("Sorry that input is incorrect you have to re-enter");
									i = y + ship.getLength();
									solve = 1;
								}
									
							}
							//if all the values are in fact empty and the ship can be placed then the ship is placed successfully
							for (int i = y; i < y + ship.getLength() && solve == 0; i++) {
								player.getSecond().setPoint(x, i, '⚓');
								//flag is set to false so while loops can terminate and input is not re-entered
								flag = false;
							}
						}
					}
					//if the direction is North
					if (choice == 4) {
						//this loop checks if all the values on the board that this ship would take up are in fact empty (marked by zero)
						//if any value is not zero (means already taken up by another ship then console forces to re-enter input
						if (y - ship.getLength() < -1) {
							System.out.println("Sorry that input is incorrect you have to re-enter");
						}
						else {
							for (int i = y; i > y - ship.getLength(); i--) {
								if (player.second.getBoard()[x][i] != '⚪' )  {
									System.out.println("Sorry that input is incorrect you have to re-enter");
									i = 0;
									solve = 1;
								}
							}
							//if all the values are in fact empty and the ship can be placed then the ship is placed successfully
							for (int i = y; i > y - ship.getLength() && solve == 0; i--) {
								player.getSecond().setPoint(x, i, '⚓');
								//flag is set to false so while loops can terminate and input is not re-entered
								flag = false;
							}
					}
					}
				}
			}
			//lets the user know that the ship is succesfully placed
			System.out.println("ship placed");
		}
		
		//will show the boards at the start and the end of every turn so the user know where he or she has already shot
		//and so they can see where they have been hit
		public static void ShowBoards(Player player) {
			System.out.println("");
			System.out.println("Here is the board you are shooting into:");
			player.getFirst().printBoard();
			System.out.println("");
			System.out.println("Here is how your board is currently working:");
			player.getSecond().printBoard();
		}
}
